package com.C195.Controllers;

import com.C195.Database.QueryAppointments;
import com.C195.Models.Appointment;
import com.C195.Models.Contact;
import com.C195.Models.Customer;
import com.C195.Models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import static com.C195.Database.JDBC.closeConnection;
import static com.C195.Database.JDBC.openConnection;
import static com.C195.Database.QueryAppointments.*;
import static com.C195.Database.QueryContacts.queryContacts;
import static com.C195.Database.QueryCustomers.queryCustomer;
import static com.C195.Database.QueryCustomers.queryCustomers;
import static com.C195.Database.QueryUsers.queryUser;
import static com.C195.Database.QueryUsers.queryUsers;

/**
 * This class is responsible for controlling all form functionality for the appointmentView scene.
 * @author Brady Bassett
 */
public class AppointmentViewController extends FormController implements Initializable {
    public boolean newAppointment = true;
    @FXML private TextField appointmentIdField;
    @FXML private TextField appointmentTitleField;
    @FXML private TextField appointmentDescriptionField;
    @FXML private TextField appointmentLocationField;
    @FXML private TextField appointmentTypeField;
    @FXML private TextField appointmentStartField;
    @FXML private TextField appointmentEndField;
    @FXML private ComboBox<Customer> appointmentCustomerBox;
    @FXML private ComboBox<User> appointmentUserBox;
    @FXML private ComboBox<Contact> appointmentContactBox;

    /**
     * Initializes all text and prompt texts values, as well as the items in each ComboBox.
     * @param url The url to the appointmentView.fxml file.
     * @param resourceBundle This parameter contains all locale-specific objects.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initButtons();
        try {
            openConnection();
            appointmentIdField.setText(String.valueOf(QueryAppointments.queryMaxId() + 1));
        } catch (SQLException e) {
            showAlert(e);
        } finally {
            closeConnection();
        }
        appointmentTitleField.setPromptText(bundle.getString("field.title"));
        appointmentDescriptionField.setPromptText(bundle.getString("field.description"));
        appointmentLocationField.setPromptText(bundle.getString("field.location"));
        appointmentTypeField.setPromptText(bundle.getString("field.type"));
        appointmentStartField.setPromptText(bundle.getString("field.start"));
        appointmentEndField.setPromptText(bundle.getString("field.end"));
        appointmentCustomerBox.setPromptText(bundle.getString("field.customerId"));
        appointmentUserBox.setPromptText(bundle.getString("field.userId"));
        appointmentContactBox.setPromptText(bundle.getString("field.contact"));
        initBoxes();
    }

    /**
     * This function is called to initialize customer, user, and contacts data into their respective ComboBoxes.
     */
    private void initBoxes() {
        ObservableList<Customer> customers;
        ObservableList<User> users;
        ObservableList<Contact> contacts;
        try {
            openConnection();
            customers = FXCollections.observableArrayList(queryCustomers());
            users = FXCollections.observableArrayList(queryUsers());
            contacts = FXCollections.observableArrayList(queryContacts());
        } catch (SQLException e) {
            showAlert(e);
            return;
        } finally {
            closeConnection();
        }

        // Displays the Customer ID value instead of the memory address
        appointmentCustomerBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Customer customer) {
                return String.valueOf(customer.getCustomerId());
            }

