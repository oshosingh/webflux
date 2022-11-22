package com.reactor.movies.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.reactor.movies.mapper.MovieMapper;
import com.reactor.movies.model.MovieInfoDto;
import com.reactor.movies.model.entity.MovieInfo;
import com.reactor.movies.repo.MovieInfoRepo;
import com.reactor.movies.util.MovieInfoUtil;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@Service
public class MovieInfoService {

	@Autowired
	private MovieInfoRepo movieInfoRepo;

	@Autowired
	private MovieMapper movieMapper;

	@Autowired
	private MovieInfoUtil movieInfoUtil;
	
	private Sinks.Many<MovieInfo> movieFluxSink = Sinks.many().replay().all();

	public Mono<MovieInfoDto> saveMovieInfo(MovieInfoDto movieInfoDto) {
		return movieMapper.getMovieInfoEntity(movieInfoDto).flatMap(movieInfoRepo::save)
				.doOnNext(movieFluxSink::tryEmitNext)
				.map(movieMapper::getMovieInfoDtoFromEntity).log();
	}

	public Flux<MovieInfoDto> getMovieInfo() {
		return movieInfoRepo.findAll().map(movieMapper::getMovieInfoDtoFromEntity).log();
	}

	public Mono<MovieInfoDto> getMovieInfoById(String movieId) {
		return movieInfoRepo.findById(movieId).map(movieMapper::getMovieInfoDtoFromEntity);
	}

	public Mono<MovieInfoDto> updateMovieById(MovieInfoDto movieInfoDto, String movieId) {
		return movieInfoRepo.findById(movieId).flatMap(movieInfo -> {
			movieInfoUtil.copyProperties(movieInfoDto, movieInfo);
			return movieInfoRepo.save(movieInfo).map(movieMapper::getMovieInfoDtoFromEntity);
		});
	}

	public Mono<ResponseEntity<MovieInfoDto>> updateMovieByIdAlt(MovieInfoDto movieInfoDto, String movieId) {
		return movieInfoRepo.findById(movieId)
				.flatMap(movieInfo -> {
					movieInfoUtil.copyProperties(movieInfoDto, movieInfo);
					return movieInfoRepo.save(movieInfo).map(movieMapper::getMovieInfoDtoFromEntity);
				})
				.map(updatedMovieInfo -> ResponseEntity.ok().body(updatedMovieInfo))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}

	public Mono<Void> deleteMovieInfoById(String movieId) {
		return movieInfoRepo.deleteById(movieId);
	}

	public Flux<String> movieStream() {
		Flux<String> movieInfoFlux = movieFluxSink.asFlux().map(MovieInfo::toString);
		return movieInfoFlux;
	}
}
