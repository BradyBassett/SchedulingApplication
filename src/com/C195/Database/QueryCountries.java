package com.C195.Database;

import com.C195.Models.Country;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.C195.Database.JDBC.connection;

public abstract class QueryCountries extends Query {
    public static Country queryCountry(int countryId) throws SQLException {
        String statement = "SELECT * " +
                "FROM countries " +
                "WHERE Country_ID=" + countryId + ";";
        ResultSet results = getResults(connection, statement);
        if (results.next()) {
            return new Country(countryId, results.getString("Country"));
        }
        return null;
    }

    public static ArrayList<Country> queryCountries() throws SQLException {
        ArrayList<Country> countries = new ArrayList<>();
        String statement = "SELECT * FROM countries;";
        ResultSet results = getResults(connection, statement);
        while (results.next()) {
            Country country = new Country(results.getInt("Country_ID"), results.getString("Country"));
            countries.add(country);
        }
        return countries;
    }
}
