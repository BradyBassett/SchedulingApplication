package com.C195.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class Query {
    protected static ResultSet getResults(Connection connection, String statement) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.execute();
        return preparedStatement.getResultSet();
    }
}
