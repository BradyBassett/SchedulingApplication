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

/**
 * Abstract class which is responsible for performing all queries for the appointments table.
 * @author Brady Bassett
 */
public abstract class QueryAppointments extends Query {
    /**
     * A function to query all appointments currently in the appointments table.
     * @return Returns the value of accessResults.
     * @throws SQLException If the sql statement has an error or if there is an issue with the database a SQLException
     * is thrown.
     */
    public static ArrayList<Appointment> queryAllAppointments() throws SQLException {
        String statement = "SELECT * FROM appointments";
        return accessResults(statement);
    }

    /**
     * A function to query all appointments currently in the appointments table where the day is equal to the day in the
     * given timestamp.
     * @param timestamp The timestamp that contains the day to select for.
     * @return Returns the value of accessResults.
     * @throws SQLException If the sql statement has an error or if there is an issue with the database a SQLException
     * is thrown.
     */
    public static ArrayList<Appointment> queryAppointmentsOnDay(String timestamp, String offset) throws SQLException {
        String cvtStart = "CONVERT_TZ(Start, '+00:00', '" + offset + "')";
        String cvtEnd = "CONVERT_TZ(End, '+00:00', '" + offset + "')";
        String statement = "SELECT *, " + cvtStart + ", " + cvtEnd + " FROM appointments WHERE DAY(" + cvtStart +
                           ")=DAY('" + timestamp + "') AND " + "YEAR(" + cvtStart + ")=YEAR('" + timestamp + "');";
        return accessResults(statement);
    }

    /**
     * A function to query all appointments currently in the appointments table where the day is equal to the week in
     * the given timestamp.
     * @param timestamp The timestamp that contains the week to select for.
     * @return Returns the value of accessResults.
     * @throws SQLException If the sql statement has an error or if there is an issue with the database a SQLException
     * is thrown.
     */
    public static ArrayList<Appointment> queryAppointmentsOnWeek(String timestamp, String offset) throws SQLException {
        String cvtStart = "CONVERT_TZ(Start, '+00:00', '" + offset + "')";
        String cvtEnd = "CONVERT_TZ(End, '+00:00', '" + offset + "')";
        String statement = "SELECT *, " + cvtStart + ", " + cvtEnd + " FROM appointments WHERE WEEK(" + cvtStart +
                           ")=WEEK('" + timestamp + "') " + "AND YEAR(" + cvtStart + ")=YEAR('" + timestamp + "');";
        return accessResults(statement);
    }

    /**
     * A function to query all appointments currently in the appointments table where the day is equal to the month in
     * the given timestamp.
     * @param timestamp The timestamp that contains the month to select for.
     * @return Returns the value of accessResults.
     * @throws SQLException If the sql statement has an error or if there is an issue with the database a SQLException
     * is thrown.
     */
    public static ArrayList<Appointment> queryAppointmentsOnMonth(String timestamp, String offset) throws SQLException {
        String cvtStart = "CONVERT_TZ(Start, '+00:00', '" + offset + "')";
        String cvtEnd = "CONVERT_TZ(End, '+00:00', '" + offset + "')";
        String statement = "SELECT *, " + cvtStart + ", " + cvtEnd + " FROM appointments WHERE MONTH(" + cvtStart +
                           ")=MONTH('" + timestamp + "') AND " + "YEAR(" + cvtStart + ")=YEAR('" + timestamp + "');";
        return accessResults(statement);
    }

    /**
     * A function to query all appointments currently in the appointments table where the contact ID is equal to the
     * contact ID in the parameters.
     * @param contactId The contact ID in which to check each appointment for.
     * @return Returns the value of accessResults.
     * @throws SQLException If the sql statement has an error or if there is an issue with the database a SQLException
     * is thrown.
     */
    public static ArrayList<Appointment> queryAppointmentsByCustomer(int contactId) throws SQLException {
        String statement = "SELECT * FROM appointments WHERE Contact_ID=" + contactId + ";";
        return accessResults(statement);
    }

    /**
     * A function to query all appointments currently in the appointments table where the user ID is equal to the user
     * ID in the parameters.
     * @param userId The user ID in which to check each appointment for.
     * @return Returns the value of accessResults.
     * @throws SQLException If the sql statement has an error or if there is an issue with the database a SQLException
     * is thrown.
     */
    public static ArrayList<Appointment> queryAppointmentsByUser(int userId) throws SQLException {
        String statement = "SELECT * FROM appointments WHERE User_ID=" + userId + ";";
        return accessResults(statement);
    }

    /**
     * A function to query all appointments in a single day that are associated with a provided user ID.
     * @param userId User ID to match.
     * @param day The specific day to restrict search to.
     * @return Returns the value of accessResults.
     * @throws SQLException If the sql statement has an error or if there is an issue with the database a SQLException
     * is thrown.
     */
    public static ArrayList<Appointment> queryAppointmentsByUserOnDay(int userId, String day, String offset) throws SQLException {
        String cvtStart = "CONVERT_TZ(Start, '+00:00', '" + offset + "')";
        String cvtEnd = "CONVERT_TZ(End, '+00:00', '" + offset + "')";
        String statement = "SELECT *, " + cvtStart + ", " + cvtEnd + " FROM appointments WHERE DAY(" + cvtStart +
                           ")=DAY('" + day + "') AND " + "YEAR(" + cvtStart + ")=YEAR('" + day + " AND User_ID=" +
                           userId + ";";
        return accessResults(statement);
    }

