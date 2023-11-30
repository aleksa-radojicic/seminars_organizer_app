/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fon.server.repository.db;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Singleton class for connecting to the database.
 *
 * @author Aleksa
 */
public class DbConnectionFactory {

    /**
     * The singleton instance of the {@code DbConnectionFactory} class.
     */
    private static DbConnectionFactory instance;

    /**
     * SQL connection instance as {@code Connection}.
     */
    private Connection connection;

    /**
     * Private non-parametric constructor.
     */
    private DbConnectionFactory() {
    }

    /**
     * Getter of instance (Singleton pattern).
     *
     * @return The singleton instance of the {@code DbConnectionFactory} class.
     */
    public static DbConnectionFactory getInstance() {
        if (instance == null) {
            instance = new DbConnectionFactory();
        }
        return instance;
    }

    /**
     * Connects to a database from the {@code dbconfig} properties file.
     *
     * @return SQL connection instance as {@code Connection}.
     * @throws java.io.FileNotFoundException When there is no {@code dbconfig}
     * properties file.
     * @throws java.io.IOException When data from {@code dbconfig} properties
     * file cannot be properly read.
     * @throws java.sql.SQLException When database server is not up or the
     * database credentials from {@code dbconfig} properties file are incorrect.
     */
    public Connection getConnection() throws FileNotFoundException, IOException, SQLException {
        if (connection == null || connection.isClosed()) {
            Properties properties = new Properties();
            properties.load(new FileInputStream("config/dbconfig.properties"));
            String url = properties.getProperty("url");
            String username = properties.getProperty("username");
            String password = properties.getProperty("password");
            connection = DriverManager.getConnection(url, username, password);
            connection.setAutoCommit(false);
        }
        return connection;
    }
}
