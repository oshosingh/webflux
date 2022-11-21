package com.movie.review.repo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.movie.review.entity.MovieReview;

import reactor.core.publisher.Flux;

public interface MovieReviewRepo extends ReactiveMongoRepository<MovieReview, String>{

	 Flux<MovieReview> findByMovieInfoId(String movieInfoId);

}
