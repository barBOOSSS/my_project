package by.pleshkov.web.servlet;

import by.pleshkov.database.entity.UserEntity;
import by.pleshkov.service.service.UserService;
import by.pleshkov.web.util.PagesUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

import java.io.IOException;

public class UserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ApplicationContext context = (ApplicationContext) getServletContext().getAttribute("applicationContext");
        UserService userService = context.getBean(UserService.class);
        String id = req.getParameter("id");
        if (id == null) {
            req.setAttribute("users", userService.getAll());
            req.getRequestDispatcher(PagesUtil.USERS).forward(req, resp);
        } else {
            req.setAttribute("user", userService.getById(Long.parseLong(id)));
            req.getRequestDispatcher(PagesUtil.USER).forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        ApplicationContext context = (ApplicationContext) getServletContext().getAttribute("applicationContext");
        UserService userService = context.getBean(UserService.class);
        String id = req.getParameter("id");
        if (userService.delete(Long.valueOf(id))) {
            redirectToUsersPage(req, resp);
        } else {
            onFailedRemoval(req, resp, userService.getById(Long.valueOf(id)));
        }
    }

    @SneakyThrows
    private static void redirectToUsersPage(HttpServletRequest req, HttpServletResponse resp) {
        resp.sendRedirect("/users");
    }

    @SneakyThrows
    private static void onFailedRemoval(HttpServletRequest req, HttpServletResponse resp, UserEntity user) {
        req.setAttribute("user", user);
        req.setAttribute("error", true);
        req.getRequestDispatcher(PagesUtil.USER).forward(req, resp);
    }
}