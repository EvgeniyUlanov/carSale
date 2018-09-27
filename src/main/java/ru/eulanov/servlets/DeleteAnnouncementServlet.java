package ru.eulanov.servlets;

import ru.eulanov.utils.DaoContainer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteAnnouncementServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            long announcementId = Long.parseLong(req.getParameter("announcementId"));
            DaoContainer.getInstance().getAnnouncementDao().delete(announcementId);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
