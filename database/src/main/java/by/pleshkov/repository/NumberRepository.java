package by.pleshkov.repository;




import by.pleshkov.connection.AbstractConnection;
import by.pleshkov.constant.ClassRoom;
import by.pleshkov.constant.Status;
import by.pleshkov.model.Number;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class NumberRepository implements INumberRepository<Number> {


//    public boolean create(Number number) {
//        final String sql = "INSERT INTO numbers (number, places, class, status, user) VALUES (?,?,?,?,?)";
//        Connection conn = null;
//        try {
//            conn = AbstractConnection.connect();
//            PreparedStatement stmt = conn.prepareStatement(sql);
//            stmt.setInt(1, number.getNumberRoom());
//            stmt.setInt(2, (number.getPlaces()));
//            stmt.setString(3, String.valueOf(number.getClassRoom()));
//            stmt.setString(4, String.valueOf(number.getStatus()));
//            stmt.setString(5, number.getUser());
//            boolean isExecution = stmt.execute();
//            return isExecution;
//        } catch (ClassNotFoundException | SQLException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                AbstractConnection.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        return false;
//    }

    @Override
    public Number read(int numberRoom) {
        Number number = new Number();
        final String sql = "SELECT * FROM numbers";
        Connection conn = null;
        try {
            conn = AbstractConnection.connect();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                if (numberRoom == rs.getInt("number")) {
                    number.setNumberRoom(Integer.parseInt("number"));
                    number.setPlaces(Integer.parseInt("places"));
                    number.setClassRoom(ClassRoom.valueOf("class"));
                    number.setStatus(Status.valueOf("status"));
                    number.setUser(rs.getString("user"));
                    return number;
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
        return number;
    }

    public boolean update(Number number) {
        final String sql = "UPDATE numbers SET places=?, class=?, status=?, user=? WHERE number=?";
        Connection conn = null;
        try {
            conn = AbstractConnection.connect();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, number.getPlaces());
            stmt.setString(2, String.valueOf(number.getClassRoom()));
            stmt.setString(3, String.valueOf(number.getStatus()));
            stmt.setString(4, number.getUser());
            stmt.setInt(5, (int) number.getNumberRoom());
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

//    public boolean delete(Number number) {
//        final String sql = "DELETE FROM movies WHERE title=?";
//        Connection conn = null;
//        try {
//            conn = AbstractConnection.connect();
//            PreparedStatement stmt = conn.prepareStatement(sql);
//            stmt.setString(1, movie.getTitle());
//            boolean isExecution = stmt.execute();
//            return isExecution;
//        } catch (ClassNotFoundException | SQLException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                AbstractConnection.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        return false;
//    }

    @Override
    public List<Number> readAll() {
        List<Number> numbers = new ArrayList<>();
        final String sql = "SELECT * FROM numbers";
        Connection conn = null;
        try {
            conn = AbstractConnection.connect();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Number number = new Number();
                number.setNumberRoom(Integer.parseInt("number"));
                number.setPlaces(Integer.parseInt("places"));
                number.setClassRoom(ClassRoom.valueOf(("class")));
                number.setStatus(Status.valueOf(("status")));
                number.setUser("user");
                numbers.add(number);
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
        return numbers;
    }
}
