package com.C195.Database;

import com.C195.Models.Country;
import com.C195.Models.Division;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.C195.Database.JDBC.connection;
import static com.C195.Database.QueryCountries.queryCountry;

/**
 * Abstract class which is responsible for performing all queries for the first_level_divisions table.
 * @author Brady Bassett
 */
public abstract class QueryDivisions extends Query {
    /**
     * This function is responsible for querying the first_level_divisions table for a specific division record.
     * @param divisionId The specific division that is being queried.
     * @return Returns the matching division record.
     * @throws SQLException If the sql statement has an error or if there is an issue with the database a SQLException
     * is thrown.
     */
    public static Division queryDivision(int divisionId) throws SQLException {
        String statement = "SELECT * FROM first_level_divisions WHERE Division_ID=" + divisionId + ";";
        ResultSet results = getResults(connection, statement);
        if (results.next()) {
            Country country = queryCountry(results.getInt("Country_ID"));
            return new Division(divisionId, results.getString("Division"), country);
        }
        return null;
    }

    /**
     * This function is responsible for querying all divisions within the first_level_divisions table.
     * @return Returns an ArrayList of all divisions contained within the first_level_divisions table.
     * @throws SQLException If the sql statement has an error or if there is an issue with the database a SQLException
     * is thrown.
     */
    public static ArrayList<Division> queryDivisions(int countryId) throws SQLException {
        ArrayList<Division> divisions = new ArrayList<>();
        String statement = "SELECT * FROM first_level_divisions WHERE Country_ID=" + countryId + ";";
        ResultSet results = getResults(connection, statement);
        while (results.next()) {
            Country country = queryCountry(countryId);
            Division division = new Division(results.getInt("Division_ID"),
                    results.getString("Division"),
                    country);
            divisions.add(division);
        }
        return divisions;
    }
}
