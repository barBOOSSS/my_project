package by.pleshkov.web.servlet;

import by.pleshkov.database.dto.LoginDto;
import by.pleshkov.database.entity.UserEntity;
import by.pleshkov.service.service.UserService;
import by.pleshkov.web.util.PagesUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.context.ApplicationContext;

import java.io.IOException;

public class LoginServlet extends HttpServlet {

//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        req.getRequestDispatcher(PagesUtil.LOGIN).forward(req, resp);
//    }

//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
//        ApplicationContext context = (ApplicationContext) getServletContext().getAttribute("applicationContext");
//        UserService userService = context.getBean(UserService.class);
//        String email = req.getParameter("email");
//        String password = req.getParameter("password");
//        userService.getBy()
//                .ifPresentOrElse(
//                        user -> successLogin(req, resp, user),
//                        () -> failedLogin(req, resp));
//    }
//
//    @SneakyThrows
//    private static void successLogin(HttpServletRequest req, HttpServletResponse resp, UserEntity user) {
//        req.getSession().setAttribute("user", user);
//        resp.sendRedirect("/main");
//    }
//
//    @SneakyThrows
//    private static void failedLogin(HttpServletRequest req, HttpServletResponse resp) {
//        resp.sendRedirect("/login?error=true");
//    }
}