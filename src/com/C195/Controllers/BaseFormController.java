package com.C195.Controllers;

import javafx.scene.control.Alert;

import java.util.Locale;
import java.util.ResourceBundle;

public abstract class BaseFormController {
    protected final static Locale locale = Locale.getDefault();
    protected final static ResourceBundle bundle = ResourceBundle.getBundle("com.C195.Resources.language", locale);

    protected void showAlert(Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR, e.toString());
        alert.showAndWait();
    }
}
