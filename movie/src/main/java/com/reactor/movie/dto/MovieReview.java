package com.reactor.movie.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class MovieReview {
    private String reviewId;
    private String movieInfoId;
    private String comment;
    private Double rating;
}
