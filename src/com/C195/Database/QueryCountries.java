package com.C195.Database;

import com.C195.Models.Country;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.C195.Database.JDBC.connection;

public abstract class QueryCountries extends Query {
    private static Country currentCountry;

    public static Country getCurrentCountry() {
        return currentCountry;
    }

    private static void setCurrentCountry(Country country) {
        currentCountry = country;
    }

    public static void queryCountry(int countryId) throws SQLException {
        String statement = "SELECT * " +
                "FROM countries " +
                "WHERE Country_ID='" + countryId + ";";

        setPreparedStatement(connection, statement);
        PreparedStatement preparedStatement = getPreparedStatement();
        preparedStatement.execute();
        ResultSet results = preparedStatement.getResultSet();
        if (results.next()) {
            Country country = new Country(countryId, results.getString("Country"));
            setCurrentCountry(country);
        }
    }
}
