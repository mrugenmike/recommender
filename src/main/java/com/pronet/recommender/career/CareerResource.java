package com.pronet.recommender.career;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by mrugen on 4/28/15.
 */
@RestController
@RequestMapping("/api/v1/career")
public class CareerResource {

    @Autowired
    CareerRecommendationService service;
    @RequestMapping(method = RequestMethod.GET)
    List<Document> getCareerPathRecommendation(){
        return service.fetchRecommendations();
    }
}
