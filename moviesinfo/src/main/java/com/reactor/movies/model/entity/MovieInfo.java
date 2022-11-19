package com.reactor.movies.model.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@Accessors(chain = true)
@Document
@NoArgsConstructor
public class MovieInfo {
	@Id
	private String movieInfoId;
	private String name;
	private Integer year;
	private List<String> cast;
	private String releaseDate;
}
