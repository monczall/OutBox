package main.java.controllers.courier;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.java.SceneManager;

import java.io.IOException;

public class CourierHome {

    @FXML
    private AnchorPane mainWindow;


    public void openSecond(ActionEvent actionEvent) throws IOException {
        SceneManager.loadScene("../../../resources/view/courier/courierSecond.fxml", mainWindow);
    }


    public void openSettings(ActionEvent actionEvent) throws IOException {
        SceneManager.loadScene("../../../resources/view/courier/courierSettings.fxml", mainWindow);
    }

    public void logout(ActionEvent actionEvent) throws IOException {

        SceneManager.renderScene("login");
    }
}
