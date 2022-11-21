package com.reactor.movie.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Movie {

    private MovieInfo movieInfo;
    private List<MovieReview> reviews;
}
