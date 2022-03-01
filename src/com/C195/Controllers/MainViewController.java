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

import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static com.C195.Database.JDBC.closeConnection;
import static com.C195.Database.JDBC.openConnection;
import static com.C195.Database.QueryAppointments.*;
import static com.C195.Database.QueryCustomers.deleteCustomer;
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
        // todo set to last set values also
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

        appointmentTable.setRowFactory(tv -> {
            TableRow<Appointment> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (tv.getSelectionModel().getSelectedItem() != null) {
                    modifyAppointmentButton.setDisable(false);
                    deleteAppointmentButton.setDisable(false);
                } else {
                    modifyAppointmentButton.setDisable(true);
                    deleteAppointmentButton.setDisable(true);
                }
            });
            return row;
        });
    }

    private void setAppointments() {
        try {
            openConnection();

            String timestamp = convertLocalToUTC(Timestamp.valueOf(selectedDay.getValue().atTime(LocalTime.now()))).toString();
            if (byDayRadioButton.isSelected())
                appointments = FXCollections.observableArrayList(queryAppointmentsOnDay(timestamp));
            if (byWeekRadioButton.isSelected())
                appointments = FXCollections.observableArrayList(queryAppointmentsOnWeek(timestamp));
            if (byMonthRadioButton.isSelected())
                appointments = FXCollections.observableArrayList(queryAppointmentsOnMonth(timestamp));
        } catch (SQLException e) {
            showAlert(e);
        } finally {
            closeConnection();
        }

        appointments.forEach(appointment -> {
            appointment.setStart(convertUTCToLocal(appointment.getStart()));
            appointment.setEnd(convertUTCToLocal(appointment.getEnd()));
        });
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

    @FXML private void handleAppointmentAdd(ActionEvent e) {
        showView(e, "../Views/appointmentView.fxml");
    }

    @FXML private void handleAppointmentModify(ActionEvent e) {
        Appointment appointment = appointmentTable.getSelectionModel().getSelectedItem();
        showView(e, appointment);
    }

    @FXML private void handleAppointmentDelete() {
        if (confirmationAlert(bundle.getString("alert.confirmAppointmentDelete"))) {
            try {
                openConnection();
                deleteAppointment(appointmentTable.getSelectionModel().getSelectedItem().getAppointmentId());
                showAlert(bundle.getString("alert.appointmentDeleted").replace("_", "ID: " +
                        appointmentTable.getSelectionModel().getSelectedItem().getAppointmentId() + " - Type: " +
                        appointmentTable.getSelectionModel().getSelectedItem().getType()));
            } catch (SQLException e) {
                showAlert(e);
            } finally {
                closeConnection();
            }
            initAppointmentTable();
        }
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

        // tv means table view
        customerTable.setRowFactory(tv -> {
            TableRow<Customer> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (tv.getSelectionModel().getSelectedItem() != null) {
                    modifyCustomerButton.setDisable(false);
                    deleteCustomerButton.setDisable(false);
                } else {
                    modifyCustomerButton.setDisable(true);
                    deleteCustomerButton.setDisable(true);
                }
            });
            return row;
        });
    }

    private void setCustomers() {
        try {
            openConnection();
            customers = FXCollections.observableArrayList(queryCustomers());
        } catch (SQLException e) {
            showAlert(e);
        } finally {
            closeConnection();
        }
    }

    @FXML private void handleCustomerAdd(ActionEvent e) {
        showView(e, "../Views/customerView.fxml");
    }

    @FXML private void handleCustomerModify(ActionEvent e) {
        Customer customer = customerTable.getSelectionModel().getSelectedItem();
        showView(e, customer);
    }

    @FXML private void handleCustomerDelete() {
        if (confirmationAlert(bundle.getString("alert.confirmCustomerDelete"))) {
            try {
                openConnection();
                validateDelete();
                deleteCustomer(customerTable.getSelectionModel().getSelectedItem().getCustomerId());
                showAlert(bundle.getString("alert.customerDeleted"));
            } catch (SQLException | IllegalAccessException e) {
                showAlert(e);
            } finally {
                closeConnection();
            }
            initCustomerTable();
        }
    }

    private void validateDelete() throws IllegalAccessException, SQLException {
        ArrayList<Appointment> allAppointments = queryAllAppointments();
        for (Appointment appointment : allAppointments) {
            if (appointment.getCustomer().getCustomerId() == customerTable.getSelectionModel().getSelectedItem().getCustomerId()) {
                throw new IllegalAccessException(bundle.getString("error.customerDependencyPresent"));
            }
        }
    }
}
