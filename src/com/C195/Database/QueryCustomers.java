package com.C195.Database;

import com.C195.Models.Customer;
import com.C195.Models.Division;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import static com.C195.Controllers.BaseController.getCurrentUser;
import static com.C195.Database.JDBC.connection;
import static com.C195.Database.QueryDivisions.queryDivision;

public abstract class QueryCustomers extends Query {
    public static Customer queryCustomer(int customerId) throws SQLException {
        String statement = "SELECT * FROM customers WHERE Customer_ID=" + customerId + ";";
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
            Customer customer = new Customer(results.getInt("Customer_ID"),
                    results.getString("Customer_Name"), results.getString("Address"),
                    results.getString("Postal_Code"), results.getString("Phone"), division);
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

    public static void createNewCustomer(Customer customer, Timestamp now) throws SQLException {
        String statement = "INSERT INTO customers VALUES (" + customer.getCustomerId() + ", '" +
                           customer.getCustomerName() + "', '" + customer.getAddress() + "', '" +
                           customer.getPostalCode() + "', '" + customer.getPhone() + "', '" + now + "', '" +
                           getCurrentUser().getUserName() + "', '" + now + "', '" +
                           getCurrentUser().getUserName() + "', " + customer.getDivision().getDivisionID() + ");";
        getResults(connection, statement);
    }

    public static void modifyCustomer(Customer customer, Timestamp now) throws SQLException {
        String statement = "UPDATE customers SET Customer_Name='" + customer.getCustomerName() + "', Address='" +
                           customer.getAddress() + "', Postal_Code='" + customer.getPostalCode() + "', Phone='" +
                           customer.getPhone() + "', Last_Update='" + now + "', Last_Updated_By='" +
                           getCurrentUser().getUserName() + "', Division_ID=" + customer.getDivision().getDivisionID() +
                           " WHERE Customer_ID=" + customer.getCustomerId() + ";";
        getResults(connection, statement);
    }

    public static void deleteCustomer(int customerId) throws SQLException {
        String statement = "DELETE FROM customers WHERE Customer_ID=" + customerId + ";";
        getResults(connection, statement);
    }
}
