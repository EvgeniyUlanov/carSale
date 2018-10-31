package ru.eulanov.servlets;

import com.google.gson.Gson;
import ru.eulanov.dto.AnnouncementDTO;
import ru.eulanov.models.Announcement;
import ru.eulanov.models.Car;
import ru.eulanov.utils.DaoContainer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class GetUserAnnouncementsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long userId = Long.parseLong(req.getParameter("userId"));
        Collection<Announcement> announcements =
                DaoContainer.getInstance().getAnnouncementDao().getUserAnnouncement(userId);
        if (announcements != null) {
            Collection<AnnouncementDTO> announcementDTOs = new ArrayList<>();
            for (Announcement announcement : announcements) {
                announcementDTOs.add(AnnouncementDTO.createFromAnnouncement(announcement));
            }
            Gson gson = new Gson();
            String json = gson.toJson(announcementDTOs);
            resp.setCharacterEncoding("utf-8");
            resp.setContentType("application/json");
            resp.getWriter().write(json);
        }
    }
}
