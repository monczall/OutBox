package main.java.controllers.client;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import main.java.controllers.animations.Animations;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientRegisterPackage implements Initializable {

    @FXML
    private AnchorPane packageSizePane;

    @FXML
    private ToggleButton smallPackage;

    @FXML
    private ToggleButton mediumPackage;

    @FXML
    private ToggleButton bigPackage;

    @FXML
    private AnchorPane recipientDetailsPane;

    @FXML
    private AnchorPane deliveryTimePane;

    @FXML
    private AnchorPane registerSummaryPane;

    @FXML
    private Circle navCircle;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ToggleGroup packageGroup = new ToggleGroup();       // Added three buttons to the group
        smallPackage.setToggleGroup(packageGroup);
        mediumPackage.setToggleGroup(packageGroup);
        bigPackage.setToggleGroup(packageGroup);

        recipientDetailsPane.setTranslateX(+800);
    }

    @FXML
    void toRecipientPane(ActionEvent event) {
        Animations.changePane(packageSizePane,recipientDetailsPane,-800,0.7);

        Animations.moveByX(navCircle, +114,0.7);
    }

    @FXML
    void toSizePane(ActionEvent event) {
        Animations.changePane(recipientDetailsPane,packageSizePane,+800,0.7);
        Animations.moveByX(navCircle,-114,0.7);

    }
}
