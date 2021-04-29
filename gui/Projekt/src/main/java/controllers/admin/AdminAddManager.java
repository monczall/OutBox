package main.java.controllers.admin;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import main.java.SceneManager;
import main.java.features.Alerts;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdminAddManager implements Initializable {

    @FXML
    private Button registerExitButtonButton;

    @FXML
    private Button registerReturnButtonButton;

    @FXML
    private Button registerRegisterButtonButton;

    @FXML
    private Circle registerFirstNameCircle;

    @FXML
    private Circle registerAreaCircle;

    @FXML
    private TextField registerFirstNameField;

    @FXML
    private Circle registerLastNameCircle;

    @FXML
    private TextField registerLastNameField;

    @FXML
    private Circle registerPhoneNumberCircle;

    @FXML
    private TextField registerPhoneNumberField;

    @FXML
    private Circle registerEmailAddressCircle;

    @FXML
    private TextField registerEmailAddressField;

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
    private Circle registerPasswordCircle;

    @FXML
    private PasswordField registerPasswordField;

    @FXML
    private Circle registerRepeatPasswordCircle;

    @FXML
    private PasswordField registerRepeatPasswordField;

    @FXML
    private ChoiceBox registerAreaChoiceBox;

    @FXML
    private AnchorPane RightPaneAnchorPane;


    public void register(){
        if(!isEmpty()){
            if(isValid(registerFirstNameField.getText(), registerLastNameField.getText(), registerStreetField.getText(), registerCityField.getText(), registerVoivodeshipField.getText())){
                if(isPhoneNumber(registerPhoneNumberField.getText())){
                    if(isEmail(registerEmailAddressField.getText())){
                        //POMYSLNIE DODANE
                        System.out.println("Dodano kierownika");
                    }else{
                        errorOnEmailAddress();

                        Alerts.createCustomAlert(RightPaneAnchorPane, registerRegisterButtonButton,"WARNING","Niepoprawny format danych! Podany mail nie jest prawidłowy.", 560, 86, "alertFailure");
                    }
                }else{
                    errorOnPhoneNumber();

                    Alerts.createCustomAlert(RightPaneAnchorPane, registerRegisterButtonButton,"WARNING","Niepoprawny format danych! Podany numer nie jest prawidłowy.", 565, 86, "alertFailure");
                }
            }else{
                //SPRAWDZENIE BLEDOW




                Alerts.createCustomAlert(RightPaneAnchorPane, registerRegisterButtonButton,"WARNING","Podano niepoprawne dane! Popraw zaznaczone błędy w formularzu rejestracji.", 670, 86, "alertFailure");
            }
        }else{


            Alerts.createCustomAlert(RightPaneAnchorPane, registerRegisterButtonButton,"WARNING","Pozostawiono puste pola! Uzupełnij wymagane informacje.", 525, 86, "alertFailure");
        }
    }

    private boolean isEmpty(){
        int error = 0;
        if(registerFirstNameField.getText().isEmpty()){
            errorOnFirstName();
            error++;
        }
        if(registerLastNameField.getText().isEmpty()){
            errorOnLastName();
            error++;
        }
        if(registerPhoneNumberField.getText().isEmpty()){
            errorOnPhoneNumber();
            error++;
        }
        if(registerEmailAddressField.getText().isEmpty()){
            errorOnEmailAddress();
            error++;
        }
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
        if(registerPasswordField.getText().isEmpty()){
            errorOnPassword();
            error++;
        }
        if(registerRepeatPasswordField.getText().isEmpty()){
            errorOnRepeatPassword();
            error++;
        }
        if(registerAreaChoiceBox.getSelectionModel().isEmpty()){
            errorOnArea();
            error++;
        }
        if(error > 0){
            return true;
        }else{
            return false;
        }
    }

    private boolean isValid(String firstName, String lastName, String street, String city, String voivodeship){
        int error = 0;
        Pattern pattern = Pattern.compile("[A-Za-z]{2,60}");
        Pattern patternFirstName = Pattern.compile("[A-Za-z]{2,30}\\s?[A-Za-z]{2,30}");
        Pattern patternLastName = Pattern.compile("[A-Za-z]{2,30}\\s?\\-\\s?[A-Za-z]{2,30}");
        Pattern patternStreet = Pattern.compile("[A-Za-z]{0,2}\\.?\\s?[A-Za-z]{2,40}\\s?\\-?[A-Za-z]{0,40}?\\s?\\-?[A-Za-z]{0,40}?\\s[0-9]{1,4}\\s?[A-Za-z]?\\s?\\/?\\s?[0-9]{0,5}");
        Pattern patternCity = Pattern.compile("[A-Za-z]{2,40}\\s?\\-?\\s?[A-Za-z]{0,40}\\s?\\-?\\s?[A-Za-z]{0,40}");
        Pattern patternVoivodeship = Pattern.compile("[A-Za-z]{7,40}\\s?\\-?\\s?[A-Za-z]{0,40}");

        Matcher matchSingleFirstName = pattern.matcher(firstName);
        Matcher matchDoubleFirstName = patternFirstName.matcher(firstName);

        Matcher matchSingleLastName = pattern.matcher(lastName);
        Matcher matchDoubleLastName = patternLastName.matcher(lastName);

        Matcher matchStreet = patternStreet.matcher(street);

        Matcher matchCity = patternCity.matcher(city);

        Matcher matchVoivodeship = patternVoivodeship.matcher(voivodeship);

        if(!matchSingleFirstName.matches() && !matchDoubleFirstName.matches()){
            errorOnFirstName();
            error++;
        }
        if(!matchSingleLastName.matches() && !matchDoubleLastName.matches()){
            errorOnLastName();
            error++;
        }
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

        if(error > 0){
            return false;
        }else{
            return true;
        }
    }

    private boolean isPhoneNumber(String phoneNumber){
        Pattern patternPhoneNumber = Pattern.compile("\\+?[0-9]{0,3}\\s?[0-9]{2,3}\\s?[0-9]{2,3}\\s?[0-9]{2,3}\\s?");

        Matcher matchPhoneNumber = patternPhoneNumber.matcher(phoneNumber);

        if(matchPhoneNumber.matches()){
            return true;
        }else{
            return false;
        }
    }

    private boolean isEmail(String email){
        Pattern patternEmail = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        Matcher matchEmail = patternEmail.matcher(email);

        if(matchEmail.matches()){
            return true;
        }else{
            return false;
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

    public void handleReturn(ActionEvent actionEvent) {
        SceneManager.renderScene("login");
    }

    public void handleExit(ActionEvent actionEvent) {
        Stage stage = (Stage) registerExitButtonButton.getScene().getWindow();
        stage.close();
    }
    //FIRST NAME
    private void errorOnFirstName(){
        //FirstNameField
        registerFirstNameField.getStyleClass().clear();
        registerFirstNameField.getStyleClass().add("textFieldsError");
        //FirstNameCircle
        registerFirstNameCircle.getStyleClass().clear();
        registerFirstNameCircle.getStyleClass().add("circleError");
    }

    public void clearErrorsOnFirstName(KeyEvent keyEvent) {
        //FirstNameField
        registerFirstNameField.getStyleClass().clear();
        registerFirstNameField.getStyleClass().add("textFields");
        //FirstNameCircle
        registerFirstNameCircle.getStyleClass().clear();
        registerFirstNameCircle.getStyleClass().add("circle");
    }
    //LAST NAME
    private void errorOnLastName(){
        //LastNameField
        registerLastNameField.getStyleClass().clear();
        registerLastNameField.getStyleClass().add("textFieldsError");
        //LastNameCircle
        registerLastNameCircle.getStyleClass().clear();
        registerLastNameCircle.getStyleClass().add("circleError");
    }

    public void clearErrorsOnLastName(KeyEvent keyEvent) {
        //LastNameField
        registerLastNameField.getStyleClass().clear();
        registerLastNameField.getStyleClass().add("textFields");
        //LastNameCircle
        registerLastNameCircle.getStyleClass().clear();
        registerLastNameCircle.getStyleClass().add("circle");
    }
    //PHONE NUMBER
    private void errorOnPhoneNumber(){
        //PhoneNumberField
        registerPhoneNumberField.getStyleClass().clear();
        registerPhoneNumberField.getStyleClass().add("textFieldsError");
        //PhoneNumberCircle
        registerPhoneNumberCircle.getStyleClass().clear();
        registerPhoneNumberCircle.getStyleClass().add("circleError");
    }

    public void clearErrorsOnPhoneNumber(KeyEvent keyEvent) {
        //PhoneNumberField
        registerPhoneNumberField.getStyleClass().clear();
        registerPhoneNumberField.getStyleClass().add("textFields");
        //PhoneNumberCircle
        registerPhoneNumberCircle.getStyleClass().clear();
        registerPhoneNumberCircle.getStyleClass().add("circle");
    }
    //EMAIL ADDRESS
    private void errorOnEmailAddress(){
        //EmailAddressField
        registerEmailAddressField.getStyleClass().clear();
        registerEmailAddressField.getStyleClass().add("textFieldsError");
        //EmailAddressCircle
        registerEmailAddressCircle.getStyleClass().clear();
        registerEmailAddressCircle.getStyleClass().add("circleError");
    }

    public void clearErrorsOnEmailAddress(KeyEvent keyEvent) {
        //EmailAddressField
        registerEmailAddressField.getStyleClass().clear();
        registerEmailAddressField.getStyleClass().add("textFields");
        //EmailAddressFCircle
        registerEmailAddressCircle.getStyleClass().clear();
        registerEmailAddressCircle.getStyleClass().add("circle");
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
    //PASSWORD
    private void errorOnPassword(){
        //PasswordField
        registerPasswordField.getStyleClass().clear();
        registerPasswordField.getStyleClass().add("textFieldsError");
        //VoivodeshipCircle
        registerPasswordCircle.getStyleClass().clear();
        registerPasswordCircle.getStyleClass().add("circleError");
    }

    public void clearErrorsOnPassword(KeyEvent keyEvent) {
        //PasswordField
        registerPasswordField.getStyleClass().clear();
        registerPasswordField.getStyleClass().add("textFields");
        //PasswordCircle
        registerPasswordCircle.getStyleClass().clear();
        registerPasswordCircle.getStyleClass().add("circle");
    }
    //REPEAT PASSWORD
    private void errorOnRepeatPassword(){
        //RepeatPasswordField
        registerRepeatPasswordField.getStyleClass().clear();
        registerRepeatPasswordField.getStyleClass().add("textFieldsError");
        //RepeatVoivodeshipCircle
        registerRepeatPasswordCircle.getStyleClass().clear();
        registerRepeatPasswordCircle.getStyleClass().add("circleError");
    }

    public void clearErrorsOnRepeatPassword(KeyEvent keyEvent) {
        //RepeatPasswordField
        registerRepeatPasswordField.getStyleClass().clear();
        registerRepeatPasswordField.getStyleClass().add("textFields");
        //RepeatPasswordCircle
        registerRepeatPasswordCircle.getStyleClass().clear();
        registerRepeatPasswordCircle.getStyleClass().add("circle");
    }

    //AREA
    private void errorOnArea(){

        registerAreaChoiceBox.getStyleClass().clear();
        registerAreaChoiceBox.getStyleClass().add("textFieldsError");

        registerAreaCircle.getStyleClass().clear();
        registerAreaCircle.getStyleClass().add("circleError");
    }

    public void clearErrorOnArea(MouseEvent mouseEvent) {

        registerAreaChoiceBox.getStyleClass().clear();
        registerAreaChoiceBox.getStyleClass().add("textFields");

        registerAreaCircle.getStyleClass().clear();
        registerAreaCircle.getStyleClass().add("circle");
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        registerAreaChoiceBox.setItems(FXCollections.observableArrayList(
                "First", "Second", "Third"));
    }



}
