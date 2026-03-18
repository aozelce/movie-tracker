package com.themoviedb;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The type Movie.
 */
public class Movie{

	@JsonProperty("page")
	private int page;

	@JsonProperty("total_pages")
	private int totalPages;

	@JsonProperty("results")
	private List<ResultsItem> results;

	@JsonProperty("total_results")
	private int totalResults;

	/**
	 * Set page.
	 *
	 * @param page the page
	 */
	public void setPage(int page){
		this.page = page;
	}

	/**
	 * Get page int.
	 *
	 * @return the int
	 */
	public int getPage(){
		return page;
	}

	/**
	 * Set total pages.
	 *
	 * @param totalPages the total pages
	 */
	public void setTotalPages(int totalPages){
		this.totalPages = totalPages;
	}

	/**
	 * Get total pages int.
	 *
	 * @return the int
	 */
	public int getTotalPages(){
		return totalPages;
	}

	/**
	 * Set results.
	 *
	 * @param results the results
	 */
	public void setResults(List<ResultsItem> results){
		this.results = results;
	}

	/**
	 * Get results list.
	 *
	 * @return the list
	 */
	public List<ResultsItem> getResults(){
		return results;
	}

	/**
	 * Set total results.
	 *
	 * @param totalResults the total results
	 */
	public void setTotalResults(int totalResults){
		this.totalResults = totalResults;
	}

	/**
	 * Get total results int.
	 *
	 * @return the int
	 */
	public int getTotalResults(){
		return totalResults;
	}

	@Override
 	public String toString(){
		return 
			"Movie{" + 
			"page = '" + page + '\'' + 
			",total_pages = '" + totalPages + '\'' + 
			",results = '" + results + '\'' + 
			",total_results = '" + totalResults + '\'' + 
			"}";
		}
}