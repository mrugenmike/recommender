package com.pronet.recommender;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
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

    @Bean
    MongoTemplate getMongoTemplate(){
        return new MongoTemplate(new SimpleMongoDbFactory(mongoClient(),mongoDatabase));
    }

    @Bean
    MongoClient mongoClient(){
        return new MongoClient(mongoHost,mongoPort);
    }
}
