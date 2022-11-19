package com.reactor.movies.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.reactor.movies.model.MovieInfoDto;
import com.reactor.movies.service.MovieInfoService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1")
public class MovieInfoController {
	
	@Autowired
	private MovieInfoService movieInfoService;
	
	@PostMapping("/movieinfos")
	@ResponseStatus(HttpStatus.CREATED)
	public Mono<MovieInfoDto> addMovieInfo(@RequestBody MovieInfoDto movieInfoDto) {
		return movieInfoService.saveMovieInfo(movieInfoDto);
	}
	
	@GetMapping("/movieinfos")
	public Flux<MovieInfoDto> getMovieInfo() {
		return movieInfoService.getMovieInfo();
	}
	
	@GetMapping("/movieinfos/{movieId}")
	public Mono<MovieInfoDto> getMovieInfoById(@PathVariable String movieId) {
		return movieInfoService.getMovieInfoById(movieId);
	}
	
	@PutMapping("/movieinfos/{movieId}")
	public Mono<ResponseEntity<MovieInfoDto>> updateMovieById(@RequestBody MovieInfoDto movieInfoDto, @PathVariable String movieId) {
		return movieInfoService.updateMovieByIdAlt(movieInfoDto, movieId);
	}
	
	@DeleteMapping("/movieinfos/{movieId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public Mono<Void> deleteMovieInfoById(@PathVariable String movieId) {
		return movieInfoService.deleteMovieInfoById(movieId);
	}
}
