package by.pleshkov.database.dao;

import by.pleshkov.database.entity.Room;
import org.junit.Test;

import java.util.List;

public class RoomDaoTest {

    private RoomDao roomDao = new RoomDao();

    @Test
    public void getAll() {
        List<Room> rooms = roomDao.findAll();
        System.out.println();
    }

}