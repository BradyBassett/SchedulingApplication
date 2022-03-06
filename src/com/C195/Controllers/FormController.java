package com.C195.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.util.regex.Pattern;

/**
 * Abstract class that contains common functions and variables between all form views. Every FormController is also a
 * ViewController.
 * @author Brady Bassett
 */
public abstract class FormController extends ViewController{
    @FXML protected Button cancelButton;
    @FXML protected Button saveButton;

    /**
     * Function which is called whenever the cancel button is pressed which switches back to the main view.
     * @param e The ActionEvent to pass down the current window to the main view.
     */
    @FXML
    protected void handleCancel(ActionEvent e) {
        showView(e, "../Views/mainView.fxml");
    }

    /**
     * A common function which initializes the cancel and save buttons text.
     */
    protected void initButtons() {
        cancelButton.setText(bundle.getString("button.cancel"));
        saveButton.setText(bundle.getString("button.save"));
    }

    /**
     * This function will take a given field String value and if that value is empty, it will throw a
     * NullPointerException with a custom provided error message.
     * @param field The field to check.
     * @param error The custom error message to pass.
     */
    protected void validateFieldNotEmpty(String field, String error) {
        if (field.isEmpty())
            throw new NullPointerException(error);
    }

    /**
     * This function takes a given regex value and checks if the given field matches the regex. If it doesn't match,
     * then throw an IllegalArgumentException with a custom provided error message.
     * @param regex The regex to match the field to.
     * @param field The field to match the regex to.
     * @param error The custom error message to pass.
     */
    protected void validateField(String regex, String field, String error) {
        if (!Pattern.matches(regex, field))
            throw new IllegalArgumentException(error);
    }
}
