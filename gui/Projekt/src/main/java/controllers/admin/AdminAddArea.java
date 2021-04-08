package main.java.controllers.admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import main.java.SceneManager;

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




    public void register(){
        if(!isEmpty()){
            if(isValid(registerStreetField.getText(), registerCityField.getText(), registerVoivodeshipField.getText(), registerAreaName.getText())){
                //POMYSLNE DODANIE OBSZARU
                System.out.println("Dodano obszar");
            }else{
                //SPRAWDZENIE BLEDOW


                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Niepoprawne dane");
                alert.setHeaderText(null);
                alert.setContentText("Podano niepoprawne dane! \nPopraw zaznaczone błędy w formularzu rejestracji.");

                alert.showAndWait();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Puste pola");
            alert.setHeaderText(null);
            alert.setContentText("Pozostawiono puste pola! Uzupełnij wymagane informacje.");

            alert.showAndWait();
        }
    }

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

    private boolean isValid(String street, String city, String voivodeship, String name){
        int error = 0;

        Pattern patternStreet = Pattern.compile("[A-Za-z]{0,2}\\.?\\s?[A-Za-z]{2,40}\\s?\\-?[A-Za-z]{0,40}?\\s?\\-?[A-Za-z]{0,40}?\\s[0-9]{1,4}\\s?[A-Za-z]?\\s?\\/?\\s?[0-9]{0,5}");
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





    public void handleRegister(MouseEvent mouseEvent) {
        register();
    }

    public void handleRegisterOnEnterPressed(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.ENTER)
        {
            register();
        }
    }




    //STREET
    private void errorOnStreet(){
        //StreetField
        registerStreetField.getStyleClass().clear();
        registerStreetField.getStyleClass().add("textFieldsError");
        //StreetCircle
        registerStreetCircle.getStyleClass().clear();
        registerStreetCircle.getStyleClass().add("circleError");
    }

    public void clearErrorsOnStreet(KeyEvent keyEvent) {
        //StreetField
        registerStreetField.getStyleClass().clear();
        registerStreetField.getStyleClass().add("textFields");
        //StreetCircle
        registerStreetCircle.getStyleClass().clear();
        registerStreetCircle.getStyleClass().add("circle");
    }
    //CITY
    private void errorOnCity(){
        //CityField
        registerCityField.getStyleClass().clear();
        registerCityField.getStyleClass().add("textFieldsError");
        //CityCircle
        registerCityCircle.getStyleClass().clear();
        registerCityCircle.getStyleClass().add("circleError");
    }

    public void clearErrorsOnCity(KeyEvent keyEvent) {
        //CityField
        registerCityField.getStyleClass().clear();
        registerCityField.getStyleClass().add("textFields");
        //CityCircle
        registerCityCircle.getStyleClass().clear();
        registerCityCircle.getStyleClass().add("circle");
    }
    //VOIVODESHIP
    private void errorOnVoivodeship(){
        //VoivodeshipField
        registerVoivodeshipField.getStyleClass().clear();
        registerVoivodeshipField.getStyleClass().add("textFieldsError");
        //VoivodeshipCircle
        registerVoivodeshipCircle.getStyleClass().clear();
        registerVoivodeshipCircle.getStyleClass().add("circleError");
    }

    public void clearErrorsOnVoivodeship(KeyEvent keyEvent) {
        //VoivodeshipField
        registerVoivodeshipField.getStyleClass().clear();
        registerVoivodeshipField.getStyleClass().add("textFields");
        //VoivodeshipCircle
        registerVoivodeshipCircle.getStyleClass().clear();
        registerVoivodeshipCircle.getStyleClass().add("circle");
    }



    //AREA
    private void errorOnArea(){

        registerAreaName.getStyleClass().clear();
        registerAreaName.getStyleClass().add("textFieldsError");

        registerAreaCircle.getStyleClass().clear();
        registerAreaCircle.getStyleClass().add("circleError");
    }

    public void clearErrorsOnArea(KeyEvent keyEvent) {

        registerAreaName.getStyleClass().clear();
        registerAreaName.getStyleClass().add("textFields");

        registerAreaCircle.getStyleClass().clear();
        registerAreaCircle.getStyleClass().add("circle");
    }
}
