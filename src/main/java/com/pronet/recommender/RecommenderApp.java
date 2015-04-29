package com.pronet.recommender;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by mrugen on 4/28/15.
 */
@SpringBootApplication
public class RecommenderApp {
    public static void main(String[] args) {
        SpringApplication.run(RecommenderConfig.class,args);
    }
}
