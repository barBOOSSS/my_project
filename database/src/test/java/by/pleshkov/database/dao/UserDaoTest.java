package by.pleshkov.database.dao;

import by.pleshkov.database.TestDataImporter;
import by.pleshkov.database.entity.UserEntity;
import by.pleshkov.database.hibernate.HibernateFactory;
import lombok.Cleanup;
import org.hibernate.Session;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Assertions;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserDaoTest {

    private static final UserDao userDao = UserDao.getInstance();
    private static final HibernateFactory sessionFactory = HibernateFactory.getInstance();

    @BeforeAll
    static void beforeAll() {
        try (Session session = sessionFactory.getSession()) {
            var transaction = session.beginTransaction();
            TestDataImporter.importTestData(session);
            transaction.commit();
        }
    }

    @Test
    @Order(1)
    void whenFindAllInvoked_ThenAllTheUsersAreReturned() {
        @Cleanup Session session = sessionFactory.getSession();
        String[] actual = userDao.findAll(session)
                .stream()
                .map(UserEntity::getEmail)
                .toArray(String[]::new);
        String[] expected = (List.of("pit@gmail.com", "vin@gmail.com")
                .toArray(String[]::new));
        assertArrayEquals(expected, actual);
    }

    @Test
    @Order(2)
    void whenGetByEmailAndPassContainsOnlyUserInvoked_ThenAllTheFilteredByUserRoomsAreReturned() {
        @Cleanup Session session = sessionFactory.getSession();
        UserEntity testUser = userDao.findByID(session, 1L)
                .orElse(UserEntity.builder()
                        .name("XXX")
                        .surname("XXX")
                        .email("xxx@gmail.com")
                        .password("XXX")
                        .build());
        String[] actual = userDao.getByEmailAndPass(session, testUser.getEmail(), testUser.getPassword())
                .stream()
                .map(UserEntity::getName)
                .toArray(String[]::new);
        String[] expected = (List.of("Bred")
                .toArray(String[]::new));
        assertArrayEquals(expected, actual);
    }

    @Test
    @Order(3)
    void whenFindAllByIdInvoked_ThenAllTheUsersOfIdAreReturn() {
        @Cleanup Session session = sessionFactory.getSession();
        String[] actual = userDao.findByID(session, 1L)
                .stream()
                .map(UserEntity::getEmail)
                .toArray(String[]::new);
        String[] expected = (List.of("pit@gmail.com")
                .toArray(String[]::new));
        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    @Order(4)
    void whenCreatedInvokeWithUser_ThenUserIsSaved() {
        UserEntity testUser = UserEntity.builder()
                .name("Tom")
                .surname("Cruise")
                .email("tom@gmail.com")
                .password("333")
                .build();
        @Cleanup Session session = sessionFactory.getSession();
        var transaction = session.beginTransaction();
        userDao.create(session, testUser);
        transaction.commit();
        List<String> all = userDao.findAll(session).stream()
                .map(UserEntity::getEmail)
                .toList();
        assertTrue(all.contains(testUser.getEmail()));
    }

    @Test
    @Order(5)
    void whenDeletedInvokeWithUser_ThenUserIsDeleted() {
        @Cleanup Session session = sessionFactory.getSession();
        var transaction = session.beginTransaction();
        userDao.delete(session, 3L);
        transaction.commit();
        String[] actual = userDao.findAll(session)
                .stream()
                .map(UserEntity::getEmail)
                .toArray(String[]::new);
        String[] expected = (List.of("pit@gmail.com", "vin@gmail.com")
                .toArray(String[]::new));
        assertArrayEquals(expected, actual);
    }

    @Test
    @Order(6)
    void whenUpdatedInvokeWithUser_ThenUserIsUpdated() {
        @Cleanup Session session = sessionFactory.getSession();
        var transaction = session.beginTransaction();
        UserEntity testUser = userDao.findByID(session, 1L)
                .orElse(UserEntity.builder()
                        .name("XXX")
                        .surname("XXX")
                        .email("xxx@gmail.com")
                        .password("XXX")
                        .build());
        testUser.setEmail("bredpit@gmail.com");
        userDao.update(session, testUser);
        transaction.commit();
        String[] actual = userDao.findByID(session, 1L)
                .stream()
                .map(UserEntity::getEmail)
                .toArray(String[]::new);
        String[] expected = (List.of("bredpit@gmail.com")
                .toArray(String[]::new));
        Assertions.assertArrayEquals(expected, actual);
    }
}