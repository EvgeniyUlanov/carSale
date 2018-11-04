package ru.eulanov.dao;

import org.hibernate.SessionFactory;
import org.junit.*;
import ru.eulanov.models.Announcement;
import ru.eulanov.models.User;
import ru.eulanov.utils.HibernateUtil;

import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class AnnouncementDaoTest {

    private static SessionFactory sessionFactory;
    private static AnnouncementDao announcementDao;
    private static UserDao userDao;
    private static User user;

    @BeforeClass
    public static void init() {
        sessionFactory = HibernateUtil.getNewFactory();
        announcementDao = new AnnouncementDao();
        userDao = new UserDao();
        announcementDao.setSessionFactory(sessionFactory);
        userDao.setSessionFactory(sessionFactory);
        user = new User();
        user.setName("nikolay");
        userDao.create(user);
    }

    @AfterClass
    public static void close() {
        if (sessionFactory != null) {
            if (userDao != null) {
                userDao.delete(user.getId());
            }
            sessionFactory.close();
        }
    }

    @Test
    public void createGetUpdateDeleteAnnouncementTest() {
        Announcement newAnnouncement = new Announcement();
        newAnnouncement.setSeller(user);
        newAnnouncement.setDescription("new description");
        announcementDao.create(newAnnouncement);
        long announcementId = newAnnouncement.getId();
        Announcement expected = announcementDao.getById(announcementId);

        assertThat(expected, is(newAnnouncement));

        newAnnouncement.setDescription("updated description");
        announcementDao.update(newAnnouncement);
        expected = announcementDao.getById(announcementId);

        assertThat(expected.getDescription(), is("updated description"));

        announcementDao.delete(announcementId);
        expected = announcementDao.getById(announcementId);

        assert expected == null;
    }

    @Test
    public void getAllAnnouncementTest() {
        Announcement firstAnnouncement = new Announcement();
        Announcement secondAnnouncement = new Announcement();
        firstAnnouncement.setSeller(user);
        secondAnnouncement.setSeller(user);
        firstAnnouncement.setDescription("first description");
        secondAnnouncement.setDescription("second description");
        announcementDao.create(firstAnnouncement);
        announcementDao.create(secondAnnouncement);

        Collection<Announcement> announcements = announcementDao.getAll();

        assert announcements.contains(firstAnnouncement);
        assert announcements.contains(secondAnnouncement);
    }

    @Test
    public void closeAnnouncementTest() {
        Announcement announcement = new Announcement();
        announcement.setSeller(user);
        announcement.setDescription("description");
        announcementDao.create(announcement);

        assertThat(announcement.isSold(), is(false));

        announcementDao.closeAnnouncement(announcement.getId());
        announcement = announcementDao.getById(announcement.getId());

        assertThat(announcement.isSold(), is(true));
    }

    @Test
    public void testGetAllOpenAnnouncements() {
        Announcement openAnnouncement = new Announcement();
        Announcement closedAnnouncement = new Announcement();
        openAnnouncement.setSeller(user);
        closedAnnouncement.setSeller(user);
        closedAnnouncement.setSold(true);
        announcementDao.create(openAnnouncement);
        announcementDao.create(closedAnnouncement);
        Collection<Announcement> announcements = announcementDao.getAllOpenAnnouncements();

        assertThat(announcements.contains(openAnnouncement), is(true));
        assertThat(announcements.contains(closedAnnouncement), is(false));
    }

    @Test
    public void testGetAllUserAnnouncement() {
        User ivan = new User();
        ivan.setName("ivan");
        userDao.create(ivan);
        Announcement ivanOpenAnnouncement = new Announcement();
        ivanOpenAnnouncement.setSeller(ivan);
        Announcement ivanClosedAnnouncement = new Announcement();
        ivanClosedAnnouncement.setSeller(ivan);
        ivanClosedAnnouncement.setSold(true);
        announcementDao.create(ivanOpenAnnouncement);
        announcementDao.create(ivanClosedAnnouncement);
        Announcement nikolaysAnnouncement = new Announcement();
        nikolaysAnnouncement.setSeller(user);
        announcementDao.create(nikolaysAnnouncement);

        Collection<Announcement> announcements = announcementDao.getUserAnnouncement(ivan.getId());

        assertThat(announcements.contains(ivanOpenAnnouncement), is(true));
        assertThat(announcements.contains(ivanClosedAnnouncement), is(true));
        assertThat(announcements.contains(nikolaysAnnouncement), is(false));

        userDao.delete(ivan.getId());
    }
}
