package com.aozelce.persistence;

import com.aozelce.entity.Media;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;

import java.util.List;

/**
 * The type Media dao.
 *
 * @author aozelce
 */
public class MediaDao {

    private final Logger logger = LogManager.getLogger(this.getClass());

    SessionFactory sessionFactory = SessionFactoryProvider.getSessionFactory();

    /**
     * Gets media by id.
     *
     * @param id the id
     * @return the media by id
     */
    public Media getMediaById(int id) {
        Session session = sessionFactory.openSession();
        Media media = session.get(Media.class, id);
        session.close();
        return media;
    }

    /**
     * Save media.
     *
     * @param media the media
     */
    public void saveMedia(Media media) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.merge(media);
        transaction.commit();
        session.close();
    }

    /**
     * Insert a new media.
     *
     * @param media the media to be inserted
     * @return the generated id
     */
    public int insert(Media media) {
        int id = 0;
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(media);
        transaction.commit();
        id = media.getId();
        logger.debug("Inserted media with ID: {}", id);
        session.close();
        return id;
    }

    /**
     * Delete a media.
     *
     * @param media the media to be deleted
     */
    public void delete(Media media) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.remove(media);
        transaction.commit();
        session.close();
    }

    /**
     * Return a list of all media.
     *
     * @return all media
     */
    public List<Media> getAll() {
        Session session = sessionFactory.openSession();

        HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Media> query = builder.createQuery(Media.class);
        Root<Media> root = query.from(Media.class);
        List<Media> mediaList = session.createSelectionQuery(query).getResultList();

        logger.debug("The list of media {}", mediaList);
        session.close();

        return mediaList;
    }

    /**
     * Get media by property (exact match).
     * sample usage: getByPropertyEqual("mediaType", "movie")
     *
     * @param propertyName the property name
     * @param value        the value
     * @return the list of matching media
     */
    public List<Media> getByPropertyEqual(String propertyName, String value) {
        Session session = sessionFactory.openSession();

        logger.debug("Searching for media with {} = {}", propertyName, value);

        HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Media> query = builder.createQuery(Media.class);
        Root<Media> root = query.from(Media.class);
        query.select(root).where(builder.equal(root.get(propertyName), value));
        List<Media> mediaList = session.createSelectionQuery(query).getResultList();

        session.close();
        return mediaList;
    }

    /**
     * Get media by property (like).
     * sample usage: getByPropertyLike("title", "Dark")
     *
     * @param propertyName the property name
     * @param value        the value
     * @return the list of matching media
     */
    public List<Media> getByPropertyLike(String propertyName, String value) {
        Session session = sessionFactory.openSession();

        logger.debug("Searching for media with {} like {}", propertyName, value);

        HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Media> query = builder.createQuery(Media.class);
        Root<Media> root = query.from(Media.class);
        Expression<String> propertyPath = root.get(propertyName);

        query.where(builder.like(propertyPath, "%" + value + "%"));

        List<Media> mediaList = session.createQuery(query).getResultList();
        session.close();
        return mediaList;
    }
}