package main.java.controllers.admin;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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

public class Admin implements Initializable {
    @FXML
    private VBox paneRight;
    @FXML
    private FontAwesomeIconView hamburger;

    @FXML
    private AnchorPane mainWindow;

    @FXML
    private AnchorPane window;

    @FXML
    private Pane alertPane;

    boolean hamburgerClicked = false;

    public Admin() {
    }

    @Override
    public void initialize(URL url, ResourceBundle rb){

        try {
            SceneManager.loadScene("../../../resources/view/admin/adminHome.fxml", mainWindow);
        } catch (IOException e) {
            e.printStackTrace();
        }


        paneRight.setTranslateX(-200);
        alertPane.setTranslateY(-500);

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
                Animations.moveByX(hello,+170,0.5);
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
                Animations.moveByX(hello,-170,0.5);
                Animations.moveByX(mainWindow,-70,0.5);

                fadeTransition.setOnFinished(event1 -> {
                    paneRight.setVisible(false);
                    hamburger.setDisable(false);
                });
            }
        });
    }




    @FXML
    private Pane hello;

    public void go_home(MouseEvent mouseEvent) throws IOException {
        SceneManager.loadScene("../../../resources/view/admin/adminHome.fxml", mainWindow);

    }

    public void go_edit_employee(MouseEvent mouseEvent) throws IOException {
        SceneManager.loadScene("../../../resources/view/admin/adminEditEmployee.fxml", mainWindow);
    }

    public void go_add_manager(MouseEvent mouseEvent) throws IOException {
        SceneManager.loadScene("../../../resources/view/admin/adminAddManager.fxml", mainWindow);
    }

    public void go_add_area(MouseEvent mouseEvent) throws IOException {
        SceneManager.loadScene("../../../resources/view/admin/adminAddArea.fxml", mainWindow);
    }

    public void go_pack_settings(MouseEvent mouseEvent) throws IOException {
        SceneManager.loadScene("../../../resources/view/admin/adminPackSettings.fxml", mainWindow);
    }

    public void go_raport(MouseEvent mouseEvent) throws IOException {
        SceneManager.loadScene("../../../resources/view/admin/adminRaport.fxml", mainWindow);
    }

    public void go_settings(MouseEvent mouseEvent) throws IOException {
        SceneManager.loadScene("../../../resources/view/admin/adminSettings.fxml", mainWindow);
    }

    @FXML
    void go_logout(MouseEvent mouseEvent) {
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
}
