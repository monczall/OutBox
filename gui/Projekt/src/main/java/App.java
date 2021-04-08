package main.java;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class App extends Application {

    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        SceneManager.setStage(primaryStage);

        SceneManager.addScene("login", "../resources/view/auth/login.fxml");
        SceneManager.addScene("register", "../resources/view/auth/register.fxml");
        SceneManager.addScene("passwordReset", "../resources/view/auth/passwordReset.fxml");

        SceneManager.addScene("client", "../resources/view/client/client.fxml");

        SceneManager.addScene("courier", "../resources/view/courier/courier.fxml");

        SceneManager.addScene("interbranchCourier", "../resources/view/interbranchCourier/interbranchCourier.fxml");

        SceneManager.addScene("manager", "../resources/view/manager/manager.fxml");

        SceneManager.addScene("admin", "../resources/view/admin/admin.fxml");

        primaryStage.setResizable(false);
      //primaryStage.initStyle(StageStyle.UNDECORATED);

        SceneManager.renderScene("login");
    }
}
