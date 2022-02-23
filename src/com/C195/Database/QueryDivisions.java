package com.C195.Database;

import com.C195.Models.Country;
import com.C195.Models.Division;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.C195.Database.JDBC.connection;
import static com.C195.Database.QueryCountries.queryCountry;

public abstract class QueryDivisions extends Query {
    public static Division queryDivision(int divisionId) throws SQLException {
        String statement = "SELECT * " +
                "FROM first_level_divisions " +
                "WHERE Division_ID=" + divisionId + ";";
        ResultSet results = getResults(connection, statement);
        if (results.next()) {
            Country country = queryCountry(results.getInt("Country_ID"));
            return new Division(divisionId, results.getString("Division"), country);
        }
        return null;
    }

    public static ArrayList<Division> queryDivisions(int countryId) throws SQLException {
        ArrayList<Division> divisions = new ArrayList<>();
        String statement = "SELECT * " +
                           "FROM first_level_divisions " +
                           "WHERE Country_ID=" + countryId + ";";
        ResultSet results = getResults(connection, statement);
        while (results.next()) {
            Country country = queryCountry(countryId);
            Division division = new Division(results.getInt("Division_ID"), results.getString("Division"),
                    country);
            divisions.add(division);
        }
        return divisions;
    }
}
