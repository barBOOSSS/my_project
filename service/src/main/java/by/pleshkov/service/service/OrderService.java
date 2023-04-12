package by.pleshkov.service.service;

import by.pleshkov.database.dao.OrderDao;
import by.pleshkov.database.model.Order;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class OrderService implements IService<Order> {

    private static final OrderService INSTANCE = new OrderService();
    private final Logger log = Logger.getLogger(this.getClass().getSimpleName());
    private final OrderDao orderDao = OrderDao.getInstance();

    public Order create(Order order) {
        try {
            if (orderDao.create(order).equals(null)) {
                log.log(Level.INFO, "Заказ не создан");
                return null;
            } else {
                return order;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Order read(long id) {
        try {
            Order order = orderDao.read(id);
            if (order.equals(null)) {
                log.log(Level.INFO, "Заказ не найден");
                return null;
            } else {
                return order;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean update(Order order) {
        try {
            if (orderDao.update(order)) {
                log.log(Level.INFO, "Заказ не обновлен");
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public boolean delete(long id) {
        try {
            if (orderDao.delete(id)) {
                log.log(Level.INFO, "Заказ не удален");
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<Order> readAll() {
        return orderDao.readAll();
    }

    public static OrderService getInstance() {
        return INSTANCE;
    }
}
