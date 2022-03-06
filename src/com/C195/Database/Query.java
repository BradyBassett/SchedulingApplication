package com.C195.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Abstract Query class in which all queries are based upon.
 * @author Brady Bassett
 */
public abstract class Query {
    /**
     * Executes the given statement to the given database connection and then returns the result set.
     * @param connection The connection to the database.
     * @param statement The sql statement to be prepared and executed.
     * @return Returns a result set based on what the database query returns.
     * @throws SQLException If the sql statement has an error or if there is an issue with the database a SQLException
     * is thrown.
     */
    protected static ResultSet getResults(Connection connection, String statement) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.execute();
        return preparedStatement.getResultSet();
    }
}
