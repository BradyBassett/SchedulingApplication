package com.C195.Database;

import com.C195.Models.Contact;

import java.sql.ResultSet;
import java.sql.SQLException;

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
}
