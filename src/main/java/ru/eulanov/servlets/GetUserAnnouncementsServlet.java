package ru.eulanov.servlets;

import ru.eulanov.models.Announcement;
import ru.eulanov.models.Car;
import ru.eulanov.utils.DaoContainer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

public class GetUserAnnouncementsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long userId = Long.parseLong(req.getParameter("userId"));
        Collection<Announcement> announcements =
                DaoContainer.getInstance().getAnnouncementDao().getUserAnnouncement(userId);
        StringBuilder json = new StringBuilder();
        json.append("[");
        boolean first = true;
        for (Announcement announcement : announcements) {
            Car car = announcement.getCar();
            if (car != null) {
                if (!first) {
                    json.append(",");
                } else {
                    first = false;
                }
                json.append("{\"id\":\"" + announcement.getId() +
                        "\",\"carModel\":\"" + car.getModel() +
                        "\",\"carBrand\":\"" + car.getBrand() +
                        "\",\"createDate\":\"" + announcement.getCreatedDate() +
                        "\",\"price\":\"" + announcement.getPrice() +
                        "\",\"isSold\":\"" + announcement.isSold() +
                        "\"}"
                );
            }
        }
        json.append("]");
        resp.setContentType("application/json");
        resp.setCharacterEncoding("utf-8");
        resp.getWriter().write(json.toString());
    }
}
