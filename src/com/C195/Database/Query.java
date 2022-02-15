package com.C195.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class Query {
    private static PreparedStatement preparedStatement;

    protected static void setPreparedStatement(Connection connection, String statement) throws SQLException {
        preparedStatement = connection.prepareStatement(statement);
    }

    protected static PreparedStatement getPreparedStatement() {
        return preparedStatement;
    }
}
