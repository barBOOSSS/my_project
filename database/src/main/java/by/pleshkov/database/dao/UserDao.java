package by.pleshkov.database.dao;


import by.pleshkov.database.connection.ConnectionManager;
import by.pleshkov.database.constant.Role;
import by.pleshkov.database.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao implements IDao<User> {

    private static final UserDao INSTANCE = new UserDao();

    public User create(User user) {
        final String sql = "INSERT INTO users (login, password, role) VALUES (?,?,?)";
        final int firstParameter = 1;
        final int secondParameter = 2;
        final int thirdDParameter = 3;
        Connection conn = null;
        try {
            conn = ConnectionManager.connect();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(firstParameter, user.getLogin());
            stmt.setString(secondParameter, user.getPassword());
            stmt.setString(thirdDParameter, String.valueOf(user.getRole()));
            stmt.execute();
            return user;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                ConnectionManager.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public User read(long id) {
        User user = User.builder().build();
        final String sql = "SELECT * FROM users";
        Connection conn = null;
        try {
            conn = ConnectionManager.connect();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                if (id == rs.getInt("id")) {
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
                ConnectionManager.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return user;
    }

    public User readByLoginAndPassword(String login, String password) {
        User user = User.builder().build();
        final String sql = "SELECT * FROM users";
        Connection conn = null;
        try {
            conn = ConnectionManager.connect();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                if (login.equals(rs.getString("login")) && password.equals(rs.getString("password"))) {
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
                ConnectionManager.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return user;
    }

    public boolean update(User user) {
        final String sql = "UPDATE users SET login=?, password=?, role=? WHERE id=?";
        final int firstParameter = 1;
        final int secondParameter = 2;
        final int thirdDParameter = 3;
        final int fourthParameter = 4;
        Connection conn = null;
        try {
            conn = ConnectionManager.connect();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(firstParameter, user.getLogin());
            stmt.setString(secondParameter, user.getPassword());
            stmt.setString(thirdDParameter, String.valueOf(user.getRole()));
            stmt.setInt(fourthParameter, (int) user.getId());
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

    public boolean delete(long id) {
        final String sql = "DELETE FROM users WHERE login=?";
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

    public List<User> readAll() {
        List<User> users = new ArrayList<>();
        final String sql = "SELECT * FROM users";
        Connection conn = null;
        try {
            conn = ConnectionManager.connect();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                User user = User.builder().build();
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
                ConnectionManager.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return users;
    }

    public static UserDao getInstance() {
        return INSTANCE;
    }
}
