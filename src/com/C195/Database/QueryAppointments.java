package com.C195.Database;

import com.C195.Models.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Month;
import java.util.ArrayList;

import static com.C195.Controllers.BaseController.getCurrentUser;
import static com.C195.Database.JDBC.connection;
import static com.C195.Database.QueryContacts.queryContact;
import static com.C195.Database.QueryCustomers.queryCustomer;
import static com.C195.Database.QueryUsers.queryUser;

public abstract class QueryAppointments extends Query {
    public static ArrayList<Appointment> queryAllAppointments() throws SQLException {
        String statement = "SELECT * FROM appointments";
        return accessResults(statement);
    }

    public static ArrayList<Appointment> queryAppointmentsOnDay(String timestamp) throws SQLException {
        String statement = "SELECT * FROM appointments WHERE DAY(Start)=DAY('" + timestamp + "') AND " +
                           "YEAR(Start)=YEAR('" + timestamp + "');";
        return accessResults(statement);
    }

    public static ArrayList<Appointment> queryAppointmentsOnWeek(String timestamp) throws SQLException {
        String statement = "SELECT * FROM appointments WHERE WEEK(Start)=WEEK('" + timestamp + "') " +
                           "AND YEAR(Start)=YEAR('" + timestamp + "');";
        return accessResults(statement);
    }

    public static ArrayList<Appointment> queryAppointmentsOnMonth(String timestamp) throws SQLException {
        String statement = "SELECT * FROM appointments WHERE MONTH(Start)=MONTH('" + timestamp + "') " +
                           "AND YEAR(Start)=YEAR('" + timestamp + "');";
        return accessResults(statement);
    }

    public static ArrayList<Appointment> queryAppointmentsByCustomer(int contactId) throws SQLException {
        String statement = "SELECT * FROM appointments WHERE Contact_ID=" + contactId + ";";
        return accessResults(statement);
    }

    public static ArrayList<Appointment> queryAppointmentsByUser(int userId) throws SQLException {
        String statement = "SELECT * FROM appointments WHERE User_ID=" + userId + ";";
        return accessResults(statement);
    }

    private static ArrayList<Appointment> accessResults(String statement) throws SQLException {
        ArrayList<Appointment> appointments = new ArrayList<>();
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

    public static Appointment queryNextAppointment(Timestamp now, Timestamp next15) throws SQLException {
        String statement = "SELECT * FROM appointments WHERE User_ID=" + getCurrentUser().getUserId()
                           + " AND Start BETWEEN '" + now + "' AND '" + next15 + "';";
        ResultSet results = getResults(connection, statement);
        if (results.next()) {
            Customer customer = queryCustomer(results.getInt("Customer_ID"));
            Contact contact = queryContact(results.getInt("Contact_ID"));
            User user = queryUser(results.getInt("User_ID"));
            return new Appointment(results.getInt("Appointment_ID"),
                    results.getString("Title"), results.getString("Description"),
                    results.getString("Location"), results.getString("Type"),
                    results.getTimestamp("Start"), results.getTimestamp("End"),
                    customer, user, contact);
        }
        return null;
    }

    public static int queryNumberOfCustomerAppointments(String type, Month month) throws SQLException {
        String statement = "SELECT COUNT(*) FROM appointments WHERE MONTH(Start)=" + month.getValue() + " AND Type='" +
                           type + "';";
        ResultSet results = getResults(connection, statement);
        if (results.next())
            return results.getInt(1);
        return 0;
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

    public static ArrayList<String> queryAppointmentTypes() throws SQLException {
        ArrayList<String> types = new ArrayList<>();
        String statement = "SELECT Type FROM appointments";
        ResultSet results = getResults(connection, statement);
        while (results.next()) {
            types.add(results.getString(1));
        }
        return types;
    }

    public static void createNewAppointment(Appointment appointment, Timestamp now) throws SQLException {
        String statement = "INSERT INTO client_schedule.appointments VALUES(" + appointment.getAppointmentId() + ", '" +
                           appointment.getTitle() + "', '" + appointment.getDescription() + "', '" +
                           appointment.getLocation() + "', '" + appointment.getType() + "', '" +
                           appointment.getStart() + "', '" + appointment.getEnd() + "', '" + now + "', '" +
                           getCurrentUser().getUserName() + "', '" + now + "', '" +
                           getCurrentUser().getUserName() + "', " + appointment.getCustomer().getCustomerId() + ", " +
                           appointment.getUser().getUserId() + ", " + appointment.getContact().getContactId() + ");";
        getResults(connection, statement);
    }

    public static void modifyAppointment(Appointment appointment, Timestamp now) throws SQLException {
        String statement = "UPDATE appointments SET Title='" + appointment.getTitle() + "', Description='" +
                           appointment.getDescription() + "', Location='" + appointment.getLocation() + "', Type='" +
                           appointment.getType() + "', Start='" + appointment.getStart() + "', End='" +
                           appointment.getEnd() + "', Last_Update='" + now + "', Last_Updated_By='" +
                           getCurrentUser().getUserName() + "', Customer_ID=" +
                           appointment.getCustomer().getCustomerId() + ", User_ID=" +
                           appointment.getUser().getUserId() + ", Contact_ID=" +
                           appointment.getContact().getContactId() + " WHERE Appointment_ID=" +
                           appointment.getAppointmentId() + ";";
        getResults(connection, statement);
    }

    public static void deleteAppointment(int appointmentId) throws SQLException {
        String statement = "DELETE FROM appointments WHERE Appointment_ID=" + appointmentId + ";";
        getResults(connection, statement);
    }

    public static boolean validTimestamp(String timestamp) throws SQLException {
        boolean valid = false;
        String statement = "SELECT TIMESTAMP('" + timestamp + "') IS NOT NULL;";
        ResultSet results = getResults(connection, statement);
        if (results.next()) {
            if (results.getInt(1) == 1)
                valid = true;
        }
        return valid;
    }
}
