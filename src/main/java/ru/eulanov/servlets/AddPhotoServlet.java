package ru.eulanov.servlets;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import ru.eulanov.models.Announcement;
import ru.eulanov.models.Photo;
import ru.eulanov.utils.DaoContainer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class AddPhotoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
                    try {
                        Photo photo = new Photo();
                        photo.setName(fileName);
                        photo.setByteArray(fi.get());
                        announcement.getPhotos().add(photo);
                        photo.setAnnouncement(announcement);
                        resp.getWriter().write(photo.getName());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
