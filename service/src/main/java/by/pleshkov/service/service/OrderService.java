package by.pleshkov.service.service;

import by.pleshkov.database.constant.Solution;
import by.pleshkov.database.constant.StatusOrder;
import by.pleshkov.database.dao.OrderDao;
import by.pleshkov.database.entity.OrderEntity;
import by.pleshkov.database.hibernate.HibernateFactory;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class OrderService {

    private static final OrderService INSTANCE = new OrderService();
    private final OrderDao orderDao = OrderDao.getInstance();
    private final HibernateFactory hibernateFactory = HibernateFactory.getInstance();

    public Optional<OrderEntity> save(OrderEntity order) {
        Optional<OrderEntity> newOrder;
        try (Session session = hibernateFactory.getSession()) {
            Transaction transaction = session.beginTransaction();
            newOrder = orderDao.create(session, order);
            transaction.commit();
        }
        return newOrder;
    }

    public OrderEntity getById(Long id) {
        OrderEntity order;
        try (Session session = hibernateFactory.getSession()) {
            Transaction transaction = session.beginTransaction();
            order = orderDao.findByID(session, id)
                    .orElse(OrderEntity.builder()
                            .statusOrder(StatusOrder.NEW)
                            .solution(Solution.DENIED)
                            .build());
            transaction.commit();
        }
        return order;
    }

    public Optional<OrderEntity> update(OrderEntity order) {
        Optional<OrderEntity> newOrder;
        try (Session session = hibernateFactory.getSession()) {
            Transaction transaction = session.beginTransaction();
            newOrder = orderDao.update(session, order);
            transaction.commit();
        }
        return newOrder;
    }

    public boolean delete(Long id) {
        try (Session session = hibernateFactory.getSession()) {
            Transaction transaction = session.beginTransaction();
            if (orderDao.delete(session, id)) {
                transaction.commit();
                return true;
            } else {
                transaction.commit();
                return false;
            }
        }
    }

    public List<OrderEntity> readAll() {
        List<OrderEntity> orders;
        try (Session session = hibernateFactory.getSession()) {
            Transaction transaction = session.beginTransaction();
            orders = orderDao.findAll(session);
            transaction.commit();
        }
        return orders;
    }

    public static OrderService getInstance() {
        return INSTANCE;
    }
}
