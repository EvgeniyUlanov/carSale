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

    private final static SessionFactory SESSION_FACTORY = HibernateUtil.getNewFactory();
    private final static AnnouncementDao ANNOUNCEMENT_DAO = new AnnouncementDao();
    private final static UserDao USER_DAO = new UserDao();
    private final static User USER = new User("nikolay");

    @BeforeClass
    public static void init() {
        ANNOUNCEMENT_DAO.setSessionFactory(SESSION_FACTORY);
        USER_DAO.setSessionFactory(SESSION_FACTORY);
        USER_DAO.create(USER);
    }

    @AfterClass
    public static void close() {
        if (SESSION_FACTORY != null) {
            SESSION_FACTORY.close();
        }
    }

    @Test
    public void createGetUpdateDeleteAnnouncementTest() {
        Announcement newAnnouncement = new Announcement();
        newAnnouncement.setSeller(USER);
        newAnnouncement.setDescription("new description");
        ANNOUNCEMENT_DAO.create(newAnnouncement);
        long announcementId = newAnnouncement.getId();
        Announcement expected = ANNOUNCEMENT_DAO.getById(announcementId);

        assertThat(expected, is(newAnnouncement));

        newAnnouncement.setDescription("updated description");
        ANNOUNCEMENT_DAO.update(newAnnouncement);
        expected = ANNOUNCEMENT_DAO.getById(announcementId);

        assertThat(expected.getDescription(), is("updated description"));

        ANNOUNCEMENT_DAO.delete(announcementId);
        expected = ANNOUNCEMENT_DAO.getById(announcementId);

        assertThat(expected, is(nullValue()));
    }

    @Test
    public void getAllAnnouncementTest() {
        Announcement firstAnnouncement = new Announcement();
        Announcement secondAnnouncement = new Announcement();
        firstAnnouncement.setSeller(USER);
        secondAnnouncement.setSeller(USER);
        firstAnnouncement.setDescription("first description");
        secondAnnouncement.setDescription("second description");
        ANNOUNCEMENT_DAO.create(firstAnnouncement);
        ANNOUNCEMENT_DAO.create(secondAnnouncement);

        Collection<Announcement> announcements = ANNOUNCEMENT_DAO.getAll();

        assertThat(announcements, hasItem(firstAnnouncement));
        assertThat(announcements, hasItem(secondAnnouncement));
    }

    @Test
    public void closeAnnouncementTest() {
        Announcement announcement = new Announcement();
        announcement.setSeller(USER);
        announcement.setDescription("description");
        ANNOUNCEMENT_DAO.create(announcement);

        assertThat(announcement.isSold(), is(false));

        ANNOUNCEMENT_DAO.closeAnnouncement(announcement.getId());
        announcement = ANNOUNCEMENT_DAO.getById(announcement.getId());

        assertThat(announcement.isSold(), is(true));
    }

    @Test
    public void testGetAllOpenAnnouncements() {
        Announcement openAnnouncement = new Announcement();
        Announcement closedAnnouncement = new Announcement();
        openAnnouncement.setSeller(USER);
        closedAnnouncement.setSeller(USER);
        closedAnnouncement.setSold(true);
        ANNOUNCEMENT_DAO.create(openAnnouncement);
        ANNOUNCEMENT_DAO.create(closedAnnouncement);
        Collection<Announcement> announcements = ANNOUNCEMENT_DAO.getAllOpenAnnouncements();

        assertThat(announcements, hasItem(openAnnouncement));
        assertThat(announcements, not(hasItem(closedAnnouncement)));
    }

    @Test
    public void testGetAllUserAnnouncement() {
        User ivan = new User("ivan");
        USER_DAO.create(ivan);
        Announcement ivanOpenAnnouncement = new Announcement();
        ivanOpenAnnouncement.setSeller(ivan);
        Announcement ivanClosedAnnouncement = new Announcement();
        ivanClosedAnnouncement.setSeller(ivan);
        ivanClosedAnnouncement.setSold(true);
        ANNOUNCEMENT_DAO.create(ivanOpenAnnouncement);
        ANNOUNCEMENT_DAO.create(ivanClosedAnnouncement);
        Announcement nikolaysAnnouncement = new Announcement();
        nikolaysAnnouncement.setSeller(USER);
        ANNOUNCEMENT_DAO.create(nikolaysAnnouncement);

        Collection<Announcement> announcements = ANNOUNCEMENT_DAO.getUserAnnouncement(ivan.getId());

        assertThat(announcements, hasItem(ivanOpenAnnouncement));
        assertThat(announcements, hasItem(ivanClosedAnnouncement));
        assertThat(announcements, not(hasItem(nikolaysAnnouncement)));

        USER_DAO.delete(ivan.getId());
    }
}
