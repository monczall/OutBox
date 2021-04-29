package main.java.controllers.admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import main.java.SceneManager;

import java.io.IOException;

public class AdminHome  {

    @FXML
    private AnchorPane mainWindow;

    public void logout(ActionEvent actionEvent) {
        SceneManager.renderScene("login");
    }

   

    public void viewSettings(ActionEvent actionEvent) throws IOException {
        SceneManager.loadScene("../../../resources/view/admin/adminSettings.fxml", mainWindow);
    }

    

    public void viewPackSettings(ActionEvent actionEvent) throws IOException {
        SceneManager.loadScene("../../../resources/view/admin/adminPackSettings.fxml", mainWindow);
    }

    public void viewRaport(ActionEvent actionEvent) throws IOException {
        SceneManager.loadScene("../../../resources/view/admin/adminRaport.fxml", mainWindow);
    }

    public void viewAddArea(ActionEvent actionEvent) throws IOException {
        SceneManager.loadScene("../../../resources/view/admin/adminAddArea.fxml", mainWindow);
    }

    public void viewEditEmployee(ActionEvent actionEvent) throws IOException {
        SceneManager.loadScene("../../../resources/view/admin/adminEditEmployee.fxml", mainWindow);
    }

    public void viewAddManager(ActionEvent actionEvent) throws IOException {
        SceneManager.loadScene("../../../resources/view/admin/adminAddManager.fxml", mainWindow);
    }
}
