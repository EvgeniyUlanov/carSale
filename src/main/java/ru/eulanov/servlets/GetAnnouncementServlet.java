package ru.eulanov.servlets;

import com.google.gson.Gson;
import ru.eulanov.models.Announcement;
import ru.eulanov.utils.DaoContainer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GetAnnouncementServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long announcementId = Long.parseLong(req.getParameter("announcementId"));
        Announcement announcement = DaoContainer.getInstance().getAnnouncementDao().getById(announcementId);
        if (announcement != null) {
            announcement.setSeller(null);
            announcement.getCar().setAnnouncement(null);
            announcement.setPhotos(null);
            Gson gson = new Gson();
            String announcementJson = gson.toJson(announcement);
            resp.setContentType("application/json");
            resp.setCharacterEncoding("utf-8");
            resp.getWriter().write(announcementJson);
        }
    }
}
