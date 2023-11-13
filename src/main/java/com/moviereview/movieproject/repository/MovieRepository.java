package com.moviereview.movieproject.repository;

import com.moviereview.movieproject.MovieprojectApplication;
import com.moviereview.movieproject.model.Movies;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends MongoRepository<Movies , ObjectId> {

    public Movies findByImdbId(String imdbId);

    public Movies findByTitle(String title);


}
