package main.java.controllers.animations;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;


public class Alerts {
    public static void createAlert(AnchorPane parent, int prefWidth, int prefHeight, int additionalX, int additionalY, Button button, String icon, String text){

        Pane pane = new Pane();
        pane.setPrefWidth(prefWidth);
        pane.setPrefHeight(prefHeight);
        pane.setLayoutX( (parent.getPrefWidth() / 2) - (prefWidth/2) + additionalX);
        pane.setLayoutY( (parent.getPrefHeight() / 2) - (prefHeight/2) + additionalY);
        pane.getStyleClass().add("alert");

        FontAwesomeIconView alertIcon = new FontAwesomeIconView();
        alertIcon.setSize("30");
        alertIcon.setGlyphName(icon);
        alertIcon.setLayoutX(21);
        alertIcon.setLayoutY(54);
        alertIcon.getStyleClass().add("partIcon");

        Label alertText = new Label(text);
        alertText.setLayoutX(62);
        alertText.setLayoutY(31);
        alertText.getStyleClass().add("alertText");

//        System.out.println(pane.getLayoutX());
//        System.out.println(pane.getLayoutY());

        pane.getChildren().add(alertText);
        pane.getChildren().add(alertIcon);
        parent.getChildren().add(pane);

        Animations.alertAnim(pane,400,0.3,4, button, parent);
    }
    public void createAlertWithOptions(){
    }
}
