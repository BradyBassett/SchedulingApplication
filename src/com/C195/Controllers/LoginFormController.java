package com.C195.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

import static com.C195.Database.JDBC.executeQuery;

public class LoginFormController extends BaseFormController implements Initializable {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginBtn;
    @FXML private Label zoneLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usernameField.setPromptText(bundle.getString("login.username"));
        loginBtn.setText(bundle.getString("login.login"));
        zoneLabel.setText(String.valueOf(locale));
    }

    @FXML private void onLogin(ActionEvent event) {
        try {
            loginValidation();
            executeQuery();
        } catch (NullPointerException e) {
            showAlert(e);
            return;
        }
        ViewController viewController = new ViewController();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        viewController.showView(stage, "../Views/Login/loginView.fxml");
    }

    private void loginValidation() {
        if (usernameField.getText().isEmpty()) throw new NullPointerException(bundle.getString("error.NullUsername"));
        if (passwordField.getText().isEmpty()) throw new NullPointerException(bundle.getString("error.Nullpassword"));
    }
}