    /**
     * A helper function for all queries which return an ArrayList of appointments.
     * @param statement The sql statement which is being queried.
     * @return Returns the ArrayList of appointments which were queried from the statement
     * @throws SQLException If the sql statement has an error or if there is an issue with the database a SQLException
     * is thrown.
     */
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
                    results.getTimestamp("Start"), results.getTimestamp("End"), customer, user,
                    contact);
            appointments.add(appointment);
        }
        return appointments;
    }

    /**
     * This function queries the appointments table for the first appointment within the current time and the next 15
     * minutes and where the user is equal to the current logged-in user.
     * @param now The current time to begin the search for.
     * @param next15 The next 15 minutes from the current time to begin the search for.
     * @return Returns the first appointment that fits the query properties, if there is one.
     * @throws SQLException If the sql statement has an error or if there is an issue with the database a SQLException
     * is thrown.
     */
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

    /**
     * This function queries the appointments table for all appointments that match the given type and given month.
     * @param type The type that must be matched.
     * @param month The month that must be matched.
     * @return Returns the value of the mysql COUNT function.
     * @throws SQLException If the sql statement has an error or if there is an issue with the database a SQLException
     * is thrown.
     */
    public static int queryNumberOfCustomerAppointments(String type, Month month) throws SQLException {
        String statement = "SELECT COUNT(*) FROM appointments WHERE MONTH(Start)=" + month.getValue() + " AND Type='" +
                           type + "';";
        ResultSet results = getResults(connection, statement);
        if (results.next())
            return results.getInt(1);
        return 0;
    }

    /**
     * This function queries the appointments database and finds the biggest Appointment_ID value.
     * @return Returns the value of the mysql MAX function.
     * @throws SQLException If the sql statement has an error or if there is an issue with the database a SQLException
     * is thrown.
     */
    public static int queryMaxId() throws SQLException {
        int nextId = 1;
        String statement = "SELECT MAX(Appointment_ID) FROM appointments";
        ResultSet results = getResults(connection, statement);
        if (results.next()) {
            nextId = results.getInt(1);
        }
        return nextId;
    }

    /**
     * This function queries the appointments database to find each Type value that is present in all appointments.
     * @return Returns an ArrayList of strings which represent each type value present in all the appointments.
     * @throws SQLException If the sql statement has an error or if there is an issue with the database a SQLException
     * is thrown.
     */
    public static ArrayList<String> queryAppointmentTypes() throws SQLException {
        ArrayList<String> types = new ArrayList<>();
        String statement = "SELECT Type FROM appointments";
        ResultSet results = getResults(connection, statement);
        while (results.next()) {
            if (!types.contains(results.getString(1)))
                types.add(results.getString(1));
        }
        return types;
    }

    /**
     * This function will run an insert into query for the appointments table to add a new appointment record.
     * @param appointment The new appointment to insert into appointments table.
     * @param now The users current time.
     * @throws SQLException If the sql statement has an error or if there is an issue with the database a SQLException
     * is thrown.
     */
    public static void createNewAppointment(Appointment appointment, Timestamp now) throws SQLException {
        String statement = "INSERT INTO appointments VALUES(" + appointment.getAppointmentId() + ", '" +
                           appointment.getTitle() + "', '" + appointment.getDescription() + "', '" +
                           appointment.getLocation() + "', '" + appointment.getType() + "', '" +
                           appointment.getStart() + "', '" + appointment.getEnd() + "', '" + now + "', '" +
                           getCurrentUser().getUserName() + "', '" + now + "', '" +
                           getCurrentUser().getUserName() + "', " + appointment.getCustomer().getCustomerId() + ", " +
                           appointment.getUser().getUserId() + ", " + appointment.getContact().getContactId() + ");";
        getResults(connection, statement);
    }

    /**
     * This function will run an update query for the appointmets table to update an existing appointment record.
     * @param appointment The appointment with the updated values.
     * @param now The users current time.
     * @throws SQLException If the sql statement has an error or if there is an issue with the database a SQLException
     * is thrown.
     */
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

    /**
     * This function will run a delete query for the appointments table to delete the provided appointment.
     * @param appointmentId The appointment that will be deleted.
     * @throws SQLException If the sql statement has an error or if there is an issue with the database a SQLException
     * is thrown.
     */
    public static void deleteAppointment(int appointmentId) throws SQLException {
        String statement = "DELETE FROM appointments WHERE Appointment_ID=" + appointmentId + ";";
        getResults(connection, statement);
    }

    /**
     * This function utilizes the database to validate if user inputted timestamp is a valid mysql Timestamp value.
     * @param timestamp The timestamp that is being validated.
     * @return Returns true if the query results in a 1 and false if the query results in a 0.
     * @throws SQLException If the sql statement has an error or if there is an issue with the database a SQLException
     * is thrown.
     */
    public static boolean validTimestamp(String timestamp) throws SQLException {
        boolean valid = false;
        // if the timestamp is valid it will return 1, otherwise it will return 0
        String statement = "SELECT TIMESTAMP('" + timestamp + "') IS NOT NULL;";
        ResultSet results = getResults(connection, statement);
        if (results.next()) {
            if (results.getInt(1) == 1)
                valid = true;
        }
        return valid;
    }
}
