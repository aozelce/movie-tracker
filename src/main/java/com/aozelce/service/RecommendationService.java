package com.aozelce.service;


import com.aozelce.util.AuthUtils;
import com.aozelce.entity.Media;
import com.aozelce.entity.Recommendation;
import com.aozelce.entity.Source;
import com.aozelce.entity.User;
import com.aozelce.persistence.GenericDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Service class for creating and managing Recommendation entities.
 * <p>
 * This class provides methods to create new recommendations for users,
 * optionally associating them with media, sources, notes, and watched status.
 * It handles persistence using GenericDao for Recommendation and Source
 * entities.
 *
 * @author aozelce
 */
public class RecommendationService {
    private final Logger logger = LogManager.getLogger(this.getClass());


    /**
     * Helper method to create a Recommendation entity and persist it.
     *
     * @param request the HTTP request containing recommendation details
     * @param media   the media object for the recommendation
     */
    public void createRecommendation(HttpServletRequest request, Media media) {
        User user = null;
        try {
            user = AuthUtils.getAuthenticatedUser(request, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            Recommendation recommendation = new Recommendation();
            recommendation.setUser(user);
            recommendation.setMedia(media);

            String sourceName = request.getParameter("sourceName");
            if (sourceName != null && !sourceName.trim().isEmpty()) {
                sourceName = sourceName.trim();

                GenericDao<Source> sourceDao = new GenericDao<>(Source.class);
                List<Source> userSources = sourceDao.getByPropertyEqual("user", user);
                Source source = null;
                for (Source s : userSources) {
                    if (sourceName.equals(s.getName())) {
                        source = s;
                        break;
                    }
                }

                if (source != null) {
                    logger.info("Using existing source: {}", sourceName);
                } else {
                    source = new Source();
                    source.setUser(user);
                    source.setName(sourceName);
                    int sourceId = sourceDao.insert(source);
                    source.setId(sourceId);

                    // Keep the session user's sources list in sync so newly
                    // created sources appear in JSP datalists without re-login
                    user.getSources().add(source);
                    HttpSession session = request.getSession(false);
                    if (session != null) {
                        session.setAttribute("user", user);
                    }

                    logger.info("Created new source: {} for user: {}", sourceName, user.getId());
                }

                recommendation.setSource(source);
            }

            String notes = request.getParameter("notes");
            if (notes != null && !notes.isEmpty()) {
                recommendation.setNotes(notes.trim());
            }

            String isWatched = request.getParameter("isWatched");
            recommendation.setWatched("on".equals(isWatched));

            GenericDao<Recommendation> recDao = new GenericDao<>(Recommendation.class);
            recDao.insert(recommendation);

        } catch (Exception e) {
            logger.error("Error creating recommendation", e);
            throw new RuntimeException("Failed to create recommendation", e);
        }
    }
}