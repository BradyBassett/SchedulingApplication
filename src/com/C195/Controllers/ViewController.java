package com.C195.Controllers;

import com.C195.Models.Appointment;
import com.C195.Models.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class ViewController extends BaseController {
    public void showView(Stage stage, String view) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(view)));
            Scene scene = new Scene(root);
            stage.setTitle(bundle.getString("app.title"));
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void showView(ActionEvent event, String view) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(view)));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    protected void showView(ActionEvent event, Customer customer) {
        try {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("../Views/customerView.fxml")));
            Parent root = loader.load();
            CustomerViewController customerViewController = loader.getController();
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            customerViewController.initCustomerData(customer);
            customerViewController.newCustomer = false;
            stage.show();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    protected void showView(ActionEvent event, Appointment appointment) {
        try {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("../Views/appointmentView.fxml")));
            Parent root = loader.load();
            AppointmentViewController appointmentViewController = loader.getController();
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            appointmentViewController.initAppointmentData(appointment);
            appointmentViewController.newAppointment = false;
            stage.show();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
