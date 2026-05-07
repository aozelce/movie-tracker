package com.themoviedb;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The type Results item.
 */
@JsonIgnoreProperties(ignoreUnknown = true) //If TMDB adds new fields, don’t break my app.
public class ResultsItem{

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

	/**
	 * Set overview.
	 *
	 * @param overview the overview
	 */
	public void setOverview(String overview){
		this.overview = overview;
	}

	/**
	 * Get overview string.
	 *
	 * @return the string
	 */
	public String getOverview(){
		return overview;
	}

	/**
	 * Set original language.
	 *
	 * @param originalLanguage the original language
	 */
	public void setOriginalLanguage(String originalLanguage){
		this.originalLanguage = originalLanguage;
	}

	/**
	 * Get original language string.
	 *
	 * @return the string
	 */
	public String getOriginalLanguage(){
		return originalLanguage;
	}

	/**
	 * Set original title.
	 *
	 * @param originalTitle the original title
	 */
	public void setOriginalTitle(String originalTitle){
		this.originalTitle = originalTitle;
	}

	/**
	 * Get original title string.
	 *
	 * @return the string
	 */
	public String getOriginalTitle(){
		return originalTitle;
	}

	/**
	 * Set video.
	 *
	 * @param video the video
	 */
	public void setVideo(boolean video){
		this.video = video;
	}

	/**
	 * Is video boolean.
	 *
	 * @return the boolean
	 */
	public boolean isVideo(){
		return video;
	}

	/**
	 * Set title.
	 *
	 * @param title the title
	 */
	public void setTitle(String title){
		this.title = title;
	}

	/**
	 * Get title string.
	 *
	 * @return the string
	 */
	public String getTitle(){
		return title;
	}

	/**
	 * Set genre ids.
	 *
	 * @param genreIds the genre ids
	 */
	public void setGenreIds(List<Integer> genreIds){
		this.genreIds = genreIds;
	}

	/**
	 * Get genre ids list.
	 *
	 * @return the list
	 */
	public List<Integer> getGenreIds(){
		return genreIds;
	}

	/**
	 * Set poster path.
	 *
	 * @param posterPath the poster path
	 */
	public void setPosterPath(String posterPath){
		this.posterPath = posterPath;
	}

	/**
	 * Get poster path string.
	 *
	 * @return the string
	 */
	public String getPosterPath(){
		return posterPath;
	}

	/**
	 * Set backdrop path.
	 *
	 * @param backdropPath the backdrop path
	 */
	public void setBackdropPath(String backdropPath){
		this.backdropPath = backdropPath;
	}

	/**
	 * Get backdrop path string.
	 *
	 * @return the string
	 */
	public String getBackdropPath(){
		return backdropPath;
	}

	/**
	 * Set release date.
	 *
	 * @param releaseDate the release date
	 */
	public void setReleaseDate(String releaseDate){
		this.releaseDate = releaseDate;
	}

	/**
	 * Get release date string.
	 *
	 * @return the string
	 */
	public String getReleaseDate(){
		return releaseDate;
	}

	/**
	 * Set popularity.
	 *
	 * @param popularity the popularity
	 */
	public void setPopularity(Object popularity){
		this.popularity = popularity;
	}

	/**
	 * Get popularity object.
	 *
	 * @return the object
	 */
	public Object getPopularity(){
		return popularity;
	}

	/**
	 * Set vote average.
	 *
	 * @param voteAverage the vote average
	 */
	public void setVoteAverage(Object voteAverage){
		this.voteAverage = voteAverage;
	}

	/**
	 * Get vote average object.
	 *
	 * @return the object
	 */
	public Object getVoteAverage(){
		return voteAverage;
	}

	/**
	 * Set id.
	 *
	 * @param id the id
	 */
	public void setId(int id){
		this.id = id;
	}

	/**
	 * Get id int.
	 *
	 * @return the int
	 */
	public int getId(){
		return id;
	}

	/**
	 * Set adult.
	 *
	 * @param adult the adult
	 */
	public void setAdult(boolean adult){
		this.adult = adult;
	}

	/**
	 * Is adult boolean.
	 *
	 * @return the boolean
	 */
	public boolean isAdult(){
		return adult;
	}

	/**
	 * Set vote count.
	 *
	 * @param voteCount the vote count
	 */
	public void setVoteCount(int voteCount){
		this.voteCount = voteCount;
	}

	/**
	 * Get vote count int.
	 *
	 * @return the int
	 */
	public int getVoteCount(){
		return voteCount;
	}

	/**
	 * Set media type.
	 *
	 * @param mediaType the media type
	 */
	public void setMediaType(String mediaType){
		this.mediaType = mediaType;
	}

	/**
	 * Get media type string.
	 *
	 * @return the string
	 */
	public String getMediaType(){
		return mediaType;
	}

	/**
	 * Set name.
	 *
	 * @param name the name
	 */
	public void setName(String name){
		this.name = name;
	}

	/**
	 * Get name string.
	 *
	 * @return the string
	 */
	public String getName(){
		return name;
	}

	/**
	 * Sets genres.
	 *
	 * @param genres the genres
	 */
	public void setGenres(String genres) {
        this.genres = genres;
    }

	/**
	 * Gets genres.
	 *
	 * @return the genres
	 */
	public String getGenres() {
        return genres;
    }

	/**
	 * Set first air date.
	 *
	 * @param firstAirDate the first air date
	 */
	public void setFirstAirDate(String firstAirDate) {
        this.firstAirDate = firstAirDate;
    }

	/**
	 * Get first air date string.
	 *
	 * @return the string
	 */
	public String getFirstAirDate() {
        return firstAirDate;
    }

	@Override
 	public String toString(){
		return 
			"ResultsItem{" + 
			"overview = '" + overview + '\'' + 
			",original_language = '" + originalLanguage + '\'' + 
			",original_title = '" + originalTitle + '\'' + 
			",video = '" + video + '\'' + 
			",title = '" + title + '\'' + 
			",genre_ids = '" + genreIds + '\'' + 
			",poster_path = '" + posterPath + '\'' + 
			",backdrop_path = '" + backdropPath + '\'' + 
			",release_date = '" + releaseDate + '\'' + 
			",popularity = '" + popularity + '\'' + 
			",vote_average = '" + voteAverage + '\'' + 
			",id = '" + id + '\'' + 
			",adult = '" + adult + '\'' + 
			",vote_count = '" + voteCount + '\'' + 
			",media_type = '" + mediaType + '\'' + 
			",name = '" + name + '\'' + 
			"}";
		}
}