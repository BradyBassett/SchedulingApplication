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
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import static com.C195.Database.JDBC.closeConnection;
import static com.C195.Database.JDBC.openConnection;
import static com.C195.Database.QueryAppointments.*;
import static com.C195.Database.QueryCustomers.deleteCustomer;
import static com.C195.Database.QueryCustomers.queryCustomers;

/**
 * This class is responsible for controlling all functionality for the mainView scene.
 * @author Brady Bassett
 */
public class MainViewController extends ViewController implements Initializable {
    private ObservableList<Appointment> appointments = FXCollections.observableArrayList();
    private ObservableList<Customer> customers = FXCollections.observableArrayList();
    @FXML private Label appointmentsLabel;
    @FXML private Label customersLabel;
    @FXML private Label reportsLabel;
    @FXML private Tab appointmentsTab;
    @FXML private Tab customersTab;
    @FXML private Tab reportsTab;
    @FXML private Button addAppointmentButton;
    @FXML private Button modifyAppointmentButton;
    @FXML private Button deleteAppointmentButton;
    @FXML private Button addCustomerButton;
    @FXML private Button modifyCustomerButton;
    @FXML private Button deleteCustomerButton;
    @FXML private Button customerAppointmentsButton;
    @FXML private Button contactsScheduleButton;
    @FXML private Button usersScheduleButton;
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

