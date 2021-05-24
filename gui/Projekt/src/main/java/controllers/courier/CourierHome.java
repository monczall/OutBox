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

    /**
     * method that opens main panel after clicking button
     * @param actionEvent
     * @throws IOException
     */
    public void openSecond(ActionEvent actionEvent) throws IOException {
        SceneManager.loadScene("../../../resources/view/courier/courierSecond.fxml", mainWindow);
    }

    /**
     * method that opens settings panel after clicking button
     * @param actionEvent
     * @throws IOException
     */
    public void openSettings(ActionEvent actionEvent) throws IOException {
        SceneManager.loadScene("../../../resources/view/courier/courierSettings.fxml", mainWindow);
    }

    public void logout(ActionEvent actionEvent) throws IOException {

        SceneManager.renderScene("login");
    }
}
