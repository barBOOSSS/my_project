package by.pleshkov.web.servlet;

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

@WebServlet("/orders")
@Controller
@RequiredArgsConstructor
public class OrderServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ApplicationContext context = (ApplicationContext) getServletContext().getAttribute("applicationContext");
        OrderService orderService = context.getBean(OrderService.class);
        String id = req.getParameter("id");
        if (id == null) {
            req.setAttribute("orders", orderService.getAll());
            req.getRequestDispatcher(PagesUtil.ORDERS).forward(req, resp);
        } else {
            req.setAttribute("order", orderService.getById(Long.parseLong(id)));
            req.getRequestDispatcher(PagesUtil.ORDER).forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        ApplicationContext context = (ApplicationContext) getServletContext().getAttribute("applicationContext");
        OrderService orderService = context.getBean(OrderService.class);
        String id = req.getParameter("id");
        if (orderService.delete(Long.valueOf(id))) {
            redirectToOrdersPage(req, resp);
        } else {
            onFailedRemoval(req, resp, orderService.getById(Long.valueOf(id)));
        }
    }

    @SneakyThrows
    private static void redirectToOrdersPage(HttpServletRequest req, HttpServletResponse resp) {
        resp.sendRedirect("/orders");
    }

    @SneakyThrows
    private static void onFailedRemoval(HttpServletRequest req, HttpServletResponse resp, OrderEntity order) {
        req.setAttribute("order", order);
        req.setAttribute("error", true);
        req.getRequestDispatcher(PagesUtil.ORDER).forward(req, resp);
    }
}
