package main.java.features;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.scene.control.Tooltip;
import org.controlsfx.control.textfield.CustomTextField;
import java.util.ArrayList;

public class ErrorHandler {

    /* Method that check if input matches regular expression
       It takes three arguments CustomTextField (which is textfield from ControlsFX lib), regular expression formula that will be used for checking the inputs
       and toolTipMessage that will help user understand what did he typed wrong */
    public static void checkInputs(CustomTextField textField, String regExp, String toolTipMessage){

        FontAwesomeIconView icon = new FontAwesomeIconView();
        icon.setSize("16");
        icon.setGlyphName("WARNING");
        icon.getStyleClass().add("partIcon");

        Tooltip tooltip = new Tooltip(toolTipMessage);

        textField.setRight(icon);
        textField.getRight().setVisible(false);
        textField.setTooltip(tooltip);


        textField.setOnKeyReleased(event -> {
            if(textField.getText().matches(regExp)){
                textField.getRight().setVisible(false);
            }
            else
                textField.getRight().setVisible(true);
        });
    }

    /* Method that check if input is empty
       It takes ArrayList of CustomTextFields and in FOR loop check all passed CustomTextFields if they are empty.*/
    public static void checkIfEmpty(ArrayList<CustomTextField> list){
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).getText().isEmpty())
            {
                FontAwesomeIconView icon = new FontAwesomeIconView();
                icon.setSize("16");
                icon.setGlyphName("WARNING");
                icon.getStyleClass().add("partIcon");

                list.get(i).setRight(icon);
                list.get(i).getRight().setVisible(true);
            }
        }
    }
}
