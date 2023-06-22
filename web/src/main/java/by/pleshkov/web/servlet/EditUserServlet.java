package by.pleshkov.web.servlet;

import by.pleshkov.database.constant.Role;
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

public class EditUserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ApplicationContext context = (ApplicationContext) getServletContext().getAttribute("applicationContext");
        UserService userService = context.getBean(UserService.class);
        String id = req.getParameter("id");
        req.setAttribute("user", userService.getById(Long.valueOf(id)));
        req.getRequestDispatcher(PagesUtil.USER_EDIT).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        ApplicationContext context = (ApplicationContext) getServletContext().getAttribute("applicationContext");
        UserService userService = context.getBean(UserService.class);
        String id = req.getParameter("id");
        UserEntity user = userService.getById(Long.valueOf(id));
        user.setName(req.getParameter("name"));
        user.setSurname(req.getParameter("surname"));
        user.setPassword(req.getParameter("password"));
        user.setEmail(req.getParameter("email"));
        user.setRole(Role.valueOf(req.getParameter("role")));
        if (userService.save(user) != null) {
            redirectToUserPage(req, resp, user);
        } else {
            onFailedCreation(req, resp);
        }
    }

    @SneakyThrows
    private static void redirectToUserPage(HttpServletRequest req, HttpServletResponse resp, UserEntity user) {
        req.setAttribute("user", user);
        req.getRequestDispatcher(PagesUtil.USER).forward(req, resp);
    }

    @SneakyThrows
    private static void onFailedCreation(HttpServletRequest req, HttpServletResponse resp) {
        resp.sendRedirect("/user-edit?error=true");
    }
}
