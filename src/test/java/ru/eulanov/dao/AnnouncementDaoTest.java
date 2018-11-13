package ru.eulanov.dao;

import org.hibernate.SessionFactory;
import org.junit.*;
import ru.eulanov.models.Announcement;
import ru.eulanov.models.User;
import ru.eulanov.utils.HibernateUtil;

import java.util.Collection;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsCollectionContaining.hasItem;

public class AnnouncementDaoTest {

    private final static SessionFactory sessionFactory = HibernateUtil.getNewFactory();
    private final static AnnouncementDao announcementDao = new AnnouncementDao();
    private final static UserDao userDao = new UserDao();
    private final static User user = new User("nikolay");

    @BeforeClass
    public static void init() {
        announcementDao.setSessionFactory(sessionFactory);
        userDao.setSessionFactory(sessionFactory);
        userDao.create(user);
    }

    @AfterClass
    public static void close() {
        if (sessionFactory != null) {
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

        assertThat(expected, is(nullValue()));
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

        assertThat(announcements, hasItem(firstAnnouncement));
        assertThat(announcements, hasItem(secondAnnouncement));
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

        assertThat(announcements, hasItem(openAnnouncement));
        assertThat(announcements, not(hasItem(closedAnnouncement)));
    }

    @Test
    public void testGetAllUserAnnouncement() {
        User ivan = new User("ivan");
        ivan.setId(3);
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

        assertThat(announcements, hasItem(ivanOpenAnnouncement));
        assertThat(announcements, hasItem(ivanClosedAnnouncement));
        assertThat(announcements, not(hasItem(nikolaysAnnouncement)));

        userDao.delete(ivan.getId());
    }
}
