package main.java.controllers.admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import main.java.SceneManager;

import java.io.IOException;


public class AdminArea {

    @FXML
    public AnchorPane mainWindow;

    public void viewAddArea(MouseEvent mouseEvent) throws IOException {
        SceneManager.loadScene("main/resources/view/admin/adminAddArea.fxml", mainWindow);
    }

    public void viewEditArea(MouseEvent mouseEvent) throws IOException {
        SceneManager.loadScene("main/resources/view/admin/adminSearchArea.fxml", mainWindow);
    }
}
