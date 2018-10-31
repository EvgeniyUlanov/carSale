package ru.eulanov.dao;

import org.hibernate.query.Query;
import org.joda.time.DateTime;
import ru.eulanov.models.Announcement;
import ru.eulanov.models.Photo;
import ru.eulanov.models.User;
import sun.util.calendar.BaseCalendar;

import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class AnnouncementDao extends BasicDao<Announcement> {

    public AnnouncementDao() {
        super(Announcement.class);
    }

    public Collection<Photo> getAnnouncementImages(long announcementId) {
        return tx(session -> {
            Announcement announcement = session.load(Announcement.class, announcementId);
            return announcement.getPhotos();
        });
    }

    public Collection<Announcement> getUserAnnouncement(long userId) {
        return tx(session -> {
            Query<Announcement> query =
                    session.createQuery(
                            "from Announcement  as a where a.seller.id =:userId", Announcement.class
                    );
            query.setParameter("userId", userId);
            return query.list();
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
            Query<Announcement> query =
                    session.createQuery("from Announcement as a where a.isSold = false", Announcement.class
                    );
            return query.list();
        });
    }

    public Collection<Announcement> getAnnouncementForTheLastDay() {
        final DateTime today = DateTime.now().withTimeAtStartOfDay();
        return getAllOpenAnnouncements()
                .stream()
                .filter(e -> e.getCreatedDate().after(today.toDate()))
                .collect(Collectors.toList());
    }

    public Collection<Announcement> getAnnouncementByBrand(final String brand) {
        return tx(session -> {
            Query<Announcement> query =
                    session.createQuery(
                            "from Announcement as a where a.car.brand =:brand", Announcement.class);
            query.setParameter("brand", brand);
            return query.list();
        });
    }

    public Collection<Announcement> getAnnouncementWithPhoto() {
        return getAllOpenAnnouncements()
                .stream()
                .filter(e -> e.getPhotos().size() > 0)
                .collect(Collectors.toList());
    }
}
