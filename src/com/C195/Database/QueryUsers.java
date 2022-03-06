package com.C195.Database;

import com.C195.Models.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.C195.Controllers.BaseController.setCurrentUser;
import static com.C195.Database.JDBC.*;

/**
 * Abstract class which is responsible for performing all queries for the users table.
 * @author Brady Bassett
 */
public abstract class QueryUsers extends Query {
    /**
     * This function will first check if the username and password matches a record in the users table, then if a match
     * is found, the user will be stored as the current user.
     * @param username The inputted username.
     * @param password The inputted password.
     * @throws SQLException If the sql statement has an error or if there is an issue with the database a SQLException
     * is thrown.
     */
    public static void setCurrUser(String username, String password) throws SQLException {
        String statement = "SELECT * FROM users WHERE User_Name='" + username + "' AND Password='" + password + "';";
        ResultSet results = getResults(connection, statement);
        if (results.next()) {
            User user = new User(results.getInt("User_ID"), results.getString("User_Name"));
            setCurrentUser(user);
        }
    }

    /**
     * This function is responsible for querying the users table for a specific user record.
     * @param userId The specific user that is being queried.
     * @return Returns the matching user record.
     * @throws SQLException If the sql statement has an error or if there is an issue with the database a SQLException
     * is thrown.
     */
    public static User queryUser(int userId) throws SQLException {
        String statement = "SELECT * FROM users WHERE User_ID=" + userId + ";";
        ResultSet results = getResults(connection, statement);
        if (results.next()) {
            return new User(userId, results.getString("User_Name"));
        }
        return null;
    }

    /**
     * This function is responsible for querying all users within the users table.
     * @return Returns an ArrayList of all users contained within the users table.
     * @throws SQLException If the sql statement has an error or if there is an issue with the database a SQLException
     * is thrown.
     */
    public static ArrayList<User> queryUsers() throws SQLException {
        ArrayList<User> users = new ArrayList<>();
        String statement = "SELECT * FROM users";
        ResultSet results = getResults(connection, statement);
        while (results.next()) {
            User user = new User(results.getInt("User_ID"), results.getString("User_Name"));
            users.add(user);
        }
        return users;
    }
}
