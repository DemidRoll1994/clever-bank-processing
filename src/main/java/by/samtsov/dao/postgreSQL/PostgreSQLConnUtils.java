package by.samtsov.dao.postgreSQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgreSQLConnUtils {
    private static Connection conn = null;

    public static Connection getPostgreConnection() {
        if (conn == null) {
            try {
                conn = DriverManager.getConnection(
                        "jdbc:postgresql://127.0.0.1:5432/clevertec_bank?useUnicode=true&charSet=UTF8",
                        "postgres", "admin");
                if (conn != null) {
                    System.out.println("Connected to the database!");
                } else {
                    System.out.println("Failed to make connection!");
                }

            } catch (SQLException e) {
                System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
            }
        }
        return conn;
    }
}