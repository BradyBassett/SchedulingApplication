package com.C195.Controllers;

import com.C195.Database.QueryAppointments;
import com.C195.Models.Appointment;
import com.C195.Models.Contact;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.util.StringConverter;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static com.C195.Database.JDBC.closeConnection;
import static com.C195.Database.JDBC.openConnection;
import static com.C195.Database.QueryContacts.queryContacts;

public class ContactsScheduleReportViewController extends ReportController {
    @FXML private ComboBox<Contact> contactBox;
    @FXML private TableColumn<Appointment, Integer> contactId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        titleLabel.setText(bundle.getString("label.contactsScheduleReport"));
        closeButton.setText(bundle.getString("button.close"));
        contactBox.setPromptText(bundle.getString("field.contact"));
        initCommonTableText();
        contactId.setText(bundle.getString("table.contact"));

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

        contactBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Contact contact) {
                return String.valueOf(contact.getContactId());
            }

            @Override
            public Contact fromString(String s) {
                return null;
            }
        });
        contactBox.setItems(contacts);
    }

    @FXML private void getReport() {
        ObservableList<Appointment> appointments;
        try {
            openConnection();
            appointments = FXCollections.observableArrayList(QueryAppointments.queryAppointmentsByCustomer(contactBox.getValue().getContactId()));
        } catch (SQLException e) {
            showAlert(e);
            return;
        } finally {
            closeConnection();
        }

        setCommonAppointmentValues(appointments);
        contactId.setCellValueFactory(cd -> new SimpleIntegerProperty(cd.getValue().getContact().getContactId()).asObject());
        reportTable.setItems(appointments);
    }
}