package ru.eulanov.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Collection;

public interface BasicDao<T> {
    Session getSession();
    T create(T entity);
    T getById(long id);
    Collection<T> getAll();
    T delete(long id);
    T update(T entity);
    SessionFactory getSessionFactory();
    void setSessionFactory(SessionFactory sessionFactory);
}
