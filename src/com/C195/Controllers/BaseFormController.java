package com.C195.Controllers;

import javafx.scene.control.Alert;

import java.util.Locale;
import java.util.ResourceBundle;

import static helper.JDBC.*;

public abstract class BaseFormController {
    protected static Locale locale = Locale.getDefault();
    protected static ResourceBundle bundle = ResourceBundle.getBundle("com.C195.language", locale);

    protected void showAlert(Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR, e.toString());
        alert.showAndWait();
    }
}
