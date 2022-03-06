package com.C195.Database;

import com.C195.Models.Country;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.C195.Database.JDBC.connection;

/**
 * Abstract class which is responsible for performing all queries for the countries table.
 * @author Brady Bassett
 */
public abstract class QueryCountries extends Query {
    /**
     * This function is responsible for querying the countries table for a specific country record.
     * @param countryId The specific record that is being queried.
     * @return Returns the matching country record.
     * @throws SQLException If the sql statement has an error or if there is an issue with the database a SQLException is thrown.
     */
    public static Country queryCountry(int countryId) throws SQLException {
        String statement = "SELECT * FROM countries WHERE Country_ID=" + countryId + ";";
        ResultSet results = getResults(connection, statement);
        if (results.next()) {
            return new Country(countryId, results.getString("Country"));
        }
        return null;
    }

    /**
     * This function is responsible for querying all countries within the countries table.
     * @return Returns an ArrayList of all countries contained within the countries table.
     * @throws SQLException If the sql statement has an error or if there is an issue with the database a SQLException is thrown.
     */
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
