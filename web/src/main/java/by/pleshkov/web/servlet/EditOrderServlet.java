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
import lombok.SneakyThrows;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/order-edit")
public class EditOrderServlet extends HttpServlet {

    private final OrderService orderService = OrderService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        req.setAttribute("order", orderService.getById(Long.valueOf(id)));
        req.getRequestDispatcher(PagesUtil.ORDER_EDIT).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String id = req.getParameter("id");
        OrderEntity order = orderService.getById(Long.valueOf(id));
        order.setPrice(Integer.valueOf(req.getParameter("price")));
        order.setStatusOrder(StatusOrder.valueOf(req.getParameter("statusOrder")));
        order.setSolution(Solution.valueOf(req.getParameter("solution")));
        Optional<OrderEntity> update = orderService.update(order);
        update.ifPresentOrElse(
                o -> redirectToRoomPage(req, resp, order),
                () -> onFailedCreation(req, resp)
        );
    }

    @SneakyThrows
    private static void redirectToRoomPage(HttpServletRequest req, HttpServletResponse resp, OrderEntity order) {
        req.setAttribute("order", order);
        req.getRequestDispatcher(PagesUtil.ORDER).forward(req, resp);
    }

    @SneakyThrows
    private static void onFailedCreation(HttpServletRequest req, HttpServletResponse resp) {
        resp.sendRedirect("/order-edit?error=true");
    }
}
