package main.java.controllers.admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import main.java.App;
import main.java.SceneManager;
import main.java.dao.AreasDAO;
import main.java.dao.PackageTypeDAO;
import main.java.features.Alerts;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdminAddArea {



    @FXML
    private Circle registerAreaCircle;

    @FXML
    private Circle registerStreetCircle;

    @FXML
    private TextField registerStreetField;

    @FXML
    private Circle registerCityCircle;

    @FXML
    private TextField registerCityField;

    @FXML
    private Circle registerVoivodeshipCircle;

    @FXML
    private TextField registerVoivodeshipField;

    @FXML
    private TextField registerAreaName;

    @FXML
    private Button addAreaButton;

    @FXML
    private AnchorPane RightPaneAnchorPane;

    /**
     * Method that adds an area to the base
     * Retrieves entered data from inputs and checks their correctness
     */

    public void register(){
        if(!isEmpty()){

            if(isValid(registerStreetField.getText(), registerCityField.getText(), registerVoivodeshipField.getText(), registerAreaName.getText())){
                // SUCCESSFUL ADDING AREA
                AreasDAO.insertAreas(registerAreaName.getText(), registerVoivodeshipField.getText(), registerCityField.getText(), registerStreetField.getText());
                System.out.println("An area has been added");
                Alerts.createCustomAlert(RightPaneAnchorPane, addAreaButton,"CHECK",
                        App.getLanguageProperties("adminSuccessAreaAdd"), 320, 86, "alertSuccess");
                clearData();
            }else{
                // CHECK FOR ERRORS


                Alerts.createCustomAlert(RightPaneAnchorPane, addAreaButton,"WARNING",
                        App.getLanguageProperties("adminInvalidData"), 670, 86, "alertFailure");
            }
        }else{
            Alerts.createCustomAlert(RightPaneAnchorPane, addAreaButton,"WARNING",
                    App.getLanguageProperties("adminBlankFields"), 525, 86, "alertFailure");
        }
    }

    /**
     * Method that checks if all data is given and there are no empty inputs
     * True is returned if all data is entered, otherwise false
     * @return
     */

    private boolean isEmpty(){
        int error = 0;

        if(registerStreetField.getText().isEmpty()){
            errorOnStreet();
            error++;
        }
        if(registerCityField.getText().isEmpty()){
            errorOnCity();
            error++;
        }
        if(registerVoivodeshipField.getText().isEmpty()){
            errorOnVoivodeship();
            error++;
        }

        if(registerAreaName.getText().isEmpty()){
            errorOnArea();
            error++;
        }

        if(error > 0){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Method that checks if all data is correctly entered
     * True is returned if all data are correct compared to patterns, otherwise false
     * @param street street name
     * @param city city name
     * @param voivodeship voivodeship name
     * @param name name of area
     * @return
     */

    private boolean isValid(String street, String city, String voivodeship, String name){
        int error = 0;

        Pattern patternStreet = Pattern.compile("[A-Za-z]{0,2}\\.?\\s?[A-Za-z]{2,40}\\s?\\-?[A-Za-z]" +
                "{0,40}?\\s?\\-?[A-Za-z]{0,40}?\\s[0-9]{1,4}\\s?[A-Za-z]?\\s?\\/?\\s?[0-9]{0,5}");
        Pattern patternCity = Pattern.compile("[A-Za-z]{2,40}\\s?\\-?\\s?[A-Za-z]{0,40}\\s?\\-?\\s?[A-Za-z]{0,40}");
        Pattern patternVoivodeship = Pattern.compile("[A-Za-z]{7,40}\\s?\\-?\\s?[A-Za-z]{0,40}");
        Pattern patternName = Pattern.compile("[A-Za-z]{0,40}\\s?\\-?\\s?[A-Za-z]{0,40}\\s?[0-9]{0,3}");


        Matcher matchStreet = patternStreet.matcher(street);

        Matcher matchCity = patternCity.matcher(city);

        Matcher matchVoivodeship = patternVoivodeship.matcher(voivodeship);

        Matcher matchName = patternName.matcher(name);

        if(!matchStreet.matches()){
            errorOnStreet();
            error++;
        }
        if(!matchCity.matches()){
            errorOnCity();
            error++;
        }
        if(!matchVoivodeship.matches()){
            errorOnVoivodeship();
            error++;
        }

        if(!matchName.matches()){
            errorOnArea();
            error++;
        }

        if(error > 0){
            return false;
        }else{
            return true;
        }
    }



    /**
     * Method that clear inputs after area add
     */
    private void clearData() {
        registerStreetField.setText(" ");
        registerCityField.setText(" ");
        registerVoivodeshipField.setText(" ");
        registerAreaName.setText(" ");


    }



    /**
     * Method that doing "register" function after button click
     * @param mouseEvent mouse event
     */
    public void handleRegister(MouseEvent mouseEvent) {
        register();
    }



    /**
     * Method that doing "register" function after enter pressed
     * @param keyEvent enter pressed
     */
    public void handleRegisterOnEnterPressed(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.ENTER)
        {
            register();
        }
    }






    /**
     * Method that change css style while an error occurred
     */
    private void errorOnStreet(){
        //StreetField
        registerStreetField.getStyleClass().clear();
        registerStreetField.getStyleClass().add("textFieldsError");
        //StreetCircle
        registerStreetCircle.getStyleClass().clear();
        registerStreetCircle.getStyleClass().add("circleError");
    }


    /**
     * Method that change css style while fields with errors are change
     * @param keyEvent key event
     */
    public void clearErrorsOnStreet(KeyEvent keyEvent) {
        //StreetField
        registerStreetField.getStyleClass().clear();
        registerStreetField.getStyleClass().add("textFields");
        //StreetCircle
        registerStreetCircle.getStyleClass().clear();
        registerStreetCircle.getStyleClass().add("circle");
    }
    /**
     * Method that change css style while an error occurred
     */
    private void errorOnCity(){
        //CityField
        registerCityField.getStyleClass().clear();
        registerCityField.getStyleClass().add("textFieldsError");
        //CityCircle
        registerCityCircle.getStyleClass().clear();
        registerCityCircle.getStyleClass().add("circleError");
    }
    /**
     * Method that change css style while fields with errors are change
     * @param keyEvent key event
     */
    public void clearErrorsOnCity(KeyEvent keyEvent) {
        //CityField
        registerCityField.getStyleClass().clear();
        registerCityField.getStyleClass().add("textFields");
        //CityCircle
        registerCityCircle.getStyleClass().clear();
        registerCityCircle.getStyleClass().add("circle");
    }
    /**
     * Method that change css style while an error occurred
     */
    private void errorOnVoivodeship(){
        //VoivodeshipField
        registerVoivodeshipField.getStyleClass().clear();
        registerVoivodeshipField.getStyleClass().add("textFieldsError");
        //VoivodeshipCircle
        registerVoivodeshipCircle.getStyleClass().clear();
        registerVoivodeshipCircle.getStyleClass().add("circleError");
    }
    /**
     * Method that change css style while fields with errors are change
     * @param keyEvent key event
     */
    public void clearErrorsOnVoivodeship(KeyEvent keyEvent) {
        //VoivodeshipField
        registerVoivodeshipField.getStyleClass().clear();
        registerVoivodeshipField.getStyleClass().add("textFields");
        //VoivodeshipCircle
        registerVoivodeshipCircle.getStyleClass().clear();
        registerVoivodeshipCircle.getStyleClass().add("circle");
    }



    /**
     * Method that change css style while an error occurred
     */
    private void errorOnArea(){
        //Area Name
        registerAreaName.getStyleClass().clear();
        registerAreaName.getStyleClass().add("textFieldsError");

        registerAreaCircle.getStyleClass().clear();
        registerAreaCircle.getStyleClass().add("circleError");
    }
    /**
     * Method that change css style while fields with errors are change
     * @param keyEvent key event
     */
    public void clearErrorsOnArea(KeyEvent keyEvent) {
        //Area Name
        registerAreaName.getStyleClass().clear();
        registerAreaName.getStyleClass().add("textFields");

        registerAreaCircle.getStyleClass().clear();
        registerAreaCircle.getStyleClass().add("circle");
    }
}
