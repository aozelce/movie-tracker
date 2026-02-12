package com.aozelce.entity;

/**
 * The type Media.
 *
 * @author aozelce
 */
public class Media {

    private int id;
    private int tmdbId;
    private  String title;
    private String mediaType;
    private int year;
    private String posterPath;
    private String overview;
    private String genres;


    /**
     * Instantiates a new Media.
     */
    public Media() {
    }

    /**
     * Instantiates a new Media.
     *
     * @param id         the id
     * @param tmdbId     the tmdb id
     * @param title      the title
     * @param mediaType  the media type
     * @param year       the year
     * @param posterPath the poster path
     * @param overview   the overview
     * @param genres     the genres
     */
    public Media(int id, int tmdbId, String title, String mediaType, int year, String posterPath, String overview, String genres) {
        this.id = id;
        this.tmdbId = tmdbId;
        this.title = title;
        this.mediaType = mediaType;
        this.year = year;
        this.posterPath = posterPath;
        this.overview = overview;
        this.genres = genres;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets tmdb id.
     *
     * @return the tmdb id
     */
    public int getTmdbId() {
        return tmdbId;
    }

    /**
     * Sets tmdb id.
     *
     * @param tmdbId the tmdb id
     */
    public void setTmdbId(int tmdbId) {
        this.tmdbId = tmdbId;
    }

    /**
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets title.
     *
     * @param title the title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets media type.
     *
     * @return the media type
     */
    public String getMediaType() {
        return mediaType;
    }

    /**
     * Sets media type.
     *
     * @param mediaType the media type
     */
    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    /**
     * Gets year.
     *
     * @return the year
     */
    public int getYear() {
        return year;
    }

    /**
     * Sets year.
     *
     * @param year the year
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * Gets poster path.
     *
     * @return the poster path
     */
    public String getPosterPath() {
        return posterPath;
    }

    /**
     * Sets poster path.
     *
     * @param posterPath the poster path
     */
    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    /**
     * Gets overview.
     *
     * @return the overview
     */
    public String getOverview() {
        return overview;
    }

    /**
     * Sets overview.
     *
     * @param overview the overview
     */
    public void setOverview(String overview) {
        this.overview = overview;
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
     * Sets genres.
     *
     * @param genres the genres
     */
    public void setGenres(String genres) {
        this.genres = genres;
    }

    @Override
    public String toString() {
        return "Media{" +
                "id=" + id +
                ", tmdbId=" + tmdbId +
                ", title='" + title + '\'' +
                ", mediaType='" + mediaType + '\'' +
                ", year=" + year +
                ", posterPath='" + posterPath + '\'' +
                ", overview='" + overview + '\'' +
                ", genres='" + genres + '\'' +
                '}';
    }
}
