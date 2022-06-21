package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConfig {
    public static Connection connection(String dbname) throws SQLException {
        String url = "" + dbname;
        String name = "postgres";
        String password = "2004";
        return DriverManager.getConnection(url,name,password);
    }
}
