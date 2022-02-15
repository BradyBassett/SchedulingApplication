package com.C195.Controllers;

import com.C195.Models.Appointment;
import com.C195.Models.Contact;
import com.C195.Models.Customer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class MainViewController extends BaseFormController implements Initializable {
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
    @FXML private TableColumn<Appointment, Customer> appointmentCustomer;
    @FXML private TableColumn<Appointment, Contact> appointmentContact;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setText();
    }

    @FXML private void handleTabSelection() {

    }

    @FXML private void handleAdd() {

    }

    @FXML private void handleModify() {

    }

    @FXML private void handleDelete() {

    }

    @FXML private void handleDateSelect() {

    }

    private void setTableValues() {

    }

    private void setText() {
        appointmentsTab.setText(bundle.getString("tab.appointments"));
        customersTab.setText(bundle.getString("tab.customers"));
        addButton.setText(bundle.getString("button.add"));
        modifyButton.setText(bundle.getString("button.modify"));
        deleteButton.setText(bundle.getString("button.delete"));
        selectedDate.setValue(LocalDate.now());
        appointmentTitle.setText(bundle.getString("table.title"));
        appointmentDescription.setText(bundle.getString("table.description"));
        appointmentLocation.setText(bundle.getString("table.location"));
        appointmentType.setText(bundle.getString("table.type"));
        appointmentStart.setText(bundle.getString("table.start"));
        appointmentEnd.setText(bundle.getString("table.end"));
        appointmentCustomer.setText(bundle.getString("table.customer"));
        appointmentContact.setText(bundle.getString("table.contact"));
    }
}
