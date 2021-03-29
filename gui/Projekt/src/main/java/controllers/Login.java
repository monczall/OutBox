package main.java.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import main.java.SceneManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Login{

    @FXML
    private Button loginExitButtonButton;

    @FXML
    private Button loginCreateAccountButton;

    @FXML
    private Button loginLoginButtonButton;

    @FXML
    private TextField loginEmailTextField;

    @FXML
    private Circle loginUserCircleCircle;

    @FXML
    private PasswordField loginPasswordPasswordField;

    @FXML
    private Circle loginPasswordCircleCircle;

    public void login(){
        if(!isEmpty()){
            if(isEmail(loginEmailTextField.getText())){
                if(false){
                    //LOGOWANIE "NA SZTYWNO"
                    if(loginEmailTextField.getText().equals("client") && loginPasswordPasswordField.getText().equals("123123")){
                        SceneManager.renderScene("client");
                        System.out.println("Zalogowano jako uzytkownik!");

                    }else if(loginEmailTextField.getText().equals("courier") && loginPasswordPasswordField.getText().equals("123123")){
                        SceneManager.renderScene("courier");
                        System.out.println("Zalogowano jako kurier!");

                    }else if(loginEmailTextField.getText().equals("courier2") && loginPasswordPasswordField.getText().equals("123123")){
                        //SceneManager.renderScene("TU PODAC NAZWE SCENY KURIERA MIEDZYODDZIALOWEGO");
                        System.out.println("Zalogowano jako kurier miedzyoddzialowy!");

                    }else if(loginEmailTextField.getText().equals("kierownik") && loginPasswordPasswordField.getText().equals("123123")){
                        //SceneManager.renderScene("TU PODAC NAZWE SCENY KIEROWNIKA");
                        System.out.println("Zalogowano jako kierownik!");

                    }else if(loginEmailTextField.getText().equals("admin") && loginPasswordPasswordField.getText().equals("123123")){
                        SceneManager.renderScene("admin");
                        System.out.println("Zalogowano jako admin!");

                    }else{
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Błąd logowania");
                        alert.setHeaderText(null);
                        alert.setContentText("Podany użytkownik nie istnieje lub podano błędne dane.");

                        alert.showAndWait();
                    }
                    System.out.println(loginEmailTextField.getText());
                    System.out.println(loginPasswordPasswordField.getText());
                }else{
                    //UserTextField
                    loginEmailTextField.getStyleClass().clear();
                    loginEmailTextField.getStyleClass().add("textFieldsError");
                    //UserCircle
                    loginUserCircleCircle.getStyleClass().clear();
                    loginUserCircleCircle.getStyleClass().add("circleError");

                    //PasswordTextField
                    loginPasswordPasswordField.getStyleClass().clear();
                    loginPasswordPasswordField.getStyleClass().add("textFieldsError");
                    //PasswordCircle
                    loginPasswordCircleCircle.getStyleClass().clear();
                    loginPasswordCircleCircle.getStyleClass().add("circleError");

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Niepoprawne dane");
                    alert.setHeaderText(null);
                    alert.setContentText("Podano niepoprawne dane! \nE-Mail i/lub hasło nie pasują! Wprowadź poprawne dane i spróbuj ponownie.");

                    alert.showAndWait();
                }
            }else{
                //UserTextField
                loginEmailTextField.getStyleClass().clear();
                loginEmailTextField.getStyleClass().add("textFieldsError");
                //UserCircle
                loginUserCircleCircle.getStyleClass().clear();
                loginUserCircleCircle.getStyleClass().add("circleError");

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Niepoprawny email");
                alert.setHeaderText(null);
                alert.setContentText("Niepoprawny format danych! Podany E-Mail nie jest prawidłowy.");

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
        if(loginEmailTextField.getText().isEmpty()){
            //UserTextField
            loginEmailTextField.getStyleClass().clear();
            loginEmailTextField.getStyleClass().add("textFieldsError");
            //UserCircle
            loginUserCircleCircle.getStyleClass().clear();
            loginUserCircleCircle.getStyleClass().add("circleError");
            error++;
        }
        if(loginPasswordPasswordField.getText().isEmpty()){
            //PasswordTextField
            loginPasswordPasswordField.getStyleClass().clear();
            loginPasswordPasswordField.getStyleClass().add("textFieldsError");
            //PasswordCircle
            loginPasswordCircleCircle.getStyleClass().clear();
            loginPasswordCircleCircle.getStyleClass().add("circleError");
            error++;
        }
        if(error > 0){
            return true;
        }else{
            return false;
        }
    }
    private boolean isEmail(String email){
        Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        Matcher mat = pattern.matcher(email);

        if(mat.matches()){
            return true;
        }else{
            return false;
        }
    }

    public void handleLogin(MouseEvent mouseEvent) {
        login();
    }

    public void handleLoginOnEnterPressed(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.ENTER)
        {
            login();
        }
    }

    public void handleCreateAccount(MouseEvent mouseEvent) {
        SceneManager.renderScene("register");
    }

    public void handlePasswordReset(MouseEvent mouseEvent) {
        SceneManager.renderScene("passwordReset");
    }

    public void handleExit(ActionEvent actionEvent) {
        Stage stage = (Stage) loginExitButtonButton.getScene().getWindow();
        stage.close();
    }

    public void handleUser(MouseEvent mouseEvent) {
        SceneManager.renderScene("client");
        System.out.println("Zalogowano jako uzytkownik!");
    }

    public void handleCourier(MouseEvent mouseEvent) {
        SceneManager.renderScene("courier");
        System.out.println("Zalogowano jako kurier!");
    }

    public void handleCourier2(MouseEvent mouseEvent) {
        SceneManager.renderScene("courier2");
        System.out.println("Zalogowano jako kurier miedzyoddzialowy!");
    }

    public void handleManager(MouseEvent mouseEvent) {
        SceneManager.renderScene("manager");
        System.out.println("Zalogowano jako kierownik!");
    }

    public void handleAdmin(MouseEvent mouseEvent) {
        SceneManager.renderScene("admin");
        System.out.println("Zalogowano jako admin!");
    }

    public void clearErrorsOnEmail(KeyEvent keyEvent) {
        //UserTextField
        loginEmailTextField.getStyleClass().clear();
        loginEmailTextField.getStyleClass().add("textFields");
        //UserCircle
        loginUserCircleCircle.getStyleClass().clear();
        loginUserCircleCircle.getStyleClass().add("circle");
    }

    public void clearErrorsOnPassword(KeyEvent keyEvent) {
        //PasswordTextField
        loginPasswordPasswordField.getStyleClass().clear();
        loginPasswordPasswordField.getStyleClass().add("textFields");
        //PasswordCircle
        loginPasswordCircleCircle.getStyleClass().clear();
        loginPasswordCircleCircle.getStyleClass().add("circle");
    }

}
