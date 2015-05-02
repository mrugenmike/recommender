package com.recommender;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;

public class SparkContextFactory {
    static JavaSparkContext sparkContext;

    public static JavaSparkContext getInstance(){
        if(sparkContext==null){
            final SparkConf sparkConf = new SparkConf().setAppName("Pronet-Recommendation").
                    setMaster("local[2]").
                    set("spark.akka.heartbeat.interval", "10000").set("spark.akka.failure-detector.threshold","3000")
                    .set("spark.akka.heartbeat.pauses", "60000").set("spark.akka.timeout","1000");
            sparkContext = new JavaSparkContext(sparkConf);
            return sparkContext;
        }else{
            return sparkContext;
        }
    }
}
