package by.pleshkov.web.servlet;

import by.pleshkov.database.constant.Role;
import by.pleshkov.database.model.User;
import by.pleshkov.service.service.UserService;
import by.pleshkov.web.util.PagesUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {

    private UserService userService = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(PagesUtil.REGISTRATION).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        userService.create(
                User.builder()
                        .login(req.getParameter("login"))
                        .password(req.getParameter("password"))
                        .role(Role.USER)
                        .build());
        resp.sendRedirect("/login");
    }
}
