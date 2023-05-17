package by.pleshkov.service.service;


import by.pleshkov.database.dao.RoomDao;
import by.pleshkov.database.dto.RoomFilter;
import by.pleshkov.database.entity.RoomEntity;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class RoomService {
    private static final RoomService INSTANCE = new RoomService();
    private final RoomDao roomDao = RoomDao.getInstance();

    public List<RoomEntity> getFindByFilter(RoomFilter filter) {
        return roomDao.findByFilter(filter);
    }

    public Optional<RoomEntity> create(RoomEntity room) {
        return roomDao.create(room);
    }

    public RoomEntity getById(Long id) {
        return roomDao.findByID(id)
                .orElse(RoomEntity.builder()
                        .number(1)
                        .places(2)
                        .build());
    }

    public Optional<RoomEntity> update(RoomEntity room) {
        return roomDao.update(room);
    }

    public boolean delete(Long id) {
            return roomDao.delete(id);
    }

    public List<RoomEntity> getAll() {
        return roomDao.findAll();
    }

    public static RoomService getInstance() {
        return INSTANCE;
    }
}
