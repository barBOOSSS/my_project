package by.pleshkov.web.controller;

import by.pleshkov.database.dto.RoomCreationDto;
import by.pleshkov.database.dto.RoomReadDto;
import by.pleshkov.service.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static by.pleshkov.web.util.PagesUtil.API;
import static by.pleshkov.web.util.PagesUtil.ROOMS;

@RestController
@RequestMapping(API + ROOMS)
@RequiredArgsConstructor
public class RoomRestController {

    private final RoomService roomService;

    @GetMapping
    public ResponseEntity<List<RoomReadDto>> getAllRooms() {
        return ResponseEntity.ok(roomService.getAll());
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<RoomReadDto> getRoom(@PathVariable Long id) {
        return roomService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('MANAGER')")
    @PostMapping
    public ResponseEntity<Long> createRoom(@RequestBody RoomCreationDto newRoom) {
        return ResponseEntity.ok(roomService.create(newRoom));
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('MANAGER')")
    @PutMapping(path = "{id}")
    public ResponseEntity<RoomReadDto> updateRoom(@PathVariable Long id, @RequestBody RoomCreationDto newRoom) {
        return roomService.update(id, newRoom)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('MANAGER')")
    @DeleteMapping(path = "{id}")
    public ResponseEntity<Object> deleteRoom(@PathVariable Long id) {
        roomService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
