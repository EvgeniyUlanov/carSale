package ru.eulanov.dao.impl;

import ru.eulanov.dao.UserDao;
import ru.eulanov.models.User;

public class UserDaoImpl extends BasicDaoImpl<User> implements UserDao {

    public UserDaoImpl() {
        super(User.class);
    }

    @Override
    public User getByLogin() {
        return null;
    }
}