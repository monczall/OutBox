package main.java.controllers.auth;

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

public class Register {

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

        }else{
            //SPRAWDZENIE BLEDOW
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Niepoprawne dane");
            alert.setHeaderText(null);
            alert.setContentText("Podano niepoprawne dane! \nPopraw zaznaczone błędy w formularzu rejestracji.");

            alert.showAndWait();
        }
    }

    private boolean isValid(String firstName, String lastName, String phoneNumber, String email, String street, String city, String voivodeship, String password, String password2){
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
            error++;
        }
        if(!matchSingleLastName.matches() && !matchDoubleLastName.matches()){
            errorOnLastName();
            error++;
        }
        if(!matchPhoneNumber.matches() && !matchMobilePhoneNumber.matches()){
            errorOnPhoneNumber();
            error++;
        }
        if(!matchEmail.matches()){
            errorOnEmailAddress();
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
        if(!matchPassword.matches()){
            errorOnPassword();
            errorOnRepeatPassword();
            error++;
        }else{
            if(!password.equals(password2)){
                errorOnRepeatPassword();

                //PASSWORDS DO NOT MATCH
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Hasła nie są takie same");
                alert.setHeaderText(null);
                alert.setContentText("Podano niepoprawne dane! \nHasła nie są takie same.");

                alert.showAndWait();
                error++;
            }
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
