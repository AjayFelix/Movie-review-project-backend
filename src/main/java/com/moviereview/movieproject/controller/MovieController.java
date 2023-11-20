package com.moviereview.movieproject.controller;

import com.moviereview.movieproject.model.ErrorClass;
import com.moviereview.movieproject.model.Movies;
import com.moviereview.movieproject.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
@CrossOrigin("http://localhost:3000/")
@RestController
@RequestMapping("/api/rrcritics/movie")

public class MovieController {

    @Autowired
    private MovieService movieSvc;

    @GetMapping("/public/allmovies")
    public ResponseEntity<?> getAllMovies() {


        try {
            List<Movies> allmovies = movieSvc.allMovies();
            if (allmovies.isEmpty()) {
                ErrorClass error = new ErrorClass(200, "There is no movies in the list");
                return new ResponseEntity<ErrorClass>(error, HttpStatus.OK);
            } else {
                return new ResponseEntity<List<Movies>>(allmovies, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/public/id/{imdbId}")
    public ResponseEntity<?> getMovieByImdbId(@PathVariable String imdbId) {


        try {
            Movies movie = movieSvc.findMovieByImdbId(imdbId);
            if (movie == null) {

                ErrorClass error = new ErrorClass(200, "there is no movie in this particular ImdbId");

                return new ResponseEntity<ErrorClass>(error, HttpStatus.OK);

            } else {
                return new ResponseEntity<Movies>(movie, HttpStatus.OK);
            }

        } catch (Exception e) {

            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @GetMapping("/public/title/{title}")
    public ResponseEntity<?> getMoviesByTitle(@PathVariable String title) {

        try {
            Movies movie = movieSvc.findMovieByTitle(title);
            if (movie == null) {

                ErrorClass error = new ErrorClass(200, "there is no movie in this particular title");

                return new ResponseEntity<ErrorClass>(error, HttpStatus.OK);

            } else {
                return new ResponseEntity<Movies>(movie, HttpStatus.OK);
            }
        } catch (Exception e) {

            return new ResponseEntity<String>(e.getMessage(), HttpStatus.OK);

        }

    }
    @PostMapping("/public/createMovie")
    public ResponseEntity<?> createMovie(@RequestBody Movies newMovie) {

        try {
            movieSvc.saveOrUpdateMovie(newMovie);
            return  new ResponseEntity<String>("given movie updated", HttpStatus.OK);
        } catch (Exception e) {

            return new ResponseEntity<String>(e.getMessage(), HttpStatus.OK);

        }



    }






}
