package by.pleshkov.web.controller;

import by.pleshkov.database.dto.RoomReadDto;
import by.pleshkov.service.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static by.pleshkov.web.util.PagesUtil.API;
import static by.pleshkov.web.util.PagesUtil.ROOMS;

@RestController
@RequestMapping(API + ROOMS)
@RequiredArgsConstructor
public class RoomRestController {

    private final RoomService roomService;

    @GetMapping
    public List<RoomReadDto> getAllRooms() {
        return roomService.getAll();
    }

//    @GetMapping(path = "{id}")
//    public RoomReadDto getRoom(@PathVariable Long id) {
//        return roomService.getById(id);
//    }
}
