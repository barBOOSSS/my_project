package by.pleshkov.web.servlet;

import by.pleshkov.database.constant.Role;
import by.pleshkov.database.entity.User;
import by.pleshkov.service.service.UserService;
import by.pleshkov.web.util.PagesUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/user-edit")
public class EditUserServlet extends HttpServlet {

    private final UserService userService = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        req.setAttribute("user", userService.getById(Long.valueOf(id)));
        req.getRequestDispatcher(PagesUtil.USER_EDIT).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String id = req.getParameter("id");
        User user = userService.getById(Long.valueOf(id));
        user.setName(req.getParameter("name"));
        user.setSurname(req.getParameter("surname"));
        user.setPassword(req.getParameter("password"));
        user.setEmail(req.getParameter("email"));
        user.setRole(Role.valueOf(req.getParameter("role")));
        Optional<User> update = userService.update(user);
        update.ifPresentOrElse(
                u -> redirectToRoomPage(req, resp, user),
                () -> onFailedCreation(req, resp)
        );
    }

    @SneakyThrows
    private static void redirectToRoomPage(HttpServletRequest req, HttpServletResponse resp, User user) {
        req.setAttribute("user", user);
        req.getRequestDispatcher(PagesUtil.USER).forward(req, resp);
    }

    @SneakyThrows
    private static void onFailedCreation(HttpServletRequest req, HttpServletResponse resp) {
        resp.sendRedirect("/user-edit?error=true");
    }
}
