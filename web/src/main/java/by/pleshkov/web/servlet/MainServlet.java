package by.pleshkov.web.servlet;

import by.pleshkov.web.util.PagesUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/")
public class MainServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        if (id == null) {
            req.getRequestDispatcher(PagesUtil.MAIN).forward(req, resp);
        } else {
            menu(id, req, resp);
        }
    }

    public void menu(String id, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if ("1".equals(id)) {
            resp.sendRedirect("/registration");
        }
        if ("2".equals(id)) {
            resp.sendRedirect("/login");
        }
        if ("3".equals(id)) {
            resp.sendRedirect("/logout");
        }
    }
}