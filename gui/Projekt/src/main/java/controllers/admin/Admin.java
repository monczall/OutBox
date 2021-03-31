package main.java.controllers.admin;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
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

public class Admin implements Initializable {
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

    public Admin() {
    }

    @Override
    public void initialize(URL url, ResourceBundle rb){

        try {
            SceneManager.loadScene("../../resources/view/admin/adminHome.fxml", mainWindow);
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

    public void homeButton(MouseEvent mouseEvent) {

    }

    public void packageButton(MouseEvent mouseEvent) {
    }

    public void btnHome(MouseEvent mouseEvent) {
    }


    @FXML
    private Pane hello;

    public void go_home(MouseEvent mouseEvent) throws IOException {
        SceneManager.loadScene("../../resources/view/admin/adminHome.fxml", mainWindow);

    }

    public void go_edit_employee(MouseEvent mouseEvent) throws IOException {
        SceneManager.loadScene("../../resources/view/admin/adminEditEmployee.fxml", mainWindow);
    }

    public void go_add_manager(MouseEvent mouseEvent) throws IOException {
        SceneManager.loadScene("../../resources/view/admin/adminAddManager.fxml", mainWindow);
    }

    public void go_add_area(MouseEvent mouseEvent) throws IOException {
        SceneManager.loadScene("../../resources/view/admin/adminAddArea.fxml", mainWindow);
    }

    public void go_pack_settings(MouseEvent mouseEvent) throws IOException {
        SceneManager.loadScene("../../resources/view/admin/adminPackSettings.fxml", mainWindow);
    }

    public void go_raport(MouseEvent mouseEvent) throws IOException {
        SceneManager.loadScene("../../resources/view/admin/adminRaport.fxml", mainWindow);
    }

    public void go_settings(MouseEvent mouseEvent) throws IOException {
        SceneManager.loadScene("../../resources/view/admin/adminSettings.fxml", mainWindow);
    }

    public void go_logout(MouseEvent mouseEvent) {
        SceneManager.renderScene("login");
    }
}
