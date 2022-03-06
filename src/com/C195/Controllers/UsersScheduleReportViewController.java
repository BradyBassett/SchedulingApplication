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

/**
 * This class is responsible for displaying a report where every appointment associated with a given user is listed.
 * @author Brady Bassett
 */
public class UsersScheduleReportViewController extends ReportController {
    @FXML
    private ComboBox<User> userBox;
    @FXML private TableColumn<Appointment, Integer> userId;

    /**
     * Initializes all text and prompt texts values, as well as the items in the ComboBox and TableView.
     * @param url The url to the usersScheduleReportView.fxml file.
     * @param resourceBundle This parameter contains all locale-specific objects.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        titleLabel.setText(bundle.getString("label.usersScheduleReport"));
        closeButton.setText(bundle.getString("button.close"));
        userBox.setPromptText(bundle.getString("field.userId"));
        initCommonTableText();
        userId.setText(bundle.getString("table.user"));

        initUsers();
    }

    /**
     * Queries the database for every user record and displays each by its user id in the ComboBox.
     */
    private void initUsers() {
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

    /**
     * This function will query the database for all appointments that contain the selected user as a foreign key.
     * It then reloads the TableView to represent the newly queried data.
     * LAMBDA JUSTIFICATION - In order to display the user ID as the value in the table cell, it is necessary to
     * use a lambda function to create a SimpleIntegerProperty displaying the user id.
     */
    @FXML private void getReport() {
        ObservableList<Appointment> appointments;
        try {
            if (userBox.getValue() == null)
                throw new NullPointerException(bundle.getString("error.nullUserBox"));
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
