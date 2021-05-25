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

    /**
     * Method that change scene to "Settings"
     * @param actionEvent action event
     * @throws IOException if doesn't find a scene then throw IOException
     */
    public void viewSettings(ActionEvent actionEvent) throws IOException {
        SceneManager.loadScene("../../../resources/view/admin/adminSettings.fxml", mainWindow);
    }

    /**
     * Method that change scene to "Pack Settings"
     * @param actionEvent action event
     * @throws IOException if doesn't find a scene then throw IOException
     */
    public void viewPackSettings(ActionEvent actionEvent) throws IOException {
        SceneManager.loadScene("../../../resources/view/admin/adminPackSettings.fxml", mainWindow);
    }
    /**
     * Method that change scene to "Reports"
     * @param actionEvent action event
     * @throws IOException if doesn't find a scene then throw IOException
     */
    public void viewRaport(ActionEvent actionEvent) throws IOException {
        SceneManager.loadScene("../../../resources/view/admin/adminRaport.fxml", mainWindow);
    }
    /**
     * Method that change scene to "Add area"
     * @param actionEvent action event
     * @throws IOException if doesn't find a scene then throw IOException
     */
    public void viewAddArea(ActionEvent actionEvent) throws IOException {
        SceneManager.loadScene("../../../resources/view/admin/adminAddArea.fxml", mainWindow);
    }
    /**
     * Method that change scene to "Edit employee"
     * @param actionEvent action event
     * @throws IOException if doesn't find a scene then throw IOException
     */
    public void viewEditEmployee(ActionEvent actionEvent) throws IOException {
        SceneManager.loadScene("../../../resources/view/admin/adminEditEmployee.fxml", mainWindow);
    }
    /**
     * Method that change scene to "Add manager"
     * @param actionEvent action event
     * @throws IOException if doesn't find a scene then throw IOException
     */
    public void viewAddManager(ActionEvent actionEvent) throws IOException {
        SceneManager.loadScene("../../../resources/view/admin/adminAddManager.fxml", mainWindow);
    }
}
