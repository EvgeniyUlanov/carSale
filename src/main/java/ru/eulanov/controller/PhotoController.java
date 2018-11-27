package ru.eulanov.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.eulanov.dao.AnnouncementDao;
import ru.eulanov.models.Announcement;
import ru.eulanov.models.Photo;
import ru.eulanov.utils.PhotoReceiver;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/photo")
public class PhotoController {

    @Autowired
    private AnnouncementDao announcementDao;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public List<String> addPhoto(HttpServletRequest req) {
        return PhotoReceiver.getPhotoFromRequestAndAddToCurrentAnnouncement(req);
    }

    @RequestMapping(value = "/get/{announId}",
            method = RequestMethod.GET,
            produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public byte[] getPhoto(@PathVariable("announId") String announcementId) {
        byte[] result = null;
        Announcement announcement = announcementDao.getById(Long.parseLong(announcementId));
        if (announcement != null) {
            Photo photo = announcement.getPhotos().stream().findFirst().orElseGet(Photo::new);
            result = photo.getByteArray();
        }
        return result;
    }
}
