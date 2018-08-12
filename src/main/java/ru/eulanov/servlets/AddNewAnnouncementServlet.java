package ru.eulanov.servlets;

import ru.eulanov.models.Announcement;
import ru.eulanov.models.Car;
import ru.eulanov.models.User;
import ru.eulanov.utils.DaoContainer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;

public class AddNewAnnouncementServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("currentUser");
        if (user != null) {
            Announcement announcement = new Announcement();
            announcement.setSeller(user);
            announcement.setCreatedDate(new Timestamp(System.currentTimeMillis()));
            announcement.setContactInfo(req.getParameter("contact"));
            announcement.setDescription(req.getParameter("description"));
            Car car = new Car();
            car.setBrand(req.getParameter("carBrand"));
            car.setModel(req.getParameter("carModel"));
            announcement.setCar(car);
            try {
                announcement.setPrice(Integer.parseInt(req.getParameter("price")));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            DaoContainer.getInstance().getAnnouncementDao().create(announcement);
        }
    }
}
