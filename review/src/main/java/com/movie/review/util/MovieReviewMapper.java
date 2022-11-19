package com.movie.review.util;

import com.movie.review.dto.MovieReviewDto;
import com.movie.review.entity.MovieReview;

public interface MovieReviewMapper {
	
	MovieReview movieReviewEntityFromDto(MovieReviewDto reviewDto);
	MovieReviewDto movieReviewDtoFromEntity(MovieReview review);

}
