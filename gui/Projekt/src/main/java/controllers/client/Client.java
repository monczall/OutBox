package main.java.controllers.client;


import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import main.java.SceneManager;
import main.java.controllers.animations.Animations;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
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
        SceneManager.loadScene("../../../resources/view/client/clientHome.fxml", mainWindow);
    } catch (IOException e) {
        e.printStackTrace();
    }

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
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout Alert");
        alert.setHeaderText("Proces wylogowania");
        alert.setContentText("Czy napewno chcesz się wylogować?");
        alert.initStyle(StageStyle.UNDECORATED);

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("../../../resources/css/client.css").toExternalForm());
        dialogPane.getStyleClass().add("alert");

        Optional<ButtonType> result = alert.showAndWait();

        if(result.get() == ButtonType.OK)
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
