package com.movie.review.repo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.movie.review.entity.MovieReview;

public interface MovieReviewRepo extends ReactiveMongoRepository<MovieReview, String>{

}
