package com.C195.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ResourceBundle;

public abstract class JDBC {
    private final static ResourceBundle bundle = ResourceBundle.getBundle("com.C195.Resources.db.properties");
    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = bundle.getString("location");
    private static final String databaseName = bundle.getString("databaseName");
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER"; // LOCAL
    private static final String driver = bundle.getString("driver"); // Driver reference
    private static final String userName = bundle.getString("userName"); // Username
    private static final String password = bundle.getString("password"); // Password
    public static Connection connection;  // Connection Interface

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
