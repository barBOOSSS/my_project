package by.pleshkov.web.controller;

import by.pleshkov.database.constant.StatusRoom;
import by.pleshkov.database.dto.RoomCreationDto;
import by.pleshkov.database.dto.RoomFilter;
import by.pleshkov.service.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
        return roomService.getById(id).
                map(room -> {
                    model.addAttribute("room", room);
                    return "room";
                })
                .orElse("redirect:/rooms");
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('MANAGER')")
    @GetMapping(path = "/create")
    public String createRoomPage() {
        return "roomCreated";
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('MANAGER')")
    @PostMapping(path = "/create")
    public String createRoom(RoomCreationDto room) {
        room.setStatusRoom(StatusRoom.FREE);
        return "redirect:/rooms/" + roomService.create(room);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('MANAGER')")
    @GetMapping(path = "/update/{id}")
    public String editRoomPage(Model model, @PathVariable Long id) {
        return roomService.getById(id).
                map(room -> {
                    model.addAttribute("room", room);
                    return "roomEdit";
                })
                .orElse("redirect:/rooms");
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('MANAGER')")
    @PostMapping(path = "/update/{id}")
    public String updateRoom(@PathVariable Long id, RoomCreationDto room) {
        return roomService.update(id, room).map(
                        updateRoom -> "redirect:/rooms/" + id
                )
                .orElse("redirect:/rooms/update/{id}?error=true");
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('MANAGER')")
    @PostMapping(path = "/{id}/delete")
    public String deleteRoom(@PathVariable Long id) {
        roomService.delete(id);
        return "redirect:/rooms";
    }
}
