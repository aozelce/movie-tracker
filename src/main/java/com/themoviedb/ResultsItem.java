package com.themoviedb;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

/**
 * The type Results item.
 */
@JsonIgnoreProperties(ignoreUnknown = true) //If TMDB adds new fields, don't break my app.
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ResultsItem {

	@JsonProperty("overview")
	private String overview;

	@JsonProperty("original_language")
	private String originalLanguage;

	@JsonProperty("original_title")
	private String originalTitle;

	@JsonProperty("video")
	private boolean video;

	@JsonProperty("title")
	private String title;

	@JsonProperty("genre_ids")
	private List<Integer> genreIds;

	@JsonProperty("poster_path")
	private String posterPath;

	@JsonProperty("backdrop_path")
	private String backdropPath;

	@JsonProperty("release_date")
	private String releaseDate;

	@JsonProperty("popularity")
	private Object popularity;

	@JsonProperty("vote_average")
	private Object voteAverage;

	@JsonProperty("id")
	private int id;

	@JsonProperty("adult")
	private boolean adult;

	@JsonProperty("vote_count")
	private int voteCount;

	@JsonProperty("media_type")
	private String mediaType;

	@JsonProperty("name")
	private String name;

	@JsonProperty("genres")
	private String genres;

	@JsonProperty("first_air_date")
	private String firstAirDate;
}