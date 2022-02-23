package com.C195.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class AppointmentViewController extends ViewController {
    @FXML
    private Button cancelButton;
    @FXML private Button saveButton;

    @FXML private void handleCancel(ActionEvent e) throws IOException {
        showView(e, "../Views/mainView.fxml");
    }

    @FXML private void handleSave(ActionEvent e) throws IOException {
        showView(e, "../Views/mainView.fxml");
    }
}
