package com.reactor.movie.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.redis.core.RedisHash;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@ToString
@RedisHash("movie")
public class Movie {

    private MovieInfo movieInfo;
    private List<MovieReview> reviews;
}
