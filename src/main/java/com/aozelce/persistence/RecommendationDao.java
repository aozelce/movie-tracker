package com.aozelce.persistence;

import com.aozelce.entity.Recommendation;
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
 * The type Recommendation dao.
 *
 * @author aozelce
 */
public class RecommendationDao {

    private final Logger logger = LogManager.getLogger(this.getClass());

    SessionFactory sessionFactory = SessionFactoryProvider.getSessionFactory();

    /**
     * Gets recommendation by id.
     *
     * @param id the id
     * @return the recommendation by id
     */
    public Recommendation getRecommendationById(int id) {
        Session session = sessionFactory.openSession();
        Recommendation recommendation = session.get(Recommendation.class, id);
        session.close();
        return recommendation;
    }

    /**
     * Save recommendation.
     *
     * @param recommendation the recommendation
     */
    public void saveRecommendation(Recommendation recommendation) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.merge(recommendation);
        transaction.commit();
        session.close();
    }

    /**
     * Insert a new recommendation.
     *
     * @param recommendation the recommendation to be inserted
     * @return the generated id
     */
    public int insert(Recommendation recommendation) {
        int id = 0;
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(recommendation);
        transaction.commit();
        id = recommendation.getId();
        logger.debug("Inserted recommendation with ID: {}", id);
        session.close();
        return id;
    }

    /**
     * Delete a recommendation.
     *
     * @param recommendation the recommendation to be deleted
     */
    public void delete(Recommendation recommendation) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.remove(recommendation);
        transaction.commit();
        session.close();
    }

    /**
     * Return a list of all recommendations.
     *
     * @return all recommendations
     */
    public List<Recommendation> getAll() {
        Session session = sessionFactory.openSession();

        HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Recommendation> query = builder.createQuery(Recommendation.class);
        Root<Recommendation> root = query.from(Recommendation.class);
        List<Recommendation> recommendations = session.createSelectionQuery(query).getResultList();

        logger.debug("The list of recommendations {}", recommendations);
        session.close();

        return recommendations;
    }

    /**
     * Get recommendation by property (exact match).
     * sample usage: getByPropertyEqual("isWatched", "true")
     *
     * @param propertyName the property name
     * @param value        the value
     * @return the list of matching recommendations
     */
    public List<Recommendation> getByPropertyEqual(String propertyName, String value) {
        Session session = sessionFactory.openSession();

        logger.debug("Searching for recommendation with {} = {}", propertyName, value);

        HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Recommendation> query = builder.createQuery(Recommendation.class);
        Root<Recommendation> root = query.from(Recommendation.class);
        query.select(root).where(builder.equal(root.get(propertyName), value));
        List<Recommendation> recommendations = session.createSelectionQuery(query).getResultList();

        session.close();
        return recommendations;
    }

    /**
     * Get recommendation by property (like).
     * sample usage: getByPropertyLike("notes", "best")
     *
     * @param propertyName the property name
     * @param value        the value
     * @return the list of matching recommendations
     */
    public List<Recommendation> getByPropertyLike(String propertyName, String value) {
        Session session = sessionFactory.openSession();

        logger.debug("Searching for recommendation with {} like {}", propertyName, value);

        HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Recommendation> query = builder.createQuery(Recommendation.class);
        Root<Recommendation> root = query.from(Recommendation.class);
        Expression<String> propertyPath = root.get(propertyName);

        query.where(builder.like(propertyPath, "%" + value + "%"));

        List<Recommendation> recommendations = session.createQuery(query).getResultList();
        session.close();
        return recommendations;
    }
}