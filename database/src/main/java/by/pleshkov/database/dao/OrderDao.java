package by.pleshkov.database.dao;


import by.pleshkov.database.entity.OrderEntity;
import org.hibernate.Session;

import java.util.List;
import java.util.Optional;

public class OrderDao {

    private static final OrderDao INSTANCE = new OrderDao();

    public Optional<OrderEntity> create(Session session, OrderEntity order) {
        session.persist(order);
        return Optional.ofNullable(order);
    }

    public Optional<OrderEntity> findByID(Session session, Long id) {
        return Optional.ofNullable(session.get(OrderEntity.class, id));
    }

    public Optional<OrderEntity> update(Session session, OrderEntity order) {
        return Optional.ofNullable(session.merge(order));
    }

    public boolean delete(Session session, Long id) {
        return Optional.ofNullable(session.get(OrderEntity.class, id))
                .map(order -> {
                    session.remove(order);
                    return true;
                })
                .orElse(false);
    }

    public List<OrderEntity> findAll(Session session) {
        return session.createQuery("SELECT order FROM OrderEntity  order", OrderEntity.class).list();
    }

    public static OrderDao getInstance() {
        return INSTANCE;
    }
}
