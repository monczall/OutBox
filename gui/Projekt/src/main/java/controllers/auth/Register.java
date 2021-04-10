package main.java.controllers.auth;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import main.java.SceneManager;
import main.java.features.Alerts;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register {

    @FXML
    private AnchorPane loginRightPaneAnchorPane;

    @FXML
    private Button registerExitButtonButton;

    @FXML
    private Button registerReturnButtonButton;

    @FXML
    private Button registerRegisterButtonButton;

    @FXML
    private Circle registerFirstNameCircle;

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
    private Label registerFirstNameErrorExample;

    @FXML
    private Label registerLastNameErrorExample;

    @FXML
    private Label registerPhoneNumberErrorExample;

    @FXML
    private Label registerEmailErrorExample;

    @FXML
    private Label registerStreetErrorExample;

    @FXML
    private Label registerCityErrorExample;

    @FXML
    private Label registerVoivodeshipErrorExample;

    @FXML
    private Label registerSixCharsRequirement;

    @FXML
    private Label registerSmallLetterRequirement;

    @FXML
    private Label registerBigLetterRequirement;

    @FXML
    private Label registerNumberRequirement;

    @FXML
    private Label registerSpecialCharRequirement;

    @FXML
    private Label registerSamePasswordsRequirement;

    public void register(){
        if(isValid(registerFirstNameField.getText(), registerLastNameField.getText(), registerPhoneNumberField.getText(), registerEmailAddressField.getText(), registerStreetField.getText(), registerCityField.getText(), registerVoivodeshipField.getText(), registerPasswordField.getText(), registerRepeatPasswordField.getText())){
            //REJESTRACJA POMYSLNA
            System.out.println("Zarejestrowano");
            System.out.println(registerFirstNameField.getText());
            System.out.println(registerLastNameField.getText());
            System.out.println(registerPhoneNumberField.getText());
            System.out.println(registerEmailAddressField.getText());
            System.out.println(registerStreetField.getText());
            System.out.println(registerCityField.getText());
            System.out.println(registerVoivodeshipField.getText());
            System.out.println(registerPasswordField.getText());
            System.out.println(registerRepeatPasswordField.getText());
        }
    }

    private boolean isValid(String firstName, String lastName, String phoneNumber, String email, String street, String city, String voivodeship, String password, String password2){
        boolean firstNameError = false, lastNameError = false, phoneNumberError = false, emailError = false, streetError = false, cityError = false, voivodeshipError = false, passwordError = false, passwordNotTheSameError = false;
        int error = 0;
        Pattern pattern = Pattern.compile("[A-Za-z]{2,60}");

        Pattern patternFirstName = Pattern.compile("[A-Za-z]{2,30}\\s?[A-Za-z]{2,30}");

        Pattern patternLastName = Pattern.compile("[A-Za-z]{2,30}\\s?\\-\\s?[A-Za-z]{2,30}");

        Pattern patternMobilePhoneNumber = Pattern.compile("\\+?[0-9]{0,2}\\s?[0-9]{3}\\s?[0-9]{3}\\s?[0-9]{3}");
        Pattern patternPhoneNumber = Pattern.compile("[0-9]{3}\\s?[0-9]{2}\\s?[0-9]{2}\\s?[0-9]{2}");

        Pattern patternEmail = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");

        Pattern patternStreet = Pattern.compile("[A-Za-z]{0,2}\\.?\\s?[A-Za-z]{2,40}\\s?\\-?[A-Za-z]{0,40}?\\s?\\-?[A-Za-z]{0,40}?\\s[0-9]{1,4}\\s?[A-Za-z]?\\s?\\/?\\s?[0-9]{0,5}");

        Pattern patternCity = Pattern.compile("[A-Za-z]{2,40}\\s?\\-?\\s?[A-Za-z]{0,40}\\s?\\-?\\s?[A-Za-z]{0,40}");

        Pattern patternVoivodeship = Pattern.compile("[A-Za-z]{7,40}\\s?\\-?\\s?[A-Za-z]{0,40}");

        Pattern patternPassword = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,}$");

        Matcher matchSingleFirstName = pattern.matcher(firstName);
        Matcher matchDoubleFirstName = patternFirstName.matcher(firstName);

        Matcher matchSingleLastName = pattern.matcher(lastName);
        Matcher matchDoubleLastName = patternLastName.matcher(lastName);

        Matcher matchMobilePhoneNumber = patternMobilePhoneNumber.matcher(phoneNumber);
        Matcher matchPhoneNumber = patternPhoneNumber.matcher(phoneNumber);

        Matcher matchEmail = patternEmail.matcher(email);

        Matcher matchStreet = patternStreet.matcher(street);

        Matcher matchCity = patternCity.matcher(city);

        Matcher matchVoivodeship = patternVoivodeship.matcher(voivodeship);

        Matcher matchPassword = patternPassword.matcher(password);

        if(!matchSingleFirstName.matches() && !matchDoubleFirstName.matches()){
            errorOnFirstName();
            firstNameError = true;
            error++;
        }
        if(!matchSingleLastName.matches() && !matchDoubleLastName.matches()){
            errorOnLastName();
            lastNameError = true;
            error++;
        }
        if(!matchPhoneNumber.matches() && !matchMobilePhoneNumber.matches()){
            errorOnPhoneNumber();
            phoneNumberError = true;
            error++;
        }
        if(!matchEmail.matches()){
            errorOnEmailAddress();
            emailError = true;
            error++;
        }
        if(!matchStreet.matches()){
            errorOnStreet();
            streetError = true;
            error++;
        }
        if(!matchCity.matches()){
            errorOnCity();
            cityError = true;
            error++;
        }
        if(!matchVoivodeship.matches()){
            errorOnVoivodeship();
            voivodeshipError = true;
            error++;
        }
        if(!matchPassword.matches()){
            errorOnPassword();
            passwordError = true;
            errorOnRepeatPassword();
            error++;
        }else{
            if(!password.equals(password2)){
                errorOnRepeatPassword();
                passwordNotTheSameError = true;
                error++;
            }
        }

        if(error <= 0){
            return true;
        }else if(error == 1){
            if(firstNameError){

                Alerts.createAlert(loginRightPaneAnchorPane, registerReturnButtonButton,"WARNING","Podany format imienia jest błędny");
            }
            if(lastNameError){

                Alerts.createAlert(loginRightPaneAnchorPane, registerReturnButtonButton,"WARNING","Podany format nazwiska jest błedny");
            }
            if(phoneNumberError){

                Alerts.createAlert(loginRightPaneAnchorPane, registerReturnButtonButton,"WARNING","Podany format numeru telefonu jest błędny");
            }
            if(emailError){

                Alerts.createAlert(loginRightPaneAnchorPane, registerReturnButtonButton,"WARNING","Podany format adresu email jest błędny");
            }
            if(streetError){

                Alerts.createAlert(loginRightPaneAnchorPane, registerReturnButtonButton,"WARNING","Podany format ulicy jest błędny");
            }
            if(cityError){

                Alerts.createAlert(loginRightPaneAnchorPane, registerReturnButtonButton,"WARNING","Podany format miasta jest błędny");
            }
            if(voivodeshipError){

                Alerts.createAlert(loginRightPaneAnchorPane, registerReturnButtonButton,"WARNING","Podany format województwa jest błędny");
            }
            if(passwordError){

                Alerts.createAlert(loginRightPaneAnchorPane, registerReturnButtonButton,"WARNING","Podany format hasła jest błędny");
            }
            if(passwordNotTheSameError){

                Alerts.createAlert(loginRightPaneAnchorPane, registerReturnButtonButton,"WARNING","Hasła nie są takie same");
            }
            return false;
        }else{

            Alerts.createAlert(loginRightPaneAnchorPane, registerReturnButtonButton,"WARNING","Popraw błędy na zaznaczonych polach");
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

        registerFirstNameErrorExample.setVisible(true);
    }

    public void clearErrorsOnFirstName(KeyEvent keyEvent) {
        //FirstNameField
        registerFirstNameField.getStyleClass().clear();
        registerFirstNameField.getStyleClass().add("textFields");
        //FirstNameCircle
        registerFirstNameCircle.getStyleClass().clear();
        registerFirstNameCircle.getStyleClass().add("circle");

        registerFirstNameErrorExample.setVisible(false);
    }
    //LAST NAME
    private void errorOnLastName(){
        //LastNameField
        registerLastNameField.getStyleClass().clear();
        registerLastNameField.getStyleClass().add("textFieldsError");
        //LastNameCircle
        registerLastNameCircle.getStyleClass().clear();
        registerLastNameCircle.getStyleClass().add("circleError");

        registerLastNameErrorExample.setVisible(true);
    }

    public void clearErrorsOnLastName(KeyEvent keyEvent) {
        //LastNameField
        registerLastNameField.getStyleClass().clear();
        registerLastNameField.getStyleClass().add("textFields");
        //LastNameCircle
        registerLastNameCircle.getStyleClass().clear();
        registerLastNameCircle.getStyleClass().add("circle");

        registerLastNameErrorExample.setVisible(false);
    }
    //PHONE NUMBER
    private void errorOnPhoneNumber(){
        //PhoneNumberField
        registerPhoneNumberField.getStyleClass().clear();
        registerPhoneNumberField.getStyleClass().add("textFieldsError");
        //PhoneNumberCircle
        registerPhoneNumberCircle.getStyleClass().clear();
        registerPhoneNumberCircle.getStyleClass().add("circleError");

        registerPhoneNumberErrorExample.setVisible(true);
    }

    public void clearErrorsOnPhoneNumber(KeyEvent keyEvent) {
        //PhoneNumberField
        registerPhoneNumberField.getStyleClass().clear();
        registerPhoneNumberField.getStyleClass().add("textFields");
        //PhoneNumberCircle
        registerPhoneNumberCircle.getStyleClass().clear();
        registerPhoneNumberCircle.getStyleClass().add("circle");

        registerPhoneNumberErrorExample.setVisible(false);
    }
    //EMAIL ADDRESS
    private void errorOnEmailAddress(){
        //EmailAddressField
        registerEmailAddressField.getStyleClass().clear();
        registerEmailAddressField.getStyleClass().add("textFieldsError");
        //EmailAddressCircle
        registerEmailAddressCircle.getStyleClass().clear();
        registerEmailAddressCircle.getStyleClass().add("circleError");

        registerEmailErrorExample.setVisible(true);
    }

    public void clearErrorsOnEmailAddress(KeyEvent keyEvent) {
        //EmailAddressField
        registerEmailAddressField.getStyleClass().clear();
        registerEmailAddressField.getStyleClass().add("textFields");
        //EmailAddressFCircle
        registerEmailAddressCircle.getStyleClass().clear();
        registerEmailAddressCircle.getStyleClass().add("circle");

        registerEmailErrorExample.setVisible(false);
    }
    //STREET
    private void errorOnStreet(){
        //StreetField
        registerStreetField.getStyleClass().clear();
        registerStreetField.getStyleClass().add("textFieldsError");
        //StreetCircle
        registerStreetCircle.getStyleClass().clear();
        registerStreetCircle.getStyleClass().add("circleError");

        registerStreetErrorExample.setVisible(true);
    }

    public void clearErrorsOnStreet(KeyEvent keyEvent) {
        //StreetField
        registerStreetField.getStyleClass().clear();
        registerStreetField.getStyleClass().add("textFields");
        //StreetCircle
        registerStreetCircle.getStyleClass().clear();
        registerStreetCircle.getStyleClass().add("circle");

        registerStreetErrorExample.setVisible(false);
    }
    //CITY
    private void errorOnCity(){
        //CityField
        registerCityField.getStyleClass().clear();
        registerCityField.getStyleClass().add("textFieldsError");
        //CityCircle
        registerCityCircle.getStyleClass().clear();
        registerCityCircle.getStyleClass().add("circleError");

        registerCityErrorExample.setVisible(true);
    }

    public void clearErrorsOnCity(KeyEvent keyEvent) {
        //CityField
        registerCityField.getStyleClass().clear();
        registerCityField.getStyleClass().add("textFields");
        //CityCircle
        registerCityCircle.getStyleClass().clear();
        registerCityCircle.getStyleClass().add("circle");

        registerCityErrorExample.setVisible(false);
    }
    //VOIVODESHIP
    private void errorOnVoivodeship(){
        //VoivodeshipField
        registerVoivodeshipField.getStyleClass().clear();
        registerVoivodeshipField.getStyleClass().add("textFieldsError");
        //VoivodeshipCircle
        registerVoivodeshipCircle.getStyleClass().clear();
        registerVoivodeshipCircle.getStyleClass().add("circleError");

        registerVoivodeshipErrorExample.setVisible(true);
    }

    public void clearErrorsOnVoivodeship(KeyEvent keyEvent) {
        //VoivodeshipField
        registerVoivodeshipField.getStyleClass().clear();
        registerVoivodeshipField.getStyleClass().add("textFields");
        //VoivodeshipCircle
        registerVoivodeshipCircle.getStyleClass().clear();
        registerVoivodeshipCircle.getStyleClass().add("circle");

        registerVoivodeshipErrorExample.setVisible(false);
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

    private void passwordRequirements(){
        Pattern sixChars = Pattern.compile(".{6,}");
        Pattern smallLetter = Pattern.compile(".*[a-z]+.*");
        Pattern bigLetter = Pattern.compile(".*[A-Z]+.*");
        Pattern number = Pattern.compile(".*[0-9]+.*");
        Pattern specialChar = Pattern.compile(".*[!@#$%^&*()_\\-+=]+.*");

        Matcher matchSixChars = sixChars.matcher(registerPasswordField.getText());
        Matcher matchSmallLetter = smallLetter.matcher(registerPasswordField.getText());
        Matcher matchBigLetter = bigLetter.matcher(registerPasswordField.getText());
        Matcher matchNumber = number.matcher(registerPasswordField.getText());
        Matcher matchSpecialChar = specialChar.matcher(registerPasswordField.getText());

        if(matchSixChars.matches()){
            registerSixCharsRequirement.getStyleClass().clear();
            registerSixCharsRequirement.getStyleClass().add("successText");
        }else{
            registerSixCharsRequirement.getStyleClass().clear();
            registerSixCharsRequirement.getStyleClass().add("errorText");
        }

        if(matchSmallLetter.matches()){
            registerSmallLetterRequirement.getStyleClass().clear();
            registerSmallLetterRequirement.getStyleClass().add("successText");
        }else{
            registerSmallLetterRequirement.getStyleClass().clear();
            registerSmallLetterRequirement.getStyleClass().add("errorText");
        }

        if(matchBigLetter.matches()){
            registerBigLetterRequirement.getStyleClass().clear();
            registerBigLetterRequirement.getStyleClass().add("successText");
        }else{
            registerBigLetterRequirement.getStyleClass().clear();
            registerBigLetterRequirement.getStyleClass().add("errorText");
        }

        if(matchNumber.matches()){
            registerNumberRequirement.getStyleClass().clear();
            registerNumberRequirement.getStyleClass().add("successText");
        }else{
            registerNumberRequirement.getStyleClass().clear();
            registerNumberRequirement.getStyleClass().add("errorText");
        }

        if(matchSpecialChar.matches()){
            registerSpecialCharRequirement.getStyleClass().clear();
            registerSpecialCharRequirement.getStyleClass().add("successText");
        }else{
            registerSpecialCharRequirement.getStyleClass().clear();
            registerSpecialCharRequirement.getStyleClass().add("errorText");
        }

        if(registerRepeatPasswordField.getText().equals(registerPasswordField.getText())){
            registerSamePasswordsRequirement.getStyleClass().clear();
            registerSamePasswordsRequirement.getStyleClass().add("successText");
        }else{
            registerSamePasswordsRequirement.getStyleClass().clear();
            registerSamePasswordsRequirement.getStyleClass().add("errorText");
        }
    }

    public void checkRequirements(KeyEvent keyEvent) {
        passwordRequirements();
    }


}
