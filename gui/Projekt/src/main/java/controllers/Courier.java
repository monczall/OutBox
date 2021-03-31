package main.java.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import main.java.SceneManager;

import java.io.IOException;


public class Courier {

    @FXML
    private AnchorPane mainAnchorPane;


    public void openHome(ActionEvent actionEvent) throws IOException {
        SceneManager.loadScene("../../resources/view/interbranchCourierHome.fxml", mainAnchorPane);
    }
    
}
