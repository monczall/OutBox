package main.java.controllers.courier;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import main.java.SceneManager;
import main.java.controllers.animations.Animations;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class Courier implements Initializable {

    @FXML
    private AnchorPane mainWindow;

    @FXML
    private VBox paneRight;

    @FXML
    private FontAwesomeIconView hamburger;

    @FXML
    private Pane welcomeMessage;

    boolean hamburgerClicked = false;


    @Override
    public void initialize(URL url, ResourceBundle rb){
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
        try {
            SceneManager.loadScene("../../../resources/view/courier/courierHome.fxml", mainWindow);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openHome(ActionEvent actionEvent) throws IOException {
        SceneManager.loadScene("../../../resources/view/courier/courierHome.fxml", mainWindow);
    }

    public void openSecond(ActionEvent actionEvent) throws IOException {
        SceneManager.loadScene("../../../resources/view/courier/courierSecond.fxml", mainWindow);
    }


    public void openSettings(ActionEvent actionEvent) throws IOException {
        SceneManager.loadScene("../../../resources/view/courier/courierSettings.fxml", mainWindow);
    }

    public void logout(ActionEvent actionEvent) throws IOException {
        SceneManager.renderScene("login");
    }
}
