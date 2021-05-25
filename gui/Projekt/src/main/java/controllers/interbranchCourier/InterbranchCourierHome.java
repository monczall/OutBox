package main.java.controllers.interbranchCourier;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import main.java.SceneManager;

import java.io.IOException;

public class InterbranchCourierHome {


    @FXML
    private AnchorPane mainWindow;

    /**
     * method that opens main panel after clicking button
     * @param actionEvent
     * @throws IOException
     */
    public void openSecond(ActionEvent actionEvent) throws IOException {
        SceneManager.loadScene("../../../resources/view/interbranchCourier/interbranchCourierSecond.fxml", mainWindow);
    }

    /**
     * method that opens settings panel after clicking button
     * @param actionEvent
     * @throws IOException
     */
    public void openSettings(ActionEvent actionEvent) throws IOException {
        SceneManager.loadScene("../../../resources/view/interbranchCourier/interbranchCourierSettings.fxml",
                mainWindow);
    }

    public void logout(ActionEvent actionEvent) throws IOException {

        SceneManager.renderScene("login");
    }
}
