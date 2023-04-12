package by.pleshkov.service.service;

import by.pleshkov.database.dao.RoomDao;
import by.pleshkov.database.model.Room;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class RoomService implements IService<Room> {
    private static final RoomService INSTANCE = new RoomService();
    private final Logger log = Logger.getLogger(this.getClass().getSimpleName());
    private final RoomDao roomDao = RoomDao.getInstance();


    public Room create(Room room) {
        try {
            if (roomDao.create(room).equals(null)) {
                log.log(Level.INFO, "Комната не создана");
                return null;
            } else {
                return room;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Room read(long id) {
        try {
            Room room = roomDao.read(id);
            if (room.getNumberRoom() == 0) {
                log.log(Level.INFO, "Комната не найдена");
                return null;
            } else {
                return room;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public boolean update(Room room) {
        try {
            if (roomDao.update(room)) {
                log.log(Level.INFO, "Комната не обновлена");
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean delete(long id) {
        try {
            if (roomDao.delete(id)) {
                log.log(Level.INFO, "Комната не удалена");
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<Room> readAll() {
        return roomDao.readAll();
    }

    public static RoomService getInstance() {
        return INSTANCE;
    }
}
