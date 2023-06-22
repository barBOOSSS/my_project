package by.pleshkov.web.controller;

import by.pleshkov.database.constant.StatusRoom;
import by.pleshkov.database.dto.RoomFilter;
import by.pleshkov.database.dto.RoomReadDto;
import by.pleshkov.database.entity.RoomEntity;
import by.pleshkov.service.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static by.pleshkov.web.util.PagesUtil.ROOMS;

@Controller
@RequestMapping(ROOMS)
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @GetMapping
    public String getRoomsPage(Model model, RoomFilter roomFilter) {
        model.addAttribute("rooms", roomService.getFindByFilter(roomFilter));
        return "rooms";
    }

    @GetMapping(path = "/{id}")
    public String getRoomPage(Model model, @PathVariable Long id) {
        model.addAttribute("room", roomService.getById(id));
        return "room";
    }

    @GetMapping(path = "/create")
    public String createRoomPage() {
        return "roomCreated";
    }

    @PostMapping(path = "/create")
    public String createRoom(RoomEntity room) {
        room.setStatusRoom(StatusRoom.FREE);
        RoomEntity newRoom = roomService.save(room);
        return "redirect:/rooms/" + newRoom.getId();
    }
}
