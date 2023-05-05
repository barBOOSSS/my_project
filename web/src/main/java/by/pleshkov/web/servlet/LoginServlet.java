package by.pleshkov.web.servlet;

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

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private UserService userService = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(PagesUtil.LOGIN).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        userService.getBy(email, password)
                .ifPresentOrElse(
                        user -> successLogin(req, resp, user),
                        () -> failedLogin(req, resp));
    }

    @SneakyThrows
    private static void successLogin(HttpServletRequest req, HttpServletResponse resp, User user) {
        req.getSession().setAttribute("user", user);
        resp.sendRedirect("/main");
    }

    @SneakyThrows
    private static void failedLogin(HttpServletRequest req, HttpServletResponse resp) {
        resp.sendRedirect("/login?error=true");
    }
}