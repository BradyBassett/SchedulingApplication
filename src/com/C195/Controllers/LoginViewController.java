package com.C195.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

import static com.C195.Database.JDBC.*;
import static com.C195.Database.QueryUsers.setCurrUser;

/**
 * This class is responsible for controlling all functionality for the login view. Every LoginViewController is a
 * ViewController.
 * @author Brady Bassett
 */
public class LoginViewController extends ViewController implements Initializable {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginBtn;
    @FXML private Label zoneLabel;

    /**
     * Initializes all text and prompt texts.
     * @param url The url to the loginView.fxml file.
     * @param resourceBundle This parameter contains all locale-specific objects.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usernameField.setPromptText(bundle.getString("field.username"));
        loginBtn.setText(bundle.getString("button.login"));
        zoneLabel.setText(String.valueOf(Locale.getDefault()));
    }

    /**
     * Whenever a user attempts to click the login button, this function will validate the username and password fields,
     * and also authenticate the users credentials. In the case that a user fails the validation or authentication then
     * display an alert with the appropriate error message. The login attempt will then be logged, and if the
     * authentication is successful switch to the main view.
     * @param event The ActionEvent to pass down the current window to the main view.
     */
    @FXML private void onLogin(ActionEvent event) {
        boolean successfulLogin = true;
        String errorMessage = "N/A";

        try {
            openConnection();
            setCurrUser(usernameField.getText(), passwordField.getText());
            loginValidation();
        } catch (NullPointerException | SQLException exception) {
            showAlert(exception);
            errorMessage = exception.getMessage();
            successfulLogin = false;
        } finally {
            closeConnection();
        }

        logLoginAttempt(successfulLogin, errorMessage);
        if (successfulLogin) {
            try {
                FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("../Views/mainView.fxml")));
                Parent root = loader.load();
                MainViewController mvc = loader.getController();
                showView(event, root, mvc);
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }

    /**
     * This function will check if the username or password fields are empty, and will also check if the current user is
     * assigned to null. If any of these are true, a NullPointerException is thrown with the appropriate error message.
     */
    private void loginValidation() {
        if (usernameField.getText().isEmpty())
            throw new NullPointerException(bundle.getString("error.nullUsername"));
        if (passwordField.getText().isEmpty())
            throw new NullPointerException(bundle.getString("error.nullPassword"));
        if (getCurrentUser() == null)
            throw new NullPointerException(bundle.getString("error.noMatchingUser"));
    }

    /**
     * This function logs every login attempt to the login_activity.txt file (one is created if it does not already
     * exist). If the login attempt is successful then no error message is provided, otherwise if the attempt was
     * unsuccessful then the appropriate error message is displayed in addition to the login failure.
     * @param successfulLogin This value determines if the login was successful or not.
     * @param errorMessage If the login was successful then this should be an empty String, otherwise the appropriate
     *                     error message is provided.
     */
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
