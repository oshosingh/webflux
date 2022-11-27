package com.reactor.movie.webclients;

import com.reactor.movie.dto.MovieInfo;
import com.reactor.movie.exceptions.MovieInfoClientException;
import com.reactor.movie.exceptions.MovieInfoServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class MovieInfoClient {

    @Autowired
    private WebClient webClient;

    @Value("${rest.client.movieinfourl}")
    private String movieInfoBaseUrl;

    @Autowired
    private ReactiveStringRedisTemplate redisTemplate;

    public Mono<MovieInfo> getMovieInfoById(String movieId) {
        String url = movieInfoBaseUrl.concat("/{id}");

        return webClient
                .get()
                .uri(url, movieId)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, clientResponse -> {
                    if(clientResponse.statusCode() == HttpStatus.NOT_FOUND) {
                        return Mono.error(new MovieInfoClientException("MovieId not found", clientResponse.statusCode().value()));
                    }
                    return Mono.error(new MovieInfoClientException("MovieInfo Client Exception", clientResponse.statusCode().value()));
                })
                .onStatus(HttpStatus::is5xxServerError, clientResponse -> {
                    return Mono.error(new MovieInfoServerException("Server not responsing", clientResponse.statusCode().value()));
                })
                .bodyToMono(MovieInfo.class);
    }
}
