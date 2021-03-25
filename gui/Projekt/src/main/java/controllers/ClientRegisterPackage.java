package main.java.controllers;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import main.java.SceneManager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientRegisterPackage implements Initializable {

    @FXML
    private ToggleButton smallPackage;

    @FXML
    private ToggleButton mediumPackage;

    @FXML
    private ToggleButton bigPackage;

    @FXML
    private Circle navCircle;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ToggleGroup packageGroup = new ToggleGroup();

        smallPackage.setToggleGroup(packageGroup);
        mediumPackage.setToggleGroup(packageGroup);
        bigPackage.setToggleGroup(packageGroup);
    }

}
