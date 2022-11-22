package com.reactor.movie.webclients;

import com.reactor.movie.dto.MovieReview;
import com.reactor.movie.exceptions.MovieInfoClientException;
import com.reactor.movie.exceptions.MovieInfoServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class MovieReviewClient {

    @Autowired
    private WebClient webClient;

    @Value("${rest.client.reviewurl}")
    private String reviewBaseUrl;

    public Flux<MovieReview> getReviewsByMovieId(String movieId) {
        String url = reviewBaseUrl.concat("/{id}");

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
                .bodyToFlux(MovieReview.class);
    }

}
