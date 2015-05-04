package com.recommender;

import com.mongodb.*;
import com.mongodb.hadoop.MongoInputFormat;
import org.apache.hadoop.conf.Configuration;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.mllib.fpm.FPGrowth;
import org.apache.spark.mllib.fpm.FPGrowthModel;
import org.bson.BSONObject;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import scala.Serializable;
import scala.Tuple2;

import java.net.UnknownHostException;
import java.util.List;
import java.util.stream.Collectors;

@DisallowConcurrentExecution
public class EventCruncher implements org.quartz.Job, Serializable {

    static JavaSparkContext sparkContext = SparkContextFactory.getInstance();
    final static MongoProperties props = new MongoProperties();
    static void  doRun(){

        final Configuration mongodbConfig = new Configuration();
        mongodbConfig.set("mongo.job.input.format", "com.mongodb.hadoop.MongoInputFormat");
        mongodbConfig.set("mongo.input.uri",String.format("mongodb://%s:%d/%s.%s",props.getHost(),props.getPort(),props.getDB(),props.getInputCollection()));

        final JavaPairRDD<Object, BSONObject> javaPairRDD = sparkContext.newAPIHadoopRDD(mongodbConfig, MongoInputFormat.class, Object.class, BSONObject.class);

        final JavaRDD<List<String>> transactions = javaPairRDD.map(new Function<Tuple2<Object, BSONObject>, List<String>>() {
            @Override
            public List<String> call(Tuple2<Object, BSONObject> v1) throws Exception {
                final BasicDBList career = (BasicDBList) v1._2().get("career");
                return career.subList(0, career.size() - 1).stream().map(c -> {
                    return ((BSONObject) c).get("title").toString();
                }).distinct().collect(Collectors.<String>toList());
            }
        });
        final long start = System.currentTimeMillis();
        System.out.println("Starting time for Spark FPGrowth Execution: "+ start);
        FPGrowth fpg = new FPGrowth()
                .setMinSupport(0.0)
                .setNumPartitions(10);
        FPGrowthModel<String> model = fpg.run(transactions);
        final long stop = System.currentTimeMillis();
        System.out.println(String.format("Total time taken for fpgowth is %d seconds ",(stop-start)));
        final MongoClient mongoClient;
        try {
            mongoClient = props.getMongoClient();
            final DB db = mongoClient.getDB(props.getDB());
            final DBCollection collection = db.getCollection(props.getOutputCollection());
            collection.remove(new BasicDBObject());
            for (FPGrowth.FreqItemset<String> itemset : model.freqItemsets().toJavaRDD().collect()) {
                    collection.insert(new BasicDBObject("frequency", itemset.freq()).append("paths", itemset.javaItems()));
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }



    }
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        EventCruncher.doRun();
    }
}
