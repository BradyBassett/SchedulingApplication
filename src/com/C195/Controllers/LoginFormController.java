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

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;

import static com.C195.Database.JDBC.closeConnection;
import static com.C195.Database.JDBC.openConnection;
import static com.C195.Database.QueryUsers.getCurrentUser;
import static com.C195.Database.QueryUsers.queryUser;


public class LoginFormController extends ViewController implements Initializable {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginBtn;
    @FXML private Label zoneLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usernameField.setPromptText(bundle.getString("login.username"));
        loginBtn.setText(bundle.getString("button.login"));
        zoneLabel.setText(String.valueOf(locale));
    }

    @FXML private void onLogin(ActionEvent event) {
        boolean successfulLogin = true;
        String errorMessage = "N/A";

        try {
            openConnection();
            queryUser(usernameField.getText(), passwordField.getText());
            loginValidation();
        } catch (NullPointerException | SQLException e) {
            showErrorAlert(e);
            errorMessage = e.getMessage();
            successfulLogin = false;
        } finally {
            closeConnection();
        }

        logLoginAttempt(successfulLogin, errorMessage);
        if (successfulLogin) {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            showView(stage, "../Views/mainView.fxml");
        }
    }

    private void loginValidation() throws SQLException {
        if (usernameField.getText().isEmpty())
            throw new NullPointerException(bundle.getString("error.nullUsername"));
        if (passwordField.getText().isEmpty())
            throw new NullPointerException(bundle.getString("error.nullPassword"));
        if (getCurrentUser() == null)
            throw new NullPointerException(bundle.getString("error.noMatchingUser"));
    }

    private void logLoginAttempt(boolean successfulLogin, String errorMessage) {
        final DateTimeFormatter dtf = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        final String dateTime = dtf.format(OffsetDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        final String delimiter = "---";

        try {
            final FileWriter fw = new FileWriter("login_activity.txt", true);
            fw.write("Successful Login: " + successfulLogin + delimiter + "Username Attempted: " +
                    (usernameField.getText().equals("") ? "N/A" : usernameField.getText()) + delimiter + "Datetime: " +
                    dateTime + delimiter + "Error Message: " + errorMessage + "\n");
            fw.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
