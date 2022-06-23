package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConfig {
    public static Connection connection;

    static {
        try {
            connection = DbConfig.connection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection connection() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/phones-market";
        String name = "postgres";
        String password = "2004";
        return DriverManager.getConnection(url,name,password);
    }
}
