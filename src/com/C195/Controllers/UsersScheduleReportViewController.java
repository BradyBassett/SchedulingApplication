package com.C195.Controllers;

import com.C195.Database.QueryAppointments;
import com.C195.Models.Appointment;
import com.C195.Models.User;
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
import static com.C195.Database.QueryUsers.queryUsers;

public class UsersScheduleReportViewController extends ReportController {
    @FXML
    private ComboBox<User> userBox;
    @FXML private TableColumn<Appointment, Integer> userId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        titleLabel.setText(bundle.getString("label.usersScheduleReport"));
        closeButton.setText(bundle.getString("button.close"));
        userBox.setPromptText(bundle.getString("field.userId"));
        initCommonTableText();
        userId.setText(bundle.getString("table.user"));

        initContacts();
    }

    private void initContacts() {
        ObservableList<User> users;
        try {
            openConnection();
            users = FXCollections.observableArrayList(queryUsers());
        } catch (SQLException e) {
            showAlert(e);
            return;
        } finally {
            closeConnection();
        }

        userBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(User user) {
                return String.valueOf(user.getUserId());
            }

            @Override
            public User fromString(String s) {
                return null;
            }
        });
        userBox.setItems(users);
    }

    @FXML private void getReport() {
        ObservableList<Appointment> appointments;
        try {
            openConnection();
            appointments = FXCollections.observableArrayList(QueryAppointments.queryAppointmentsByUser(userBox.getValue().getUserId()));
        } catch (SQLException e) {
            showAlert(e);
            return;
        } finally {
            closeConnection();
        }

        setCommonAppointmentValues(appointments);
        userId.setCellValueFactory(cd -> new SimpleIntegerProperty(cd.getValue().getUser().getUserId()).asObject());
        reportTable.setItems(appointments);
    }
}