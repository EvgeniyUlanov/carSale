package ru.eulanov.dto;

import ru.eulanov.models.Announcement;
import ru.eulanov.models.Car;
import ru.eulanov.models.User;

import java.sql.Timestamp;

public class AnnouncementDTO {

    private long id;
    private String description;
    private String contactInfo;
    private Car car;
    private User seller;
    private Timestamp createdDate;
    private int price;
    private boolean isSold;

    private AnnouncementDTO() {
    }

    public static AnnouncementDTO createFromAnnouncement(Announcement announcement) {
        AnnouncementDTO announcementDTO = new AnnouncementDTO();
        announcementDTO.setId(announcement.getId());
        announcementDTO.setDescription(announcement.getDescription());
        announcementDTO.setContactInfo(announcement.getContactInfo());
        announcement.getCar().setAnnouncement(null);
        announcementDTO.setCar(announcement.getCar());
        announcement.getSeller().setAnnouncements(null);
        announcementDTO.setSeller(announcement.getSeller());
        announcementDTO.setCreatedDate(announcement.getCreatedDate());
        announcementDTO.setPrice(announcement.getPrice());
        announcementDTO.setSold(announcement.isSold());
        return announcementDTO;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isSold() {
        return isSold;
    }

    public void setSold(boolean sold) {
        isSold = sold;
    }

    @Override
    public String toString() {
        return "AnnouncementDTO{"
                + "id=" + id
                + ", description='" + description + '\''
                + ", contactInfo='" + contactInfo + '\''
                + ", car=" + car
                + ", seller=" + seller
                + ", createdDate=" + createdDate
                + ", price=" + price
                + ", isSold=" + isSold
                + '}';
    }
}
