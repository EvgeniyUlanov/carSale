package ru.eulanov.servlets;

import ru.eulanov.models.Photo;
import ru.eulanov.utils.DaoContainer;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.Collection;

public class GetImageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long announcId = null;
        try {
            announcId = Long.parseLong(req.getParameter("announId"));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        Collection<Photo> images = null;
        if (announcId != null) {
            images = DaoContainer.getInstance().getAnnouncementDao().getAnnouncementImages(announcId);
        }
        if (images != null) {
            if (images.size() != 0) {
                Photo photo = images.iterator().next();
                resp.setContentType("image/jpeg");
                try (OutputStream out = resp.getOutputStream()) {
                    BufferedImage bi = ImageIO.read(new ByteArrayInputStream(photo.getByteArray()));
                    ImageIO.setUseCache(false);
                    ImageIO.write(bi, "jpg", out);
                }
            }
        } else {
            ClassLoader cl = this.getClass().getClassLoader();
            resp.setContentType("image/jpeg");
            URL imageUrl = cl.getResource("noimage.jpg");
            if (imageUrl != null) {
                try (OutputStream out = resp.getOutputStream()) {
                    BufferedImage bi = ImageIO.read(imageUrl);
                    ImageIO.setUseCache(false);
                    ImageIO.write(bi, "jpg", out);
                }
            }
        }
    }
}