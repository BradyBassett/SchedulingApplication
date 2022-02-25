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
import static com.C195.Database.QueryUsers.queryUser;

public class AppointmentViewController extends FormController implements Initializable {
    public boolean newAppointment = true;
    @FXML private TextField appointmentIdField;
    @FXML private TextField appointmentTitleField;
    @FXML private TextField appointmentDescriptionField;
    @FXML private TextField appointmentLocationField;
    @FXML private TextField appointmentTypeField;
    @FXML private TextField appointmentCustomerIdField;
    @FXML private TextField appointmentUserIdField;
    @FXML private TextField appointmentStartField;
    @FXML private TextField appointmentEndField;
    @FXML private ComboBox<Contact> appointmentContactBox;

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
        appointmentCustomerIdField.setPromptText(bundle.getString("field.customerId"));
        appointmentUserIdField.setPromptText(bundle.getString("field.userId"));
        appointmentStartField.setPromptText(bundle.getString("field.start"));
        appointmentEndField.setPromptText(bundle.getString("field.end"));
        appointmentContactBox.setPromptText(bundle.getString("field.contact"));
        initContacts();
    }

    private void initContacts() {
        ObservableList<Contact> contacts;
        try {
            openConnection();
            contacts = FXCollections.observableArrayList(queryContacts());
        } catch (SQLException e) {
            showAlert(e);
            return;
        } finally {
            closeConnection();
        }

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

    public void initAppointmentData(Appointment appointment) {
        appointmentIdField.setText(String.valueOf(appointment.getAppointmentId()));
        appointmentTitleField.setText(appointment.getTitle());
        appointmentDescriptionField.setText(appointment.getDescription());
        appointmentLocationField.setText(appointment.getLocation());
        appointmentTypeField.setText(appointment.getType());
        appointmentCustomerIdField.setText(String.valueOf(appointment.getCustomer().getCustomerId()));
        appointmentUserIdField.setText(String.valueOf(appointment.getUser().getUserId()));
        appointmentStartField.setText(appointment.getStart().toString());
        appointmentEndField.setText(appointment.getEnd().toString());
        appointmentContactBox.setValue(appointment.getContact());
    }

    @FXML private void handleSave(ActionEvent e) {
        boolean success = false;

        try {
            openConnection();
            validateItems();
            Timestamp start = Timestamp.valueOf(appointmentStartField.getText());
            Timestamp end = Timestamp.valueOf(appointmentEndField.getText());
            Customer customer = queryCustomer(Integer.parseInt(appointmentCustomerIdField.getText()));
            User user = queryUser(Integer.parseInt(appointmentUserIdField.getText()));
            Contact contact = appointmentContactBox.getValue();
            Appointment appointment = new Appointment(Integer.parseInt(appointmentIdField.getText()),
                    appointmentTitleField.getText(), appointmentDescriptionField.getText(),
                    appointmentLocationField.getText(), appointmentTypeField.getText(), start, end, customer, user,
                    contact);
            if (newAppointment){
                createNewAppointment(appointment);
            } else {
                modifyAppointment(appointment);
            }
            success = true;
        } catch (NullPointerException | IllegalArgumentException | SQLException exception) {
            showAlert(exception);
        } finally {
            closeConnection();
        }

        if (success)
            showView(e, "../Views/mainView.fxml");
    }

    private void validateItems() throws SQLException {
        validateFieldNotEmpty(appointmentTitleField.getText(), bundle.getString("error.nullTitleField"));
        validateFieldNotEmpty(appointmentDescriptionField.getText(), bundle.getString("error.nullDescriptionField"));
        validateFieldNotEmpty(appointmentLocationField.getText(), bundle.getString("error.nullLocationField"));
        validateFieldNotEmpty(appointmentTypeField.getText(), bundle.getString("error.nullTypeField"));
        validateFieldNotEmpty(appointmentCustomerIdField.getText(), bundle.getString("error.nullCustomerIdField"));
        validateFieldNotEmpty(appointmentUserIdField.getText(), bundle.getString("error.nullUserIdField"));
        validateFieldNotEmpty(appointmentStartField.getText(), bundle.getString("error.nullStartField"));
        validateDates(appointmentStartField.getText());
        validateFieldNotEmpty(appointmentEndField.getText(), bundle.getString("error.nullEndField"));
        validateDates(appointmentEndField.getText());

        if (Timestamp.valueOf(appointmentStartField.getText()).after(Timestamp.valueOf(appointmentEndField.getText()))
                || Timestamp.valueOf(appointmentStartField.getText()).equals(Timestamp.valueOf(appointmentEndField.getText()))) {
            throw new IllegalArgumentException(bundle.getString("error.invalidHoursSequence"));
        }
        if (appointmentContactBox.getValue() == null)
            throw new NullPointerException(bundle.getString("error.nullContactBox"));
    }

    private void validateDates(String field) throws SQLException {
        if (!validTimestamp(field))
            throw new IllegalArgumentException(bundle.getString("error.invalidTimestamp"));
        LocalDateTime fieldLDT = Timestamp.valueOf(field).toLocalDateTime();
        if (fieldLDT.getHour() >= 22 || fieldLDT.getHour() < 8)
            throw new IllegalArgumentException(bundle.getString("error.invalidHours"));

    }
}
