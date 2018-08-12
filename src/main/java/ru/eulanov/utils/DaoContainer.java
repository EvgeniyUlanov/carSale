package ru.eulanov.utils;

import ru.eulanov.dao.AnnouncementDao;
import ru.eulanov.dao.UserDao;

public class DaoContainer {
    private static final DaoContainer CONTAINER = new DaoContainer();
    private UserDao userDao;
    private AnnouncementDao announcementDao;

    private DaoContainer() {
        userDao = new UserDao();
        announcementDao = new AnnouncementDao();
        userDao.setSessionFactory(HibernateUtil.getFactory());
        announcementDao.setSessionFactory(HibernateUtil.getFactory());
    }

    public static DaoContainer getInstance() {
        return CONTAINER;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public AnnouncementDao getAnnouncementDao() {
        return announcementDao;
    }
}
