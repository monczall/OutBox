package main.java.controllers.admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import main.java.App;
import main.java.dao.PackageTypeDAO;
import main.java.entity.PackageType;
import main.java.features.Alerts;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdminPackSettings implements Initializable {

    @FXML
    private TextField smallSize;
    @FXML
    private TextField medSize;
    @FXML
    private TextField bigSize;
    @FXML
    private TextField smallWeight;
    @FXML
    private TextField medWeight;
    @FXML
    private TextField bigWeight;
    @FXML
    private TextField smallPrice;
    @FXML
    private TextField medPrice;
    @FXML
    private TextField bigPrice;
    @FXML
    private Button btnSaveChanges;
    @FXML
    private AnchorPane RightPaneAnchorPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        List<PackageType> listOfTypeInfo = PackageTypeDAO.getPackageTypes();
        smallSize.setText(listOfTypeInfo.get(0).getSize());
        medSize.setText(listOfTypeInfo.get(1).getSize());
        bigSize.setText(listOfTypeInfo.get(2).getSize());

        smallWeight.setText(listOfTypeInfo.get(0).getWeight());
        medWeight.setText(listOfTypeInfo.get(1).getWeight());
        bigWeight.setText(listOfTypeInfo.get(2).getWeight());

        smallPrice.setText(listOfTypeInfo.get(0).getPrice());
        medPrice.setText(listOfTypeInfo.get(1).getPrice());
        bigPrice.setText(listOfTypeInfo.get(2).getPrice());

    }

    /**
     * Method that save data from inputs about package data
     *
     * @param actionEvent action event
     */
    @FXML
    public void saveChanges(ActionEvent actionEvent) {

        if (!isEmpty()) {
            if (isValid(smallSize.getText(), smallWeight.getText(), smallPrice.getText())
                    && isValid(medSize.getText(), medWeight.getText(), medPrice.getText())
                    && isValid(bigSize.getText(), bigWeight.getText(), bigPrice.getText())) {

                PackageTypeDAO.updatePackageType(1, smallSize.getText(), smallWeight.getText(), smallPrice.getText());

                PackageTypeDAO.updatePackageType(2, medSize.getText(), medWeight.getText(), medPrice.getText());

                PackageTypeDAO.updatePackageType(3, bigSize.getText(), bigWeight.getText(), bigPrice.getText());

                Alerts.createCustomAlert(RightPaneAnchorPane, btnSaveChanges, "CHECK",
                        App.getLanguageProperties("adminSuccessPackEdit"), 320, 86, "alertSuccess");

            } else {
                // CHECK FOR ERRORS


                Alerts.createCustomAlert(RightPaneAnchorPane, btnSaveChanges, "WARNING",
                        App.getLanguageProperties("adminInvalidData"), 670, 86, "alertFailure");
            }
        } else {
            Alerts.createCustomAlert(RightPaneAnchorPane, btnSaveChanges, "WARNING",
                    App.getLanguageProperties("adminBlankFields"), 525, 86, "alertFailure");
        }
    }

    /**
     * Method that checks if all data is given and there are no empty inputs
     * True is returned if all data is entered, otherwise false
     *
     * @return
     */
    private boolean isEmpty() {
        int error = 0;
        if (smallSize.getText().isEmpty()) {
            errorOnSmallSize();
            error++;
        }
        if (smallWeight.getText().isEmpty()) {
            errorOnSmallWeight();
            error++;
        }
        if (smallPrice.getText().isEmpty()) {
            errorOnSmallPrice();
            error++;
        }
        if (medSize.getText().isEmpty()) {
            errorOnMedSize();
            error++;
        }
        if (medWeight.getText().isEmpty()) {
            errorOnMedWeight();
            error++;
        }
        if (medPrice.getText().isEmpty()) {
            errorOnMedPrice();
            error++;
        }
        if (bigSize.getText().isEmpty()) {
            errorOnBigSize();
            error++;
        }
        if (bigWeight.getText().isEmpty()) {
            errorOnBigWeight();
            error++;
        }
        if (bigPrice.getText().isEmpty()) {
            errorOnBigPrice();
            error++;
        }
        return error > 0;
    }

    /**
     * Method that change css style while an error occurred
     */
    private void errorOnSmallSize() {
        //SmallSize
        smallSize.getStyleClass().clear();
        smallSize.getStyleClass().add("textFieldsSettingsPackegeError");
    }

    /**
     * Method that change css style while fields with errors are change
     *
     * @param keyEvent key event
     */
    public void clearErrorsOnSmallSize(KeyEvent keyEvent) {
        //SmallSize
        smallSize.getStyleClass().clear();
        smallSize.getStyleClass().add("textFieldsSettingsPackege");
    }

    /**
     * Method that change css style while an error occurred
     */
    private void errorOnSmallWeight() {
        //SmallWeight
        smallWeight.getStyleClass().clear();
        smallWeight.getStyleClass().add("textFieldsSettingsPackegeError");
    }

    /**
     * Method that change css style while fields with errors are change
     *
     * @param keyEvent key event
     */
    public void clearErrorsOnSmallWeight(KeyEvent keyEvent) {
        //SmallWeight
        smallWeight.getStyleClass().clear();
        smallWeight.getStyleClass().add("textFieldsSettingsPackege");
    }

    /**
     * Method that change css style while an error occurred
     */
    private void errorOnSmallPrice() {
        //SmallPrice
        smallPrice.getStyleClass().clear();
        smallPrice.getStyleClass().add("textFieldsSettingsPackegeError");
    }

    /**
     * Method that change css style while fields with errors are change
     *
     * @param keyEvent key event
     */
    public void clearErrorsOnSmallPrice(KeyEvent keyEvent) {
        //SmallPrice
        smallPrice.getStyleClass().clear();
        smallPrice.getStyleClass().add("textFieldsSettingsPackege");
    }

    /**
     * Method that change css style while an error occurred
     */
    private void errorOnMedSize() {
        //MedSize
        medSize.getStyleClass().clear();
        medSize.getStyleClass().add("textFieldsSettingsPackegeError");
    }

    /**
     * Method that change css style while fields with errors are change
     *
     * @param keyEvent key event
     */
    public void clearErrorsOnMedSize(KeyEvent keyEvent) {
        //MedSize
        medSize.getStyleClass().clear();
        medSize.getStyleClass().add("textFieldsSettingsPackege");
    }

    /**
     * Method that change css style while an error occurred
     */
    private void errorOnMedWeight() {
        //MedWeight
        medWeight.getStyleClass().clear();
        medWeight.getStyleClass().add("textFieldsSettingsPackegeError");
    }

    /**
     * Method that change css style while fields with errors are change
     *
     * @param keyEvent key event
     */
    public void clearErrorsOnMedWeight(KeyEvent keyEvent) {
        //MedWeight
        medWeight.getStyleClass().clear();
        medWeight.getStyleClass().add("textFieldsSettingsPackege");
    }

    /**
     * Method that change css style while an error occurred
     */
    private void errorOnMedPrice() {
        //MedPrice
        medPrice.getStyleClass().clear();
        medPrice.getStyleClass().add("textFieldsSettingsPackegeError");
    }

    /**
     * Method that change css style while fields with errors are change
     *
     * @param keyEvent key event
     */
    public void clearErrorsOnMedPrice(KeyEvent keyEvent) {
        //MedPrice
        medPrice.getStyleClass().clear();
        medPrice.getStyleClass().add("textFieldsSettingsPackege");
    }

    /**
     * Method that change css style while an error occurred
     */
    private void errorOnBigSize() {
        //BigSize
        bigSize.getStyleClass().clear();
        bigSize.getStyleClass().add("textFieldsSettingsPackegeError");
    }

    /**
     * Method that change css style while fields with errors are change
     *
     * @param keyEvent key event
     */
    public void clearErrorsOnBigSize(KeyEvent keyEvent) {
        //BigSize
        bigSize.getStyleClass().clear();
        bigSize.getStyleClass().add("textFieldsSettingsPackege");
    }

    /**
     * Method that change css style while an error occurred
     */
    private void errorOnBigWeight() {
        //BigWeight
        bigWeight.getStyleClass().clear();
        bigWeight.getStyleClass().add("textFieldsSettingsPackegeError");
    }

    /**
     * Method that change css style while fields with errors are change
     *
     * @param keyEvent key event
     */
    public void clearErrorsOnBigWeight(KeyEvent keyEvent) {
        //BigWeight
        bigWeight.getStyleClass().clear();
        bigWeight.getStyleClass().add("textFieldsSettingsPackege");
    }

    /**
     * Method that change css style while an error occurred
     */
    private void errorOnBigPrice() {
        //BigPrice
        bigPrice.getStyleClass().clear();
        bigPrice.getStyleClass().add("textFieldsSettingsPackegeError");
    }

    /**
     * Method that change css style while fields with errors are change
     *
     * @param keyEvent key event
     */
    public void clearErrorsOnBigPrice(KeyEvent keyEvent) {
        //BigPrice
        bigPrice.getStyleClass().clear();
        bigPrice.getStyleClass().add("textFieldsSettingsPackege");
    }


    /**
     * Method that checks if all data is correctly entered
     * True is returned if all data are correct compared to patterns, otherwise false
     *
     * @param size   size
     * @param weight weight
     * @param price  price
     * @return
     */
    private boolean isValid(String size, String weight, String price) {
        int error = 0;
        Pattern patternSize = Pattern.compile("[0-9]{1,3}[x][0-9]{1,3}[x][0-9]{1,3}");
        Pattern patternWeight = Pattern.compile("[0-9]{1,3}");
        Pattern patternPrice = Pattern.compile("[0-9]{1,3}\\.[0-9]{1,2}");

        Matcher matchSize = patternSize.matcher(size);
        Matcher matchWeight = patternWeight.matcher(weight);
        Matcher matchPrice = patternPrice.matcher(price);

        if (!matchSize.matches()) {

            error++;
        }

        if (!matchWeight.matches()) {

            error++;
        }

        if (!matchPrice.matches()) {

            error++;
        }

        return error <= 0;
    }
}
