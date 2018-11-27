package ru.eulanov.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.eulanov.dao.UserDao;
import ru.eulanov.dto.UserDTO;
import ru.eulanov.models.Announcement;
import ru.eulanov.models.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addNewUser(@RequestBody User user) {
        userDao.create(user);
        return user.getLogin();
    }

    @RequestMapping(value = "/getCurrent", method = RequestMethod.GET)
    public UserDTO getCurrentUser(HttpSession session) {
        User user = (User) session.getAttribute("currentUser");
        return UserDTO.createUserDTOFromUser(user);
    }

    @RequestMapping(value = "/signIn", method = RequestMethod.POST)
    public UserDTO signInUser(@RequestParam Map<String, String> params, HttpServletRequest req) {
        HttpSession session = req.getSession();
        User currentUser = (User) session.getAttribute("currentUser");
        if (currentUser == null) {
            currentUser = userDao.getByLogin(params.get("login"));
            if (currentUser != null && currentUser.getPassword().equals(params.get("password"))) {
                session.setAttribute("currentUser", currentUser);
                session.setAttribute("currentAnnouncement", new Announcement());
            }
        }
        return UserDTO.createUserDTOFromUser(currentUser);
    }

    @RequestMapping(value = "/signOut", method = RequestMethod.GET)
    public String signOut(HttpSession session) {
        session.invalidate();
        return "ok";
    }
}
