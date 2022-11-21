package com.reactor.movie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reactor.movie.dto.Movie;
import com.reactor.movie.service.MovieService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/movie")
public class MovieController {
    @Autowired
    private MovieService movieService;

    @GetMapping("/{id}")
    Mono<Movie> getMovieById(@PathVariable("id") String movieId) {
        return movieService.findMovieById(movieId);
    }

}
