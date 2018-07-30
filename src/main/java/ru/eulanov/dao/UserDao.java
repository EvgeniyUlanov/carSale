package ru.eulanov.dao;

import ru.eulanov.models.User;

public interface UserDao extends BasicDao<User> {
    public User getByLogin();
}
