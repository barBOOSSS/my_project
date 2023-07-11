package by.pleshkov.service.service;

import by.pleshkov.database.constant.ClassRoom;
import by.pleshkov.database.constant.StatusRoom;
import by.pleshkov.database.dto.RoomFilter;
import by.pleshkov.database.entity.RoomEntity;
import by.pleshkov.database.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RoomService {

    private final RoomRepository roomRepository;

    public List<RoomEntity> getFindByFilter(RoomFilter filter) {
        return roomRepository.findByFilter(filter);
    }

    public RoomEntity save(RoomEntity room) {
        return roomRepository.save(room);
    }

    public RoomEntity getById(Long id) {
        return roomRepository.findById(id)
                .orElse(RoomEntity.builder()
                        .number(100)
                        .price(1)
                        .statusRoom(StatusRoom.FREE)
                        .classRoom(ClassRoom.STANDARD)
                        .places(1)
                        .build());
    }

    public List<RoomEntity> getAll() {
        return roomRepository.findAll();
    }

    public boolean delete(Long id) {
        roomRepository.deleteById(id);
        return roomRepository.findById(id).isEmpty();
    }
}
