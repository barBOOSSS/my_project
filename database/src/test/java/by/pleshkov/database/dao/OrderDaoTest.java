package by.pleshkov.database.dao;

import by.pleshkov.database.constant.Solution;
import by.pleshkov.database.constant.StatusOrder;
import by.pleshkov.database.entity.OrderEntity;
import by.pleshkov.database.entity.UserEntity;
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

public class OrderDaoTest {

    private static final OrderDao orderDao = OrderDao.getInstance();
    private static final UserDao userDao = UserDao.getInstance();
    private static final HibernateFactory sessionFactory = HibernateFactory.getInstance();

    @Test
    @Order(1)
    void whenFindAllInvoked_ThenAllTheOrdersAreReturned() {
        @Cleanup Session session = sessionFactory.getSession();
        Integer[] actual = orderDao.findAll(session)
                .stream()
                .map(OrderEntity::getPrice)
                .toArray(Integer[]::new);
        Integer[] expected = (List.of(200, 300, 400)
                .toArray(Integer[]::new));
        assertArrayEquals(expected, actual);
    }

    @Test
    @Order(2)
    void whenFindAllByIdInvoked_ThenAllTheOrdersOfIdAreReturn() {
        @Cleanup Session session = sessionFactory.getSession();
        Integer[] actual = orderDao.findByID(session, 1L)
                .stream()
                .map(OrderEntity::getPrice)
                .toArray(Integer[]::new);
        Integer[] expected = (List.of(200)
                .toArray(Integer[]::new));
        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    @Order(3)
    void whenCreatedInvokeWithOrder_ThenOrderIsSaved() {
        OrderEntity testOrder = OrderEntity.builder()
                .price(500)
                .statusOrder(StatusOrder.NEW)
                .solution(Solution.UNPROCESSED)
                .build();
        @Cleanup Session session = sessionFactory.getSession();
        var transaction = session.beginTransaction();
        UserEntity testUser = userDao.findByID(session, 1L)
                .orElse(UserEntity.builder()
                        .name("XXX")
                        .surname("XXX")
                        .email("xxx@gmail.com")
                        .password("XXX")
                        .build());
        testUser.addOrder(testOrder);
        orderDao.create(session, testOrder);
        transaction.commit();
        List<Integer> all = orderDao.findAll(session).stream()
                .map(OrderEntity::getPrice)
                .toList();
        assertTrue(all.contains(testOrder.getPrice()));
    }

    @Test
    @Order(5)
    void whenDeletedInvokeWithOrder_ThenOrderIsDeleted() {
        @Cleanup Session session = sessionFactory.getSession();
        var transaction = session.beginTransaction();
        orderDao.delete(session, 4L);
        transaction.commit();
        Integer[] actual = orderDao.findAll(session)
                .stream()
                .map(OrderEntity::getPrice)
                .toArray(Integer[]::new);
        Integer[] expected = (List.of(200, 300, 400)
                .toArray(Integer[]::new));
        assertArrayEquals(expected, actual);
    }

    @Test
    @Order(6)
    void whenUpdatedInvokeWithOrder_ThenOrderIsUpdated() {
        @Cleanup Session session = sessionFactory.getSession();
        var transaction = session.beginTransaction();
        OrderEntity testOrder = orderDao.findByID(session, 1L)
                .orElse(OrderEntity.builder()
                        .price(0)
                        .statusOrder(StatusOrder.NEW)
                        .solution(Solution.UNPROCESSED)
                        .build());
        testOrder.setPrice(1000);
        orderDao.update(session, testOrder);
        transaction.commit();
        Integer[] actual = orderDao.findByID(session, 1L)
                .stream()
                .map(OrderEntity::getPrice)
                .toArray(Integer[]::new);
        Integer[] expected = (List.of(1000)
                .toArray(Integer[]::new));
        Assertions.assertArrayEquals(expected, actual);
    }

}