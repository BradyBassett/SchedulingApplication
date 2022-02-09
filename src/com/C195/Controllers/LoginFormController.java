package com.C195.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginFormController extends BaseFormController implements Initializable {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginBtn;
    @FXML private Label zoneLabel;
    private Stage stage;
    private Parent root;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usernameField.setPromptText(bundle.getString("login.username"));
        loginBtn.setText(bundle.getString("login.login"));
        zoneLabel.setText(String.valueOf(locale));
    }

    @FXML private void onLogin() {
        System.out.println(usernameField.getText());
        System.out.println(passwordField.getText());
        System.out.println(loginBtn);
    }
}
