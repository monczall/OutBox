package main.java.features;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.geometry.Point2D;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.CustomTextField;

public class ErrorHandler {

    public static void checkIfLetters(CustomTextField textField, String regExp, String toolTipMessage){

        FontAwesomeIconView icon = new FontAwesomeIconView();
        icon.setSize("16");
        icon.setGlyphName("WARNING");
        icon.getStyleClass().add("partIcon");

        Point2D p = textField.localToScene(0.0, 0.0);

        final Tooltip tooltip = new Tooltip(toolTipMessage);
        tooltip.setAutoHide(true);

        textField.setRight(icon);
        textField.getRight().setVisible(false);
        textField.setTooltip(tooltip);

        textField.setOnKeyReleased(event -> {
            if(textField.getText().matches(regExp)){    // [a-zA-Z]+
                textField.getRight().setVisible(false);
            }
            else
                textField.getRight().setVisible(true);
        });

    }
}
