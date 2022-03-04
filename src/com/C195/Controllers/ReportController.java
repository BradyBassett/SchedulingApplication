package com.C195.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;


public abstract class ReportController extends ViewController implements Initializable {
    @FXML protected Label titleLabel;
    @FXML protected Button closeButton;

    @FXML protected void handleClose(ActionEvent e) {
        showView(e, "../Views/mainView.fxml");
    }
}
