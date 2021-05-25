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
import javafx.stage.FileChooser;
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

    /**
     * Method that change scene to "admin"
     * @param mouseEvent mouse event
     * @throws IOException if doesn't find a scene then throw IOException
     */
    public void go_home(MouseEvent mouseEvent) throws IOException {
        SceneManager.renderScene("admin");
    }

    /**
     * Method that change scene to "editEmployee"
     * @param mouseEvent mouse event
     * @throws IOException if doesn't find a scene then throw IOException
     */
    public void go_edit_employee(MouseEvent mouseEvent) throws IOException {
        SceneManager.loadScene("../../../resources/view/admin/adminEditEmployee.fxml", mainWindow);
    }

    /**
     * Method that change scene to "addManager"
     * @param mouseEvent mouse event
     * @throws IOException if doesn't find a scene then throw IOException
     */
    public void go_add_manager(MouseEvent mouseEvent) throws IOException {
        SceneManager.loadScene("../../../resources/view/admin/adminAddManager.fxml", mainWindow);
    }

    /**
     * Method that change scene to "addArea"
     * @param mouseEvent mouse event
     * @throws IOException if doesn't find a scene then throw IOException
     */
    public void go_add_area(MouseEvent mouseEvent) throws IOException {
        SceneManager.loadScene("../../../resources/view/admin/adminAddArea.fxml", mainWindow);
    }

    /**
     * Method that change scene to "packSettings"
     * @param mouseEvent mouse event
     * @throws IOException if doesn't find a scene then throw IOException
     */
    public void go_pack_settings(MouseEvent mouseEvent) throws IOException {
        SceneManager.loadScene("../../../resources/view/admin/adminPackSettings.fxml", mainWindow);
    }

    /**
     * Method that change scene to "raports"
     * @param mouseEvent mouse event
     * @throws IOException if doesn't find a scene then throw IOException
     */
    public void go_raport(MouseEvent mouseEvent) throws IOException {
        SceneManager.loadScene("../../../resources/view/admin/adminRaport.fxml", mainWindow);
    }

    /**
     * Method that change scene to "settings"
     * @param mouseEvent mouse event
     * @throws IOException if doesn't find a scene then throw IOException
     */
    public void go_settings(MouseEvent mouseEvent) throws IOException {
        SceneManager.loadScene("../../../resources/view/admin/adminSettings.fxml", mainWindow);
    }

    /**
     * Method that show alert with logout option
     * @param mouseEvent mosue event
     */
    @FXML
    void go_logout(MouseEvent mouseEvent) {
        Animations.moveByY(alertPane,+500,0.3);
        GaussianBlur gaussianBlur = new GaussianBlur();
        gaussianBlur.setRadius(8);
        window.setDisable(true);
        window.setEffect(gaussianBlur);
    }

    /**
     * Method that close alert with logout if "NO" is chosen
     * @param event event
     */
    @FXML
    void logoutNo(ActionEvent event) {
        Animations.moveByY(alertPane,-500,0.3);
        window.setEffect(null);
        window.setDisable(false);
    }

    /**
     * Method that logout admin
     * @param event event
     */
    @FXML
    void logoutYes(ActionEvent event) {
        SceneManager.renderScene("login");
    }

    /**
     * Method that close alert with logout if "NO" is chosen
     * @param actionEvent event
     */
    public void logout(ActionEvent actionEvent) {
        Animations.moveByY(alertPane,+500,0.3);
        GaussianBlur gaussianBlur = new GaussianBlur();
        gaussianBlur.setRadius(8);
        window.setDisable(true);
        window.setEffect(gaussianBlur);
    }

    /**
     * Method that change scene to "packSettings"
     * @param actionEvent action event
     * @throws IOException if doesn't find a scene then throw IOException
     */
    public void viewPackSettings(ActionEvent actionEvent) throws IOException {
        SceneManager.loadScene("../../../resources/view/admin/adminPackSettings.fxml", mainWindow);
    }
    /**
     * Method that change scene to "raports"
     * @param actionEvent action event
     * @throws IOException if doesn't find a scene then throw IOException
     */
    public void viewRaport(ActionEvent actionEvent) throws IOException {
        SceneManager.loadScene("../../../resources/view/admin/adminRaport.fxml", mainWindow);
    }
    /**
     * Method that change scene to "settings"
     * @param actionEvent action event
     * @throws IOException if doesn't find a scene then throw IOException
     */
    public void viewSettings(ActionEvent actionEvent) throws IOException {
        SceneManager.loadScene("../../../resources/view/admin/adminSettings.fxml", mainWindow);
    }
    /**
     * Method that change scene to "addArea"
     * @param actionEvent action event
     * @throws IOException if doesn't find a scene then throw IOException
     */
    public void viewAddArea(ActionEvent actionEvent) throws IOException {
        SceneManager.loadScene("../../../resources/view/admin/adminAddArea.fxml", mainWindow);
    }
    /**
     * Method that change scene to "editEmployee"
     * @param actionEvent action event
     * @throws IOException if doesn't find a scene then throw IOException
     */
    public void viewEditEmployee(ActionEvent actionEvent) throws IOException {
        SceneManager.loadScene("../../../resources/view/admin/adminEditEmployee.fxml", mainWindow);
    }
    /**
     * Method that change scene to "addManager"
     * @param actionEvent action event
     * @throws IOException if doesn't find a scene then throw IOException
     */
    public void viewAddManager(ActionEvent actionEvent) throws IOException {
        SceneManager.loadScene("../../../resources/view/admin/adminAddManager.fxml", mainWindow);
    }
}
