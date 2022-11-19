package com.movie.review.util;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.movie.review.dto.MovieReviewDto;
import com.movie.review.entity.MovieReview;

@Service
public class MovieReviewMapperImpl implements MovieReviewMapper{

	@Override
	public MovieReview movieReviewEntityFromDto(MovieReviewDto reviewDto) {
		MovieReview review = new MovieReview();
		BeanUtils.copyProperties(reviewDto, review);
		return review;
	}

	@Override
	public MovieReviewDto movieReviewDtoFromEntity(MovieReview review) {
		MovieReviewDto reviewDto = new MovieReviewDto();
		BeanUtils.copyProperties(review, reviewDto);
		return reviewDto;
	}

}