            @Override
            public Customer fromString(String s) {
                return null;
            }
        });
        appointmentCustomerBox.setItems(customers);

        // Displays the Appointment ID value instead of the memory address
        appointmentUserBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(User user) {
                return String.valueOf(user.getUserId());
            }

            @Override
            public User fromString(String s) {
                return null;
            }
        });
        appointmentUserBox.setItems(users);

        // Displays the Contact ID value instead of the memory address
        appointmentContactBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Contact contact) {
                return String.valueOf(contact.getContactId());
            }

            @Override
            public Contact fromString(String s) {
                return null;
            }
        });
        appointmentContactBox.setItems(contacts);
    }

    /**
     * This function is called whenever the modify button is selected on the mainView, and passes the selected
     * appointment information into all fields.
     * @param appointment The appointment that is being loaded.
     */
    public void initAppointmentData(Appointment appointment) {
        appointmentIdField.setText(String.valueOf(appointment.getAppointmentId()));
        appointmentTitleField.setText(appointment.getTitle());
        appointmentDescriptionField.setText(appointment.getDescription());
        appointmentLocationField.setText(appointment.getLocation());
        appointmentTypeField.setText(appointment.getType());
        appointmentStartField.setText(appointment.getStart().toString());
        appointmentEndField.setText(appointment.getEnd().toString());
        appointmentCustomerBox.setValue(appointment.getCustomer());
        appointmentUserBox.setValue(appointment.getUser());
        appointmentContactBox.setValue(appointment.getContact());
    }

    /**
     * This function saves the appointment data and either creates or modifies an appointment in the database.
     * @param e The ActionEvent to pass down the current window to the main view.
     */
    @FXML private void handleSave(ActionEvent e) {
        try {
            openConnection();
            validateItems();
            Timestamp start = convertLocalToUTC(Timestamp.valueOf(appointmentStartField.getText()));
            Timestamp end = convertLocalToUTC(Timestamp.valueOf(appointmentEndField.getText()));
            Customer customer = queryCustomer(appointmentCustomerBox.getValue().getCustomerId());
            User user = queryUser(appointmentUserBox.getValue().getUserId());
            Contact contact = appointmentContactBox.getValue();
            Appointment appointment = new Appointment(Integer.parseInt(appointmentIdField.getText()),
                    appointmentTitleField.getText(), appointmentDescriptionField.getText(),
                    appointmentLocationField.getText(), appointmentTypeField.getText(), start, end, customer, user,
                    contact);
            if (newAppointment){
                createNewAppointment(appointment, convertLocalToUTC(Timestamp.valueOf(LocalDateTime.now())));
            } else {
                modifyAppointment(appointment, convertLocalToUTC(Timestamp.valueOf(LocalDateTime.now())));
            }
        } catch (NullPointerException | IllegalArgumentException | SQLException exception) {
            showAlert(exception);
            return;
        } finally {
            closeConnection();
        }
        showView(e, "../Views/mainView.fxml");
    }

    /**
     * This function is responsible for validating that every user inputted field is a valid input.
     * @throws SQLException Throws an SQLException if the query experiences an error.
     */
    private void validateItems() throws SQLException {
        // checks if all input fields are not empty
        validateFieldNotEmpty(appointmentTitleField.getText(), bundle.getString("error.nullTitleField"));
        validateFieldNotEmpty(appointmentDescriptionField.getText(), bundle.getString("error.nullDescriptionField"));
        validateFieldNotEmpty(appointmentLocationField.getText(), bundle.getString("error.nullLocationField"));
        validateFieldNotEmpty(appointmentTypeField.getText(), bundle.getString("error.nullTypeField"));
        validateFieldNotEmpty(appointmentStartField.getText(), bundle.getString("error.nullStartField"));
        validateFieldNotEmpty(appointmentEndField.getText(), bundle.getString("error.nullEndField"));

        // checks if combo box values are empty
        if (appointmentCustomerBox.getValue() == null)
            throw new NullPointerException(bundle.getString("error.nullCustomerBox"));
        if (appointmentUserBox.getValue() == null)
            throw new NullPointerException(bundle.getString("error.nullUserBox"));
        if (appointmentContactBox.getValue() == null)
            throw new NullPointerException(bundle.getString("error.nullContactBox"));

        // checks if times inputted in start and end fields are valid TimeStamp objects
        validateDates(appointmentStartField.getText());
        validateDates(appointmentEndField.getText());

        Timestamp fieldStartEST = convertLocalToEST(Timestamp.valueOf(appointmentStartField.getText()));
        Timestamp fieldEndEST = convertLocalToEST(Timestamp.valueOf(appointmentEndField.getText()));

        // checks if start time equals end time or is after end time
        if (fieldStartEST.after(fieldEndEST) || fieldStartEST.equals(fieldEndEST)) {
            throw new IllegalArgumentException(bundle.getString("error.invalidHoursSequence"));
        }

        // checks if appointment extends passed accepted hours (ex 2022-02-25 12:00:00 - 2022-02-26 13:00:00)
        if (fieldStartEST.toLocalDateTime().getYear() != fieldEndEST.toLocalDateTime().getYear() ||
                fieldStartEST.toLocalDateTime().getDayOfYear() != fieldEndEST.toLocalDateTime().getDayOfYear()) {
            throw new IllegalArgumentException(bundle.getString("error.invalidHours"));
        }

        Timestamp fieldStart = Timestamp.valueOf(appointmentStartField.getText());
        Timestamp fieldEnd = Timestamp.valueOf(appointmentEndField.getText());
        // checks if new apt start or end times conflict with existing appointments for selected user, or if the
        // selected customer already has conflicting appointments
        for (Appointment apt : queryAppointmentsByUserOnDay(appointmentUserBox.getValue().getUserId(),
                convertLocalToUTC(Timestamp.valueOf(appointmentStartField.getText())).toString())) {
            Timestamp aptStart = convertUTCToLocal(apt.getStart());
            Timestamp aptEnd = convertUTCToLocal(apt.getEnd());
            if (apt.getAppointmentId() != Integer.parseInt(appointmentIdField.getText())){
                if (fieldStart.equals(aptStart) || fieldEnd.equals(aptEnd) ||
                        (fieldStart.after(aptStart) && aptStart.before(aptEnd)) ||
                        (fieldEnd.after(aptStart) && fieldEnd.before(aptEnd))) {
                    throw new IllegalArgumentException(bundle.getString("error.conflictingTimes"));
                }
            }
        }
        for (Appointment apt : queryAppointmentsOnDay(convertLocalToUTC(Timestamp.valueOf(appointmentStartField
                .getText())).toString())) {
            Timestamp aptStart = convertUTCToLocal(apt.getStart());
            Timestamp aptEnd = convertUTCToLocal(apt.getEnd());
            if (apt.getCustomer().getCustomerId() == appointmentCustomerBox.getValue().getCustomerId() &&
                apt.getAppointmentId() != Integer.parseInt(appointmentIdField.getText())) {
                if (fieldStart.equals(aptStart) || fieldEnd.equals(aptEnd) ||
                        (fieldStart.after(aptStart) && aptStart.before(aptEnd)) ||
                        (fieldEnd.after(aptStart) && fieldEnd.before(aptEnd))) {
                    throw new IllegalArgumentException(bundle.getString("error.conflictingCustomerTimes"));
                }
            }
        }
    }

    /**
     * This function validates that the start date and end date fields are valid Timestamp values that are within, the
     * given parameters.
     * @param field The Timestamp field that is being validated.
     * @throws SQLException Throws an SQLException if the query experiences an error.
     */
    private void validateDates(String field) throws SQLException {
        if (!validTimestamp(field))
            throw new IllegalArgumentException(bundle.getString("error.invalidTimestamp"));
        LocalDateTime fieldLDT = convertLocalToEST(Timestamp.valueOf(field)).toLocalDateTime();
        if (fieldLDT.getHour() >= 22 || fieldLDT.getHour() < 8)
            throw new IllegalArgumentException(bundle.getString("error.invalidHours"));
    }
}
