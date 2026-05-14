package com.aozelce.service;

import com.aozelce.controller.AddRecommendation;
import com.aozelce.entity.Media;
import com.aozelce.persistence.GenericDao;
import com.themoviedb.ResultsItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * Service class for creating and managing Media entities.
 * <p>
 * This class provides methods to create Media objects from TMDB search results
 * and to find or persist Media entities in the database.
 *
 * @author aozelce
 */
public class MediaService {

    private final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * Find or create media media.
     *
     * @param tmdbId the tmdb id
     * @param media  the media
     * @return the media
     */
    public Media findOrCreateMedia(int tmdbId, Media media) {
        GenericDao<Media> mediaDao = new GenericDao<Media>(Media.class);
        List<Media> existingMedia = mediaDao.getByPropertyEqual("tmdbId", tmdbId);
        if (existingMedia != null && !existingMedia.isEmpty()) {
            media = existingMedia.get(0);
        } else {
            int mediaId = mediaDao.insert(media);
            media.setId(mediaId);
        }
        return media;
    }

    /**
     * Creates a Media object from a TMDB search result.
     * <p>
     * This method extracts relevant information from the given ResultsItem,
     * including the title, media type, poster path, overview, genres, and
     * release year. It handles both movies and TV shows, using either the
     * release date or first air date to determine the year.
     *
     * @param selected the ResultsItem from TMDB search (must contain TMDB
     *                 data)
     * @return a Media object populated with data from the selected item,
     * or null if an error occurs or required fields (title/name) are missing
     * @throws IllegalArgumentException if the selected item has neither a title
     *                                  nor a name
     */
    public Media createMediaFromTmdbResult(ResultsItem selected) {

        try {
            int tmdbId = selected.getId();
            String title = selected.getTitle();
            if (title == null || title.trim().isEmpty()) {
                title = selected.getName();
            }
            if (title == null || title.trim().isEmpty()) {
                throw new IllegalArgumentException("TMDB result is missing a title and name");
            }

            String mediaType = selected.getMediaType();
            String year = null;
            String posterPath = selected.getPosterPath();
            String overview = selected.getOverview();
            String genres = selected.getGenres();

            Media media = new Media();
            media.setTmdbId(tmdbId);
            media.setTitle(title.trim());
            media.setMediaType(mediaType);
            media.setPosterPath(posterPath);
            media.setOverview(overview);
            media.setGenres(genres);

            // Extract year from release date or first air date based on media type
            String releaseDate = selected.getReleaseDate(); // get from ResultsItem for Movie
            String firstAirDate = selected.getFirstAirDate(); // get from ResultsItem for TV


            if ("movie".equalsIgnoreCase(mediaType) && releaseDate != null && releaseDate.length() >= 4) {
                year = releaseDate.substring(0, 4);
            } else if ("tv".equalsIgnoreCase(mediaType) && firstAirDate != null && firstAirDate.length() >= 4) {
                year = firstAirDate.substring(0, 4);
            }
            if (year != null) {
                media.setYear(Integer.parseInt(year));
            }

            return media; // Return the created Media object

        } catch (Exception e) {
            logger.error("Error creating Media", e);
            return null;
        }
    }
}