package main.java.controllers.admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import main.java.App;
import main.java.SceneManager;
import main.java.controllers.auth.Encryption;
import main.java.dao.AreasDAO;
import main.java.dao.UserInfosDAO;
import main.java.dao.UsersDAO;
import main.java.entity.Users;
import main.java.entity.UsersDTO;
import main.java.features.Alerts;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static main.java.dao.UsersDAO.getUsers;

public class AdminEdit implements Initializable {

    @FXML
    private AnchorPane edit;

    @FXML
    private Circle editFirstNameCircle;

    @FXML
    private Circle editAreaCircle;

    @FXML
    private TextField editFirstNameField;

    @FXML
    private Circle editLastNameCircle;

    @FXML
    private TextField editLastNameField;

    @FXML
    private Circle editPhoneNumberCircle;

    @FXML
    private TextField editPhoneNumberField;

    @FXML
    private Circle editEmailAddressCircle;

    @FXML
    private TextField editEmailAddressField;

    @FXML
    private Circle editStreetCircle;

    @FXML
    private TextField editStreetField;

    @FXML
    private Circle editCityCircle;

    @FXML
    private TextField editCityField;

    @FXML
    private Circle editVoivodeshipCircle;

    @FXML
    private TextField editVoivodeshipField;

    @FXML
    private Circle editRoleCircle;


    @FXML
    private ChoiceBox editAreaChoiceBox;

    @FXML
    private ChoiceBox editRoleChoiceBox;

    @FXML
    private AnchorPane RightPaneAnchorPane;

    @FXML
    private Button editEditButtonButton;

    @FXML
    private Button deleteButton;

