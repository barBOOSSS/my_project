package by.pleshkov.service.service;


import by.pleshkov.database.dao.RoomDao;
import by.pleshkov.database.dto.RoomFilter;
import by.pleshkov.database.entity.RoomEntity;
import by.pleshkov.database.hibernate.HibernateFactory;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class RoomService {
    private static final RoomService INSTANCE = new RoomService();
    private final RoomDao roomDao = RoomDao.getInstance();
    private final HibernateFactory hibernateFactory = HibernateFactory.getInstance();

    public List<RoomEntity> getFindByFilter(RoomFilter filter) {
        List<RoomEntity> rooms;
        try (Session session = hibernateFactory.getSession()) {
            Transaction transaction = session.beginTransaction();
            rooms = roomDao.findByFilter(session, filter);
            transaction.commit();
        }
        return rooms;
    }

    public Optional<RoomEntity> create(RoomEntity room) {
        Optional<RoomEntity> newRoom;
        try (Session session = hibernateFactory.getSession()) {
            Transaction transaction = session.beginTransaction();
            newRoom = roomDao.create(session, room);
            transaction.commit();
        }
        return newRoom;
    }

    public RoomEntity getById(Long id) {
        RoomEntity room;
        try (Session session = hibernateFactory.getSession()) {
            Transaction transaction = session.beginTransaction();
            room = roomDao.findByID(session, id)
                    .orElse(RoomEntity.builder()
                            .number(1)
                            .places(2)
                            .build());
            transaction.commit();
        }
        return room;
    }

    public Optional<RoomEntity> update(RoomEntity room) {
        Optional<RoomEntity> newRoom;
        try (Session session = hibernateFactory.getSession()) {
            Transaction transaction = session.beginTransaction();
            newRoom = roomDao.update(session, room);
            transaction.commit();
        }
        return newRoom;
    }

    public boolean delete(Long id) {
        try (Session session = hibernateFactory.getSession()) {
            Transaction transaction = session.beginTransaction();
            if (roomDao.delete(session, id)) {
                transaction.commit();
                return true;
            } else {
                transaction.commit();
                return false;
            }
        }
    }

    public List<RoomEntity> getAll() {
        List<RoomEntity> rooms;
        try (Session session = hibernateFactory.getSession()) {
            Transaction transaction = session.beginTransaction();
            rooms = roomDao.findAll(session);
            transaction.commit();
        }
        return rooms;
    }

    public static RoomService getInstance() {
        return INSTANCE;
    }
}
