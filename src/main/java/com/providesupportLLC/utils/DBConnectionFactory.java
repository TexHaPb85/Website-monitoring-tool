package com.providesupportLLC.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class DBConnectionFactory {
    private static Connection connection = null;

    public static Connection getConnection() {
        if (connection != null)
            return connection;
        else {
            try {
                Properties prop = new Properties();
                InputStream inputStream = DBConnectionFactory.class.getClassLoader().getResourceAsStream("application.properties");
                prop.load(inputStream);
                String driver = prop.getProperty("db_driver");
                String url = prop.getProperty("db_url");
                String user = prop.getProperty("db_username");
                String password = prop.getProperty("db_password");
                Class.forName(driver);
                connection = DriverManager.getConnection(url, user, password);
            } catch (SQLException | ClassNotFoundException | IOException e) {
                e.printStackTrace();
            }
            return connection;
        }

    }
}
