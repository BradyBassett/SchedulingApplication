package com.C195.Controllers;

import com.C195.Models.User;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

public abstract class BaseController {
    protected final static Locale locale = Locale.getDefault();
    protected final static ResourceBundle bundle = ResourceBundle.getBundle("com.C195.Resources.language", locale);
    protected static User currentUser;

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    protected void showAlert(Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR, e.toString());
        alert.showAndWait();
    }

    protected void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message);
        alert.showAndWait();
    }

    protected boolean confirmationAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message);
        Optional<ButtonType> confirmation = alert.showAndWait();
        return confirmation.isPresent() && confirmation.get() == ButtonType.OK;
    }
}
