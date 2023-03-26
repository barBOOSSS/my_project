package by.pleshkov.repository;


import by.pleshkov.connection.AbstractConnection;
import by.pleshkov.constant.Role;
import by.pleshkov.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserRepository implements IUserRepository<User> {

    public boolean create(User user) {
        final String sql = "INSERT INTO users (login, password, role) VALUES (?,?,?)";
        Connection conn = null;
        try {
            conn = AbstractConnection.connect();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, user.getLogin());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, String.valueOf(user.getRole()));
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
    public User read(String login) {
        User user = new User();
        final String sql = "SELECT * FROM users";
        Connection conn = null;
        try {
            conn = AbstractConnection.connect();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                if (login.equals(rs.getString("login"))) {
                    user.setId(rs.getLong("id"));
                    user.setLogin(rs.getString("login"));
                    user.setPassword(rs.getString("password"));
                    user.setRole(Role.valueOf(rs.getString("role")));
                    return user;
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
        return user;
    }

    public boolean update(User user) {
        final String sql = "UPDATE users SET login=?, password=?, role=? WHERE id=?";
        Connection conn = null;
        try {
            conn = AbstractConnection.connect();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, user.getLogin());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, String.valueOf(user.getRole()));
            stmt.setInt(4, (int) user.getId());
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

    public boolean delete(User user) {
        final String sql = "DELETE FROM users WHERE login=?";
        Connection conn = null;
        try {
            conn = AbstractConnection.connect();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, user.getLogin());
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

    public List<User> readAll() {
        List<User> users = new ArrayList<>();
        final String sql = "SELECT * FROM users";
        Connection conn = null;
        try {
            conn = AbstractConnection.connect();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setLogin(rs.getString("login"));
                user.setPassword(rs.getString("password"));
                user.setRole(Role.valueOf(rs.getString("role")));
                users.add(user);
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
        return users;
    }
}
