package main.java.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import main.java.SceneManager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientSettings implements Initializable {

    @FXML
    private ToggleButton appSettings;

    @FXML
    private ToggleButton userSettings;

    @FXML
    private AnchorPane settingsAnchorPane;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            SceneManager.loadScene("../../resources/view/client/clientSettingsApp.fxml", settingsAnchorPane);
        } catch (IOException e) {
            e.printStackTrace();
        }


        ToggleGroup group = new ToggleGroup();
        appSettings.setToggleGroup(group);
        userSettings.setToggleGroup(group);
        appSettings.setSelected(true);
    }

    @FXML
    void changeAppSettings(ActionEvent event) throws IOException {
        SceneManager.loadScene("../../resources/view/client/clientSettingsApp.fxml", settingsAnchorPane);
    }

    @FXML
    void changeUserSettings(ActionEvent event) throws IOException {
        SceneManager.loadScene("../../resources/view/client/clientSettingsUser.fxml", settingsAnchorPane);
    }
}
