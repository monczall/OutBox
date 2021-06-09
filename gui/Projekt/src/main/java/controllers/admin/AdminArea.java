package main.java.controllers.admin;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import main.java.SceneManager;

import java.io.IOException;


public class AdminArea {

    @FXML
    public AnchorPane mainWindow;

    /**
     * Method that change scene to "addArea"
     *
     * @param mouseEvent mouse event
     * @throws IOException if doesn't find a scene then throw IOException
     */
    public void viewAddArea(MouseEvent mouseEvent) throws IOException {
        SceneManager.loadScene("main/resources/view/admin/adminAddArea.fxml", mainWindow);
    }

    /**
     * Method that change scene to "searchArea"
     *
     * @param mouseEvent mouse event
     * @throws IOException if doesn't find a scene then throw IOException
     */
    public void viewEditArea(MouseEvent mouseEvent) throws IOException {
        SceneManager.loadScene("main/resources/view/admin/adminSearchArea.fxml", mainWindow);
    }
}
