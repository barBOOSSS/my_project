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
        userRepository.findAllByIdIn(room.getUsersIds())
                .forEach(newRoom::addUser);
        return roomRepository.save(newRoom).getId();
    }

    public Optional<RoomReadDto> update(Long id, RoomCreationDto update) {
        Optional<RoomEntity> existedRoom = roomRepository.findById(id);
        if (existedRoom.isPresent()) {
            RoomEntity room = existedRoom.get();
            room.setNumber(update.getNumber());
            room.setPlaces(update.getPlaces());
            room.setStatusRoom(update.getStatusRoom());
            room.setPrice(update.getPrice());
            room.setClassRoom(update.getClassRoom());
            List<UserEntity> users = userRepository.findAllByIdIn(update.getUsersIds());
            existedRoom.get().setUsers(users);
            return Optional.of(toReadDto(roomRepository.save(room)));
        }
        return Optional.empty();
    }

    public void delete(Long id) {
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
                        .toList());
    }
}
