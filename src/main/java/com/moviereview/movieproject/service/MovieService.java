package com.moviereview.movieproject.service;

import com.moviereview.movieproject.model.Movies;
import com.moviereview.movieproject.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MovieService {
    @Autowired
    private MovieRepository movieRepo;

    public List<Movies> allMovies(){

        System.out.println(movieRepo.findAll().toString());

        return movieRepo.findAll();
    }

    public Movies findMovieByImdbId(String imdbId){
        return movieRepo.findByImdbId(imdbId);
    }

    public Movies findMovieByTitle(String title){
        return movieRepo.findByTitle(title);
    }
}
