package com.movie.review.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Document
@Getter
@Setter
@ToString
public class MovieReview {
	
	@Id
	private String reviewId;
	private String movieInfoId;
	private String comment;
	private Double rating;
	
}
