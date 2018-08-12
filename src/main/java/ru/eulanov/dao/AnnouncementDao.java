package ru.eulanov.dao;

import ru.eulanov.models.Announcement;

public class AnnouncementDao extends BasicDao<Announcement> {

    public AnnouncementDao() {
        super(Announcement.class);
    }
}
