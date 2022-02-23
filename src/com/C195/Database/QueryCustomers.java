package com.C195.Database;

import com.C195.Models.Customer;
import com.C195.Models.Division;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.C195.Database.JDBC.connection;
import static com.C195.Database.QueryDivisions.queryDivision;

public abstract class QueryCustomers extends Query {
    public static Customer queryCustomer(int customerId) throws SQLException {
        String statement = "SELECT * " +
                "FROM customers " +
                "WHERE Customer_ID=" + customerId + ";";
        ResultSet results = getResults(connection, statement);
        if (results.next()) {
            Division division = queryDivision(results.getInt("Division_ID"));
            return new Customer(customerId, results.getString("Customer_Name"),
                    results.getString("Address"), results.getString("Postal_Code"),
                    results.getString("Phone"), division);
        }
        return null;
    }

    public static ArrayList<Customer> queryCustomers() throws SQLException {
        ArrayList<Customer> customers = new ArrayList<>();
        String statement = "SELECT * FROM customers";
        ResultSet results = getResults(connection, statement);
        while (results.next()) {
            Division division = queryDivision(results.getInt("Division_ID"));
            Customer customer = new Customer(results.getInt("Customer_ID"), results.getString("Customer_Name"),
                    results.getString("Address"), results.getString("Postal_Code"),
                    results.getString("Phone"), division);
            customers.add(customer);
        }
        return customers;
    }

    public static int queryMaxId() throws SQLException {
        int nextId = 1;
        String statement = "SELECT MAX(Customer_ID) FROM customers";
        ResultSet results = getResults(connection, statement);
        if (results.next()) {
            nextId = results.getInt(1);
        }
        return nextId;
    }
}
