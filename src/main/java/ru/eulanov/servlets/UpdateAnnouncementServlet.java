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

public class UpdateAnnouncementServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("currentUser");
        if (user != null) {
            Announcement announcement = DaoContainer.getInstance().getAnnouncementDao()
                    .getById(Long.parseLong(req.getParameter("id")));
            if (user.equals(announcement.getSeller())) {
                announcement.setContactInfo(req.getParameter("contact"));
                announcement.setDescription(req.getParameter("description"));
                Car car = announcement.getCar();
                car.setBrand(req.getParameter("carBrand"));
                car.setModel(req.getParameter("carModel"));
                car.setVin(req.getParameter("vin"));
                car.setYear(req.getParameter("year"));
                try {
                    car.setRun(Integer.parseInt(req.getParameter("run")));
                    car.setEnginePower(Integer.parseInt(req.getParameter("enginePower")));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                car.setColor(req.getParameter("color"));
                car.setEngineType(req.getParameter("engineType"));
                try {
                    announcement.setPrice(Integer.parseInt(req.getParameter("price")));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                DaoContainer.getInstance().getAnnouncementDao().update(announcement);
            }
        }
    }
}
