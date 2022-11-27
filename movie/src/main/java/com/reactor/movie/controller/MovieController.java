package com.reactor.movie.controller;

import com.reactor.movie.ratelimiter.UserRateLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reactor.movie.dto.Movie;
import com.reactor.movie.service.MovieService;

import reactor.core.publisher.Mono;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/v1/movie")
public class MovieController {
    @Autowired
    private MovieService movieService;

    @GetMapping("/{id}")
    Mono<Movie> getMovieById(@PathVariable("id") String movieId) {
        return movieService.findMovieById(movieId);
    }

    @GetMapping("/rate/limiter/test")
    void rateLimitTest() {
        int threadCount = 12;
        ExecutorService executors = Executors.newFixedThreadPool(threadCount);
        UserRateLimiter userRateLimiter = new UserRateLimiter(0);
        for(int i=0; i<threadCount; i++) {
            executors.execute(() -> userRateLimiter.isAllowed(0));
        }
    }

}
