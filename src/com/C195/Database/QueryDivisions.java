package com.C195.Database;

import com.C195.Models.Division;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.C195.Database.JDBC.connection;
import static com.C195.Database.QueryCountries.getCurrentCountry;
import static com.C195.Database.QueryCountries.queryCountry;

public abstract class QueryDivisions extends Query {
    private static Division currentDivision;

    public static Division getCurrentDivision() {
        return currentDivision;
    }

    private static void setCurrentDivision(Division division) {
        currentDivision = division;
    }

    public static void queryDivision(int divisionId) throws SQLException {
        String statement = "SELECT * " +
                "FROM first_level_divisions " +
                "WHERE Division_ID='" + divisionId + ";";

        setPreparedStatement(connection, statement);
        PreparedStatement preparedStatement = getPreparedStatement();
        preparedStatement.execute();
        ResultSet results = preparedStatement.getResultSet();
        if (results.next()) {
            queryCountry(results.getInt("Country_ID"));
            Division division = new Division(divisionId, results.getString("Division"), getCurrentCountry());
            setCurrentDivision(division);
        }
    }
}
