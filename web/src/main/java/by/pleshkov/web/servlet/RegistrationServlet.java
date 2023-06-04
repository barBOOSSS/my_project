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
import lombok.SneakyThrows;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {

    private UserService userService = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(PagesUtil.REGISTRATION).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
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
       Optional<UserEntity> saved = userService.save(newUser);
        saved.ifPresentOrElse(
                user -> redirectToLoginPage(req, resp),
                () -> onFailedCreation(req, resp)
        );
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