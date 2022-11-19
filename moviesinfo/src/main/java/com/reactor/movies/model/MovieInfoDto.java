package com.reactor.movies.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MovieInfoDto {
	private String name;
	private Integer year;
	private List<String> cast;
	private String releaseDate;
	private String movieInfoId;
}
