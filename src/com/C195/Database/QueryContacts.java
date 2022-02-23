package com.C195.Database;

import com.C195.Models.Contact;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.C195.Database.JDBC.connection;

public abstract class QueryContacts extends Query {
    private static Contact currentContact;

    public static Contact getCurrentContact() {
        return currentContact;
    }

    private static void setCurrentContact(Contact contact) {
        currentContact = contact;
    }

    public static void queryContact(int contactId) throws SQLException {
        String statement = "SELECT * " +
                "FROM contacts " +
                "WHERE Contact_ID=" + contactId + ";";
        ResultSet results = getResults(connection, statement);
        if (results.next()) {
            Contact contact = new Contact(contactId, results.getString("Contact_Name"), results.getString("Email"));
            setCurrentContact(contact);
        }
    }
}
