package com.C195.Controllers;

import com.C195.Models.Appointment;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static com.C195.Database.JDBC.closeConnection;
import static com.C195.Database.JDBC.openConnection;
import static com.C195.Database.QueryAppointments.getAppointments;
import static com.C195.Database.QueryAppointments.queryAppointments;

public class MainViewController extends ViewController implements Initializable {
    private ObservableList<Appointment> appointments = FXCollections.observableArrayList();
    @FXML private Tab appointmentsTab;
    @FXML private Tab customersTab;
    @FXML private Button addButton;
    @FXML private Button modifyButton;
    @FXML private Button deleteButton;
    @FXML private DatePicker selectedDate;
    @FXML private TableView<Appointment> appointmentTable;
    @FXML private TableColumn<Appointment, String> appointmentTitle;
    @FXML private TableColumn<Appointment, String> appointmentDescription;
    @FXML private TableColumn<Appointment, String> appointmentLocation;
    @FXML private TableColumn<Appointment, String> appointmentType;
    @FXML private TableColumn<Appointment, String> appointmentStart;
    @FXML private TableColumn<Appointment, String> appointmentEnd;
    @FXML private TableColumn<Appointment, String> appointmentCustomer;
    @FXML private TableColumn<Appointment, String> appointmentContact;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initText();
        initAppointmentTable();
    }

    private void initText() {
        appointmentsTab.setText(bundle.getString("tab.appointments"));
        customersTab.setText(bundle.getString("tab.customers"));
        addButton.setText(bundle.getString("button.add"));
        modifyButton.setText(bundle.getString("button.modify"));
        deleteButton.setText(bundle.getString("button.delete"));
        selectedDate.setValue(LocalDate.now());

        appointmentTable.setPlaceholder(new Label(bundle.getString("table.empty")));
        appointmentTitle.setText(bundle.getString("table.title"));
        appointmentDescription.setText(bundle.getString("table.description"));
        appointmentLocation.setText(bundle.getString("table.location"));
        appointmentType.setText(bundle.getString("table.type"));
        appointmentStart.setText(bundle.getString("table.start"));
        appointmentEnd.setText(bundle.getString("table.end"));
        appointmentCustomer.setText(bundle.getString("table.customer"));
        appointmentContact.setText(bundle.getString("table.contact"));
    }

    private void initAppointmentTable() {
        getAppointmentsArray();

        appointmentTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        appointmentDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        appointmentLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        appointmentType.setCellValueFactory(new PropertyValueFactory<>("type"));
        appointmentStart.setCellValueFactory(new PropertyValueFactory<>("start"));
        appointmentEnd.setCellValueFactory(new PropertyValueFactory<>("end"));
        // cd means cell data
        appointmentCustomer.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getCustomer().getCustomerName()));
        appointmentContact.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getContact().getContactName()));
        appointmentTable.setItems(appointments);
    }

    private void getAppointmentsArray() {
        try {
            openConnection();
            queryAppointments(Date.valueOf(selectedDate.getValue()));
        } catch (SQLException e) {
            showAlert(e);
        } finally {
            closeConnection();
        }

        appointments = FXCollections.observableArrayList(getAppointments());
    }

    @FXML private void handleAppointmentDateSelect() {
        initAppointmentTable();
    }

    @FXML private void handleAppointmentAdd() {

    }

    @FXML private void handleAppointmentModify() {

    }

    @FXML private void handleAppointmentDelete() {

    }
}
