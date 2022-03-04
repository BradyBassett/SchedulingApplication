package com.C195.Controllers;

import com.C195.Models.CustomerAppointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.sql.SQLException;
import java.time.Month;
import java.util.ResourceBundle;

import static com.C195.Database.JDBC.closeConnection;
import static com.C195.Database.JDBC.openConnection;
import static com.C195.Database.QueryAppointments.queryNumberOfCustomerAppointments;

public class CustomerAppointmentsReportViewController extends ReportController {
    @FXML private TableView<CustomerAppointment> reportTable;
    @FXML private TableColumn<CustomerAppointment, Integer> customerAppointment;
    @FXML private TableColumn<CustomerAppointment, String> typeCol;
    @FXML private TableColumn<CustomerAppointment, Month> monthCol;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        titleLabel.setText(bundle.getString("label.customerAppointmentsReport"));
        closeButton.setText(bundle.getString("button.close"));
        reportTable.setPlaceholder(new Label(bundle.getString("table.empty")));
        customerAppointment.setText(bundle.getString("table.customerAppointments"));
        typeCol.setText(bundle.getString("table.type"));
        monthCol.setText(bundle.getString("table.month"));

        initTable();
    }

    private void initTable() {
//        try {
//            openConnection();
////            customerAppointments = FXCollections.observableArrayList(queryNumberOfCustomerAppointments(type, month));
//        } catch (SQLException e) {
//            showAlert(e);
//        } finally {
//            closeConnection();
//        }
    }
}
