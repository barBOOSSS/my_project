package by.pleshkov.database.dao;


import by.pleshkov.database.connection.ConnectionPool;
import by.pleshkov.database.constant.Role;
import by.pleshkov.database.entity.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDao {

    private static final UserDao INSTANCE = new UserDao();
    public static final String SELECT_BY_EMAIL_PASS = "SELECT * FROM users WHERE email = ? AND password = ?";
    public static final String SELECT_ALL = "SELECT * FROM users";
    public static final String SELECT_BY_ID = SELECT_ALL + " WHERE id = ?";
    public static final String INSERT = "INSERT INTO users (name, surname, password, email, role) VALUES (?, ?, ?, ?, ?)";
    public static final String UPDATE = "UPDATE users SET name = ?, surname = ?, password = ?, email = ?, role = ?  WHERE id = ?";
    public static final String DELETE_BY_ID = "DELETE FROM users WHERE id = ?";

    public Optional<User> create(User user) {
        try (Connection connection = ConnectionPool.get();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getSurname());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, String.valueOf(user.getRole()));
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                user.setId(generatedKeys.getLong("id"));
            }
            return Optional.of(user);
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public Optional<User> findByID(Long id) {
        try (Connection connection = ConnectionPool.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next() ? Optional.of(User.builder()
                    .id(resultSet.getLong("id"))
                    .name(resultSet.getString("name"))
                    .surname(resultSet.getString("surname"))
                    .password(resultSet.getString("password"))
                    .email(resultSet.getString("email"))
                    .role(Role.valueOf(resultSet.getString("role")))
                    .build()) : Optional.empty();
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public Optional<User> getByEmailAndPass(String email, String password) {
        try (Connection connection = ConnectionPool.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_EMAIL_PASS)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next() ? Optional.of(User.builder()
                    .id(resultSet.getLong("id"))
                    .name(resultSet.getString("name"))
                    .surname(resultSet.getString("surname"))
                    .password(resultSet.getString("password"))
                    .email(resultSet.getString("email"))
                    .role(Role.valueOf(resultSet.getString("role")))
                    .build()) : Optional.empty();
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public Optional<User> update(User user) {
        try (Connection connection = ConnectionPool.get();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getSurname());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, String.valueOf(user.getRole()));
            preparedStatement.setLong(6, user.getId());
            preparedStatement.executeUpdate();
            return Optional.of(user);
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

    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (Connection connection = ConnectionPool.get();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SELECT_ALL);
            while (resultSet.next()) {
                users.add(User.builder()
                        .id(resultSet.getLong("id"))
                        .name(resultSet.getString("name"))
                        .surname(resultSet.getString("surname"))
                        .password(resultSet.getString("password"))
                        .email(resultSet.getString("email"))
                        .role(Role.valueOf(resultSet.getString("role")))
                        .build());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public static UserDao getInstance() {
        return INSTANCE;
    }
}
