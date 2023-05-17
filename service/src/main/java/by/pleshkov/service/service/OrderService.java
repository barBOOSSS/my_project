package by.pleshkov.service.service;

import by.pleshkov.database.constant.Solution;
import by.pleshkov.database.constant.StatusOrder;
import by.pleshkov.database.dao.OrderDao;
import by.pleshkov.database.entity.OrderEntity;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class OrderService {

    private static final OrderService INSTANCE = new OrderService();
    private final OrderDao orderDao = OrderDao.getInstance();


    public Optional<OrderEntity> save(OrderEntity order) {
        return orderDao.create(order);
    }

    public OrderEntity getById(Long id) {
        return orderDao.findByID(id)
                .orElse(OrderEntity.builder()
                        .statusOrder(StatusOrder.NEW)
                        .solution(Solution.DENIED)
                        .build());
    }

    public Optional<OrderEntity> update(OrderEntity order) {
        return orderDao.update(order);
    }

    public boolean delete(Long id) {
        return orderDao.delete(id);
    }

    public List<OrderEntity> readAll() {
        return orderDao.getAll();
    }

    public static OrderService getInstance() {
        return INSTANCE;
    }
}
