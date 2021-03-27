package main.java.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import main.java.SceneManager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static main.java.SceneManager.loadScene;

public class Manager implements Initializable {

    @FXML
    public AnchorPane mainAnchorPane;

    public void openHome(MouseEvent actionEvent) throws IOException {
        System.out.println("HOME");
        SceneManager.renderScene("manager");
    }

    public void openCouriers(MouseEvent mouseEvent) throws IOException {
        System.out.println("Couriers");
        SceneManager.loadScene("../../resources/view/manager/managerCouriers.fxml", mainAnchorPane);
    }

    public void openPackages(MouseEvent mouseEvent) throws IOException {
        System.out.println("Packages");
        SceneManager.loadScene("../../resources/view/manager/managerPackages.fxml", mainAnchorPane);
    }

    public void openSettings(MouseEvent mouseEvent) throws IOException {
        System.out.println("Settings");
        SceneManager.loadScene("../../resources/view/manager/managerSettings.fxml", mainAnchorPane);
    }

    public void openRaports(MouseEvent mouseEvent) throws IOException {
        System.out.println("Raports");
        SceneManager.loadScene("../../resources/view/manager/managerRaports.fxml", mainAnchorPane);
    }

    public void logout(MouseEvent mouseEvent) {
        System.out.println("ogout");
        SceneManager.renderScene("login");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb){
        System.out.println("Start program");
    }
}
