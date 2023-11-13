package com.moviereview.movieproject.controller;

import com.moviereview.movieproject.model.Review;
import com.moviereview.movieproject.service.ReviewService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@CrossOrigin("http://localhost:3000/")
@RestController
@RequestMapping("/review")

public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/user/createreview")
    private ResponseEntity<Review> createReview(@RequestBody Map<String,String> payload){

        return new ResponseEntity<Review>(reviewService.saveReview(payload.get("imdbId"),payload.get("body")), HttpStatus.OK);

    }

    @GetMapping("/getReview/{id}")
    private ResponseEntity<?> getReviewbody(@PathVariable ObjectId id){

        return  new ResponseEntity<String>(reviewService.getBodyById(id), HttpStatus.OK);
    }



}
