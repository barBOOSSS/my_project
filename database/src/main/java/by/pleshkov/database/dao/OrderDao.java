package by.pleshkov.database.dao;


import by.pleshkov.database.connection.ConnectionPool;
import by.pleshkov.database.constant.Solution;
import by.pleshkov.database.constant.StatusOrder;
import by.pleshkov.database.entity.Order;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderDao {

    private static final OrderDao INSTANCE = new OrderDao();
    public static final String SELECT_ALL = "SELECT * FROM orders";
    public static final String SELECT_BY_ID = SELECT_ALL + " WHERE id = ?";
    public static final String INSERT = "INSERT INTO orders (status_order, solution) VALUES (?, ?)";
    public static final String UPDATE = "UPDATE orders SET status_order = ?, solution = ? WHERE id = ?";
    public static final String DELETE_BY_ID = "DELETE FROM orders WHERE id = ?";

    public Optional<Order> create(Order order) {
        try (Connection connection = ConnectionPool.get();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, String.valueOf(order.getStatusOrder()));
            preparedStatement.setString(2, String.valueOf(order.getSolution()));
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                order.setId(generatedKeys.getLong("id"));
            }
            return Optional.of(order);
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public Optional<Order> findByID(Long id) {
        try (Connection connection = ConnectionPool.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next() ? Optional.of(Order.builder()
                    .id(resultSet.getLong("id"))
                    .statusOrder(StatusOrder.valueOf(resultSet.getString("status_order")))
                    .solution(Solution.valueOf(resultSet.getString("solution")))
                    .build()) : Optional.empty();
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public Optional<Order> update(Order order) {
        try (Connection connection = ConnectionPool.get();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {
            preparedStatement.setString(1, String.valueOf(order.getStatusOrder()));
            preparedStatement.setString(2, String.valueOf(order.getSolution()));
            preparedStatement.setLong(3, order.getId());
            preparedStatement.executeUpdate();
            return Optional.of(order);
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public boolean delete(Long id) {
        try (Connection connection = ConnectionPool.get();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_ID)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Order> getAll() {
        List<Order> orders = new ArrayList<>();
        try (Connection connection = ConnectionPool.get();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SELECT_ALL);
            while (resultSet.next()) {
                orders.add(Order.builder()
                        .id(resultSet.getLong("id"))
                        .statusOrder(StatusOrder.valueOf(resultSet.getString("status_order")))
                        .solution(Solution.valueOf(resultSet.getString("solution")))
                        .build());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public static OrderDao getInstance() {
        return INSTANCE;
    }
}
