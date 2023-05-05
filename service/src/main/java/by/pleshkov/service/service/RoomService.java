package by.pleshkov.service.service;


import by.pleshkov.database.dao.RoomDao;
import by.pleshkov.database.dto.RoomFilter;
import by.pleshkov.database.entity.Room;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class RoomService {
    private static final RoomService INSTANCE = new RoomService();
    private final RoomDao roomDao = RoomDao.getInstance();

    public List<Room> getFindByFilter(RoomFilter filter) {
        return roomDao.findByFilter(filter);
    }

    public Optional<Room> create(Room room) {
        return roomDao.create(room);
    }

    public Room getById(Long id) {
        return roomDao.findByID(id)
                .orElse(Room.builder()
                        .number(1)
                        .places(2)
                        .build());
    }

    public Optional<Room> update(Room room) {
        return roomDao.update(room);
    }

    public boolean delete(Long id) {
            return roomDao.delete(id);
    }

    public List<Room> getAll() {
        return roomDao.findAll();
    }

    public static RoomService getInstance() {
        return INSTANCE;
    }
}
