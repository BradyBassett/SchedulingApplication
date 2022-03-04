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

    protected void initCommonTableText() {
        reportTable.setPlaceholder(new Label(bundle.getString("table.empty")));
        appointmentId.setText(bundle.getString("table.appointment"));
        appointmentTitle.setText(bundle.getString("table.title"));
        appointmentDescription.setText(bundle.getString("table.description"));
        appointmentType.setText(bundle.getString("table.type"));
        appointmentStart.setText(bundle.getString("table.start"));
        appointmentEnd.setText(bundle.getString("table.end"));
    }

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

    @FXML protected void handleClose(ActionEvent e) {
        showView(e, "../Views/mainView.fxml");
    }
}
