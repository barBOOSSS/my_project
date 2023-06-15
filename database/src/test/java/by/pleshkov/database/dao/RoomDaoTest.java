package by.pleshkov.database.dao;

import by.pleshkov.database.constant.ClassRoom;
import by.pleshkov.database.constant.StatusRoom;
import by.pleshkov.database.dto.RoomDto;
import by.pleshkov.database.dto.RoomFilter;
import by.pleshkov.database.entity.RoomEntity;
import by.pleshkov.database.hibernate.HibernateFactory;
import lombok.Cleanup;
import org.hibernate.Session;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Assertions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RoomDaoTest {

    private static final RoomDao roomDao = RoomDao.getInstance();
    private static final HibernateFactory sessionFactory = HibernateFactory.getInstance();

    @Test
    @Order(1)
    void whenFindAllInvoked_ThenAllTheRoomsAreReturned() {
        @Cleanup Session session = sessionFactory.getSession();
        Integer[] actual = roomDao.findAll(session)
                .stream()
                .map(RoomEntity::getNumber)
                .toArray(Integer[]::new);
        Integer[] expected = (List.of(1, 2, 3, 4)
                .toArray(Integer[]::new));
        assertArrayEquals(expected, actual);
    }

    @Test
    @Order(2)
    void whenFindAllByUserInvoked_ThenAllTheRoomsOfUserAreReturned() {
        @Cleanup Session session = sessionFactory.getSession();
        Integer[] actual = roomDao.findByUser(session, "Bred")
                .stream()
                .map(RoomEntity::getNumber)
                .toArray(Integer[]::new);
        Integer[] expected = (List.of(1, 2)
                .toArray(Integer[]::new));
        assertArrayEquals(expected, actual);
    }

    @Test
    @Order(3)
    void whenFindAllByFilterContainsOnlyUserInvoked_ThenAllTheFilteredByUserRoomsAreReturned() {
        @Cleanup Session session = sessionFactory.getSession();
        RoomFilter filter = RoomFilter.builder()
                .userName("Bred")
                .build();
        Integer[] actual = roomDao.findByFilter(session, filter)
                .stream()
                .map(RoomEntity::getNumber)
                .toArray(Integer[]::new);
        Integer[] expected = (List.of(1, 2)
                .toArray(Integer[]::new));
        assertArrayEquals(expected, actual);
    }

    @Test
    @Order(4)
    void whenFindAllByFilterContainsUserAndPlacesInvoked_ThenAllTheFilteredByUserAndPlacesRoomsAreReturned() {
        @Cleanup Session session = sessionFactory.getSession();
        RoomFilter filter = RoomFilter.builder()
                .userName("Bred")
                .places(1)
                .build();
        Integer[] actual = roomDao.findByFilter(session, filter)
                .stream()
                .map(RoomEntity::getNumber)
                .toArray(Integer[]::new);
        Integer[] expected = (List.of(1)
                .toArray(Integer[]::new));
        assertArrayEquals(expected, actual);
    }

    @Test
    @Order(5)
    void whenFindAllDtosInvoked_ThenAllTheRoomDtosAreReturned() {
        @Cleanup Session session = sessionFactory.getSession();
        RoomDto[] actual = roomDao.findAllDtos(session).toArray(RoomDto[]::new);
        RoomDto[] expected = roomDao.findAll(session)
                .stream()
                .map(room -> new RoomDto(room.getNumber(),
                        room.getUsers().size() > 0
                                ? room.getUsers().get(0).getName() :
                                null))
                .toArray(RoomDto[]::new);
        assertArrayEquals(expected, actual);
    }

    @Test
    @Order(6)
    void whenFindAllByIdInvoked_ThenAllTheRoomsOfIdAreReturn() {
        @Cleanup Session session = sessionFactory.getSession();
        Integer[] actual = roomDao.findByID(session, 1L)
                .stream()
                .map(RoomEntity::getNumber)
                .toArray(Integer[]::new);
        Integer[] expected = (List.of(1)
                .toArray(Integer[]::new));
        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    @Order(7)
    void whenCreatedInvokeWithRoom_ThenRoomIsSaved() {
        RoomEntity testRoom = RoomEntity.builder()
                .number(1)
                .places(2)
                .price(300)
                .classRoom(ClassRoom.STANDARD)
                .statusRoom(StatusRoom.FREE)
                .build();
        @Cleanup Session session = sessionFactory.getSession();
        var transaction = session.beginTransaction();
        roomDao.create(session, testRoom);
        transaction.commit();
        List<Integer> all = roomDao.findAll(session).stream()
                .map(RoomEntity::getNumber)
                .toList();
        assertTrue(all.contains(testRoom.getNumber()));
    }

    @Test
    @Order(8)
    void whenDeletedInvokeWithRoom_ThenRoomIsDeleted() {
        @Cleanup Session session = sessionFactory.getSession();
        var transaction = session.beginTransaction();
        roomDao.delete(session, 5L);
        transaction.commit();
        Integer[] actual = roomDao.findAll(session)
                .stream()
                .map(RoomEntity::getNumber)
                .toArray(Integer[]::new);
        Integer[] expected = (List.of(1, 2, 3, 4)
                .toArray(Integer[]::new));
        assertArrayEquals(expected, actual);
    }

    @Test
    @Order(9)
    void whenUpdatedInvokeWithRoom_ThenRoomIsUpdated() {
        @Cleanup Session session = sessionFactory.getSession();
        var transaction = session.beginTransaction();
        RoomEntity testRoom = roomDao.findByID(session, 1L)
                .orElse(RoomEntity.builder()
                        .number(9)
                        .places(2)
                        .build());
        testRoom.setNumber(10);
        roomDao.update(session, testRoom);
        transaction.commit();
        Integer[] actual = roomDao.findByID(session, 1L)
                .stream()
                .map(RoomEntity::getNumber)
                .toArray(Integer[]::new);
        Integer[] expected = (List.of(10)
                .toArray(Integer[]::new));
        Assertions.assertArrayEquals(expected, actual);
    }
}