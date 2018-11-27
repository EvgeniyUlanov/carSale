package ru.eulanov.utils;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import ru.eulanov.models.Announcement;
import ru.eulanov.models.Photo;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PhotoReceiver {

    public static List<String> getPhotoFromRequestAndAddToCurrentAnnouncement(HttpServletRequest req) {
        List<String> photosNames = new ArrayList<>();
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(1024 * 1024 * 40);
        factory.setRepository(new File("/tmp"));

        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setSizeMax(1000000);

        List fileItems = null;
        try {
            fileItems = upload.parseRequest(req);
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
        if (fileItems != null) {
            Announcement announcement = (Announcement) req.getSession().getAttribute("currentAnnouncement");
            if (announcement != null) {
                for (Object fileItem : fileItems) {
                    FileItem fi = (FileItem) fileItem;
                    String fileName = fi.getName();
                    photosNames.add(fileName);
                    try {
                        Photo photo = new Photo();
                        photo.setName(fileName);
                        photo.setByteArray(fi.get());
                        announcement.getPhotos().add(photo);
                        photo.setAnnouncement(announcement);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return photosNames;
    }
}
