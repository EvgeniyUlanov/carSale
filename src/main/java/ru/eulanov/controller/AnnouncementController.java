package ru.eulanov.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.eulanov.dao.AnnouncementDao;
import ru.eulanov.dto.AnnouncementDTO;
import ru.eulanov.models.Announcement;
import ru.eulanov.models.Car;
import ru.eulanov.models.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@RestController
@RequestMapping(value = "/announcement")
public class AnnouncementController {

    @Autowired
    private AnnouncementDao announcementDao;

    @RequestMapping(value = "/close", method = RequestMethod.GET)
    public String closeAnnouncement(@RequestParam String announcementId) {
        announcementDao.closeAnnouncement(Long.parseLong(announcementId));
        return "ok";
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public AnnouncementDTO getAnnouncement(HttpServletRequest req) {
        Announcement announcement = announcementDao.getById(Long.parseLong(req.getParameter("announcementId")));
        AnnouncementDTO announcementDTO = null;
        if (announcement != null) {
            announcementDTO = AnnouncementDTO.createFromAnnouncement(announcement);
        }
        return announcementDTO;
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public Collection<AnnouncementDTO> getAllAnnouncement(HttpServletRequest req) {
        String filter = req.getParameter("filter");
        String brand = req.getParameter("searchingBrand");
        Collection<Announcement> list;
        switch (filter) {
            case "all":
                list = announcementDao.getAllOpenAnnouncements();
                break;
            case "last_day":
                list = announcementDao.getAnnouncementForTheLastDay();
                break;
            case "brand":
                list = announcementDao.getAnnouncementByBrand(brand);
                break;
            case "with_photo":
                list = announcementDao.getAnnouncementWithPhoto();
                break;
            default:
                list = new ArrayList<>();
        }
        Collection<AnnouncementDTO> announcementDTOs = new ArrayList<>();
        if (list != null && list.size() != 0) {
            for (Announcement announcement : list) {
                announcementDTOs.add(AnnouncementDTO.createFromAnnouncement(announcement));
            }
        }
        return announcementDTOs;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String deleteAnnouncement(@RequestParam String announcementId) {
        announcementDao.delete(Long.parseLong(announcementId));
        return "ok";
    }

    @RequestMapping(value = "/getUserAnnouncements", method = RequestMethod.GET)
    public Collection<AnnouncementDTO> getCurrentUserAnnouncements(HttpSession httpSession) {
        Collection<AnnouncementDTO> announcementDTOs = new ArrayList<>();
        User currentUser = (User) httpSession.getAttribute("currentUser");
        if (currentUser != null) {
            long userId = currentUser.getId();
            Collection<Announcement> announcements = announcementDao.getUserAnnouncement(userId);
            if (announcements != null) {
                for (Announcement announcement : announcements) {
                    announcementDTOs.add(AnnouncementDTO.createFromAnnouncement(announcement));
                }
            }
        }
        return announcementDTOs;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addAnnouncement(HttpServletRequest req) {
        User user = (User) req.getSession().getAttribute("currentUser");
        String result;
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
            announcementDao.create(announcement);
            req.getSession().setAttribute("currentAnnouncement", new Announcement());
            result = "ok";
        } else {
            result = "something goes wrong, cannot create announcement";
        }
        return result;
    }

    private static Announcement getFromSessionOrCreateAnnouncement(HttpSession session) {
        Announcement announcement = (Announcement) session.getAttribute("currentAnnouncement");
        if (announcement == null) {
            announcement = new Announcement();
        }
        return announcement;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public AnnouncementDTO updateAnnouncement(@RequestParam Map<String, String> params, HttpSession session) {
        User user = (User) session.getAttribute("currentUser");
        Announcement announcement = null;
        if (user != null) {
            announcement = announcementDao.getById(Long.parseLong(params.get("id")));
            if (user.equals(announcement.getSeller())) {
                announcement.setContactInfo(params.get("contact"));
                announcement.setDescription(params.get("description"));
                Car car = announcement.getCar();
                car.setBrand(params.get("carBrand"));
                car.setModel(params.get("carModel"));
                car.setVin(params.get("vin"));
                car.setYear(params.get("year"));
                try {
                    car.setRun(Integer.parseInt(params.get("run")));
                    car.setEnginePower(Integer.parseInt(params.get("enginePower")));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                car.setColor(params.get("color"));
                car.setEngineType(params.get("engineType"));
                try {
                    announcement.setPrice(Integer.parseInt(params.get("price")));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                announcementDao.update(announcement);
            }
        }
        return AnnouncementDTO.createFromAnnouncement(announcement);
    }
}
