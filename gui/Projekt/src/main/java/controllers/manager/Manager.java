package main.java.controllers.manager;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import main.java.SceneManager;
import main.java.controllers.animations.Animations;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Manager implements Initializable {

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

    public void openHome(MouseEvent actionEvent) throws IOException {
        System.out.println("Manager HOME");
        SceneManager.renderScene("manager");
    }

    public void openCouriers(MouseEvent mouseEvent) throws IOException {
        System.out.println("Manager Couriers");
        SceneManager.loadScene("../../resources/view/manager/managerCouriers.fxml", mainWindow);
    }

    public void openPackages(MouseEvent mouseEvent) throws IOException {
        System.out.println("Manager Packages");
        SceneManager.loadScene("../../resources/view/manager/managerPackages.fxml", mainWindow);
    }

    public void openSettings(MouseEvent mouseEvent) throws IOException {
        System.out.println("Manager Settings");
        SceneManager.loadScene("../../resources/view/manager/managerSettings.fxml", mainWindow);
    }

    public void openRaports(MouseEvent mouseEvent) throws IOException {
        System.out.println("Manager Raports");
        SceneManager.loadScene("../../resources/view/manager/managerRaports.fxml", mainWindow);
    }

    public void logout(MouseEvent mouseEvent) {
        System.out.println("Manager Logout");
        SceneManager.renderScene("login");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb){
        System.out.println("Start program");

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
}
