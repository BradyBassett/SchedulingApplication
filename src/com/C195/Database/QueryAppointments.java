package com.C195.Database;

import com.C195.Models.Appointment;
import com.C195.Models.Contact;
import com.C195.Models.Customer;
import com.C195.Models.User;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.C195.Database.JDBC.connection;
import static com.C195.Database.QueryContacts.queryContact;
import static com.C195.Database.QueryCustomers.queryCustomer;
import static com.C195.Database.QueryUsers.queryUser;

public abstract class QueryAppointments extends Query {
    public static ArrayList<Appointment> queryAppointmentsByTime(Date date, boolean daySelected, boolean weekSelected, boolean monthSelected) throws SQLException {
        ArrayList<Appointment> appointments = new ArrayList<>();
        String statement = "SELECT * " +
                           "FROM appointments WHERE";
        if (daySelected)
            statement += " (DAY(Start)=DAY('" + date + "') AND YEAR(Start)=YEAR('" + date + "'));";
        else if (weekSelected)
            statement += " (WEEK(Start)=WEEK('" + date + "') AND YEAR(Start)=YEAR('" + date + "'));";
        else if (monthSelected)
            statement += " (MONTH(Start)=MONTH('" + date + "') AND YEAR(Start)=YEAR('" + date + "'));";
        else
            statement += ";";
        return accessResults(appointments, statement);
    }

    public static ArrayList<Appointment> queryAllAppointments() throws SQLException {
        ArrayList<Appointment> appointments = new ArrayList<>();
        String statement = "SELECT * FROM appointments";
        return accessResults(appointments, statement);
    }

    private static ArrayList<Appointment> accessResults(ArrayList<Appointment> appointments, String statement) throws SQLException {
        ResultSet results = getResults(connection, statement);
        while (results.next()) {
            Customer customer = queryCustomer(results.getInt("Customer_ID"));
            Contact contact = queryContact(results.getInt("Contact_ID"));
            User user = queryUser(results.getInt("User_ID"));
            Appointment appointment = new Appointment(results.getInt("Appointment_ID"),
                    results.getString("Title"), results.getString("Description"),
                    results.getString("Location"), results.getString("Type"),
                    results.getTimestamp("Start"), results.getTimestamp("End"),
                    customer, user, contact);
            appointments.add(appointment);
        }
        return appointments;
    }

    public static int queryMaxId() throws SQLException {
        int nextId = 1;
        String statement = "SELECT MAX(Appointment_ID) FROM appointments";
        ResultSet results = getResults(connection, statement);
        if (results.next()) {
            nextId = results.getInt(1);
        }
        return nextId;
    }
}
