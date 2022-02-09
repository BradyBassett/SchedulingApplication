package com.C195.Controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class ViewController extends BaseFormController  {
    public void showView(Stage stage, String view) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(view)));
            Scene scene = new Scene(root);
            stage.setTitle(bundle.getString("app.title"));
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
