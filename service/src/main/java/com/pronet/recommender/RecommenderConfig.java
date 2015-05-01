package com.pronet.recommender;

import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.net.UnknownHostException;

@org.springframework.context.annotation.Configuration
@EnableAutoConfiguration
@ComponentScan
@EnableScheduling
public class RecommenderConfig {
    @Value("${spring.data.mongodb.host}")
    String mongoHost;

    @Value("${spring.data.mongodb.port}")
    int mongoPort;

    @Value("${spring.data.mongodb.database}")
    String mongoDatabase;

    @Value("${spark.master.url}")
    String sparkUrl;

    @Value("${spring.data.mongodb.collection}")
    String mongoCollection;

   /* @Bean
    Configuration getMongoConfig(){
        Configuration mongodbConfig = new Configuration();
        mongodbConfig.set("mongo.job.input.format", "com.mongodb.hadoop.MongoInputFormat");
        mongodbConfig.set("mongo.input.uri","mongodb://"+mongoHost+":"+mongoPort+"/"+mongoDatabase+"."+mongoCollection);
        return mongodbConfig;
    }*/

    @Bean
    MongoClient mongoClient() throws UnknownHostException {
        return new MongoClient(mongoHost,mongoPort);
    }

    /*@Bean(name = {"sparkContext"})
    JavaSparkContext getJavaSparkContext(){
        SparkConf conf = new SparkConf().setAppName("Recommendation").setMaster(sparkUrl);
        JavaSparkContext sc = new JavaSparkContext(conf);
        return sc;
    }*/

}
