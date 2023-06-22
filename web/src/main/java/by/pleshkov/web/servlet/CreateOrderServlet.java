package by.pleshkov.web.servlet;

import by.pleshkov.database.constant.Solution;
import by.pleshkov.database.constant.StatusOrder;
import by.pleshkov.database.entity.OrderEntity;
import by.pleshkov.service.service.OrderService;
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

public class CreateOrderServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(PagesUtil.ORDER_CREATED).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        ApplicationContext context = (ApplicationContext) getServletContext().getAttribute("applicationContext");
        OrderService orderService = context.getBean(OrderService.class);
        UserService userService = context.getBean(UserService.class);
        OrderEntity newOrder = OrderEntity.builder()
                .user(userService.getById(Long.valueOf(req.getParameter("user"))))
                .price(Integer.valueOf(req.getParameter("price")))
                .statusOrder(StatusOrder.valueOf(req.getParameter("statusOrder")))
                .solution(Solution.valueOf(req.getParameter("solution")))
                .build();
        if (orderService.save(newOrder) != null) {
            redirectToOrderPage(req, resp, newOrder);
        } else {
            onFailedCreation(req, resp);
        }
    }

    @SneakyThrows
    private static void redirectToOrderPage(HttpServletRequest req, HttpServletResponse resp, OrderEntity order) {
        req.setAttribute("order", order);
        req.getRequestDispatcher(PagesUtil.ORDER).forward(req, resp);
    }

    @SneakyThrows
    private static void onFailedCreation(HttpServletRequest req, HttpServletResponse resp) {
        resp.sendRedirect("/order-created?error=true");
    }
}
