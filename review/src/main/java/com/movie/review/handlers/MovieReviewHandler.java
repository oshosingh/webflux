package com.movie.review.handlers;

import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.movie.review.dto.MovieReviewDto;
import com.movie.review.entity.MovieReview;
import com.movie.review.exceptions.ReviewDataException;
import com.movie.review.repo.MovieReviewRepo;
import com.movie.review.util.MovieReviewMapper;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class MovieReviewHandler {

	@Autowired
	private MovieReviewRepo movieReviewRepo;

	@Autowired
	private MovieReviewMapper movieReviewMapper;

	@Autowired
	private Validator validator;

	public Mono<ServerResponse> addReview(ServerRequest request) {
		return request.bodyToMono(MovieReviewDto.class)
//				.doOnNext(this::validate)
				.map(movieReviewMapper::movieReviewEntityFromDto).flatMap(movieReviewRepo::save)
				.map(movieReviewMapper::movieReviewDtoFromEntity)
				.flatMap(ServerResponse.status(HttpStatus.CREATED)::bodyValue);
	}

	public Mono<ServerResponse> getReview() {
		Flux<MovieReviewDto> reviews = movieReviewRepo.findAll().map(movieReviewMapper::movieReviewDtoFromEntity);
		return ServerResponse.ok().body(reviews, MovieReviewDto.class);
	}

	public Mono<ServerResponse> updateReview(ServerRequest request) {
		String reviewId = request.pathVariable("id");
		var existingReview = movieReviewRepo.findById(reviewId);
	
		return existingReview.
				flatMap(review -> request.bodyToMono(MovieReviewDto.class)
						.map(updateRequest -> {
							review.setComment(updateRequest.getComment());
							review.setRating(updateRequest.getRating());
							return review;
						})
						.flatMap(movieReviewRepo::save)
						.flatMap(savedReview -> ServerResponse.ok().bodyValue(savedReview))
				);
	}
	
	public Mono<ServerResponse> deleteReview(ServerRequest request) {
		String reviewId = request.pathVariable("id");
		var existingReview = movieReviewRepo.findById(reviewId);
		
		return existingReview
			.flatMap(review -> movieReviewRepo.deleteById(reviewId))
			.then(ServerResponse.noContent().build());
	}

	private void validate(MovieReviewDto reviewDto) throws ReviewDataException {
		var constraintViolations = validator.validate(reviewDto);
		log.info("Constraint Violations : {}", constraintViolations);

		if (constraintViolations.size() > 0) {
			var errorMessage = constraintViolations.stream().map(ConstraintViolation::getMessage).sorted()
					.collect(Collectors.joining(","));
			throw new ReviewDataException(errorMessage);
		}
	}

}
