package com.C195.Controllers;

import com.C195.Models.Appointment;
import com.C195.Models.Customer;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static com.C195.Database.JDBC.closeConnection;
import static com.C195.Database.JDBC.openConnection;
import static com.C195.Database.QueryAppointments.getAppointments;
import static com.C195.Database.QueryAppointments.queryAppointments;
import static com.C195.Database.QueryCustomers.getCustomers;
import static com.C195.Database.QueryCustomers.queryCustomers;

public class MainViewController extends ViewController implements Initializable {
    private ObservableList<Appointment> appointments = FXCollections.observableArrayList();
    private ObservableList<Customer> customers = FXCollections.observableArrayList();
    @FXML private Label appointmentsLabel;
    @FXML private Label customersLabel;
    @FXML private Tab appointmentsTab;
    @FXML private Tab customersTab;
    @FXML private Button addAppointmentButton;
    @FXML private Button modifyAppointmentButton;
    @FXML private Button deleteAppointmentButton;
    @FXML private Button addCustomerButton;
    @FXML private Button modifyCustomerButton;
    @FXML private Button deleteCustomerButton;
    @FXML private RadioButton byDayRadioButton;
    @FXML private RadioButton byWeekRadioButton;
    @FXML private RadioButton byMonthRadioButton;
    @FXML private DatePicker selectedDay;
    @FXML private TableView<Appointment> appointmentTable;
    @FXML private TableColumn<Appointment, Integer> appointmentId;
    @FXML private TableColumn<Appointment, String> appointmentTitle;
    @FXML private TableColumn<Appointment, String> appointmentDescription;
    @FXML private TableColumn<Appointment, String> appointmentLocation;
    @FXML private TableColumn<Appointment, String> appointmentType;
    @FXML private TableColumn<Appointment, String> appointmentStart;
    @FXML private TableColumn<Appointment, String> appointmentEnd;
    @FXML private TableColumn<Appointment, Integer> appointmentCustomerId;
    @FXML private TableColumn<Appointment, Integer> appointmentContactId;
    @FXML private TableColumn<Appointment, Integer> appointmentUserId;
    @FXML private TableView<Customer> customerTable;
    @FXML private TableColumn<Customer, Integer> customerId;
    @FXML private TableColumn<Customer, String> customerName;
    @FXML private TableColumn<Customer, String> customerAddress;
    @FXML private TableColumn<Customer, String> customerPostalCode;
    @FXML private TableColumn<Customer, String> customerPhone;
    @FXML private TableColumn<Customer, Integer> customerDivisionId;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initText();
        initAppointmentTable();
        initCustomerTable();
    }

    private void initText() {
        appointmentsTab.setText(bundle.getString("tab.appointments"));
        customersTab.setText(bundle.getString("tab.customers"));
        appointmentsLabel.setText(bundle.getString("tab.appointments"));
        customersLabel.setText(bundle.getString("tab.customers"));
        addAppointmentButton.setText(bundle.getString("button.add"));
        modifyAppointmentButton.setText(bundle.getString("button.modify"));
        deleteAppointmentButton.setText(bundle.getString("button.delete"));
        addCustomerButton.setText(bundle.getString("button.add"));
        modifyCustomerButton.setText(bundle.getString("button.modify"));
        deleteCustomerButton.setText(bundle.getString("button.delete"));
        byMonthRadioButton.setText(bundle.getString("button.month"));
        byWeekRadioButton.setText(bundle.getString("button.week"));
        byDayRadioButton.setText(bundle.getString("button.day"));
        selectedDay.setValue(LocalDate.now());

        appointmentTable.setPlaceholder(new Label(bundle.getString("table.empty.day")));
        appointmentId.setText(bundle.getString("table.appointment"));
        appointmentTitle.setText(bundle.getString("table.title"));
        appointmentDescription.setText(bundle.getString("table.description"));
        appointmentLocation.setText(bundle.getString("table.location"));
        appointmentType.setText(bundle.getString("table.type"));
        appointmentStart.setText(bundle.getString("table.start"));
        appointmentEnd.setText(bundle.getString("table.end"));
        appointmentCustomerId.setText(bundle.getString("table.customer"));
        appointmentContactId.setText(bundle.getString("table.contact"));
        appointmentUserId.setText(bundle.getString("table.user"));

        customerTable.setPlaceholder(new Label(bundle.getString("table.empty")));
        customerId.setText(bundle.getString("table.customer"));
        customerName.setText(bundle.getString("table.name"));
        customerAddress.setText(bundle.getString("table.address"));
        customerPostalCode.setText(bundle.getString("table.postalCode"));
        customerPhone.setText(bundle.getString("table.phone"));
        customerDivisionId.setText(bundle.getString("table.division"));

    }

    @FXML private void initAppointmentTable() {
        setAppointments();

        appointmentId.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        appointmentTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        appointmentDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        appointmentLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        appointmentType.setCellValueFactory(new PropertyValueFactory<>("type"));
        appointmentStart.setCellValueFactory(new PropertyValueFactory<>("start"));
        appointmentEnd.setCellValueFactory(new PropertyValueFactory<>("end"));
        // cd means cell data
        appointmentCustomerId.setCellValueFactory(cd -> new SimpleIntegerProperty(cd.getValue().getCustomer().getCustomerId()).asObject());
        appointmentContactId.setCellValueFactory(cd -> new SimpleIntegerProperty(cd.getValue().getContact().getContactId()).asObject());
        appointmentUserId.setCellValueFactory(cd -> new SimpleIntegerProperty(cd.getValue().getUser().getUserId()).asObject());
        appointmentTable.setItems(appointments);
    }

    private void setAppointments() {
        try {
            openConnection();
            queryAppointments(Date.valueOf(selectedDay.getValue()), byDayRadioButton.isSelected(),
                    byWeekRadioButton.isSelected(), byMonthRadioButton.isSelected());
        } catch (SQLException e) {
            showAlert(e);
        } finally {
            closeConnection();
        }

        appointments = FXCollections.observableArrayList(getAppointments());
    }

    @FXML private void handleScheduleView() {
        if (byDayRadioButton.isSelected())
            appointmentTable.setPlaceholder(new Label(bundle.getString("table.empty.day")));
        else if (byWeekRadioButton.isSelected())
            appointmentTable.setPlaceholder(new Label(bundle.getString("table.empty.week")));
        else
            appointmentTable.setPlaceholder(new Label(bundle.getString("table.empty.month")));

        initAppointmentTable();
    }

    @FXML private void handleAppointmentAdd(ActionEvent e) throws IOException {
        showView(e, "../Views/appointmentView.fxml");
    }

    @FXML private void handleAppointmentModify() {

    }

    @FXML private void handleAppointmentDelete() {

    }

    private void initCustomerTable() {
        setCustomers();

        customerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        customerPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        customerPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        // cd means cell data
        customerDivisionId.setCellValueFactory(cd -> new SimpleIntegerProperty(cd.getValue().getDivision().getDivisionID()).asObject());
        customerTable.setItems(customers);
    }

    private void setCustomers() {
        try {
            openConnection();
            queryCustomers();
        } catch (SQLException e) {
            showAlert(e);
        } finally {
            closeConnection();
        }

        customers = FXCollections.observableArrayList(getCustomers());
    }

    @FXML private void handleCustomerAdd(ActionEvent e) throws IOException {
        showView(e, "../Views/customerView.fxml");
    }

    @FXML private void handleCustomerModify() {

    }

    @FXML private void handleCustomerDelete() {

    }
}
