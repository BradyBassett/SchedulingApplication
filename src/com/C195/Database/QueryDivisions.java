package com.C195.Database;

import com.C195.Models.Division;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.C195.Database.JDBC.connection;
import static com.C195.Database.QueryCountries.getCurrentCountry;
import static com.C195.Database.QueryCountries.queryCountry;

public abstract class QueryDivisions extends Query {
    private static Division currentDivision;
    private static ArrayList<Division> divisions = new ArrayList<>();

    public static Division getCurrentDivision() {
        return currentDivision;
    }

    public static ArrayList<Division> getDivisions() {
        return divisions;
    }

    private static void setCurrentDivision(Division division) {
        currentDivision = division;
    }

    public static void queryDivision(int divisionId) throws SQLException {
        String statement = "SELECT * " +
                "FROM first_level_divisions " +
                "WHERE Division_ID=" + divisionId + ";";
        ResultSet results = getResults(connection, statement);
        if (results.next()) {
            queryCountry(results.getInt("Country_ID"));
            Division division = new Division(divisionId, results.getString("Division"), getCurrentCountry());
            setCurrentDivision(division);
        }
    }

    public static void queryDivisions(int countryId) throws SQLException {
        divisions = new ArrayList<>();
        String statement = "SELECT * " +
                           "FROM first_level_divisions " +
                           "WHERE Country_ID=" + countryId + ";";
        ResultSet results = getResults(connection, statement);
        while (results.next()) {
            QueryCountries.queryCountry(countryId);
            Division division = new Division(results.getInt("Division_ID"), results.getString("Division"),
                    getCurrentCountry());
            divisions.add(division);
        }
    }
}
