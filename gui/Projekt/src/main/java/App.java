package main.java;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        SceneManager.setStage(primaryStage);

        SceneManager.addScene("login", "../resources/view/login.fxml");
        SceneManager.addScene("register", "../resources/view/register.fxml");
        SceneManager.addScene("password_reset", "../resources/view/passwordReset.fxml");
        SceneManager.addScene("client", "../resources/view/client.fxml");

        SceneManager.renderScene("client");
        primaryStage.setResizable(false);

    }
}
