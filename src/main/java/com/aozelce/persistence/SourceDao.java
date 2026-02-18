package com.aozelce.persistence;

import com.aozelce.entity.Source;
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
 * The type Source dao.
 *
 * @author aozelce
 */
public class SourceDao {

    private final Logger logger = LogManager.getLogger(this.getClass());

    SessionFactory sessionFactory = SessionFactoryProvider.getSessionFactory();

    /**
     * Gets source by id.
     *
     * @param id the id
     * @return the source by id
     */
    public Source getSourceById(int id) {
        Session session = sessionFactory.openSession();
        Source source = session.get(Source.class, id);
        session.close();
        return source;
    }

    /**
     * Save source.
     *
     * @param source the source
     */
    public void saveSource(Source source) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.merge(source);
        transaction.commit();
        session.close();
    }

    /**
     * Insert a new source.
     *
     * @param source the source to be inserted
     * @return the generated id
     */
    public int insert(Source source) {
        int id = 0;
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(source);
        transaction.commit();
        id = source.getId();
        logger.debug("Inserted source with ID: {}", id);
        session.close();
        return id;
    }

    /**
     * Delete a source.
     *
     * @param source the source to be deleted
     */
    public void delete(Source source) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.remove(source);
        transaction.commit();
        session.close();
    }

    /**
     * Return a list of all sources.
     *
     * @return all sources
     */
    public List<Source> getAll() {
        Session session = sessionFactory.openSession();

        HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Source> query = builder.createQuery(Source.class);
        Root<Source> root = query.from(Source.class);
        List<Source> sources = session.createSelectionQuery(query).getResultList();

        logger.debug("The list of sources {}", sources);
        session.close();

        return sources;
    }

    /**
     * Get source by property (exact match).
     * sample usage: getByPropertyEqual("name", "Sarah")
     *
     * @param propertyName the property name
     * @param value        the value
     * @return the list of matching sources
     */
    public List<Source> getByPropertyEqual(String propertyName, String value) {
        Session session = sessionFactory.openSession();

        logger.debug("Searching for source with {} = {}", propertyName, value);

        HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Source> query = builder.createQuery(Source.class);
        Root<Source> root = query.from(Source.class);
        query.select(root).where(builder.equal(root.get(propertyName), value));
        List<Source> sources = session.createSelectionQuery(query).getResultList();

        session.close();
        return sources;
    }

    /**
     * Get source by property (like).
     * sample usage: getByPropertyLike("name", "S")
     *
     * @param propertyName the property name
     * @param value        the value
     * @return the list of matching sources
     */
    public List<Source> getByPropertyLike(String propertyName, String value) {
        Session session = sessionFactory.openSession();

        logger.debug("Searching for source with {} like {}", propertyName, value);

        HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Source> query = builder.createQuery(Source.class);
        Root<Source> root = query.from(Source.class);
        Expression<String> propertyPath = root.get(propertyName);

        query.where(builder.like(propertyPath, "%" + value + "%"));

        List<Source> sources = session.createQuery(query).getResultList();
        session.close();
        return sources;
    }
}