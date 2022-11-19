package com.reactor.movies.repo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.reactor.movies.model.entity.MovieInfo;

public interface MovieInfoRepo extends ReactiveMongoRepository<MovieInfo, String>{

}
