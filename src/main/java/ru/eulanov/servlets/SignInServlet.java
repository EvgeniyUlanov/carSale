package ru.eulanov.servlets;

import ru.eulanov.models.User;
import ru.eulanov.utils.DaoContainer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SignInServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (session.getAttribute("login") == null) {
            User user = DaoContainer.getInstance().getUserDao().getByLogin(req.getParameter("login"));
            if (user == null) {
                resp.getWriter().write("0");
            } else if (user.getPassword().equals(req.getParameter("password"))) {
                session.setAttribute("currentUser", user);
                resp.getWriter().write(user.getLogin());
            } else {
                resp.getWriter().write("1");
            }
        }
    }
}
