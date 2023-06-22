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

@WebServlet("/room-created")
public class CreateRoomServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(PagesUtil.ROOM_CREATED).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        ApplicationContext context = (ApplicationContext) getServletContext().getAttribute("applicationContext");
        RoomService roomService = context.getBean(RoomService.class);
        RoomEntity newRoom = RoomEntity.builder()
                .number(Integer.parseInt(req.getParameter("number")))
                .places(Integer.parseInt(req.getParameter("places")))
                .price(Integer.parseInt(req.getParameter("price")))
                .classRoom(ClassRoom.valueOf(req.getParameter("classRoom")))
                .statusRoom(StatusRoom.FREE)
                .build();
        if (roomService.save(newRoom) != null) {
            redirectToRoomPage(req, resp, newRoom);
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
        resp.sendRedirect("/room-created?error=true");
    }
}

