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

/**
 * This class is used for instantiating and switching to different views. Every ViewController is also a BaseController.
 * @author Brady Bassett
 */
public class ViewController extends BaseController {
    /**
     * When given a stage, display the given view.
     * @param stage The stage to be shown.
     * @param view The view to be loaded.
     */
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

    /**
     * When given an ActionEven, a Parent, and a MainViewController instance, display the main view. Also display an
     * alert to the user displaying an upcoming appointment.
     * @param event The ActionEvent which will provide the source window.
     * @param root The root which contains the loaded view.
     * @param mvc The main view controller instance to display the alert on.
     */
    protected void showView(ActionEvent event, Parent root, MainViewController mvc) {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        mvc.appointmentAlert();
        stage.show();
    }

    /**
     * When given an ActionEvent and a valid path to a view, load and display that view.
     * @param event The ActionEvent which will provide the source window.
     * @param view The source path for the view.
     */
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

    /**
     * When given an ActionEvent and a Customer instance, display the customer view, and also initialize the provided
     * customer data.
     * @param event The ActionEvent which will provide the source window.
     * @param customer The customer data which will be initialized in the form.
     */
    protected void showView(ActionEvent event, Customer customer) {
        try {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().
                    getResource("../Views/customerView.fxml")));
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

    /**
     * When given an ActionEvent and an Appointment instance, display the appointment view, and also initialize the
     * provided appointment data.
     * @param event The ActionEvent which will provide the source window.
     * @param appointment The appointment data which will be initialized in the form.
     */
    protected void showView(ActionEvent event, Appointment appointment) {
        try {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().
                    getResource("../Views/appointmentView.fxml")));
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
