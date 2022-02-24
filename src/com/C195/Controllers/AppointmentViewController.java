package com.C195.Controllers;

import com.C195.Models.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static com.C195.Database.JDBC.closeConnection;
import static com.C195.Database.JDBC.openConnection;
import static com.C195.Database.QueryContacts.queryContacts;
import static com.C195.Database.QueryAppointments.queryMaxId;

public class AppointmentViewController extends ViewController implements Initializable {
    @FXML private Button cancelButton;
    @FXML private Button saveButton;
    @FXML private TextField appointmentIdField;
    @FXML private TextField appointmentTitleField;
    @FXML private TextField appointmentDescriptionField;
    @FXML private TextField appointmentTypeField;
    @FXML private TextField appointmentCustomerIdField;
    @FXML private TextField appointmentUserIdField;
    @FXML private TextField appointmentStartField;
    @FXML private TextField appointmentEndField;
    @FXML private ComboBox<Contact> appointmentContactBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cancelButton.setText(bundle.getString("button.cancel"));
        saveButton.setText(bundle.getString("button.save"));
        try {
            openConnection();
            appointmentIdField.setText(String.valueOf(queryMaxId() + 1));
        } catch (SQLException e) {
            showAlert(e);
        } finally {
            closeConnection();
        }
        appointmentTitleField.setPromptText(bundle.getString("field.title"));
        appointmentDescriptionField.setPromptText(bundle.getString("field.description"));
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

    @FXML private void handleCancel(ActionEvent e) {
        showView(e, "../Views/mainView.fxml");
    }

    @FXML private void handleSave(ActionEvent e) {
        showView(e, "../Views/mainView.fxml");
    }
}
