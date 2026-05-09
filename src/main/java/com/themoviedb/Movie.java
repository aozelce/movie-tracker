package com.themoviedb;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

/**
 * The type Movie.
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Movie {

	@JsonProperty("page")
	private int page;

	@JsonProperty("total_pages")
	private int totalPages;

	@JsonProperty("results")
	private List<ResultsItem> results;

	@JsonProperty("total_results")
	private int totalResults;
}