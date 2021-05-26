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
import main.java.App;
import main.java.SceneManager;
import main.java.controllers.auth.Encryption;
import main.java.dao.AreasDAO;
import main.java.dao.UserInfosDAO;
import main.java.entity.Users;
import main.java.features.Alerts;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.net.URL;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static main.java.dao.UsersDAO.getUsers;

public class AdminAddManager implements Initializable {


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
    private ChoiceBox registerAreaChoiceBox;

    @FXML
    private AnchorPane RightPaneAnchorPane;

    String email;
    String name;

    /**
     * Method that adds an manager to the base
     * Retrieves entered data from inputs and checks their correctness
     */

    public void register(){
        if(!isEmpty()){
            if(isValid(registerFirstNameField.getText(), registerLastNameField.getText(),
                    registerStreetField.getText(),
                    registerCityField.getText(), registerVoivodeshipField.getText())){
                if(isPhoneNumber(registerPhoneNumberField.getText())){
                    if(isEmail(registerEmailAddressField.getText())){
                        if(!ifExist(registerEmailAddressField.getText())) {
                            String password = new Random().ints(10, 33, 122).collect(StringBuilder::new,
                                    StringBuilder::appendCodePoint, StringBuilder::append)
                                    .toString();
                            email = registerEmailAddressField.getText();
                            name = registerFirstNameField.getText();
                            try {
                                sendEmail(email,name,password);
                            } catch (MessagingException e) {
                                e.printStackTrace();
                            }
                            //IDEALLY ADDED
                            UserInfosDAO.addUserInfo(registerFirstNameField.getText(),
                                    registerLastNameField.getText(),
                                    registerEmailAddressField.getText(),
                                    registerPhoneNumberField.getText(),
                                    registerStreetField.getText(),
                                    registerCityField.getText(),
                                    registerVoivodeshipField.getText(),
                                    Encryption.encrypt(password), "Menadżer",
                                    AreasDAO.getAreasIdByName(registerAreaChoiceBox.getSelectionModel().getSelectedItem().toString()) );

                            System.out.println("Manager added");

                            Alerts.createCustomAlert(RightPaneAnchorPane, registerRegisterButtonButton,"CHECK",
                                    App.getLanguageProperties("adminSuccessUserAdd"), 370, 86, "alertSuccess");

                            clearData();
                        }else{
                            errorOnEmailAddress();

                            Alerts.createCustomAlert(RightPaneAnchorPane, registerRegisterButtonButton,"WARNING",
                                    App.getLanguageProperties("adminEmailExist"), 310, 86, "alertFailure");
                        }



                    }else{
                        errorOnEmailAddress();

                        Alerts.createCustomAlert(RightPaneAnchorPane, registerRegisterButtonButton,"WARNING",
                                App.getLanguageProperties("adminInvalidEmail"), 560, 86, "alertFailure");
                    }
                }else{
                    errorOnPhoneNumber();

                    Alerts.createCustomAlert(RightPaneAnchorPane, registerRegisterButtonButton,"WARNING",
                            App.getLanguageProperties("adminInvalidNumber"), 565, 86, "alertFailure");
                }
            }else{
                //CHECK FOR ERRORS




                Alerts.createCustomAlert(RightPaneAnchorPane, registerRegisterButtonButton,"WARNING",
                        App.getLanguageProperties("adminInvalidData"), 670, 86, "alertFailure");
            }
        }else{


            Alerts.createCustomAlert(RightPaneAnchorPane, registerRegisterButtonButton,"WARNING",
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

    /**
     * Method that checks if all data is correctly entered
     * True is returned if all data are correct compared to patterns, otherwise false
     * @param firstName first name
     * @param lastName last name
     * @param street street
     * @param city city
     * @param voivodeship voivodeship
     * @return
     */

    private boolean isValid(String firstName, String lastName, String street, String city, String voivodeship){
        int error = 0;
        Pattern pattern = Pattern.compile("[A-Za-z]{2,60}");
        Pattern patternFirstName = Pattern.compile("[A-Za-z]{2,30}\\s?[A-Za-z]{2,30}");
        Pattern patternLastName = Pattern.compile("[A-Za-z]{2,30}\\s?\\-\\s?[A-Za-z]{2,30}");
        Pattern patternStreet = Pattern.compile("[A-Za-z]{0,2}\\.?\\s?[A-Za-z]{2,40}\\s?\\-?[A-Za-z]" +
                "{0,40}?\\s?\\-?[A-Za-z]{0,40}?\\s[0-9]{1,4}\\s?[A-Za-z]?\\s?\\/?\\s?[0-9]{0,5}");
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

    /**
     * Method that checks if phone number is correctly entered
     * True is returned if data are correct compared to patterns, otherwise false
     * @param phoneNumber phone number
     * @return
     */

    private boolean isPhoneNumber(String phoneNumber){
        Pattern patternPhoneNumber = Pattern.compile("\\+?[0-9]{0,3}\\s?[0-9]{2,3}\\s?[0-9]{2,3}\\s?[0-9]{2,3}\\s?");

        Matcher matchPhoneNumber = patternPhoneNumber.matcher(phoneNumber);

        if(matchPhoneNumber.matches()){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Method that checks if email is correctly entered
     * True is returned if data are correct compared to patterns, otherwise false
     * @param email email
     * @return
     */
    private boolean isEmail(String email){
        Pattern patternEmail = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        Matcher matchEmail = patternEmail.matcher(email);

        if(matchEmail.matches()){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Method that checks if email already exist
     * True is returned if email is in use, otherwise false
     * @param email email
     * @return
     */

    private boolean ifExist(String email){
        List<Users> listOfUsers = getUsers();
        for (int i = 0; i < getUsers().size(); i++) {
            if (email.equals(
                    listOfUsers.get(i).getEmail())) {

                return true;
            }
        }

        return false;

    }
    /**
     * Method that clear inputs after area add
     */
    private void clearData() {
        registerFirstNameField.setText(" ");
        registerLastNameField.setText(" ");
        registerPhoneNumberField.setText(" ");
        registerEmailAddressField.setText(" ");
        registerStreetField.setText(" ");
        registerCityField.setText(" ");
        registerVoivodeshipField.setText(" ");
        registerAreaChoiceBox.getSelectionModel().clearSelection();


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
    private void errorOnFirstName(){
        //FirstNameField
        registerFirstNameField.getStyleClass().clear();
        registerFirstNameField.getStyleClass().add("textFieldsError");
        //FirstNameCircle
        registerFirstNameCircle.getStyleClass().clear();
        registerFirstNameCircle.getStyleClass().add("circleError");
    }
    /**
     * Method that change css style while fields with errors are change
     * @param keyEvent key event
     */
    public void clearErrorsOnFirstName(KeyEvent keyEvent) {
        //FirstNameField
        registerFirstNameField.getStyleClass().clear();
        registerFirstNameField.getStyleClass().add("textFields");
        //FirstNameCircle
        registerFirstNameCircle.getStyleClass().clear();
        registerFirstNameCircle.getStyleClass().add("circle");
    }
    /**
     * Method that change css style while an error occurred
     */
    private void errorOnLastName(){
        //LastNameField
        registerLastNameField.getStyleClass().clear();
        registerLastNameField.getStyleClass().add("textFieldsError");
        //LastNameCircle
        registerLastNameCircle.getStyleClass().clear();
        registerLastNameCircle.getStyleClass().add("circleError");
    }
    /**
     * Method that change css style while fields with errors are change
     * @param keyEvent key event
     */
    public void clearErrorsOnLastName(KeyEvent keyEvent) {
        //LastNameField
        registerLastNameField.getStyleClass().clear();
        registerLastNameField.getStyleClass().add("textFields");
        //LastNameCircle
        registerLastNameCircle.getStyleClass().clear();
        registerLastNameCircle.getStyleClass().add("circle");
    }
    /**
     * Method that change css style while an error occurred
     */
    private void errorOnPhoneNumber(){
        //PhoneNumberField
        registerPhoneNumberField.getStyleClass().clear();
        registerPhoneNumberField.getStyleClass().add("textFieldsError");
        //PhoneNumberCircle
        registerPhoneNumberCircle.getStyleClass().clear();
        registerPhoneNumberCircle.getStyleClass().add("circleError");
    }
    /**
     * Method that change css style while fields with errors are change
     * @param keyEvent key event
     */
    public void clearErrorsOnPhoneNumber(KeyEvent keyEvent) {
        //PhoneNumberField
        registerPhoneNumberField.getStyleClass().clear();
        registerPhoneNumberField.getStyleClass().add("textFields");
        //PhoneNumberCircle
        registerPhoneNumberCircle.getStyleClass().clear();
        registerPhoneNumberCircle.getStyleClass().add("circle");
    }
    /**
     * Method that change css style while an error occurred
     */
    private void errorOnEmailAddress(){
        //EmailAddressField
        registerEmailAddressField.getStyleClass().clear();
        registerEmailAddressField.getStyleClass().add("textFieldsError");
        //EmailAddressCircle
        registerEmailAddressCircle.getStyleClass().clear();
        registerEmailAddressCircle.getStyleClass().add("circleError");
    }
    /**
     * Method that change css style while fields with errors are change
     * @param keyEvent key event
     */
    public void clearErrorsOnEmailAddress(KeyEvent keyEvent) {
        //EmailAddressField
        registerEmailAddressField.getStyleClass().clear();
        registerEmailAddressField.getStyleClass().add("textFields");
        //EmailAddressFCircle
        registerEmailAddressCircle.getStyleClass().clear();
        registerEmailAddressCircle.getStyleClass().add("circle");
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

        //AreaName
        registerAreaCircle.getStyleClass().clear();
        registerAreaCircle.getStyleClass().add("circleError");
    }
    /**
     * Method that change css style while fields with errors are change
     * @param mouseEvent mouse event
     */
    public void clearErrorOnArea(MouseEvent mouseEvent) {

        //AreaName
        registerAreaCircle.getStyleClass().clear();
        registerAreaCircle.getStyleClass().add("circle");
    }


    // Method that add areas to choiceBox
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        registerAreaChoiceBox.setItems(AreasDAO.getAreasName());
    }

    /**
     * Method that send email to new created manager with generated password
     * @param recipient e-mail recipient
     * @param firstName first name
     * @param password password
     * @throws MessagingException sending email throw
     */
    public static void sendEmail(String recipient,
                                 String firstName,
                                 String password
    ) throws MessagingException {
        System.out.println("Starting process of sending email");
        Properties properties = new Properties();

        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        String outBoxEmailAccount = "outbox2137@gmail.com";
        String outBoxEmailPassword = "zaq1@WSX";

        Session session = Session.getInstance(properties,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(outBoxEmailAccount,
                                outBoxEmailPassword);
                    }
                });

        Message message = prepareMessage(session,
                outBoxEmailAccount,
                recipient,
                firstName,
                password);

        Transport.send(message);
        System.out.println("Message sent successfully");
    }

    /**
     * Method that prepare pattern of email that will be send
     * @param session session
     * @param outBoxEmailAccount new email
     * @param recipient e-mail recipient
     * @param firstName first name
     * @param password password
     * @return
     */
    private static Message prepareMessage(Session session,
                                          String outBoxEmailAccount,
                                          String recipient,
                                          String firstName,
                                          String password) {
        Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress("OutBox_Support"));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject("New Password");
            message.setText("Your new password is: " + password);
            return message;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
