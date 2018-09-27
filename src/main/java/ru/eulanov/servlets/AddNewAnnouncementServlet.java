package ru.eulanov.servlets;

import ru.eulanov.models.Announcement;
import ru.eulanov.models.Car;
import ru.eulanov.models.User;
import ru.eulanov.utils.DaoContainer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;

public class AddNewAnnouncementServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("currentUser");
        if (user != null) {
            Announcement announcement = getFromSessionOrCreateAnnouncement(req.getSession());
            announcement.setSeller(user);
            announcement.setCreatedDate(new Timestamp(System.currentTimeMillis()));
            announcement.setContactInfo(req.getParameter("contact"));
            announcement.setDescription(req.getParameter("description"));
            Car car = new Car();
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
            announcement.setCar(car);
            car.setAnnouncement(announcement);
            try {
                announcement.setPrice(Integer.parseInt(req.getParameter("price")));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            DaoContainer.getInstance().getAnnouncementDao().create(announcement);
            req.getSession().setAttribute("currentAnnouncement", new Announcement());
        }
    }

    private Announcement getFromSessionOrCreateAnnouncement(HttpSession session) {
        Announcement announcement = (Announcement) session.getAttribute("currentAnnouncement");
        if (announcement == null) {
            announcement = new Announcement();
        }
        return announcement;
    }
}
