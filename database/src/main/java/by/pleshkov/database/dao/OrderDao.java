package by.pleshkov.database.dao;


import by.pleshkov.database.connection.ConnectionManager;
import by.pleshkov.database.constant.ClassRoom;
import by.pleshkov.database.constant.Solution;
import by.pleshkov.database.constant.StatusOrder;
import by.pleshkov.database.model.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDao implements IDao<Order> {

    private static final OrderDao INSTANCE = new OrderDao();

    public Order create(Order order) {
        final String sql = "INSERT INTO orders (user, places, class, status, solution) VALUES (?,?,?,?,?)";
        final int firstParameter = 1;
        final int secondParameter = 2;
        final int thirdDParameter = 3;
        final int fourthParameter = 4;
        final int fightHParameter = 5;
        Connection conn = null;
        try {
            conn = ConnectionManager.connect();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(firstParameter, order.getUser());
            stmt.setInt(secondParameter, order.getPlaces());
            stmt.setString(thirdDParameter, String.valueOf(order.getClassRoom()));
            stmt.setString(fourthParameter, String.valueOf(order.getStatusOrder()));
            stmt.setString(fightHParameter, String.valueOf(order.getSolution()));
            stmt.execute();
            return order;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                ConnectionManager.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return order;
    }

    @Override
    public Order read(long id) {
        Order order = Order.builder().build();
        final String sql = "SELECT * FROM orders";
        Connection conn = null;
        try {
            conn = ConnectionManager.connect();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                if (id == rs.getInt("id")) {
                    order.setId(rs.getLong("id"));
                    order.setUser(rs.getString("user"));
                    order.setPlaces(rs.getInt("places"));
                    order.setClassRoom(ClassRoom.valueOf(rs.getString("class")));
                    order.setStatusOrder(StatusOrder.valueOf(rs.getString("status")));
                    order.setSolution(Solution.valueOf(rs.getString("solution")));
                    break;
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                ConnectionManager.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return order;
    }

    @Override
    public boolean update(Order order) {
        final String sql = "UPDATE orders SET user=?, places=?, class=?, status=?, solution=? WHERE id=?";
        final int firstParameter = 1;
        final int secondParameter = 2;
        final int thirdDParameter = 3;
        final int fourthParameter = 4;
        final int fightHParameter = 5;
        final int sixthParameter = 6;
        Connection conn = null;
        try {
            conn = ConnectionManager.connect();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(firstParameter, order.getUser());
            stmt.setInt(secondParameter, order.getPlaces());
            stmt.setString(thirdDParameter, String.valueOf(order.getClassRoom()));
            stmt.setString(fourthParameter, String.valueOf(order.getStatusOrder()));
            stmt.setString(fightHParameter, String.valueOf(order.getSolution()));
            stmt.setInt(sixthParameter, (int) order.getId());
            boolean isExecution = stmt.execute();
            return isExecution;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                ConnectionManager.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public boolean delete(long id) {
        final String sql = "DELETE FROM orders WHERE id=?";
        Connection conn = null;
        try {
            conn = ConnectionManager.connect();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, Math.toIntExact(id));
            boolean isExecution = stmt.execute();
            return isExecution;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                ConnectionManager.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public List<Order> readAll() {
        List<Order> orders = new ArrayList<>();
        final String sql = "SELECT * FROM orders";
        Connection conn = null;
        try {
            conn = ConnectionManager.connect();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Order order = Order.builder().build();
                order.setId(rs.getLong("id"));
                order.setUser(rs.getString("user"));
                order.setPlaces(rs.getInt("places"));
                order.setClassRoom(ClassRoom.valueOf(rs.getString("class")));
                order.setStatusOrder(StatusOrder.valueOf(rs.getString("status")));
                order.setSolution(Solution.valueOf(rs.getString("solution")));
                orders.add(order);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                ConnectionManager.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return orders;
    }

    public static OrderDao getInstance() {
        return INSTANCE;
    }
}
