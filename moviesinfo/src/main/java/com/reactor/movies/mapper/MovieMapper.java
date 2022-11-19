package com.reactor.movies.mapper;

import com.reactor.movies.model.MovieInfoDto;
import com.reactor.movies.model.entity.MovieInfo;

import reactor.core.publisher.Mono;

public interface MovieMapper {
	Mono<MovieInfo> getMovieInfoEntity(MovieInfoDto movieInfoDto);
	MovieInfoDto getMovieInfoDtoFromEntity(MovieInfo movieInfo);
}
