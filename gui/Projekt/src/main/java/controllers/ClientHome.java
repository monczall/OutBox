package main.java.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import main.java.SceneManager;

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

    }

    @FXML
    void viewHistory(ActionEvent event) {

    }

    @FXML
    void viewHome(ActionEvent event, AnchorPane window) throws IOException {
        SceneManager.loadScene("../../resources/view/client/clientHome.fxml", mainWindow);
    }

    @FXML
    void viewRegisterPackage(ActionEvent event) {

    }

    @FXML
    void viewSettings(ActionEvent event) throws IOException {
        SceneManager.loadScene("../../resources/view/client/clientSettings.fxml", mainWindow);
    }

    @FXML
    void viewTrackPackage(ActionEvent event) {

    }
}
