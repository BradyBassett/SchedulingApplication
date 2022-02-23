package com.C195.Database;

import com.C195.Models.User;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.C195.Controllers.BaseController.setCurrentUser;
import static com.C195.Database.JDBC.*;

public abstract class QueryUsers extends Query {
    public static void queryUser(String username, String password) throws SQLException {
        String statement = "SELECT * " +
                           "FROM users " +
                           "WHERE User_Name='" + username + "' " +
                           "AND Password='" + password + "';";
        ResultSet results = getResults(connection, statement);
        if (results.next()) {
            User user = new User(results.getInt("User_ID"), results.getString("User_Name"),
                    results.getString("Password"));
            setCurrentUser(user);
        }
    }
}
