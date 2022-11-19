package com.movie.review.routers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.movie.review.handlers.MovieReviewHandler;

@Configuration
public class ReviewRouter {
	
	@Bean
	public RouterFunction<ServerResponse> movieReviewRouter(MovieReviewHandler movieReviewHandler) {
		return RouterFunctions
				.route()
				.GET("/v1/health", (request) -> ServerResponse.ok().bodyValue("Healthy"))
				.GET("/v1/review", (request) -> movieReviewHandler.getReview())
				.POST("/v1/review", (request) -> movieReviewHandler.addReview(request))
				.PUT("/v1/review/{id}", (request) -> movieReviewHandler.updateReview(request))
				.build();
	}

}
