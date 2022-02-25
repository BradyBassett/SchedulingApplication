package com.C195.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.util.regex.Pattern;

public abstract class FormController extends ViewController{
    @FXML protected Button cancelButton;
    @FXML protected Button saveButton;

    @FXML
    protected void handleCancel(ActionEvent e) {
        showView(e, "../Views/mainView.fxml");
    }

    protected void initButtons() {
        cancelButton.setText(bundle.getString("button.cancel"));
        saveButton.setText(bundle.getString("button.save"));
    }

    protected void validateFieldNotEmpty(String field, String error) {
        if (field.isEmpty())
            throw new NullPointerException(error);
    }

    protected void validateField(String regex, String field, String error) {
        if (!Pattern.matches(regex, field))
            throw new IllegalArgumentException(error);
    }
}
