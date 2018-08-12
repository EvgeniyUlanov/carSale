package ru.eulanov.dao;

import ru.eulanov.models.User;

public class UserDao extends BasicDao<User> {

    public UserDao() {
        super(User.class);
    }

    public User getByLogin(String login) {
        return (User) tx(session -> session.createQuery("from User as u where u.login = :login")
                .setParameter("login", login).uniqueResult());
    }
}