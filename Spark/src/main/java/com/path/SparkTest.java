package com.path;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.mllib.fpm.FPGrowth;
import org.apache.spark.mllib.fpm.FPGrowthModel;

import java.util.ArrayList;

public class SparkTest {

    public static void main(String[] args) {


        SparkConf sparkConf = new SparkConf().setAppName("Test").setMaster("local[2]").set("spark.akka.heartbeat.interval","10000").set("spark.akka.failure-detector.threshold","3000")
                .set("spark.akka.heartbeat.pauses","60000").set("spark.akka.timeout","1000");
        JavaSparkContext sparkContext = new JavaSparkContext(sparkConf);
        JavaRDD<ArrayList<String>> transactions = sparkContext.textFile("/Users/neerajakukday/Downloads/spark.txt").map(
                new Function<String, ArrayList<String>>() {
                    @Override
                    public ArrayList<String> call(String s) {
                        return Lists.newArrayList(s.split(" "));
                    }
                }
        );
        FPGrowth fpg = new FPGrowth()
                .setMinSupport(0.2)
                .setNumPartitions(10);
        FPGrowthModel<String> model = fpg.run(transactions);

        for (FPGrowth.FreqItemset<String> itemset : model.freqItemsets().toJavaRDD().collect()) {
            System.out.println("[" + Joiner.on(",").join(itemset.javaItems()) + "], " + itemset.freq());
        }
    }
}
