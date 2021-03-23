package main.java.controllers;


import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
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
    private AnchorPane mainWindow;

    boolean hamburgerClicked = false;

    public Client() {
    }

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

    public void homeButton(MouseEvent mouseEvent) {

    }

    public void packageButton(MouseEvent mouseEvent) {
    }

    public void btnHome(MouseEvent mouseEvent) {
    }
}
