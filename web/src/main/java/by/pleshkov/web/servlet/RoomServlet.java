package by.pleshkov.web.servlet;

import by.pleshkov.database.constant.ClassRoom;
import by.pleshkov.database.constant.StatusRoom;
import by.pleshkov.database.dto.RoomFilter;
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

@WebServlet("/rooms")
@Controller
@RequiredArgsConstructor
public class RoomServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ApplicationContext context = (ApplicationContext) getServletContext().getAttribute("applicationContext");
        RoomService roomService = context.getBean(RoomService.class);
        String id = req.getParameter("id");
        if (id == null) {
            String places;
            String classRoom;
            String statusRoom;
            String userName;
            String limit;
            String page;
            if (req.getParameter("places") == null || req.getParameter("places").isBlank()) {
                places = "10";
            } else {
                places = req.getParameter("places");
            }
            if (req.getParameter("classRoom") == null || req.getParameter("classRoom").isBlank()) {
                classRoom = "STANDARD";
            } else {
                classRoom = req.getParameter("classRoom");
            }
            if (req.getParameter("statusRoom") == null || req.getParameter("statusRoom").isBlank()) {
                statusRoom = "NOT_FREE";
            } else {
                statusRoom = req.getParameter("statusRoom");
            }
            if (req.getParameter("userName") == null || req.getParameter("userName").isBlank()) {
                userName = "";
            } else {
                userName = req.getParameter("userName");
            }
            if (req.getParameter("limit") == null || req.getParameter("limit").isBlank()) {
                limit = "3";
            } else {
                limit = req.getParameter("limit");
            }
            if (req.getParameter("page") == null || req.getParameter("page").isBlank()) {
                page = "1";
            } else {
                page = req.getParameter("page");
            }
            req.setAttribute("rooms", roomService.getFindByFilter(
                            RoomFilter.builder()
                                    .places(Integer.parseInt(places))
                                    .classRoom(ClassRoom.valueOf(classRoom))
                                    .statusRoom(StatusRoom.valueOf(statusRoom))
                                    .userName(userName)
                                    .limit(Integer.valueOf(limit))
                                    .page(Integer.valueOf(page))
                                    .build()
                    )
            );
            req.getRequestDispatcher(PagesUtil.ROOMS).forward(req, resp);
        } else {
            redirectToRoomPage(req, resp, roomService.getById(Long.parseLong(id)));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        ApplicationContext context = (ApplicationContext) getServletContext().getAttribute("applicationContext");
        RoomService roomService = context.getBean(RoomService.class);
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
    private static void redirectToRoomPage(HttpServletRequest req, HttpServletResponse resp, RoomEntity room) {
        req.setAttribute("room", room);
        req.getRequestDispatcher(PagesUtil.ROOM).forward(req, resp);
    }

    @SneakyThrows
    private static void onFailedRemoval(HttpServletRequest req, HttpServletResponse resp, RoomEntity room) {
        req.setAttribute("room", room);
        req.setAttribute("error", true);
        req.getRequestDispatcher(PagesUtil.ROOM).forward(req, resp);
    }

}
