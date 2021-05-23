package main.java.controllers.client;


import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;
import main.java.SceneManager;
import main.java.controllers.auth.Login;
import main.java.dao.UserInfosDAO;
import main.java.entity.UserInfos;
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
    private Text name;

    boolean hamburgerClicked = false;
    
    @Override
    public void initialize(URL url, ResourceBundle rb){

        //Loading name and surname from database
        UserInfos ui = UserInfosDAO.getUserInfoByID(Login.getUserInfoID()).get(0);
        name.setText(ui.getName() + " " + ui.getSurname());

        //Changing position of nodes for animation purposes
        alertPane.setTranslateY(-500);
        paneRight.setTranslateX(-200);

        // If hamburger button is clicked then menu slides in and transition last for 0.5s
        hamburger.setOnMouseClicked(event -> {
            if(hamburgerClicked == false) {

                hamburger.setDisable(true);
                hamburgerClicked = true;
                paneRight.setVisible(true);

                FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5), paneRight);
                fadeTransition.setFromValue(0);
                fadeTransition.setToValue(1);
                fadeTransition.play();

                Animations.moveByX(paneRight,+200,0.5);
                Animations.moveByX(welcomeMessage,+160,0.5);
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
                Animations.moveByX(welcomeMessage,-160,0.5);
                Animations.moveByX(mainWindow,-70,0.5);

                fadeTransition.setOnFinished(event1 -> {
                    paneRight.setVisible(false);
                    hamburger.setDisable(false);
                });
            }
        });

    }

    //Method that handle 'logout' button
    @FXML
    void logout(ActionEvent event) {
        Animations.moveByY(alertPane,+500,0.3);
        GaussianBlur gaussianBlur = new GaussianBlur();
        gaussianBlur.setRadius(8);
        window.setDisable(true);
        window.setEffect(gaussianBlur);
    }

    //Method that handle 'no' button inside logout popup
    @FXML
    void logoutNo(ActionEvent event) {
        Animations.moveByY(alertPane,-500,0.3);
        window.setEffect(null);
        window.setDisable(false);
    }

    //Method that handle 'yes' button inside logout popup
    @FXML
    void logoutYes(ActionEvent event) {
        SceneManager.renderScene("login");
    }

    //Method that handle 'history' button
    @FXML
    void viewHistory(ActionEvent event) throws IOException {
        SceneManager.loadScene("../../../resources/view/client/clientHistoryPackage.fxml", mainWindow);
    }

    //Method that handle 'home' button
    @FXML
    void viewHome(ActionEvent event) throws IOException {
        SceneManager.renderScene("client");
    }

    //Method that handle 'registerPackage' button
    @FXML
    void viewRegisterPackage(ActionEvent event) throws IOException {
        SceneManager.loadScene("../../../resources/view/client/clientRegisterPackage.fxml", mainWindow);
    }

    //Method that handle 'settings' button
    @FXML
    void viewSettings(ActionEvent event) throws IOException {
        SceneManager.loadScene("../../../resources/view/client/clientSettings.fxml", mainWindow);
    }

    //Method that handle 'trackPackage' button
    @FXML
    void viewTrackPackage(ActionEvent event) throws IOException {
        SceneManager.loadScene("../../../resources/view/client/clientTrackPackage.fxml", mainWindow);
    }

}
