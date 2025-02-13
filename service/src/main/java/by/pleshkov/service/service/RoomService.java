package by.pleshkov.service.service;

import by.pleshkov.database.dto.RoomCreationDto;
import by.pleshkov.database.dto.RoomFilter;
import by.pleshkov.database.dto.RoomReadDto;
import by.pleshkov.database.entity.RoomEntity;
import by.pleshkov.database.entity.UserEntity;
import by.pleshkov.database.repository.RoomRepository;
import by.pleshkov.database.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static by.pleshkov.database.constant.StatusRoom.FREE;
import static by.pleshkov.database.constant.StatusRoom.NOT_FREE;

@Service
@RequiredArgsConstructor
@Transactional
public class RoomService {

    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    public List<RoomEntity> getFindByFilter(RoomFilter filter) {
        return roomRepository.findByFilter(filter);
    }

    public Optional<RoomReadDto> getById(Long id) {
        return roomRepository.findById(id)
                .map(this::toReadDto);
    }

    public Optional<RoomReadDto> getByNumber(Integer number) {
        return roomRepository.findByNumber(number)
                .map(this::toReadDto);
    }

    public List<RoomReadDto> getAll() {
        return roomRepository.findAll()
                .stream()
                .map(this::toReadDto)
                .toList();
    }

    public Long create(RoomCreationDto room) {
        RoomEntity newRoom = RoomEntity
                .builder()
                .number(room.getNumber())
                .places(room.getPlaces())
                .classRoom(room.getClassRoom())
                .price(room.getPrice())
                .statusRoom(room.getStatusRoom())
                .build();
        return roomRepository.save(newRoom).getId();
    }

    public Optional<RoomReadDto> update(Long id, RoomCreationDto update) {
        Optional<RoomEntity> existedRoom = roomRepository.findById(id);
        if (existedRoom.get().getStatusRoom() == NOT_FREE && update.getStatusRoom() == FREE) {
            Long userId = existedRoom.get().getUsers().stream()
                    .map(UserEntity::getId)
                    .findFirst().orElse(null);
            existedRoom.get().removeUser(userRepository.findById(userId).get());
        }
        RoomEntity room = existedRoom.get();
        room.setNumber(update.getNumber());
        room.setPlaces(update.getPlaces());
        room.setStatusRoom(update.getStatusRoom());
        room.setPrice(update.getPrice());
        room.setClassRoom(update.getClassRoom());
        if (update.getUserId() != null) {
            room.addUser(userRepository.findById(update.getUserId()).get());
        }
        return Optional.of(toReadDto(roomRepository.save(room)));
    }

    public void delete(Long id) {
        Optional<RoomEntity> room = roomRepository.findById(id);
        Long userId = room.get().getUsers().stream()
                .map(UserEntity::getId)
                .findFirst().orElse(null);
        if (userId != null) {
            room.get().removeUser(userRepository.findById(userId).get());
        }
        roomRepository.findById(id)
                .ifPresent(roomRepository::delete);
    }

    private RoomReadDto toReadDto(RoomEntity room) {
        return new RoomReadDto(room.getId(),
                room.getNumber(),
                room.getPlaces(),
                room.getClassRoom(),
                room.getPrice(),
                room.getStatusRoom(),
                room.getUsers().stream()
                        .map(UserEntity::getName)
                        .findFirst().orElse(null),
                room.getUsers().stream()
                        .map(UserEntity::getSurname)
                        .findAny().orElse(null));
    }
}
