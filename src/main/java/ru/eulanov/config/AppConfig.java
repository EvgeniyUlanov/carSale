package ru.eulanov.config;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.eulanov.dao.AnnouncementDao;
import ru.eulanov.dao.UserDao;
import ru.eulanov.utils.HibernateUtil;

/**
 * basic configuration class
 */
@Configuration
public class AppConfig {

    @Bean
    public SessionFactory getSessionFactory() {
        return HibernateUtil.getNewFactory();
    }

    @Bean
    @Autowired
    public AnnouncementDao getAnnouncementDao(SessionFactory sessionFactory) {
        AnnouncementDao announcementDao = new AnnouncementDao();
        announcementDao.setSessionFactory(sessionFactory);
        return announcementDao;
    }

    @Bean
    @Autowired
    public UserDao getUserDao(SessionFactory sessionFactory) {
        UserDao userDao = new UserDao();
        userDao.setSessionFactory(sessionFactory);
        return userDao;
    }
}
