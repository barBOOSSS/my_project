package by.pleshkov.repository;


import by.pleshkov.connection.AbstractConnection;
import by.pleshkov.constant.ClassRoom;
import by.pleshkov.constant.Solution;
import by.pleshkov.constant.StatusOrder;
import by.pleshkov.model.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderRepository implements IOrderRepository<Order> {

    public boolean create(Order order) {
        final String sql = "INSERT INTO tickets (user, places, class, status, solution) VALUES (?,?,?,?,?)";
        Connection conn = null;
        try {
            conn = AbstractConnection.connect();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, order.getUser());
            stmt.setInt(2, order.getPlaces());
            stmt.setString(3, String.valueOf(order.getClassRoom()));
            stmt.setString(4, String.valueOf(order.getStatusOrder()));
            stmt.setString(5, String.valueOf(order.getSolution()));
            boolean isExecution = stmt.execute();
            return isExecution;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                AbstractConnection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public Order read(String input) {
        Order order = new Order();
        final String sql = "SELECT * FROM orders";
        Connection conn = null;
        try {
            conn = AbstractConnection.connect();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                if (input.equals(rs.getString("user"))) {
                    order.setId(rs.getLong("id"));
                    order.setUser(rs.getString("user"));
                    order.setPlaces(rs.getInt("places"));
                    order.setClassRoom(ClassRoom.valueOf("class"));
                    order.setStatusOrder(StatusOrder.valueOf("ststus"));
                    order.setSolution(Solution.valueOf("solution"));
                    break;
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                AbstractConnection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return order;
    }

    @Override
    public boolean update(Order order) {
        final String sql = "UPDATE orders SET user=?, places=?, class=?, status=?, solution=? WHERE id=?";
        Connection conn = null;
        try {
            conn = AbstractConnection.connect();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, order.getUser());
            stmt.setInt(2, order.getPlaces());
            stmt.setString(3, String.valueOf(order.getClassRoom()));
            stmt.setString(4, String.valueOf(order.getStatusOrder()));
            stmt.setString(5, String.valueOf(order.getSolution()));
            stmt.setInt(6, (int) order.getId());
            boolean isExecution = stmt.execute();
            return isExecution;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                AbstractConnection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public boolean delete(Order order) {
        final String sql = "DELETE FROM orders WHERE id=?";
        Connection conn = null;
        try {
            conn = AbstractConnection.connect();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, (int) order.getId());
            boolean isExecution = stmt.execute();
            return isExecution;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                AbstractConnection.close();
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
            conn = AbstractConnection.connect();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getLong("id"));
                order.setUser(rs.getString("user"));
                order.setPlaces(Integer.parseInt("places"));
                order.setClassRoom(ClassRoom.valueOf("class"));
                order.setStatusOrder(StatusOrder.valueOf("status"));
                order.setSolution(Solution.valueOf("solution"));
                orders.add(order);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                AbstractConnection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return orders;
    }
}
