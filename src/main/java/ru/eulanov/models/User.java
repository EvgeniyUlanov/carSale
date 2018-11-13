package ru.eulanov.models;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @Column(unique = true)
    private String login;
    private String password;
    @OneToMany(fetch = FetchType.EAGER,
                cascade = CascadeType.PERSIST,
                mappedBy = "seller")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Collection<Announcement> announcements = new ArrayList<>();

    public User() {
        this("empty user");
    }

    public User(String name) {
        this.name = name;
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
        if (announcements != null) {
            for (Announcement announcement : announcements) {
                announcement.setSeller(this);
            }
            this.announcements = announcements;
        } else {
            this.announcements = new ArrayList<>();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        User user = (User) o;
        return login != null ? login.equals(user.login) : user.login == null;
    }

    @Override
    public int hashCode() {
        return login != null ? login.hashCode() : 0;
    }
}
