package ru.eulanov.servlets;

import ru.eulanov.models.Announcement;
import ru.eulanov.utils.DaoContainer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CloseAnnouncementServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long announcementId = Long.parseLong(req.getParameter("announcementId"));
        DaoContainer.getInstance().getAnnouncementDao().closeAnnouncement(announcementId);
    }
}
