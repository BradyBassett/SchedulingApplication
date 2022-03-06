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

/**
 * This class is responsible for displaying a report where every appointment associated with a given contact is listed.
 * @author Brady Bassett
 */
public class ContactsScheduleReportViewController extends ReportController {
    @FXML private ComboBox<Contact> contactBox;
    @FXML private TableColumn<Appointment, Integer> contactId;

    /**
     * Initializes all text and prompt texts values, as well as the items in the ComboBox and TableView.
     * @param url The url to the contactsScheduleReportView.fxml file.
     * @param resourceBundle This parameter contains all locale-specific objects.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        titleLabel.setText(bundle.getString("label.contactsScheduleReport"));
        closeButton.setText(bundle.getString("button.close"));
        contactBox.setPromptText(bundle.getString("field.contact"));
        initCommonTableText();
        contactId.setText(bundle.getString("table.contact"));

        initContacts();
    }

    /**
     * Queries the database for every contact record and displays each by its contact id in the ComboBox.
     */
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

    /**
     * This function will query the database for all appointments that contain the selected contact as a foreign key.
     * It then reloads the TableView to represent the newly queried data.
     * LAMBDA JUSTIFICATION - In order to display the contact ID as the value in the table cell, it is necessary to
     * use a lambda function to create a SimpleIntegerProperty displaying the contact id.
     */
    @FXML private void getReport() {
        ObservableList<Appointment> appointments;
        try {
            if (contactBox.getValue() == null)
                throw new NullPointerException(bundle.getString("error.nullContactBox"));
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
