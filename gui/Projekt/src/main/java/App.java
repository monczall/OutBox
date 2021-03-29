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

        SceneManager.addScene("login", "../resources/view/login.fxml");
        SceneManager.addScene("register", "../resources/view/register.fxml");

        SceneManager.addScene("password_reset", "../resources/view/passwordReset.fxml");

        SceneManager.addScene("passwordReset", "../resources/view/passwordReset.fxml");

        SceneManager.addScene("client", "../resources/view/client/client.fxml");

        SceneManager.addScene("courier", "../resources/view/courier.fxml");
        SceneManager.addScene("courierHome", "../resources/view/courier.fxml");

        //TU SCENY KURIERA MIEDZYODDZIALOWEGO

        //TU SCENY KIEROWNIKA
        SceneManager.addScene("manager", "../resources/view/manager/manager.fxml");

        //TU ADMINA
        SceneManager.addScene("admin", "../resources/view/admin/admin.fxml");

        primaryStage.setResizable(false);
      //  primaryStage.initStyle(StageStyle.UNDECORATED);

        SceneManager.renderScene("admin");
    }
}
