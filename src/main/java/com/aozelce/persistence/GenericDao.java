package com.aozelce.persistence;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
/**
 * GenericDao is a generic Data Access Object (DAO) class that provides common database operations
 * for any entity type. It uses Hibernate for ORM (Object-Relational Mapping) to interact with the database.
 *
 * @param <T> the type of entity that this DAO will manage
 */
public class GenericDao<T> {

    private Class<T> type;

    private final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * Constructor for GenericDao that initializes the type of entity it will manage.
     * @param type the Class object representing the type of entity to be managed
     */
    public GenericDao(Class<T> type) {
        this.type = type;
    }

    /**
     * Opens a new Hibernate session from the SessionFactory.
     * @return a new Session instance
     */
    private Session getSession() {
        return SessionFactoryProvider.getSessionFactory().openSession();

    }

    /**
     * Retrieves an entity of type T from the database using its unique identifier.
     * @param id the unique identifier of the entity to be retrieved
     * @return the entity of type T with the specified id, or null if not found
     */
    public T getById(int id) {
        Session session = getSession();
        T entity = session.get(type, id);
        session.close();
        return entity;
    }

    /**
     * Saves a new entity or updates an existing one in the database.
     * @param entity the entity to be saved or updated
     */
    public void saveOrUpdate(T entity) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        session.merge(entity);
        transaction.commit();
        session.close();
    }

    /**
     * Deletes the given entity from the database.
     * @param entity the entity to be deleted
     */
    public void delete(T entity) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        session.remove(entity);
        transaction.commit();
        session.close();
    }

    /**
     * Retrieves all entities of type T from the database.
     *
     * @return a List of all entities of type T
     */
    public List<T> getAll() {
        Session session = getSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(type);
        Root<T> root = query.from(type);
        List<T> list = session.createQuery(query).getResultList();
        session.close();
        return list;
    }

    /**
    * Inserts a new entity of type T into the database and returns the generated identifier.
    *
    * @param entity the entity to be inserted
    * @return the generated identifier for the inserted entity
    */
    public int insert(T entity) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();

        session.persist(entity);
        transaction.commit();
        // Retrieve the generated identifier using the session's getIdentifier method
        int id = (int) session.getIdentifier(entity);

        session.close();
        return id;
    }

    /**
     * Retrieves a list of entities of type T from the database where a specified property matches a given value.
     *
     * @param propertyName the name of the property to be matched
     * @param value the value to be matched against the specified property
     * @return a List of entities of type T that match the specified property and value
     */
    public List<T> getByPropertyEqual(String propertyName, String value) {
        Session session = getSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(type);
        Root<T> root = query.from(type);
        Expression<String> propertyPath = root.get(propertyName);
        // Exact match condition
        query.where(builder.equal(propertyPath, value));
        List<T> list = session.createQuery(query).getResultList();
        session.close();
        return list;
    }

    /**
     * Retrieves a list of entities of type T from the database where a specified property matches a given pattern.
     *
     * @param propertyName the name of the property to be matched
     * @param value the pattern to be matched against the specified property (e.g., "%value%")
     * @return a List of entities of type T that match the specified property and pattern
     */
    public List<T> getByPropertyLike(String propertyName, String value) {
        Session session = getSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(type);
        Root<T> root = query.from(type);
        Expression<String> propertyPath = root.get(propertyName);
        query.where(builder.like(propertyPath, "%" + value + "%"));

        List<T> list = session.createQuery(query).getResultList();
        session.close();
        return list;
    }

    /**
     * Retrieves a list of entities of type T from the database where a specified integer property matches a given value.
     *
     * @param propertyName the name of the integer property to be matched
     * @param value the integer value to be matched against the specified property
     * @return a List of entities of type T that match the specified property and value
     */
    public List<T> getByPropertyEqual(String propertyName, int value) {
        Session session = getSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(type);
        Root<T> root = query.from(type);
        Expression<Integer> propertyPath = root.get(propertyName);
        // Exact match condition
        query.where(builder.equal(propertyPath, value));
        List<T> list = session.createQuery(query).getResultList();
        session.close();
        return list;
    }

}
