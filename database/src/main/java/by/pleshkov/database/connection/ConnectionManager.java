package by.pleshkov.database.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConnectionManager {

    private ConnectionManager(){
    }

    private static final String URL = "jdbc:mysql://127.0.0.1:3307/myapp";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "";
    private static Connection conn = null;



    public static Connection connect() throws ClassNotFoundException, SQLException {
        if (conn == null) {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        }
        return conn;
    }

    public static void close() throws SQLException {
        if (conn != null) {
            conn.close();
            conn = null;
        }
    }
}