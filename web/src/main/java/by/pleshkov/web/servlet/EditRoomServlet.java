package by.pleshkov.web.servlet;


import by.pleshkov.database.constant.ClassRoom;
import by.pleshkov.database.constant.StatusRoom;
import by.pleshkov.database.entity.RoomEntity;
import by.pleshkov.service.service.RoomService;
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

public class EditRoomServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ApplicationContext context = (ApplicationContext) getServletContext().getAttribute("applicationContext");
        RoomService roomService = context.getBean(RoomService.class);
        String id = req.getParameter("id");
        req.setAttribute("room", roomService.getById(Long.valueOf(id)));
        req.getRequestDispatcher(PagesUtil.ROOM_EDIT).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        ApplicationContext context = (ApplicationContext) getServletContext().getAttribute("applicationContext");
        RoomService roomService = context.getBean(RoomService.class);
        String id = req.getParameter("id");
        RoomEntity room = roomService.getById(Long.valueOf(id));
        room.setNumber(Integer.parseInt(req.getParameter("number")));
        room.setPlaces(Integer.parseInt(req.getParameter("places")));
        room.setClassRoom(ClassRoom.valueOf(req.getParameter("classRoom")));
        room.setStatusRoom(StatusRoom.valueOf(req.getParameter("statusRoom")));
        if (roomService.save(room) != null) {
            redirectToRoomPage(req, resp, room);
        } else {
            onFailedCreation(req, resp);
        }
    }

    @SneakyThrows
    private static void redirectToRoomPage(HttpServletRequest req, HttpServletResponse resp, RoomEntity room) {
        req.setAttribute("room", room);
        req.getRequestDispatcher(PagesUtil.ROOM).forward(req, resp);
    }

    @SneakyThrows
    private static void onFailedCreation(HttpServletRequest req, HttpServletResponse resp) {
        resp.sendRedirect("/room-edit?error=true");
    }
}
