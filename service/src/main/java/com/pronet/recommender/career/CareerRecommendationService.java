package com.pronet.recommender.career;

import com.mongodb.MongoClient;
import org.apache.hadoop.conf.Configuration;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.bson.BSONObject;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;

@Component
public class CareerRecommendationService {
    private  MongoClient client;
    //private  JavaSparkContext sc;
    //private  Configuration mongoConfig;

    public CareerRecommendationService(){}

    @Autowired
    public CareerRecommendationService(MongoClient client){
        this.client = client;
       /* SparkConf conf = new SparkConf().setAppName("Recommendation").setMaster("spark://Mrugens-MacBook-Pro.local:7077");
        JavaSparkContext sc = new JavaSparkContext(conf);
        this.sc = sc;*/
      //  this.mongoConfig = null;
    }


    public List<Document> fetchRecommendations(String currentPosition, String desiredPosition) {
        Configuration mongodbConfig = new Configuration();
        mongodbConfig.set("mongo.job.input.format", "com.mongodb.hadoop.MongoInputFormat");
        mongodbConfig.set("mongo.input.uri","mongodb://localhost:27017/pronet.careerPath");
        SparkConf conf = new SparkConf().setAppName("Recommendation").setMaster("spark://Mrugens-MacBook-Pro.local:7077");
        JavaSparkContext sc = new JavaSparkContext(conf);
       JavaPairRDD<Object, BSONObject> documents = sc.newAPIHadoopRDD(mongodbConfig,com.mongodb.hadoop.MongoInputFormat.class, Object.class, BSONObject.class);
        final Map<Object, BSONObject> objectBSONObjectMap = documents.collectAsMap();
        for(Object object:objectBSONObjectMap.keySet()){
            System.out.println("Key: "+object.getClass().getName());
        }
        return null;
    }









}
