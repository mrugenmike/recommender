package com.pronet.recommender;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RecommenderApp {
    public static void main(String[] args) {
        SpringApplication.run(RecommenderConfig.class,args);
    }
}
