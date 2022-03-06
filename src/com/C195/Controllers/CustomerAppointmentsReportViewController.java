package com.C195.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import java.net.URL;
import java.sql.SQLException;
import java.time.Month;
import java.util.ResourceBundle;

import static com.C195.Database.JDBC.closeConnection;
import static com.C195.Database.JDBC.openConnection;
import static com.C195.Database.QueryAppointments.queryAppointmentTypes;
import static com.C195.Database.QueryAppointments.queryNumberOfCustomerAppointments;

/**
 * This class is responsible for displaying a report where all customer appointments that match a given month and
 * appointment type.
 * @author Brady Bassett
 */
public class CustomerAppointmentsReportViewController extends ReportController {
    @FXML private ComboBox<String> typesBox;
    @FXML private ComboBox<Month> monthsBox;
    @FXML private Button getReportButton;
    @FXML private Label count;
    @FXML private Label type;
    @FXML private Label month;

    /**
     * Initializes all text and prompt texts values, as well as the items in each ComboBox.
     * @param url The url to the customerAppointmentsReportView.fxml file.
     * @param resourceBundle This parameter contains all locale-specific objects.
     */
    public void initialize(URL url, ResourceBundle resourceBundle) {
        titleLabel.setText(bundle.getString("label.customerAppointmentsReport"));
        closeButton.setText(bundle.getString("button.close"));
        getReportButton.setText(bundle.getString("button.getReport"));
        typesBox.setPromptText(bundle.getString("field.type"));
        monthsBox.setPromptText(bundle.getString("field.month"));
        initTypes();
        initMonths();
    }

    /**
     * Queries the database for every appointment type value.
     */
    private void initTypes() {
        ObservableList<String> types;
        try {
            openConnection();
            types = FXCollections.observableArrayList(queryAppointmentTypes());
        } catch (SQLException e) {
            showAlert(e);
            return;
        } finally {
            closeConnection();
        }

        typesBox.setItems(types);
    }

    /**
     * Sets the months ComboBox to include every month in the year.
     */
    private void initMonths() {
        ObservableList<Month> months = FXCollections.observableArrayList();
        months.setAll(Month.JANUARY, Month.FEBRUARY, Month.MARCH, Month.APRIL, Month.MAY, Month.JUNE, Month.JULY,
                Month.AUGUST, Month.SEPTEMBER, Month.OCTOBER, Month.NOVEMBER, Month.DECEMBER);
        monthsBox.setItems(months);
    }

    /**
     * Ensuring that no boxes are left as null, this function will query the database for the number of appointments
     * that match the given box parameters.
     */
    @FXML private void getReport() {
        int aptCount;
        try {
            validateBoxes();
            openConnection();
            aptCount = queryNumberOfCustomerAppointments(typesBox.getValue(), monthsBox.getValue());
        } catch (NullPointerException | SQLException e) {
            showAlert(e);
            return;
        } finally {
            closeConnection();
        }
        count.setText(String.valueOf(aptCount));
        type.setText(typesBox.getValue());
        month.setText(monthsBox.getValue().toString());
    }

    /**
     * Checks each ComboBox to ensure that no value is left null.
     */
    private void validateBoxes() {
        if (typesBox.getValue() == null)
            throw new NullPointerException(bundle.getString("error.nullTypeBox"));
        if (monthsBox.getValue() == null)
            throw new NullPointerException(bundle.getString("error.nullMonthBox"));
    }
}
