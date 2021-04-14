package main.java.features;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;


public class Alerts {

    /* Method is creating alert that lasts for four seconds (....)*/
    public static void createAlert(AnchorPane parent, Button button, String iconName, String text){

        Pane pane = new Pane();
        pane.setPrefWidth(293);
        pane.setPrefHeight(86);
        pane.setLayoutX( (parent.getPrefWidth() / 2) - (pane.getPrefWidth()/2));
        pane.setLayoutY( (parent.getPrefHeight() / 2) - (pane.getPrefHeight()/2) - 600);
        pane.getStyleClass().add("alert");

        FontAwesomeIconView alertIcon = new FontAwesomeIconView();
        alertIcon.setSize("30");
        alertIcon.setGlyphName(iconName);
        alertIcon.setLayoutX(21);
        alertIcon.setLayoutY(54);
        alertIcon.getStyleClass().add("partIcon");

        Label alertText = new Label(text);
        alertText.setLayoutX(62);
        alertText.setLayoutY(31);
        alertText.getStyleClass().add("alertText");

        pane.getChildren().add(alertText);
        pane.getChildren().add(alertIcon);
        parent.getChildren().add(pane);

        Animations.alertAnim(pane,400,0.3,2, button, parent);
    }

    public static void createCustomAlert(AnchorPane parent, Button button, String iconName, String text, int width, int height, String type){

        Pane pane = new Pane();
        pane.setPrefWidth(width);
        pane.setPrefHeight(height);
        pane.setLayoutX( (parent.getPrefWidth() / 2) - (pane.getPrefWidth()/2));
        pane.setLayoutY( (parent.getPrefHeight() / 2) - (pane.getPrefHeight()/2) - 600);
        pane.getStyleClass().add(type);

        FontAwesomeIconView alertIcon = new FontAwesomeIconView();
        alertIcon.setSize("30");
        alertIcon.setGlyphName(iconName);
        alertIcon.setLayoutX(21);
        alertIcon.setLayoutY(54);
        alertIcon.getStyleClass().add(type+"Icon");

        Label alertText = new Label(text);
        alertText.setLayoutX(62);
        alertText.setLayoutY(31);
        alertText.getStyleClass().add(type+"Text");

        pane.getChildren().add(alertText);
        pane.getChildren().add(alertIcon);
        parent.getChildren().add(pane);

        Animations.alertAnim(pane,400,0.3,2, button, parent);
    }

    /* NOT YET IMPLEMENTED */
    public void createAlertWithOptions(AnchorPane parent, String labelText, Button first, Button second){

        Pane alertPane = new Pane();

        alertPane.getStyleClass().add("alert");
        alertPane.setPrefWidth(293);
        alertPane.setPrefHeight(123);
        alertPane.setLayoutX(430);
        alertPane.setLayoutY(146-500);

        Label alertLabel = new Label(labelText);
        alertLabel.getStyleClass().add("alertText");
        alertLabel.setLayoutY(20);
        alertLabel.setPrefWidth(293);

        first.setPrefWidth(99);
        first.prefHeight(42);
        first.setLayoutX(48);
        first.setLayoutY(70);

        first.setPrefWidth(99);
        first.prefHeight(41);
        first.setLayoutX(158);
        first.setLayoutY(70);

        alertPane.getChildren().addAll(alertLabel,first,second);
        parent.getChildren().add(alertPane);

        Animations.moveByY(alertPane,+500,0.3);
    }


}
