package ru.eulanov.utils;

import org.hibernate.SessionFactory;
import ru.eulanov.dao.AnnouncementDao;
import ru.eulanov.dao.UserDao;

public class DaoContainer {
    private static final DaoContainer CONTAINER = new DaoContainer();
    private SessionFactory sessionFactory;
    private UserDao userDao;
    private AnnouncementDao announcementDao;

    private DaoContainer() {
        sessionFactory = HibernateUtil.getNewFactory();
        userDao = new UserDao();
        announcementDao = new AnnouncementDao();
        userDao.setSessionFactory(sessionFactory);
        announcementDao.setSessionFactory(sessionFactory);
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

    public void closeFactory() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
