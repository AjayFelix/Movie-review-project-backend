package com.moviereview.movieproject.service;

import com.moviereview.movieproject.model.Movies;
import com.moviereview.movieproject.model.Review;
import com.moviereview.movieproject.repository.ReviewRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepo;

    @Autowired
    private MongoTemplate mongoTemplate;


    public Review saveReview (String imdbId ,String body){
        Review review = reviewRepo.insert(new Review(body));

        mongoTemplate.update(Movies.class)
                .matching(Criteria.where("imdbId").is(imdbId))
                .apply(new Update().push("reviewIds").value(review)).first();


        return review;
    }
    public String getBodyById(ObjectId id){

        Optional<Review> byId = reviewRepo.findById(id);

        Review review = byId.get();

        return review.getBody();

    }
}
