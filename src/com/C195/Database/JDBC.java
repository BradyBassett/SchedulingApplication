package com.C195.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ResourceBundle;

/**
 * This class is responsible for opening and closing the connection to the database.
 * @author Brady Bassett
 */
public abstract class JDBC {
    private final static ResourceBundle bundle = ResourceBundle.getBundle("com.C195.Resources.db");
    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = bundle.getString("location");
    private static final String databaseName = bundle.getString("databaseName");
    // LOCAL
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER";
    // Driver reference
    private static final String driver = bundle.getString("driver");
    private static final String userName = bundle.getString("userName");
    private static final String password = bundle.getString("password");
    /**
     * The connection interface that contains the connection to the database.
     * @see #openConnection()
     * @see #closeConnection()
     */
    public static Connection connection;

    /**
     * Attempts to create a connection to the database and displays an error to the console in the case of an error.
     */
    public static void openConnection() {
        try {
            Class.forName(driver); // Locate Driver
            connection = DriverManager.getConnection(jdbcUrl, userName, password); // Reference Connection object
            System.out.println("Connection successful!");
        }
        catch(Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
    }

    /**
     * Attempts to close the opened connection to the database and displays an error to the console in the case of an
     * error.
     */
    public static void closeConnection() {
        try {
            connection.close();
            System.out.println("Connection closed!");
        }
        catch(Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
    }
}
