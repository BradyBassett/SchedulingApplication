package com.C195.Database;

import com.C195.Models.Contact;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.C195.Database.JDBC.connection;

public abstract class QueryContacts extends Query {
    public static Contact queryContact(int contactId) throws SQLException {
        String statement = "SELECT * " +
                "FROM contacts " +
                "WHERE Contact_ID=" + contactId + ";";
        ResultSet results = getResults(connection, statement);
        if (results.next()) {
            return new Contact(contactId, results.getString("Contact_Name"), results.getString("Email"));
        }
        return null;
    }

    public static ArrayList<Contact> queryContacts() throws SQLException {
        ArrayList<Contact> contacts = new ArrayList<>();
        String statement = "SELECT * FROM contacts";
        ResultSet results = getResults(connection, statement);
        while (results.next()) {
            Contact contact = new Contact(results.getInt("Contact_ID"), results.getString("Contact_Name"),
                    results.getString("Email"));
            contacts.add(contact);
        }
        return contacts;
    }
}
