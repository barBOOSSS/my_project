package by.pleshkov.web.servlet;

import by.pleshkov.database.constant.ClassRoom;
import by.pleshkov.database.constant.StatusRoom;
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
import java.util.Optional;

@WebServlet("/room-created")
public class CreateRoomServlet extends HttpServlet {

    private final RoomService roomService = RoomService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(PagesUtil.ROOM_CREATED).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        Optional<Room> created = roomService.create(
                Room.builder()
                        .number(Integer.parseInt(req.getParameter("number")))
                        .places(Integer.parseInt(req.getParameter("places")))
                        .classRoom(ClassRoom.valueOf(req.getParameter("classRoom")))
                        .statusRoom(StatusRoom.FREE)
                        .build());
        created.ifPresentOrElse(
                room -> redirectToRoomPage(req, resp, room),
                () -> onFailedCreation(req, resp)
        );
    }

    @SneakyThrows
    private static void redirectToRoomPage(HttpServletRequest req, HttpServletResponse resp, Room room) {
        req.setAttribute("room", room);
        req.getRequestDispatcher(PagesUtil.ROOM).forward(req, resp);
    }

    @SneakyThrows
    private static void onFailedCreation(HttpServletRequest req, HttpServletResponse resp) {
        resp.sendRedirect("/room-created?error=true");
    }
}

