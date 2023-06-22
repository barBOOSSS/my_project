package by.pleshkov.web.servlet;

import by.pleshkov.database.constant.Role;
import by.pleshkov.database.entity.Address;
import by.pleshkov.database.entity.PassportEntity;
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

public class RegistrationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ApplicationContext context = (ApplicationContext) getServletContext().getAttribute("applicationContext");
        UserService userService = context.getBean(UserService.class);
        req.getRequestDispatcher(PagesUtil.REGISTRATION).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        ApplicationContext context = (ApplicationContext) getServletContext().getAttribute("applicationContext");
        UserService userService = context.getBean(UserService.class);
        UserEntity newUser = UserEntity.builder()
                .name(req.getParameter("name"))
                .surname(req.getParameter("surname"))
                .email(req.getParameter("email"))
                .password(req.getParameter("password"))
                .role(Role.USER)
                .address(Address.builder()
                        .city(req.getParameter("city"))
                        .street(req.getParameter("street"))
                        .building(req.getParameter("building"))
                        .flat(req.getParameter("flat"))
                        .build())
                .build();
        newUser.setPassport(PassportEntity.builder()
                .number(req.getParameter("passport"))
                .build());
        if (userService.save(newUser) != null) {
            redirectToLoginPage(req, resp);
        } else {
            onFailedCreation(req, resp);
        }
    }

    @SneakyThrows
    private static void redirectToLoginPage(HttpServletRequest req, HttpServletResponse resp) {
        resp.sendRedirect("/login");
    }

    @SneakyThrows
    private static void onFailedCreation(HttpServletRequest req, HttpServletResponse resp) {
        resp.sendRedirect("/registration?error=true");
    }
}