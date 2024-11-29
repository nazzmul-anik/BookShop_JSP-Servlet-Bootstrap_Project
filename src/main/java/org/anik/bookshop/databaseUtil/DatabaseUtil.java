package org.anik.bookshop.databaseUtil;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseUtil {
    private static Connection connection = null;

    static {
        try (
                InputStream inputStream = DatabaseUtil.class.getClassLoader().getResourceAsStream("db.properties");
        ) {
            if (inputStream == null) {
                throw new FileNotFoundException("Property file 'db.properties' not found in the classpath");
            }

            Properties properties = new Properties();
            properties.load(inputStream);

            String driverClassName = properties.getProperty("driver.class.name");
            String databaseUrl = properties.getProperty("database.url");
            String databaseUserName = properties.getProperty("database.username");
            String databasePassword = properties.getProperty("database.password");

            Class.forName(driverClassName);
            connection = DriverManager.getConnection(databaseUrl, databaseUserName, databasePassword);
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver class not found: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error loading properties file: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Error establishing database connection: " + e.getMessage());
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}
