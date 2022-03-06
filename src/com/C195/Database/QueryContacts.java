package com.C195.Database;

import com.C195.Models.Contact;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.C195.Database.JDBC.connection;

/**
 * Abstract class which is responsible for performing all queries for the contacts table.
 * @author Brady Bassett
 */
public abstract class QueryContacts extends Query {
    /**
     * This function is responsible for querying the contacts table for a specific contact record.
     * @param contactId The specific record that is being queried.
     * @return Returns the matching contact record.
     * @throws SQLException If the sql statement has an error or if there is an issue with the database a SQLException
     * is thrown.
     */
    public static Contact queryContact(int contactId) throws SQLException {
        String statement = "SELECT * FROM contacts WHERE Contact_ID=" + contactId + ";";
        ResultSet results = getResults(connection, statement);
        if (results.next()) {
            return new Contact(contactId, results.getString("Contact_Name"),
                    results.getString("Email"));
        }
        return null;
    }

    /**
     * This function is responsible for querying all contacts within the contacts table.
     * @return Returns an ArrayList of all contacts contained within the contacts table.
     * @throws SQLException If the sql statement has an error or if there is an issue with the database a SQLException
     * is thrown.
     */
    public static ArrayList<Contact> queryContacts() throws SQLException {
        ArrayList<Contact> contacts = new ArrayList<>();
        String statement = "SELECT * FROM contacts";
        ResultSet results = getResults(connection, statement);
        while (results.next()) {
            Contact contact = new Contact(results.getInt("Contact_ID"),
                    results.getString("Contact_Name"),
                    results.getString("Email"));
            contacts.add(contact);
        }
        return contacts;
    }
}
