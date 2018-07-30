package ru.eulanov.models;

import java.util.ArrayList;
import java.util.Collection;

public class User {
    private long id;
    private String name;
    private String login;
    private String password;
    private Collection<Announcement> announcements = new ArrayList<>();

    public User() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<Announcement> getAnnouncements() {
        return announcements;
    }

    public void setAnnouncements(Collection<Announcement> announcements) {
        for (Announcement announcement : announcements) {
            announcement.setSeller(this);
        }
        this.announcements = announcements;
    }

    public void addAnnouncement(Announcement announcement) {
        announcement.setSeller(this);
        announcements.add(announcement);
    }
}