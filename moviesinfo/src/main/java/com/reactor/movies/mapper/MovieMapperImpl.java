package com.reactor.movies.mapper;

import org.springframework.stereotype.Service;

import com.reactor.movies.model.MovieInfoDto;
import com.reactor.movies.model.entity.MovieInfo;

import reactor.core.publisher.Mono;

@Service
public class MovieMapperImpl implements MovieMapper{

	@Override
	public Mono<MovieInfo> getMovieInfoEntity(MovieInfoDto movieInfoDto) {
		MovieInfo movieInfo = new MovieInfo();
		movieInfo.setCast(movieInfoDto.getCast());
		movieInfo.setName(movieInfoDto.getName());
		movieInfo.setReleaseDate(movieInfoDto.getReleaseDate());
		movieInfo.setYear(movieInfoDto.getYear());
		return Mono.just(movieInfo);
	}

	@Override
	public MovieInfoDto getMovieInfoDtoFromEntity(MovieInfo movieInfo) {
		MovieInfoDto movieInfoDto = new MovieInfoDto();
		movieInfoDto.setCast(movieInfo.getCast());
		movieInfoDto.setMovieInfoId(movieInfo.getMovieInfoId());
		movieInfoDto.setName(movieInfo.getName());
		movieInfoDto.setReleaseDate(movieInfo.getReleaseDate());
		movieInfoDto.setYear(movieInfo.getYear());
		return movieInfoDto;
	}

}
