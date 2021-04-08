package main.java.controllers.client;


import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import main.java.SceneManager;
import main.java.features.Animations;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Client implements Initializable {

    @FXML
    private VBox paneRight;
    @FXML
    private FontAwesomeIconView hamburger;

    @FXML
    private Pane welcomeMessage;

    @FXML
    private AnchorPane mainWindow;

    @FXML
    private AnchorPane window;

    @FXML
    private Pane alertPane;

    @FXML
    private AnchorPane appWindow;

    boolean hamburgerClicked = false;


    @Override
    public void initialize(URL url, ResourceBundle rb){


    try {
        SceneManager.loadScene("../../../resources/view/client/clientHome.fxml", mainWindow);
    } catch (IOException e) {
        e.printStackTrace();
    }

    alertPane.setTranslateY(-500);
    paneRight.setTranslateX(-200);


    hamburger.setOnMouseClicked(event -> {      // If hamburger button is clicked then menu slides in and transition last for 0.5s
        if(hamburgerClicked == false) {

            hamburger.setDisable(true);
            hamburgerClicked = true;
            paneRight.setVisible(true);

            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5), paneRight);
            fadeTransition.setFromValue(0);
            fadeTransition.setToValue(1);
            fadeTransition.play();

            Animations.moveByX(paneRight,+200,0.5);
            Animations.moveByX(welcomeMessage,+170,0.5);
            Animations.moveByX(mainWindow,+70,0.5);

            fadeTransition.setOnFinished(event1 -> {
                hamburger.setDisable(false);
            });
        }
        else {
            hamburger.setDisable(true);
            hamburgerClicked = false;

            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5), paneRight);
            fadeTransition.setFromValue(1);
            fadeTransition.setToValue(0);
            fadeTransition.play();

            Animations.moveByX(paneRight,-200,0.5);
            Animations.moveByX(welcomeMessage,-170,0.5);
            Animations.moveByX(mainWindow,-70,0.5);

            fadeTransition.setOnFinished(event1 -> {
                paneRight.setVisible(false);
                hamburger.setDisable(false);
            });
        }
    });

    }

    public void packageButton(MouseEvent mouseEvent) {
    }

    @FXML
    void logout(ActionEvent event) {
        Animations.moveByY(alertPane,+500,0.3);
        GaussianBlur gaussianBlur = new GaussianBlur();
        gaussianBlur.setRadius(8);
        window.setDisable(true);
        window.setEffect(gaussianBlur);
    }

    @FXML
    void logoutNo(ActionEvent event) {
        Animations.moveByY(alertPane,-500,0.3);
        window.setEffect(null);
        window.setDisable(false);
    }

    @FXML
    void logoutYes(ActionEvent event) {
        SceneManager.renderScene("login");
    }


    @FXML
    void viewHistory(ActionEvent event) throws IOException {
        SceneManager.loadScene("../../../resources/view/client/clientHistoryPackage.fxml", mainWindow);
    }

    @FXML
    void viewHome(ActionEvent event) throws IOException {
        SceneManager.loadScene("../../../resources/view/client/clientHome.fxml", mainWindow);
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
