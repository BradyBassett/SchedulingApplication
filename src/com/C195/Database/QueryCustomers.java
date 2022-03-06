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

/**
 * Abstract class which is responsible for performing all queries for the customers table.
 * @author Brady Bassett
 */
public abstract class QueryCustomers extends Query {
    /**
     * This function is responsible for querying the customers table for a specific customer record.
     * @param customerId The specific record that is being queried.
     * @return Returns the matching customer record.
     * @throws SQLException If the sql statement has an error or if there is an issue with the database a SQLException
     * is thrown.
     */
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

    /**
     * This function is responsible for querying all customers within the customers table.
     * @return Returns an ArrayList of all customers contained within the customers table.
     * @throws SQLException If the sql statement has an error or if there is an issue with the database a SQLException
     * is thrown.
     */
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

    /**
     * This function queries the appointments database and finds the biggest Customer_ID value.
     * @return Returns the value of the mysql MAX function.
     * @throws SQLException If the sql statement has an error or if there is an issue with the database a SQLException
     * is thrown.
     */
    public static int queryMaxId() throws SQLException {
        int nextId = 1;
        String statement = "SELECT MAX(Customer_ID) FROM customers";
        ResultSet results = getResults(connection, statement);
        if (results.next()) {
            nextId = results.getInt(1);
        }
        return nextId;
    }

    /**
     * This function will run an insert into query for the customers table to add a new customer record.
     * @param customer The new customer to insert into customers table.
     * @param now The users current time.
     * @throws SQLException If the sql statement has an error or if there is an issue with the database a SQLException
     * is thrown.
     */
    public static void createNewCustomer(Customer customer, Timestamp now) throws SQLException {
        String statement = "INSERT INTO customers VALUES (" + customer.getCustomerId() + ", '" +
                           customer.getCustomerName() + "', '" + customer.getAddress() + "', '" +
                           customer.getPostalCode() + "', '" + customer.getPhone() + "', '" + now + "', '" +
                           getCurrentUser().getUserName() + "', '" + now + "', '" +
                           getCurrentUser().getUserName() + "', " + customer.getDivision().getDivisionID() + ");";
        getResults(connection, statement);
    }

    /**
     * This function will run an update query for the customers table to update an existing customer record.
     * @param customer The customer with the updated values.
     * @param now The users current time.
     * @throws SQLException If the sql statement has an error or if there is an issue with the database a SQLException
     * is thrown.
     */
    public static void modifyCustomer(Customer customer, Timestamp now) throws SQLException {
        String statement = "UPDATE customers SET Customer_Name='" + customer.getCustomerName() + "', Address='" +
                           customer.getAddress() + "', Postal_Code='" + customer.getPostalCode() + "', Phone='" +
                           customer.getPhone() + "', Last_Update='" + now + "', Last_Updated_By='" +
                           getCurrentUser().getUserName() + "', Division_ID=" + customer.getDivision().getDivisionID() +
                           " WHERE Customer_ID=" + customer.getCustomerId() + ";";
        getResults(connection, statement);
    }

    /**
     * This function will run a delete query for the customers table to delete the provided customer.
     * @param customerId The customer that will be deleted.
     * @throws SQLException If the sql statement has an error or if there is an issue with the database a SQLException
     * is thrown.
     */
    public static void deleteCustomer(int customerId) throws SQLException {
        String statement = "DELETE FROM customers WHERE Customer_ID=" + customerId + ";";
        getResults(connection, statement);
    }
}
