package com.C195.Database;

import com.C195.Models.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.C195.Database.JDBC.*;

public abstract class QueryUsers extends Query {
    private static User currentUser;

    public static User getCurrentUser() {
        return currentUser;
    }

    private static void setCurrentUser(User user) {
        currentUser = user;
    }

    public static void queryUser(String username, String password) throws SQLException {
        String statement = "SELECT * " +
                           "FROM users " +
                           "WHERE User_Name='" + username + "' " +
                           "AND Password='" + password + "';";

        setPreparedStatement(connection, statement);
        PreparedStatement preparedStatement = getPreparedStatement();
        preparedStatement.execute();
        ResultSet results = preparedStatement.getResultSet();
        if (results.next()) {
            User user = new User(results.getInt("User_ID"), results.getString("User_Name"),
                    results.getString("Password"));
            setCurrentUser(user);
        }
    }
}
