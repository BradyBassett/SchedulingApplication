package com.C195.Database;

import com.C195.Models.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.C195.Database.JDBC.connection;
import static com.C195.Database.QueryDivisions.getCurrentDivision;
import static com.C195.Database.QueryDivisions.queryDivision;

public abstract class QueryCustomers extends Query {
    private static Customer currentCustomer;
    private static ArrayList<Customer> customers = new ArrayList<>();

    public static Customer getCurrentCustomer() {
        return currentCustomer;
    }

    private static void setCurrentCustomer(Customer customer) {
        currentCustomer = customer;
    }

    public static ArrayList<Customer> getCustomers() {
        return customers;
    }

    public static void queryCustomer(int customerId) throws SQLException {
        String statement = "SELECT * " +
                "FROM customers " +
                "WHERE Customer_ID=" + customerId + ";";
        setPreparedStatement(connection, statement);
        PreparedStatement preparedStatement = getPreparedStatement();
        preparedStatement.execute();
        ResultSet results = preparedStatement.getResultSet();
        if (results.next()) {
            queryDivision(results.getInt("Division_ID"));
            Customer customer = new Customer(customerId, results.getString("Customer_Name"),
                    results.getString("Address"), results.getString("Postal_Code"),
                    results.getString("Phone"), getCurrentDivision());
            setCurrentCustomer(customer);
        }
    }

    public static void queryCustomers() throws SQLException {
        customers = new ArrayList<>();
        String statement = "SELECT * FROM customers";
        setPreparedStatement(connection, statement);
        PreparedStatement preparedStatement = getPreparedStatement();
        preparedStatement.execute();
        ResultSet results = preparedStatement.getResultSet();
        while (results.next()) {
            queryDivision(results.getInt("Division_ID"));
            Customer customer = new Customer(results.getInt("Customer_ID"), results.getString("Customer_Name"),
                    results.getString("Address"), results.getString("Postal_Code"),
                    results.getString("Phone"), getCurrentDivision());
            customers.add(customer);
        }
    }
}
