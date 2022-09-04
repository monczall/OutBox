package main.java.features;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.scene.control.Tooltip;
import org.controlsfx.control.textfield.CustomPasswordField;
import org.controlsfx.control.textfield.CustomTextField;

import java.util.ArrayList;

public class ErrorHandler {

    /**
     * <p>
     * Method that check if input matches regular expression
     * It takes three arguments CustomTextField (which is TextField from ControlsFX lib), regular expression formula that will be used for checking the inputs
     * and toolTipMessage that will help user understand what did he typed wrong
     * </p>
     *
     * @param textField      it will be checked
     * @param regExp         the regular expresion that will be used to check TextField
     * @param toolTipMessage additional tip message
     */
    public static void checkInputs(CustomTextField textField, String regExp, String toolTipMessage) {

        FontAwesomeIconView icon = new FontAwesomeIconView();
        icon.setSize("16");
        icon.setGlyphName("WARNING");
        icon.getStyleClass().add("partIcon");

        Tooltip tooltip = new Tooltip(toolTipMessage);

        textField.setRight(icon);
        textField.getRight().setVisible(false);
        textField.setTooltip(tooltip);

        textField.setOnKeyReleased(event -> {
            textField.getRight().setVisible(!textField.getText().matches(regExp));
        });
    }

    /**
     * <p>
     * Method that check if input matches regular expression
     * and if both password are the same
     * It takes five arguments CustomTextField (which is TextField from ControlsFX lib) two times,
     * regular expression formula that will be used for checking the inputs
     * and two toolTipMessages that will help user understand what did he typed wrong
     *
     * </p>
     *
     * @param password                 first password field
     * @param passwordRepeat           second password field
     * @param regExp                   the regular expresion that will be used to check passwords
     * @param tipMessagePassword       additional tip message
     * @param tipMessageRepeatPassword additional tip message
     */
    public static void checkPasswords(CustomPasswordField password, CustomPasswordField passwordRepeat, String regExp, String tipMessagePassword, String tipMessageRepeatPassword) {

        FontAwesomeIconView icon = new FontAwesomeIconView();
        icon.setSize("16");
        icon.setGlyphName("WARNING");
        icon.getStyleClass().add("partIcon");

        FontAwesomeIconView iconRepeat = new FontAwesomeIconView();
        iconRepeat.setSize("16");
        iconRepeat.setGlyphName("WARNING");
        iconRepeat.getStyleClass().add("partIcon");

        Tooltip tooltip = new Tooltip(tipMessagePassword);
        Tooltip tooltipRepeat = new Tooltip(tipMessageRepeatPassword);

        password.setRight(icon);
        password.getRight().setVisible(false);
        password.setTooltip(tooltip);

        passwordRepeat.setRight(iconRepeat);
        passwordRepeat.getRight().setVisible(false);
        passwordRepeat.setTooltip(tooltipRepeat);

        password.setOnKeyReleased(event -> {
            passwordRepeat.getRight().setVisible(true);
            password.getRight().setVisible(!password.getText().matches(regExp));
        });

        passwordRepeat.setOnKeyReleased(event -> {
            passwordRepeat.getRight().setVisible(!password.getText().equals(passwordRepeat.getText()));
        });
    }


    /**
     * <p>
     * Method that check if inputs are empty
     * It takes ArrayList of CustomTextFields and in FOR loop checks all passed CustomTextFields if they are empty.
     * </p>
     *
     * @param list list of CustomTxtFields
     */
    public static void checkIfEmpty(ArrayList<CustomTextField> list) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getText().isEmpty()) {
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
