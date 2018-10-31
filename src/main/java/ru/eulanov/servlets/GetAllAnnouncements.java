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

public class GetAllAnnouncements extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String filter = req.getParameter("filter");
        String brand = req.getParameter("searchingBrand");
        Collection<Announcement> list;
        switch (filter) {
            case "all":
                list = DaoContainer.getInstance().getAnnouncementDao().getAllOpenAnnouncements();
                break;
            case "last_day":
                list = DaoContainer.getInstance().getAnnouncementDao().getAnnouncementForTheLastDay();
                break;
            case "brand":
                list = DaoContainer.getInstance().getAnnouncementDao().getAnnouncementByBrand(brand);
                break;
            case "with_photo":
                list = DaoContainer.getInstance().getAnnouncementDao().getAnnouncementWithPhoto();
                break;
            default:
                list = new ArrayList<>();
        }
        Collection<AnnouncementDTO> announcementDTOs = new ArrayList<>();
        if (list != null && list.size() != 0) {
            for (Announcement announcement : list) {
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