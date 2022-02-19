package com.C195.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public abstract class FormController extends ViewController {
    @FXML protected Button cancelButton;
    @FXML protected Button saveButton;

    @FXML protected void handleCancel(ActionEvent e) throws IOException {
        showView(e, "../Views/mainView.fxml");
    }

    @FXML protected void handleSave(ActionEvent e) throws IOException {
        showView(e, "../Views/mainView.fxml");
    }
}
