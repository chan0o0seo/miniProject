package common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {

    private static Connection conn;

    static {
        try {
            conn = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USER, Constants.DB_PASS);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private DbConnection() {}

    public static Connection getConnection() {
        return conn;
    }

    public static void close() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
