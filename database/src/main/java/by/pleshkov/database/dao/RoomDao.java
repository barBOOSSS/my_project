package by.pleshkov.database.dao;


import by.pleshkov.database.connection.ConnectionPool;
import by.pleshkov.database.constant.ClassRoom;
import by.pleshkov.database.constant.StatusRoom;
import by.pleshkov.database.dto.RoomFilter;
import by.pleshkov.database.entity.Room;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RoomDao {

    private static final RoomDao INSTANCE = new RoomDao();
    public static final String SELECT_ALL = "SELECT * FROM rooms";
    public static final String SELECT_BY_ID = SELECT_ALL + " WHERE id = ?";
    public static final String INSERT = "INSERT INTO rooms (number, places, class_room, status_room) VALUES (?, ?, ?, ?)";
    public static final String UPDATE = "UPDATE rooms SET number = ?, places = ?, class_room = ?, status_room = ? WHERE id = ?";
    public static final String DELETE_BY_ID = "DELETE FROM rooms WHERE id = ?";
    public static final String SELECT_BY = "SELECT * FROM rooms WHERE places < ? AND class_room = ? AND status_room = ? LIMIT ? OFFSET ?";


    public Optional<Room> create(Room room) {
        try (Connection connection = ConnectionPool.get();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, room.getNumber());
            preparedStatement.setInt(2, room.getPlaces());
            preparedStatement.setString(3, String.valueOf(room.getClassRoom()));
            preparedStatement.setString(4, String.valueOf(room.getStatusRoom()));
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                room.setId(generatedKeys.getLong("id"));
            }
            return Optional.of(room);
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public Optional<Room> findByID(Long id) {
        try (Connection connection = ConnectionPool.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next() ? Optional.of(Room.builder()
                    .id(resultSet.getLong("id"))
                    .number(resultSet.getInt("number"))
                    .places(resultSet.getInt("places"))
                    .classRoom(ClassRoom.valueOf(resultSet.getString("class_room")))
                    .statusRoom(StatusRoom.valueOf(resultSet.getString("status_room")))
                    .build()) : Optional.empty();
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public List<Room> findByFilter(RoomFilter filter) {
        List<Room> rooms = new ArrayList<>();
        try (Connection connection = ConnectionPool.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY)) {
            preparedStatement.setInt(1, filter.places());
            preparedStatement.setString(2, filter.classRoom().name());
            preparedStatement.setString(3, filter.statusRoom().name());
            preparedStatement.setInt(4, filter.limit());
            preparedStatement.setInt(5, filter.limit() * (filter.page() - 1));
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                rooms.add(Room.builder()
                        .id(resultSet.getLong("id"))
                        .number(resultSet.getInt("number"))
                        .places(resultSet.getInt("places"))
                        .classRoom(ClassRoom.valueOf(resultSet.getString(("class_room"))))
                        .statusRoom(StatusRoom.valueOf(resultSet.getString(("status_room"))))
                        .build());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }

    public Optional<Room> update(Room room) {
        try (Connection connection = ConnectionPool.get();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {
            preparedStatement.setInt(1, room.getNumber());
            preparedStatement.setInt(2, room.getPlaces());
            preparedStatement.setString(3, String.valueOf(room.getClassRoom()));
            preparedStatement.setString(4, String.valueOf(room.getStatusRoom()));
            preparedStatement.setLong(5, room.getId());
            preparedStatement.executeUpdate();
            return Optional.of(room);
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

    public List<Room> findAll() {
        List<Room> rooms = new ArrayList<>();
        try (Connection connection = ConnectionPool.get();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SELECT_ALL);
            while (resultSet.next()) {
                rooms.add(Room.builder()
                        .id(resultSet.getLong("id"))
                        .number(resultSet.getInt("number"))
                        .places(resultSet.getInt("places"))
                        .classRoom(ClassRoom.valueOf(resultSet.getString(("class_room"))))
                        .statusRoom(StatusRoom.valueOf(resultSet.getString(("status_room"))))
                        .build());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }

    public static RoomDao getInstance() {
        return INSTANCE;
    }
}
