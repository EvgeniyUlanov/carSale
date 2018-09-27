package ru.eulanov.dao;

import org.hibernate.query.Query;
import ru.eulanov.models.Announcement;
import ru.eulanov.models.Photo;
import ru.eulanov.models.User;

import java.util.Collection;

public class AnnouncementDao extends BasicDao<Announcement> {

    public AnnouncementDao() {
        super(Announcement.class);
    }

    public void addImageToAnnouncement(long announcementId, Photo photo) {
        tx(session -> {
            Announcement announcement = session.load(Announcement.class, announcementId);
            announcement.getPhotos().add(photo);
            photo.setAnnouncement(announcement);
            session.update(announcement);
            return true;
        });
    }

    public Collection<Photo> getAnnouncementImages(long announcementId) {
        return tx(session -> {
            Announcement announcement = session.load(Announcement.class, announcementId);
            return announcement.getPhotos();
        });
    }

    public Collection<Announcement> getUserAnnouncement(long userId) {
        return tx(session -> {
           User user = session.load(User.class, userId);
           return user.getAnnouncements();
        });
    }

    public void closeAnnouncement(long announcementId) {
        tx(session -> {
            Announcement announcement = session.load(Announcement.class, announcementId);
            announcement.setSold(true);
            return true;
        });
    }

    public Collection<Announcement> getAllOpenAnnouncements() {
        return tx(session -> {
            Query query = session.createQuery("from Announcement as a where a.isSold = false");
            return query.list();
        });
    }
}
