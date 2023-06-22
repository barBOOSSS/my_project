package by.pleshkov.web.servlet;

import by.pleshkov.database.constant.Solution;
import by.pleshkov.database.constant.StatusOrder;
import by.pleshkov.database.entity.OrderEntity;
import by.pleshkov.service.service.OrderService;
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

public class EditOrderServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ApplicationContext context = (ApplicationContext) getServletContext().getAttribute("applicationContext");
        OrderService orderService = context.getBean(OrderService.class);
        String id = req.getParameter("id");
        req.setAttribute("order", orderService.getById(Long.valueOf(id)));
        req.getRequestDispatcher(PagesUtil.ORDER_EDIT).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        ApplicationContext context = (ApplicationContext) getServletContext().getAttribute("applicationContext");
        OrderService orderService = context.getBean(OrderService.class);
        String id = req.getParameter("id");
        OrderEntity order = orderService.getById(Long.valueOf(id));
        order.setPrice(Integer.valueOf(req.getParameter("price")));
        order.setStatusOrder(StatusOrder.valueOf(req.getParameter("statusOrder")));
        order.setSolution(Solution.valueOf(req.getParameter("solution")));
        if (orderService.save(order) != null) {
            redirectToOrderPage(req, resp, order);
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
        resp.sendRedirect("/order-edit?error=true");
    }
}
