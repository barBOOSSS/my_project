package by.pleshkov.web.servlet;

import by.pleshkov.service.service.OrderService;
import by.pleshkov.web.util.PagesUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/orders")
public class OrderServlet extends HttpServlet {

    private final OrderService orderService = OrderService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        if (id == null) {
            req.setAttribute("orders", orderService.readAll());
            req.getRequestDispatcher(PagesUtil.ORDERS).forward(req, resp);
        } else {
            req.setAttribute("order", orderService.getById(Long.parseLong(id)));
            req.getRequestDispatcher(PagesUtil.ORDER).forward(req, resp);
        }
    }
}
