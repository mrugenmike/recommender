package com.pronet.recommender.career;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
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

    final CareerRecommendationService service;
    @Autowired
    public CareerResource(CareerRecommendationService service) {
        this.service = service;
    }

    @RequestMapping(value = "/{currentPosition}/{desiredPosition}",method = RequestMethod.GET)
    List<Document> getCareerPathRecommendation(@PathVariable()String currentPosition,@PathVariable()String desiredPosition){
        return service.fetchRecommendations(currentPosition,desiredPosition);
    }
}
