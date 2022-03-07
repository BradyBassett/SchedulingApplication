package com.C195.Main;

import com.C195.Controllers.ViewController;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * The main class which creates a view controller in order to set the stage and display the login page.
 * @author Brady Bassett
 */
public class Main extends Application {

    /**
     * Instantiates a new view as well as setting the application icon.
     * @param stage primary stage that the application gui is built upon.
     */
    @Override
    public void start(Stage stage) {
        ViewController viewController = new ViewController();
        viewController.showView(stage, "../Views/loginView.fxml");
        Image image = new Image("com/C195/Resources/icon.png");
        stage.setResizable(false);
        stage.getIcons().add(image);
    }

    /**
     * Launches the application.
     * @param args the arguments passed to the application on startup.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
