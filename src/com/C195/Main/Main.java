package com.C195.Main;

import com.C195.Controllers.ViewController;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        ViewController viewController = new ViewController();
        viewController.showView(stage, "../Views/loginView.fxml");
        Image image = new Image("com/C195/Resources/icon.png");
        stage.setResizable(false);
        stage.getIcons().add(image);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
