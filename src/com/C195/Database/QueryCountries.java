package com.C195.Database;

import com.C195.Models.Country;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.C195.Database.JDBC.connection;

public abstract class QueryCountries extends Query {
    private static Country currentCountry;
    private static final ArrayList<Country> countries = new ArrayList<>();

    public static Country getCurrentCountry() {
        return currentCountry;
    }

    public static ArrayList<Country> getCountries() {
        return countries;
    }

    private static void setCurrentCountry(Country country) {
        currentCountry = country;
    }

    public static void queryCountry(int countryId) throws SQLException {
        String statement = "SELECT * " +
                "FROM countries " +
                "WHERE Country_ID=" + countryId + ";";
        ResultSet results = getResults(connection, statement);
        if (results.next()) {
            Country country = new Country(countryId, results.getString("Country"));
            setCurrentCountry(country);
        }
    }

    public static void queryCountries() throws SQLException {
        String statement = "SELECT * FROM countries;";
        ResultSet results = getResults(connection, statement);
        while (results.next()) {
            Country country = new Country(results.getInt("Country_ID"), results.getString("Country"));
            countries.add(country);
        }
    }
}
