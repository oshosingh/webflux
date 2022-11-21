package com.reactor.movie.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class MovieInfo {
    private String movieInfoId;
    private String name;
    private Integer year;
    private List<String> cast;
    private String releaseDate;
}
