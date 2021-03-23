package main.java.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import main.java.SceneManager;

public class Login{

    @FXML
    private Button loginExitButtonButton;

    @FXML
    private TextField loginEmailTextField;

    @FXML
    private Circle loginUserCircleCircle;

    @FXML
    private PasswordField loginPasswordPasswordField;

    @FXML
    private Circle loginPasswordCircleCircle;

    private boolean isEmpty(){
        if(loginEmailTextField.getText().isEmpty() && loginPasswordPasswordField.getText().isEmpty()){
            loginEmailTextField.setStyle("-fx-border-color: RED;");
            loginUserCircleCircle.setStyle("-fx-fill: RED;");
            loginPasswordPasswordField.setStyle("-fx-border-color: RED;");
            loginPasswordCircleCircle.setStyle("-fx-fill: RED;");
            return true;
        }else if(loginEmailTextField.getText().isEmpty()){
            loginEmailTextField.setStyle("-fx-border-color: RED;");
            loginUserCircleCircle.setStyle("-fx-fill: RED;");
            loginPasswordPasswordField.setStyle(null);
            loginPasswordCircleCircle.setStyle(null);
            return true;
        }else if(loginPasswordPasswordField.getText().isEmpty()){
            loginEmailTextField.setStyle(null);
            loginUserCircleCircle.setStyle(null);
            loginPasswordPasswordField.setStyle("-fx-border-color: RED;");
            loginPasswordCircleCircle.setStyle("-fx-fill: RED;");
            return true;
        }else {
            loginEmailTextField.setStyle(null);
            loginUserCircleCircle.setStyle(null);
            loginPasswordPasswordField.setStyle(null);
            loginPasswordCircleCircle.setStyle(null);
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

    public void login(){
        if(!isEmpty()){
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
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Puste pola");
            alert.setHeaderText(null);
            alert.setContentText("Pozostawiono puste pola! Uzupełnij wymagane informacje.");

            alert.showAndWait();
        }
    }

    public void handlePasswordReset(MouseEvent mouseEvent) {
        SceneManager.renderScene("passwordReset");
    }

    public void handleExit(ActionEvent actionEvent) {
        Stage stage = (Stage) loginExitButtonButton.getScene().getWindow();
        stage.close();
    }

}
