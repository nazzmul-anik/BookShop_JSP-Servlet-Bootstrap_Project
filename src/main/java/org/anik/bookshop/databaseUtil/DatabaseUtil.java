package org.anik.bookshop.databaseUtil;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseUtil {

    private static Properties properties = new Properties();

    static {
        try (InputStream inputStream = DatabaseUtil.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (inputStream == null) {
                throw new IllegalArgumentException("Property file 'db.properties' not found in the classpath");
            }
            properties.load(inputStream);
            Class.forName(properties.getProperty("driver.class.name"));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error initializing database properties: " + e.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException {
        String databaseUrl = properties.getProperty("database.url");
        String databaseUserName = properties.getProperty("database.username");
        String databasePassword = properties.getProperty("database.password");
        return DriverManager.getConnection(databaseUrl, databaseUserName, databasePassword);
    }
}
