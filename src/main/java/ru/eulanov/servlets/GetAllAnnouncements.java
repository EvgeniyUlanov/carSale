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

public class GetAllAnnouncements extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Collection<Announcement> list = DaoContainer.getInstance().getAnnouncementDao().getAll();
        StringBuilder json = new StringBuilder();
        json.append("[");
        boolean first = true;
        for (Announcement announcement : list) {
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
                        "\",\"price\":\"" + announcement.getPrice() +
                        "\",\"description\":\"" + announcement.getDescription() +
                        "\",\"contact\":\"" + announcement.getContactInfo() +
                        "\"}"
                );
                System.out.println(announcement.getId());
            }
            System.out.println(announcement.getId() + " " + announcement.getDescription());
        }
        json.append("]");
        resp.setContentType("application/json");
        resp.setCharacterEncoding("utf-8");
        resp.getWriter().write(json.toString());
    }
}