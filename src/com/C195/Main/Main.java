package com.C195.Main;

import com.C195.Controllers.BaseFormController;
import helper.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("com.C195.language", Locale.getDefault());
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../Views/Login/loginView.fxml")));
            Scene mainScene = new Scene(root);
            stage.setTitle(bundle.getString("app.title"));
            stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("icon.png"))));
            stage.setResizable(false);
            stage.setScene(mainScene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        JDBC.openConnection();
        launch(args);
        JDBC.closeConnection();
    }
}
