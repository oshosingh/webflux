package com.reactor.movie.service;

import java.util.List;

import com.reactor.movie.ratelimiter.UserRateLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reactor.movie.dto.Movie;
import com.reactor.movie.dto.MovieReview;
import com.reactor.movie.webclients.MovieInfoClient;
import com.reactor.movie.webclients.MovieReviewClient;

import reactor.core.publisher.Mono;

@Service
public class MovieService {

    @Autowired
    private MovieInfoClient movieInfoClient;

    @Autowired
    private MovieReviewClient movieReviewClient;

    public Mono<Movie> findMovieById(String movieId) {
        return movieInfoClient.getMovieInfoById(movieId)
                .flatMap(movieInfo -> {
                    Mono<List<MovieReview>> movieReviewMono = movieReviewClient.getReviewsByMovieId(movieId)
                            .collectList();

                    return movieReviewMono.map(movieReviews -> new Movie(movieInfo, movieReviews));
                }).log();
    }
}
