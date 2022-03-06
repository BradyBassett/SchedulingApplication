package com.C195.Controllers;

import com.C195.Models.Appointment;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Abstract class which represents every report view controller. Every ReportController is also a ViewController.
 * @author Brady Bassett
 */
public abstract class ReportController extends ViewController implements Initializable {
    @FXML protected Label titleLabel;
    @FXML protected Button closeButton;
    @FXML protected TableView<Appointment> reportTable;
    @FXML protected TableColumn<Appointment, Integer> appointmentId;
    @FXML protected TableColumn<Appointment, String> appointmentTitle;
    @FXML protected TableColumn<Appointment, String> appointmentDescription;
    @FXML protected TableColumn<Appointment, String> appointmentType;
    @FXML protected TableColumn<Appointment, String> appointmentStart;
    @FXML protected TableColumn<Appointment, String> appointmentEnd;

    /**
     * If the report has a table, this function will set all common text and placeholders.
     */
    protected void initCommonTableText() {
        reportTable.setPlaceholder(new Label(bundle.getString("table.empty")));
        appointmentId.setText(bundle.getString("table.appointment"));
        appointmentTitle.setText(bundle.getString("table.title"));
        appointmentDescription.setText(bundle.getString("table.description"));
        appointmentType.setText(bundle.getString("table.type"));
        appointmentStart.setText(bundle.getString("table.start"));
        appointmentEnd.setText(bundle.getString("table.end"));
    }

    /**
     * This function will first convert all appointment start and end times to the users local timezone, then populate
     * each common table cell with the appointment data.
     * LAMBDA JUSTIFICATION - I used a lambda expression here because I wanted to loop through each appointment in the
     * appointments list and convert the start and end times to the local timezone.
     * @param appointments An ObservableList of Appointments which were queried.
     */
    protected void setCommonAppointmentValues(ObservableList<Appointment> appointments) {
        appointments.forEach(appointment -> {
            appointment.setStart(convertUTCToLocal(appointment.getStart()));
            appointment.setEnd(convertUTCToLocal(appointment.getEnd()));
        });

        appointmentId.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        appointmentTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        appointmentDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        appointmentType.setCellValueFactory(new PropertyValueFactory<>("type"));
        appointmentStart.setCellValueFactory(new PropertyValueFactory<>("start"));
        appointmentEnd.setCellValueFactory(new PropertyValueFactory<>("end"));
    }

    /**
     * Whenever the close button is selected, switch back to the main view.
     * @param e The ActionEvent to pass down the current window to the main view.
     */
    @FXML protected void handleClose(ActionEvent e) {
        showView(e, "../Views/mainView.fxml");
    }
}