    /**
     * Initializes all text, prompt texts and table values.
     * @param url The url to the mainView.fxml file.
     * @param resourceBundle This parameter contains all locale-specific objects.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initText();
        initAppointmentTable();
        initCustomerTable();
    }

    /**
     * When called, sets the text and prompt text values.
     */
    private void initText() {
        appointmentsTab.setText(bundle.getString("tab.appointments"));
        customersTab.setText(bundle.getString("tab.customers"));
        reportsTab.setText(bundle.getString("tab.reports"));
        appointmentsLabel.setText(bundle.getString("tab.appointments"));
        customersLabel.setText(bundle.getString("tab.customers"));
        reportsLabel.setText(bundle.getString("tab.reports"));

        addAppointmentButton.setText(bundle.getString("button.add"));
        modifyAppointmentButton.setText(bundle.getString("button.modify"));
        deleteAppointmentButton.setText(bundle.getString("button.delete"));
        addCustomerButton.setText(bundle.getString("button.add"));
        modifyCustomerButton.setText(bundle.getString("button.modify"));
        deleteCustomerButton.setText(bundle.getString("button.delete"));
        customerAppointmentsButton.setText(bundle.getString("button.customerAppointments"));
        contactsScheduleButton.setText(bundle.getString("button.contactsSchedule"));
        usersScheduleButton.setText(bundle.getString("button.usersSchedule"));
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

    /**
     * This function first will query all applicable appointments and then will set all cell values for each table cell.
     * After this, it will set up an onMouseClicked event handeler for each row to toggle the modify and delete buttons
     * on or off depending on if a row is selected by the user.
     * LAMBDA JUSTIFICATION - First for the cell values, a lambda function made sense because I did not want to display
     * the reference to the model, but instead wanted to display the models ID. And for the RowFactory, I wanted to set
     * up an onMouseClicked event handeler for each row present in the table. And whenever that event handeler is called
     * I wanted to toggle the modify and delete button states.
     */
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
        appointmentCustomerId.setCellValueFactory(cd -> new SimpleIntegerProperty(cd.getValue().getCustomer().
                getCustomerId()).asObject());
        appointmentContactId.setCellValueFactory(cd -> new SimpleIntegerProperty(cd.getValue().getContact().
                getContactId()).asObject());
        appointmentUserId.setCellValueFactory(cd -> new SimpleIntegerProperty(cd.getValue().getUser().getUserId()).
                asObject());
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

    /**
     * This function will first convert the usersLocal time to UTC, and then depending on what Radio Button is selected,
     * it will query the appointments table on that specific timeframe. Then for each appointment returned, convert the
     * start and end times to the users local timezone.
     * LAMBDA JUSTIFICATION - I used a lambda expression here because I wanted to loop through each appointment in the
     *      * appointments list and convert the start and end times to the local timezone.
     */
    private void setAppointments() {
        try {
            openConnection();
            String timestamp = convertLocalToUTC(Timestamp.valueOf(selectedDay.getValue().atTime(LocalTime.now()))).
                    toString();
            if (byDayRadioButton.isSelected())
                appointments = FXCollections.observableArrayList(queryAppointmentsOnDay(timestamp));
            if (byWeekRadioButton.isSelected())
                appointments = FXCollections.observableArrayList(queryAppointmentsOnWeek(timestamp));
            if (byMonthRadioButton.isSelected())
                appointments = FXCollections.observableArrayList(queryAppointmentsOnMonth(timestamp));
        } catch (SQLException e) {
            showAlert(e);
            return;
        } finally {
            closeConnection();
        }

        appointments.forEach(appointment -> {
            appointment.setStart(convertUTCToLocal(appointment.getStart()));
            appointment.setEnd(convertUTCToLocal(appointment.getEnd()));
        });
    }

    /**
     * Whenever one of the radio buttons is selected, set the table placeholder to the appropriate property, and
     * initialize the table to represent the change.
     */
    @FXML private void handleScheduleView() {
        if (byDayRadioButton.isSelected())
            appointmentTable.setPlaceholder(new Label(bundle.getString("table.empty.day")));
        else if (byWeekRadioButton.isSelected())
            appointmentTable.setPlaceholder(new Label(bundle.getString("table.empty.week")));
        else
            appointmentTable.setPlaceholder(new Label(bundle.getString("table.empty.month")));

        initAppointmentTable();
    }

    /**
     * If a user selects the add button, switch to the appointment view form.
     * @param e The ActionEvent to pass down the current window to the appointment view.
     */
    @FXML private void handleAppointmentAdd(ActionEvent e) {
        showView(e, "../Views/appointmentView.fxml");
    }

    /**
     * if a user selects the modify button, first get the selected appointment and then pass it down so that the
     * appointment data may be displayed in the form to be modified.
     * @param e The ActionEvent to pass down the current window to the appointment view.
     */
    @FXML private void handleAppointmentModify(ActionEvent e) {
        Appointment appointment = appointmentTable.getSelectionModel().getSelectedItem();
        showView(e, appointment);
    }

    /**
     * When a user attempts to delete an appointment, first show an alert to get user confirmation on the deletion, then
     * attempt to delete the appointment.
     */
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
                return;
            } finally {
                closeConnection();
            }
            initAppointmentTable();
        }
    }

    /**
     * This function first will query all customers within the customers table and then will set all cell values for
     * each table cell. After this, it will set up an onMouseClicked event handeler for each row to toggle the modify
     * and delete buttons on or off depending on if a row is selected by the user.
     * LAMBDA JUSTIFICATION - First for the cell value, a lambda function made sense because I did not want to display
     * the reference to the model, but instead wanted to display the models ID. And for the RowFactory, I wanted to set
     * up an onMouseClicked event handeler for each row present in the table. And whenever that event handeler is called
     * I wanted to toggle the modify and delete button states.
     */
    private void initCustomerTable() {
        setCustomers();

        customerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        customerPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        customerPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        // cd means cell data
        customerDivisionId.setCellValueFactory(cd -> new SimpleIntegerProperty(cd.getValue().getDivision().
                getDivisionID()).asObject());
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

    /**
     * This function will attempt to query the customers table for every customer record present and then store them
     * into the customers ObservableList.
     */
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

    /**
     * If a user selects the add button, switch to the customer view form.
     * @param e The ActionEvent to pass down the current window to the customer view.
     */
    @FXML private void handleCustomerAdd(ActionEvent e) {
        showView(e, "../Views/customerView.fxml");
    }

    /**
     * if a user selects the modify button, first get the selected customer and then pass it down so that the customer
     * data may be displayed in the form to be modified.
     * @param e The ActionEvent to pass down the current window to the customer view.
     */
    @FXML private void handleCustomerModify(ActionEvent e) {
        Customer customer = customerTable.getSelectionModel().getSelectedItem();
        showView(e, customer);
    }

    /**
     * When a user attempts to delete a customer, first show an alert to get user confirmation on the deletion, then
     * validate that the customer being deleted is not referenced in any appointments. Then, if all cases pass, delete
     * the customer.
     */
    @FXML private void handleCustomerDelete() {
        if (confirmationAlert(bundle.getString("alert.confirmCustomerDelete"))) {
            try {
                openConnection();
                validateCustomerDelete();
                deleteCustomer(customerTable.getSelectionModel().getSelectedItem().getCustomerId());
                showAlert(bundle.getString("alert.customerDeleted"));
            } catch (SQLException | IllegalAccessException e) {
                showAlert(e);
                return;
            } finally {
                closeConnection();
            }
            initCustomerTable();
        }
    }

    /**
     * This function checks all appointment records in the appointments table to see any of them contain the customer
     * that the user is attempting to delete. If the customer is present, then display an alert with the appropriate
     * message.
     * @throws IllegalAccessException Throws an IllegalAccessException if the customer is present in any appointments.
     * @throws SQLException Throws an SQLException if the query experiences an error.
     */
    private void validateCustomerDelete() throws IllegalAccessException, SQLException {
        ArrayList<Appointment> allAppointments = queryAllAppointments();
        for (Appointment appointment : allAppointments) {
            if (appointment.getCustomer().getCustomerId() == customerTable.getSelectionModel().getSelectedItem().
                    getCustomerId()) {
                throw new IllegalAccessException(bundle.getString("error.customerDependencyPresent"));
            }
        }
    }

    /**
     * This function will display an alert either telling the user if they have an upcoming appointment, or that they
     * have no upcoming appointments within the next 15 minutes.
     */
    public void appointmentAlert() {
        Appointment appointment = null;
        try {
            openConnection();
            // first get the current local time, then add 15 minutes to that time
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            Timestamp now = new Timestamp(calendar.getTimeInMillis());
            calendar.add(Calendar.MINUTE, 15);
            Timestamp next15 = new Timestamp(calendar.getTimeInMillis());
            appointment = queryNextAppointment(convertLocalToUTC(now), convertLocalToUTC(next15));
        } catch (SQLException e) {
            showAlert(e);
        } finally {
            closeConnection();
        }
        // if queryNextAppointment returns an appointment
        if (appointment != null) {
            showAlert(bundle.getString("alert.appointmentUpcoming").replace("_", "Appointment ID: "
                    + appointment.getAppointmentId() + " starts at: " + convertUTCToLocal(appointment.getStart())));
        } else {
            showAlert(bundle.getString("alert.noUpcomingAppointments"));
        }
    }

    /**
     * This function switches to the customerAppointmentsReportView when the customerAppointmentsReport button is
     * selected.
     * @param e The ActionEvent to pass down the current window to the customer view.
     */
    @FXML private void handleCustomerAppointmentsReport(ActionEvent e) {
        showView(e, "../Views/customerAppointmentsReportView.fxml");
    }

    /**
     * This function switches to the contactsScheduleReportView when the contactScheduleReport button is selected.
     * @param e The ActionEvent to pass down the current window to the customer view.
     */
    @FXML private void handleContactScheduleReport(ActionEvent e) {
        showView(e, "../Views/contactsScheduleReportView.fxml");
    }

    /**
     * This function switches to the usersScheduleReportView when the usersScheduleReport button is selected.
     * @param e The ActionEvent to pass down the current window to the customer view.
     */
    @FXML private void handleUsersScheduleReport(ActionEvent e) {
        showView(e, "../Views/usersScheduleReportView.fxml");
    }
}
