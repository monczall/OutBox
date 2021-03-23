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

        SceneManager.addScene("courier", "../resources/view/courier.fxml");
        SceneManager.addScene("register", "../resources/view/register.fxml");
        SceneManager.addScene("password_reset", "../resources/view/passwordReset.fxml");
<<<<<<< HEAD
        SceneManager.addScene("courierHome", "../resources/view/courier.fxml");

        SceneManager.renderScene("courier");
=======
        SceneManager.addScene("client", "../resources/view/client.fxml");

        SceneManager.renderScene("client");
>>>>>>> 1ab4e8366b163178106cc386bb0948d8d9e9ea02
        primaryStage.setResizable(false);

    }
}
