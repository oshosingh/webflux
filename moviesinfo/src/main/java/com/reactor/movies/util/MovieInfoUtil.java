package com.reactor.movies.util;

import org.springframework.stereotype.Service;

import com.reactor.movies.model.MovieInfoDto;
import com.reactor.movies.model.entity.MovieInfo;

@Service
public class MovieInfoUtil {
	
	public void copyProperties(MovieInfoDto movieInfoDto, MovieInfo movieInfo) {
		
		if(movieInfoDto.getCast() != null) movieInfo.setCast(movieInfoDto.getCast());
		if(movieInfoDto.getName() != null) movieInfo.setName(movieInfoDto.getName());
		if(movieInfoDto.getReleaseDate() != null) movieInfo.setReleaseDate(movieInfoDto.getReleaseDate());
		if(movieInfoDto.getYear() != null) movieInfo.setYear(movieInfoDto.getYear());
	}
	
}
