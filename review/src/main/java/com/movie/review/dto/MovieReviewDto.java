package com.movie.review.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MovieReviewDto {
	private String reviewId;
	private String movieInfoId;
	private String comment;
	private Double rating;
}