    /**
     * Method that edit employee data
     * Retrieves entered data from inputs and checks their correctness
     */
    public void edit(){
        if(!isEmpty()){
            if(isValid(editFirstNameField.getText(), editLastNameField.getText(), editStreetField.getText(), editCityField.getText(),
                    editVoivodeshipField.getText())){
                if(isPhoneNumber(editPhoneNumberField.getText())){
                    if(isEmail(editEmailAddressField.getText())){
                        if(!ifExist(editEmailAddressField.getText())) {
                        System.out.println("An employee was edited");
                        // SUCCESSFULLY ADDED
                        UserInfosDAO.editUser(AdminEditEmployee.getUserID(), editFirstNameField.getText(), editLastNameField.getText(),
                                editEmailAddressField.getText(), editPhoneNumberField.getText(), editStreetField.getText(),
                                editCityField.getText(),  editVoivodeshipField.getText(),
                                editRoleChoiceBox.getSelectionModel().getSelectedItem().toString(),
                                AreasDAO.getAreasIdByName(editAreaChoiceBox.getSelectionModel().getSelectedItem().toString()) );

                        Alerts.createCustomAlert(RightPaneAnchorPane, editEditButtonButton,"CHECK",
                                App.getLanguageProperties("adminSuccessEdit"), 360, 86, "alertSuccess");
                        }else{
                            errorOnEmailAddress();

                            Alerts.createCustomAlert(RightPaneAnchorPane, editEditButtonButton,"WARNING",
                                    App.getLanguageProperties("adminEmailExist"), 310, 86, "alertFailure");
                        }



                    }else{
                        errorOnEmailAddress();

                        Alerts.createCustomAlert(RightPaneAnchorPane, editEditButtonButton,"WARNING",
                                App.getLanguageProperties("adminInvalidEmail"), 560, 86, "alertFailure");
                    }
                }else{
                    errorOnPhoneNumber();

                    Alerts.createCustomAlert(RightPaneAnchorPane, editEditButtonButton,"WARNING",
                            App.getLanguageProperties("adminInvalidNumber"), 565, 86, "alertFailure");
                }
            }else{
                // CHECK FOR ERRORS


                Alerts.createCustomAlert(RightPaneAnchorPane, editEditButtonButton,"WARNING",
                        App.getLanguageProperties("adminInvalidData"), 670, 86, "alertFailure");
            }
        }else{
            Alerts.createCustomAlert(RightPaneAnchorPane, editEditButtonButton,"WARNING",
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
        if(editFirstNameField.getText().isEmpty()){
            errorOnFirstName();
            error++;
        }
        if(editLastNameField.getText().isEmpty()){
            errorOnLastName();
            error++;
        }
        if(editPhoneNumberField.getText().isEmpty()){
            errorOnPhoneNumber();
            error++;
        }
        if(editEmailAddressField.getText().isEmpty()){
            errorOnEmailAddress();
            error++;
        }
        if(editStreetField.getText().isEmpty()){
            errorOnStreet();
            error++;
        }
        if(editCityField.getText().isEmpty()){
            errorOnCity();
            error++;
        }
        if(editVoivodeshipField.getText().isEmpty()){
            errorOnVoivodeship();
            error++;
        }

        if(editAreaChoiceBox.getSelectionModel().isEmpty()){
            errorOnArea();
            error++;
        }

        if(editRoleChoiceBox.getSelectionModel().isEmpty()){
            errorOnRole();
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
        Pattern patternStreet = Pattern.compile("[A-Za-z]{0,2}\\.?\\s?[A-Za-z]{2,40}\\s?\\-?[A-Za-z]{0,40}?\\s?\\-?[A-Za-z]{0,40}?\\" +
                "s[0-9]{1,4}\\s?[A-Za-z]?\\s?\\/?\\s?[0-9]{0,5}");
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
     * Method that doing "edit" function after button click
     * @param mouseEvent mouse event
     */
    public void handleedit(MouseEvent mouseEvent) {
        edit();
    }
    /**
     * Method that doing "edit" function after enter pressed
     * @param keyEvent enter pressed
     */
    public void handleeditOnEnterPressed(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.ENTER)
        {
            edit();
        }
    }


    /**
     * Method that change css style while an error occurred
     */
    private void errorOnFirstName(){
        //FirstNameField
        editFirstNameField.getStyleClass().clear();
        editFirstNameField.getStyleClass().add("textFieldsError");
        //FirstNameCircle
        editFirstNameCircle.getStyleClass().clear();
        editFirstNameCircle.getStyleClass().add("circleError");
    }
    /**
     * Method that change css style while fields with errors are change
     * @param keyEvent key event
     */
    public void clearErrorsOnFirstName(KeyEvent keyEvent) {
        //FirstNameField
        editFirstNameField.getStyleClass().clear();
        editFirstNameField.getStyleClass().add("textFields");
        //FirstNameCircle
        editFirstNameCircle.getStyleClass().clear();
        editFirstNameCircle.getStyleClass().add("circle");
    }
    /**
     * Method that change css style while an error occurred
     */
    private void errorOnLastName(){
        //LastNameField
        editLastNameField.getStyleClass().clear();
        editLastNameField.getStyleClass().add("textFieldsError");
        //LastNameCircle
        editLastNameCircle.getStyleClass().clear();
        editLastNameCircle.getStyleClass().add("circleError");
    }
    /**
     * Method that change css style while fields with errors are change
     * @param keyEvent key event
     */
    public void clearErrorsOnLastName(KeyEvent keyEvent) {
        //LastNameField
        editLastNameField.getStyleClass().clear();
        editLastNameField.getStyleClass().add("textFields");
        //LastNameCircle
        editLastNameCircle.getStyleClass().clear();
        editLastNameCircle.getStyleClass().add("circle");
    }
    /**
     * Method that change css style while an error occurred
     */
    private void errorOnPhoneNumber(){
        //PhoneNumberField
        editPhoneNumberField.getStyleClass().clear();
        editPhoneNumberField.getStyleClass().add("textFieldsError");
        //PhoneNumberCircle
        editPhoneNumberCircle.getStyleClass().clear();
        editPhoneNumberCircle.getStyleClass().add("circleError");
    }
    /**
     * Method that change css style while fields with errors are change
     * @param keyEvent key event
     */
    public void clearErrorsOnPhoneNumber(KeyEvent keyEvent) {
        //PhoneNumberField
        editPhoneNumberField.getStyleClass().clear();
        editPhoneNumberField.getStyleClass().add("textFields");
        //PhoneNumberCircle
        editPhoneNumberCircle.getStyleClass().clear();
        editPhoneNumberCircle.getStyleClass().add("circle");
    }
    /**
     * Method that change css style while an error occurred
     */
    private void errorOnEmailAddress(){
        //EmailAddressField
        editEmailAddressField.getStyleClass().clear();
        editEmailAddressField.getStyleClass().add("textFieldsError");
        //EmailAddressCircle
        editEmailAddressCircle.getStyleClass().clear();
        editEmailAddressCircle.getStyleClass().add("circleError");
    }
    /**
     * Method that change css style while fields with errors are change
     * @param keyEvent key event
     */
    public void clearErrorsOnEmailAddress(KeyEvent keyEvent) {
        //EmailAddressField
        editEmailAddressField.getStyleClass().clear();
        editEmailAddressField.getStyleClass().add("textFields");
        //EmailAddressFCircle
        editEmailAddressCircle.getStyleClass().clear();
        editEmailAddressCircle.getStyleClass().add("circle");
    }
    /**
     * Method that change css style while an error occurred
     */
    private void errorOnStreet(){
        //StreetField
        editStreetField.getStyleClass().clear();
        editStreetField.getStyleClass().add("textFieldsError");
        //StreetCircle
        editStreetCircle.getStyleClass().clear();
        editStreetCircle.getStyleClass().add("circleError");
    }
    /**
     * Method that change css style while fields with errors are change
     * @param keyEvent key event
     */
    public void clearErrorsOnStreet(KeyEvent keyEvent) {
        //StreetField
        editStreetField.getStyleClass().clear();
        editStreetField.getStyleClass().add("textFields");
        //StreetCircle
        editStreetCircle.getStyleClass().clear();
        editStreetCircle.getStyleClass().add("circle");
    }
    /**
     * Method that change css style while an error occurred
     */
    private void errorOnCity(){
        //CityField
        editCityField.getStyleClass().clear();
        editCityField.getStyleClass().add("textFieldsError");
        //CityCircle
        editCityCircle.getStyleClass().clear();
        editCityCircle.getStyleClass().add("circleError");
    }
    /**
     * Method that change css style while fields with errors are change
     * @param keyEvent key event
     */
    public void clearErrorsOnCity(KeyEvent keyEvent) {
        //CityField
        editCityField.getStyleClass().clear();
        editCityField.getStyleClass().add("textFields");
        //CityCircle
        editCityCircle.getStyleClass().clear();
        editCityCircle.getStyleClass().add("circle");
    }
    /**
     * Method that change css style while an error occurred
     */
    private void errorOnVoivodeship(){
        //VoivodeshipField
        editVoivodeshipField.getStyleClass().clear();
        editVoivodeshipField.getStyleClass().add("textFieldsError");
        //VoivodeshipCircle
        editVoivodeshipCircle.getStyleClass().clear();
        editVoivodeshipCircle.getStyleClass().add("circleError");
    }
    /**
     * Method that change css style while fields with errors are change
     * @param keyEvent key event
     */
    public void clearErrorsOnVoivodeship(KeyEvent keyEvent) {
        //VoivodeshipField
        editVoivodeshipField.getStyleClass().clear();
        editVoivodeshipField.getStyleClass().add("textFields");
        //VoivodeshipCircle
        editVoivodeshipCircle.getStyleClass().clear();
        editVoivodeshipCircle.getStyleClass().add("circle");
    }


    /**
     * Method that change css style while an error occurred
     */
    private void errorOnArea(){

        editAreaChoiceBox.getStyleClass().clear();
        editAreaChoiceBox.getStyleClass().add("textFieldsError");

        editAreaCircle.getStyleClass().clear();
        editAreaCircle.getStyleClass().add("circleError");
    }
    /**
     * Method that change css style while fields with errors are change
     * @param mouseEvent mouse event
     */
    public void clearErrorOnArea(MouseEvent mouseEvent) {



        editAreaCircle.getStyleClass().clear();
        editAreaCircle.getStyleClass().add("circle");
    }
    /**
     * Method that change css style while an error occurred
     */
    private void errorOnRole(){

        editRoleChoiceBox.getStyleClass().clear();
        editRoleChoiceBox.getStyleClass().add("textFieldsError");

        editRoleCircle.getStyleClass().clear();
        editRoleCircle.getStyleClass().add("circleError");
    }
    /**
     * Method that change css style while fields with errors are change
     * @param mouseEvent mouse event
     */
    public void clearErrorOnRole(MouseEvent mouseEvent) {


        editRoleCircle.getStyleClass().clear();
        editRoleCircle.getStyleClass().add("circle");
    }





    @Override
    public void initialize(URL location, ResourceBundle resources) {
        editAreaChoiceBox.setItems(AreasDAO.getAreasName());

        editRoleChoiceBox.setItems(FXCollections.observableArrayList(
                "Kurier", "Kurier międzyoddziałowy", "Menadżer"));


        List<Users> userList = UsersDAO.getUsersById(AdminEditEmployee.getUserID());


        for (int i = 0; i < editRoleChoiceBox.getItems().size(); i++) {

                if (editRoleChoiceBox.getItems().get(i).equals(userList.get(0).getRole())) {
                    editRoleChoiceBox.getSelectionModel().select(i);
                    break;
                }

        }

        for (int i = 0; i < editAreaChoiceBox.getItems().size(); i++) {

            if (editAreaChoiceBox.getItems().get(i).equals(userList.get(0).getAreasByAreaId().getName())) {
                editAreaChoiceBox.getSelectionModel().select(i);
                break;
            }

        }

        editFirstNameField.setText(userList.get(0).getUserInfosByUserInfoId().getName());
        editLastNameField.setText(userList.get(0).getUserInfosByUserInfoId().getSurname());

        editPhoneNumberField.setText(userList.get(0).getUserInfosByUserInfoId().getPhoneNumber());
        editEmailAddressField.setText(userList.get(0).getEmail());
        editStreetField.setText(userList.get(0).getUserInfosByUserInfoId().getStreetAndNumber());

        editCityField.setText(userList.get(0).getUserInfosByUserInfoId().getCity());
        editVoivodeshipField.setText(userList.get(0).getUserInfosByUserInfoId().getVoivodeship());
    }

    /**
     * Method that handles the return from editing button
     * @param mouseEvent mouse event
     * @throws IOException if doesn't find a scene then throw IOException
     */
    public void back(MouseEvent mouseEvent) throws IOException {
        SceneManager.loadScene("../../../resources/view/admin/adminEditEmployee.fxml", RightPaneAnchorPane);
    }

    /**
     * Method that delete chosen employee
     * @param mouseEvent mouse event
     * @throws IOException if doesn't find a scene then throw IOException
     */
    public void deleteEmployee(MouseEvent mouseEvent) throws IOException {
        UserInfosDAO.deleteUser(AdminEditEmployee.getUserInfoID());
        SceneManager.loadScene("../../../resources/view/admin/adminEditEmployee.fxml", RightPaneAnchorPane);
        Alerts.createCustomAlert(RightPaneAnchorPane, deleteButton,"CHECK",
                App.getLanguageProperties("adminSuccessDelete"), 360, 86, "alertSuccess");
    }
}
