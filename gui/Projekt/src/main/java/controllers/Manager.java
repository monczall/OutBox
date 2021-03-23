package main.java.controllers;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import main.java.SceneManager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static main.java.SceneManager.loadScene;

public class Manager implements Initializable {

    @FXML
    private AnchorPane homePane;
    /*
    @FXML
    private VBox paneRight;
    @FXML
    private FontAwesomeIconView hamburger;
    @FXML
    private Button btnSettings, managerHomeButton, managerPackageButton;
    @FXML
    private FontAwesomeIconView iconSettings;

    boolean hamburgerClicked = false;

    @Override
    public void initialize(URL url, ResourceBundle rb){

    paneRight.setVisible(false);
    TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(0.5),paneRight);
    translateTransition1.setByX(-200);
    translateTransition1.play();


    hamburger.setOnMouseClicked(event -> {      // If hamburger button is clicked then menu slides in and transition last for 0.5s
        if(hamburgerClicked == false) {

            hamburgerClicked = true;
            paneRight.setVisible(true);

            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5), paneRight);
            fadeTransition.setFromValue(0);
            fadeTransition.setToValue(1);
            fadeTransition.play();

            TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5), paneRight);
            translateTransition.setByX(+200);
            translateTransition.play();
        }
        else {
            hamburgerClicked = false;

            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5), paneRight);
            fadeTransition.setFromValue(1);
            fadeTransition.setToValue(0);
            fadeTransition.play();

            TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5), paneRight);
            translateTransition.setByX(-200);
            translateTransition.play();


            fadeTransition.setOnFinished(event1 -> {
                paneRight.setVisible(false);
            });
        }
    });

    }

    @FXML
    public void actionHomeButton() throws IOException {
        SceneManager.loadScene("../../resources/view/manager/managerPackage.fxml", homePane);
    }

    @FXML
    public void actionPackageButton() {
        System.out.println("Package");
        SceneManager.renderScene("managerPackage");
    }

    @FXML
    public void actionRaportButton() {
        System.out.println("Raport");
    }

    @FXML
    public void actionCurierButton() {
        System.out.println("Curier");
    }

    @FXML
    public void actionSettingsButton() {
        System.out.println("Settings");
    }

    */

    @FXML
    public void actionLogout() {
        System.out.println("Logout");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void actionHomeButton(MouseEvent mouseEvent) {
    }

    public void actionPackageButton(MouseEvent mouseEvent) {
    }

    public void actionSettingsButton(MouseEvent mouseEvent) {
    }

    public void actionCurierButton(MouseEvent mouseEvent) {
    }

    public void actionRaportButton(MouseEvent mouseEvent) {
    }
}
