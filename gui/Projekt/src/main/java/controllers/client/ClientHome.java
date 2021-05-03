package main.java.controllers.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import main.java.SceneManager;
import main.java.dao.PackageHistoryDAO;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class ClientHome implements Initializable {

    @FXML
    private AnchorPane mainWindow;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    void logout(ActionEvent event) {
        SceneManager.renderScene("login");
    }

    @FXML
    void viewHistory(ActionEvent event) throws IOException {
        SceneManager.loadScene("../../../resources/view/client/clientHistoryPackage.fxml", mainWindow);
    }

    @FXML
    void viewRegisterPackage(ActionEvent event) throws IOException {
        SceneManager.loadScene("../../../resources/view/client/clientRegisterPackage.fxml", mainWindow);
    }

    @FXML
    void viewSettings(ActionEvent event) throws IOException {
        SceneManager.loadScene("../../../resources/view/client/clientSettings.fxml", mainWindow);
    }

    @FXML
    void viewTrackPackage(ActionEvent event) throws IOException {
        SceneManager.loadScene("../../../resources/view/client/clientTrackPackage.fxml", mainWindow);
    }
}
