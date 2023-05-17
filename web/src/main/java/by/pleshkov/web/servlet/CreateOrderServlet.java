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

@WebServlet("/order-created")
public class CreateOrderServlet extends HttpServlet {

    private final OrderService orderService = OrderService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(PagesUtil.ORDER_CREATED).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        Optional<OrderEntity> created = orderService.save(
                OrderEntity.builder()
                        .statusOrder(StatusOrder.valueOf(req.getParameter("statusOrder")))
                        .solution(Solution.valueOf(req.getParameter("solution")))
                        .build());
        created.ifPresentOrElse(
                order -> redirectToRoomPage(req, resp, order),
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
        resp.sendRedirect("/order-created?error=true");
    }
}
