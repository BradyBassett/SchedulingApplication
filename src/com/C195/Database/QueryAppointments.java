package com.C195.Database;

import com.C195.Models.Appointment;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.C195.Database.JDBC.connection;
import static com.C195.Database.QueryContacts.getCurrentContact;
import static com.C195.Database.QueryContacts.queryContact;
import static com.C195.Database.QueryCustomers.getCurrentCustomer;
import static com.C195.Database.QueryCustomers.queryCustomer;
import static com.C195.Database.QueryUsers.getCurrentUser;

public abstract class QueryAppointments extends Query {
    private static ArrayList<Appointment> appointments = new ArrayList<>();

    public static ArrayList<Appointment> getAppointments() {
        return appointments;
    }

    public static void addAppointment(Appointment appointment) {
        appointments.add(appointment);
    }

    public static void queryAppointments(Date date, boolean daySelected, boolean weekSelected, boolean monthSelected) throws SQLException {
        appointments = new ArrayList<>();
        String statement = "SELECT * " +
                           "FROM appointments " +
                           "WHERE User_ID=" + getCurrentUser().getUserId();
        if (daySelected)
            statement += " AND (DAY(Start)=DAY('" + date + "') AND YEAR(Start)=YEAR('" + date + "'));";
        else if (weekSelected)
            statement += " AND (WEEK(Start)=WEEK('" + date + "') AND YEAR(Start)=YEAR('" + date + "'));";
        else if (monthSelected)
            statement += " AND (MONTH(Start)=MONTH('" + date + "') AND YEAR(Start)=YEAR('" + date + "'));";
        else
            statement += ";";
        ResultSet results = getResults(connection, statement);
        while (results.next()) {
            queryCustomer(results.getInt("Customer_ID"));
            queryContact(results.getInt("Contact_ID"));
            Appointment appointment = new Appointment(results.getInt("Appointment_ID"),
                    results.getString("Title"), results.getString("Description"),
                    results.getString("Location"), results.getString("Type"),
                    results.getTimestamp("Start"), results.getTimestamp("End"),
                    getCurrentCustomer(), getCurrentUser(), getCurrentContact());
            addAppointment(appointment);
        }
    }
}
