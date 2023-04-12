package by.pleshkov.database.dao;


import by.pleshkov.database.connection.ConnectionManager;
import by.pleshkov.database.constant.ClassRoom;
import by.pleshkov.database.constant.Status;
import by.pleshkov.database.model.Room;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class RoomDao implements IDao<Room> {

    private static final RoomDao INSTANCE = new RoomDao();

    public Room create(Room room) {
        final String sql = "INSERT INTO rooms (id, number, places, class, status, user) VALUES (?,?,?,?,?,?)";
        final int firstParameter = 1;
        final int secondParameter = 2;
        final int thirdDParameter = 3;
        final int fourthParameter = 4;
        final int fightHParameter = 5;
        final int sixthHParameter = 6;
        Connection conn = null;
        try {
            conn = ConnectionManager.connect();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(firstParameter, Math.toIntExact(room.getId()));
            stmt.setInt(secondParameter, room.getNumberRoom());
            stmt.setInt(thirdDParameter, (room.getPlaces()));
            stmt.setString(fourthParameter, String.valueOf(room.getClassRoom()));
            stmt.setString(fightHParameter, String.valueOf(room.getStatus()));
            stmt.setString(sixthHParameter, room.getUser());
            boolean isExecution = stmt.execute();
            return room;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                ConnectionManager.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return room;
    }

    @Override
    public Room read(long id) {
        Room room = Room.builder().build();
        final String sql = "SELECT * FROM numbers";
        Connection conn = null;
        try {
            conn = ConnectionManager.connect();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                if (id == rs.getInt("id")) {
                    room = Room.builder()
                            .id(rs.getInt("id"))
                            .numberRoom(rs.getInt("number"))
                            .places(rs.getInt("places"))
                            .classRoom(ClassRoom.valueOf(rs.getString("class")))
                            .status(Status.valueOf(rs.getString("status")))
                            .user(rs.getString("user"))
                            .build();
                    return room;
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
        return room;
    }

    public boolean update(Room room) {
        final String sql = "UPDATE numbers SET number=?, places=?, class=?, status=?, user=? WHERE id=?";
        final int firstParameter = 1;
        final int secondParameter = 2;
        final int thirdDParameter = 3;
        final int fourthParameter = 4;
        final int fightHParameter = 5;
        final int sixthHParameter = 6;
        Connection conn = null;
        try {
            conn = ConnectionManager.connect();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(firstParameter, room.getNumberRoom());
            stmt.setInt(secondParameter, room.getPlaces());
            stmt.setString(thirdDParameter, String.valueOf(room.getClassRoom()));
            stmt.setString(fourthParameter, String.valueOf(room.getStatus()));
            stmt.setString(fightHParameter, room.getUser());
            stmt.setInt(sixthHParameter, Math.toIntExact(room.getId()));
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
        final String sql = "DELETE FROM rooms WHERE id=?";
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
    public List<Room> readAll() {
        List<Room> numbers = new ArrayList<>();
        final String sql = "SELECT * FROM numbers";
        Connection conn = null;
        try {
            conn = ConnectionManager.connect();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                numbers.add(Room.builder()
                        .id(rs.getInt("id"))
                        .numberRoom(rs.getInt("number"))
                        .places(rs.getInt("places"))
                        .classRoom(ClassRoom.valueOf(rs.getString(("class"))))
                        .status(Status.valueOf(rs.getString(("status"))))
                        .user(rs.getString("user"))
                        .build());
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
        return numbers;
    }

    public static RoomDao getInstance() {
        return INSTANCE;
    }
}
