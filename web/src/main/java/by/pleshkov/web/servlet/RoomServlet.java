package by.pleshkov.web.servlet;

import by.pleshkov.database.constant.ClassRoom;
import by.pleshkov.database.constant.StatusRoom;
import by.pleshkov.database.dto.RoomFilter;
import by.pleshkov.database.entity.Room;
import by.pleshkov.service.service.RoomService;
import by.pleshkov.web.util.PagesUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

import java.io.IOException;

@WebServlet("/rooms")
public class RoomServlet extends HttpServlet {
    private final RoomService roomService = RoomService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        if (id == null) {
            req.setAttribute("rooms", roomService.getFindByFilter(new RoomFilter(
                    Integer.parseInt(req.getParameter("places") != null ? req.getParameter("places") : "10"),
                    ClassRoom.valueOf(req.getParameter("classRoom") != null ? req.getParameter("classRoom") : "STANDARD"),
                    StatusRoom.valueOf(req.getParameter("statusRoom") != null ? req.getParameter("statusRoom") : "NOT_FREE"),
                    Integer.parseInt(req.getParameter("limit") != null ? req.getParameter("limit") : "100"),
                    Integer.parseInt(req.getParameter("page") != null ? req.getParameter("page") : "1")
            )));
            req.getRequestDispatcher(PagesUtil.ROOMS).forward(req, resp);
        } else {
            redirectToRoomPage(req, resp, roomService.getById(Long.parseLong(id)));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String id = req.getParameter("id");
        if (roomService.delete(Long.valueOf(id))) {
            redirectToRoomsPage(req, resp);
        } else {
            onFailedRemoval(req, resp, roomService.getById(Long.valueOf(id)));
        }
    }

    @SneakyThrows
    private static void redirectToRoomsPage(HttpServletRequest req, HttpServletResponse resp) {
        resp.sendRedirect("/rooms");
    }

    @SneakyThrows
    private static void redirectToRoomPage(HttpServletRequest req, HttpServletResponse resp, Room room) {
        req.setAttribute("room", room);
        req.getRequestDispatcher(PagesUtil.ROOM).forward(req, resp);
    }

    @SneakyThrows
    private static void onFailedRemoval(HttpServletRequest req, HttpServletResponse resp, Room room) {
        req.setAttribute("room", room);
        req.setAttribute("error", true);
        req.getRequestDispatcher(PagesUtil.ROOM).forward(req, resp);
    }

}
