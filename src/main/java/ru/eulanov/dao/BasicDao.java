package ru.eulanov.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.function.Function;

public abstract class BasicDao<T> {

    private final Class<T> entityClass;
    private SessionFactory sessionFactory;

    public BasicDao(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public T create(T entity) {
        tx(session -> session.save(entity));
        return entity;
    }

    public T getById(long id) {
        return tx(session -> session.get(entityClass, id));
    }

    public Collection<T> getAll() {
        return tx(session -> {
            CriteriaBuilder builder = sessionFactory.getCriteriaBuilder();
            CriteriaQuery<T> criteriaQuery = builder.createQuery(entityClass);
            Root<T> root = criteriaQuery.from(entityClass);
            criteriaQuery.select(root);
            return session.createQuery(criteriaQuery).list();
        });
    }

    public T delete(long id) {
        T entity = getById(id);
        return tx(session -> {
            session.delete(entity);
            return entity;
        });
    }

    public T update(T entity) {
        return tx(session -> {
            session.update(entity);
            return entity;
        });
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    protected  <K> K tx(final Function<Session, K> command) {
        final Session session = sessionFactory.openSession();
        final Transaction transaction = session.beginTransaction();
        try {
            return command.apply(session);
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        } finally {
            transaction.commit();
            session.close();
        }
    }
}
