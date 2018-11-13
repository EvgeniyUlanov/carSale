package ru.eulanov.dao;

import org.hibernate.SessionFactory;
import org.junit.*;
import ru.eulanov.models.User;
import ru.eulanov.utils.HibernateUtil;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class UserDaoTest {

    private static SessionFactory sessionFactory;
    private static UserDao userDao;

    @BeforeClass
    public static void init() {
        sessionFactory = HibernateUtil.getNewFactory();
        userDao = new UserDao();
        userDao.setSessionFactory(sessionFactory);
    }

    @AfterClass
    public static void close() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    @Test
    public void testCreateGetDeleteUser() {
        User user = new User("ivan");
        userDao.create(user);

        User expectedUser = userDao.getById(user.getId());
        assertThat(expectedUser.getName(), is("ivan"));

        userDao.delete(user.getId());

        expectedUser = userDao.getById(user.getId());
        assertThat(expectedUser, is(nullValue()));
    }

    @Test
    public void testGetUserById() {
        User user = new User();
        user.setLogin("new login");
        userDao.create(user);

        User expectedUser = userDao.getByLogin("new login");
        assertThat(expectedUser, is(user));

        userDao.delete(user.getId());
    }
}
