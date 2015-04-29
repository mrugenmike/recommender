package com.pronet.recommender.career;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mrugen on 4/28/15.
 */
@Component
public class CareerRecommendationService {
    @Autowired
    MongoClient client;
    public List<Document> fetchRecommendations() {
        final MongoDatabase pronet = client.getDatabase("pronet");
        final FindIterable<Document> companies = pronet.getCollection("companies").find();
        List<Document> data = new ArrayList<Document>();
        for (Document company:companies){
            data.add(company);
        }
        return data;
    }
}
