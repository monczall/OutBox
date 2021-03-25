package main.java.controllers;


import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import main.java.SceneManager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Client implements Initializable {

    @FXML
    private VBox paneRight;
    @FXML
    private FontAwesomeIconView hamburger;
    @FXML
    private Button btnSettings;
    @FXML
    private FontAwesomeIconView iconSettings;

    @FXML
    private Button btnHome;

    @FXML
    private Pane welcomeMessage;

    @FXML
    private AnchorPane mainWindow;

    boolean hamburgerClicked = false;


    @Override
    public void initialize(URL url, ResourceBundle rb){

    try {
        SceneManager.loadScene("../../resources/view/client/clientHome.fxml", mainWindow);
    } catch (IOException e) {
        e.printStackTrace();
    }

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

            TranslateTransition translateTransition2 = new TranslateTransition(Duration.seconds(0.5), welcomeMessage);
            translateTransition2.setByX(+170);
            translateTransition2.play();

            TranslateTransition translateTransition3 = new TranslateTransition(Duration.seconds(0.5), mainWindow);
            translateTransition3.setByX(+70);
            translateTransition3.play();
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

            TranslateTransition translateTransition2 = new TranslateTransition(Duration.seconds(0.5), welcomeMessage);
            translateTransition2.setByX(-170);
            translateTransition2.play();

            TranslateTransition translateTransition3 = new TranslateTransition(Duration.seconds(0.5), mainWindow);
            translateTransition3.setByX(-70);
            translateTransition3.play();


            fadeTransition.setOnFinished(event1 -> {
                paneRight.setVisible(false);
            });
        }
    });
    }

    public void packageButton(MouseEvent mouseEvent) {
    }

    @FXML
    void logout(ActionEvent event) {

    }

    @FXML
    void viewHistory(ActionEvent event) {

    }

    @FXML
    void viewHome(ActionEvent event) throws IOException {
        SceneManager.loadScene("../../resources/view/client/clientHome.fxml", mainWindow);
    }

    @FXML
    void viewRegisterPackage(ActionEvent event) throws IOException {
        SceneManager.loadScene("../../resources/view/client/clientRegisterPackage.fxml", mainWindow);
    }

    @FXML
    void viewSettings(ActionEvent event) throws IOException {
        SceneManager.loadScene("../../resources/view/client/clientSettings.fxml", mainWindow);
    }

    @FXML
    void viewTrackPackage(ActionEvent event) {

    }

}
