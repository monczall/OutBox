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
import main.java.dao.AreasDAO;
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


    public void edit(){
        if(!isEmpty()){
            if(isValid(editFirstNameField.getText(), editLastNameField.getText(), editStreetField.getText(), editCityField.getText(),
                    editVoivodeshipField.getText())){
                if(isPhoneNumber(editPhoneNumberField.getText())){
                    if(isEmail(editEmailAddressField.getText())){
                        //POMYSLNIE DODANE
                        System.out.println("Edytowano pracownika");
                    }else{
                        errorOnEmailAddress();

                        Alerts.createCustomAlert(RightPaneAnchorPane, editEditButtonButton,"WARNING",
                                App.getLanguageProperties("adminInvalidEmail"), 560, 86, "alertFailure");
                    }
                }else{
                    errorOnPhoneNumber();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Niepoprawny numer telefonu");
                    alert.setHeaderText(null);
                    alert.setContentText("Niepoprawny format danych! Podany numer telefonu nie jest prawidłowy.");

                    alert.showAndWait();
                }
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

    public void handleedit(MouseEvent mouseEvent) {
        edit();
    }

    public void handleeditOnEnterPressed(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.ENTER)
        {
            edit();
        }
    }


    //FIRST NAME
    private void errorOnFirstName(){
        //FirstNameField
        editFirstNameField.getStyleClass().clear();
        editFirstNameField.getStyleClass().add("textFieldsError");
        //FirstNameCircle
        editFirstNameCircle.getStyleClass().clear();
        editFirstNameCircle.getStyleClass().add("circleError");
    }

    public void clearErrorsOnFirstName(KeyEvent keyEvent) {
        //FirstNameField
        editFirstNameField.getStyleClass().clear();
        editFirstNameField.getStyleClass().add("textFields");
        //FirstNameCircle
        editFirstNameCircle.getStyleClass().clear();
        editFirstNameCircle.getStyleClass().add("circle");
    }
    //LAST NAME
    private void errorOnLastName(){
        //LastNameField
        editLastNameField.getStyleClass().clear();
        editLastNameField.getStyleClass().add("textFieldsError");
        //LastNameCircle
        editLastNameCircle.getStyleClass().clear();
        editLastNameCircle.getStyleClass().add("circleError");
    }

    public void clearErrorsOnLastName(KeyEvent keyEvent) {
        //LastNameField
        editLastNameField.getStyleClass().clear();
        editLastNameField.getStyleClass().add("textFields");
        //LastNameCircle
        editLastNameCircle.getStyleClass().clear();
        editLastNameCircle.getStyleClass().add("circle");
    }
    //PHONE NUMBER
    private void errorOnPhoneNumber(){
        //PhoneNumberField
        editPhoneNumberField.getStyleClass().clear();
        editPhoneNumberField.getStyleClass().add("textFieldsError");
        //PhoneNumberCircle
        editPhoneNumberCircle.getStyleClass().clear();
        editPhoneNumberCircle.getStyleClass().add("circleError");
    }

    public void clearErrorsOnPhoneNumber(KeyEvent keyEvent) {
        //PhoneNumberField
        editPhoneNumberField.getStyleClass().clear();
        editPhoneNumberField.getStyleClass().add("textFields");
        //PhoneNumberCircle
        editPhoneNumberCircle.getStyleClass().clear();
        editPhoneNumberCircle.getStyleClass().add("circle");
    }
    //EMAIL ADDRESS
    private void errorOnEmailAddress(){
        //EmailAddressField
        editEmailAddressField.getStyleClass().clear();
        editEmailAddressField.getStyleClass().add("textFieldsError");
        //EmailAddressCircle
        editEmailAddressCircle.getStyleClass().clear();
        editEmailAddressCircle.getStyleClass().add("circleError");
    }

    public void clearErrorsOnEmailAddress(KeyEvent keyEvent) {
        //EmailAddressField
        editEmailAddressField.getStyleClass().clear();
        editEmailAddressField.getStyleClass().add("textFields");
        //EmailAddressFCircle
        editEmailAddressCircle.getStyleClass().clear();
        editEmailAddressCircle.getStyleClass().add("circle");
    }
    //STREET
    private void errorOnStreet(){
        //StreetField
        editStreetField.getStyleClass().clear();
        editStreetField.getStyleClass().add("textFieldsError");
        //StreetCircle
        editStreetCircle.getStyleClass().clear();
        editStreetCircle.getStyleClass().add("circleError");
    }

    public void clearErrorsOnStreet(KeyEvent keyEvent) {
        //StreetField
        editStreetField.getStyleClass().clear();
        editStreetField.getStyleClass().add("textFields");
        //StreetCircle
        editStreetCircle.getStyleClass().clear();
        editStreetCircle.getStyleClass().add("circle");
    }
    //CITY
    private void errorOnCity(){
        //CityField
        editCityField.getStyleClass().clear();
        editCityField.getStyleClass().add("textFieldsError");
        //CityCircle
        editCityCircle.getStyleClass().clear();
        editCityCircle.getStyleClass().add("circleError");
    }

    public void clearErrorsOnCity(KeyEvent keyEvent) {
        //CityField
        editCityField.getStyleClass().clear();
        editCityField.getStyleClass().add("textFields");
        //CityCircle
        editCityCircle.getStyleClass().clear();
        editCityCircle.getStyleClass().add("circle");
    }
    //VOIVODESHIP
    private void errorOnVoivodeship(){
        //VoivodeshipField
        editVoivodeshipField.getStyleClass().clear();
        editVoivodeshipField.getStyleClass().add("textFieldsError");
        //VoivodeshipCircle
        editVoivodeshipCircle.getStyleClass().clear();
        editVoivodeshipCircle.getStyleClass().add("circleError");
    }

    public void clearErrorsOnVoivodeship(KeyEvent keyEvent) {
        //VoivodeshipField
        editVoivodeshipField.getStyleClass().clear();
        editVoivodeshipField.getStyleClass().add("textFields");
        //VoivodeshipCircle
        editVoivodeshipCircle.getStyleClass().clear();
        editVoivodeshipCircle.getStyleClass().add("circle");
    }


    //AREA
    private void errorOnArea(){

        editAreaChoiceBox.getStyleClass().clear();
        editAreaChoiceBox.getStyleClass().add("textFieldsError");

        editAreaCircle.getStyleClass().clear();
        editAreaCircle.getStyleClass().add("circleError");
    }

    public void clearErrorOnArea(MouseEvent mouseEvent) {

        editAreaChoiceBox.getStyleClass().clear();
        editAreaChoiceBox.getStyleClass().add("textFields");

        editAreaCircle.getStyleClass().clear();
        editAreaCircle.getStyleClass().add("circle");
    }

    private void errorOnRole(){

        editRoleChoiceBox.getStyleClass().clear();
        editRoleChoiceBox.getStyleClass().add("textFieldsError");

        editRoleCircle.getStyleClass().clear();
        editRoleCircle.getStyleClass().add("circleError");
    }

    public void clearErrorOnRole(MouseEvent mouseEvent) {

        editRoleChoiceBox.getStyleClass().clear();
        editRoleChoiceBox.getStyleClass().add("textFields");

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


    public void back(MouseEvent mouseEvent) throws IOException {
        SceneManager.loadScene("../../../resources/view/admin/adminEditEmployee.fxml", RightPaneAnchorPane);
    }

    public void deleteEmployee(MouseEvent mouseEvent) {
    }
}
