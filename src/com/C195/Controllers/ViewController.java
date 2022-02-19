package com.C195.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class ViewController extends BaseController {
    public void showView(Stage stage, String view) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(view)));
            Scene scene = new Scene(root);
            stage.setTitle(bundle.getString("app.title"));
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void showView(ActionEvent e, String view) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(view)));
        Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
