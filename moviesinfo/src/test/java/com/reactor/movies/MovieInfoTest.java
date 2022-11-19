package com.reactor.movies;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.reactor.movies.model.MovieInfoDto;
import com.reactor.movies.model.entity.MovieInfo;
import com.reactor.movies.repo.MovieInfoRepo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebClient
public class MovieInfoTest {
	
	@Autowired
	private MovieInfoRepo movieInfoRepo;
	
	@Autowired
	private WebTestClient webClientTest;
	
	static String MOVIE_INFO_URL = "/v1/movieinfos";
	
	@BeforeEach
	void setup() {
		
	}
	
	@AfterEach
	void tearDown() {
		
	}
	
	@Test
	void addMovieInfo() {
		 var movieInfoDto = new MovieInfoDto("Batman",2005, List.of("ABC", "XYZ"), "2006-06-16", null);
		 
		 webClientTest.post()
		 	.uri(MOVIE_INFO_URL)
		 	.bodyValue(movieInfoDto)
		 	.exchange()
		 	.expectStatus()
		 	.isCreated();
//		 	.expectBody(MovieInfo.class)
//		 	.consumeWith(movieInfoExchangeresult -> {
//		 		var result = movieInfoExchangeresult.getResponseBody();
//		 		
//		 	});
	}
	
}
