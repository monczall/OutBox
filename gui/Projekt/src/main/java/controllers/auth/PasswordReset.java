package main.java.controllers.auth;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import main.java.SceneManager;

public class PasswordReset {

    @FXML
    private Button passwordResetReturnButtonButton;

    public void handleSendVerificationCode(ActionEvent actionEvent) {
    }

    public void handleVerify(ActionEvent actionEvent) {
    }

    public void passwordReset(){
        System.out.println("Zresetowano");
    }

    public void handleSetNewPassword(ActionEvent actionEvent) {
        passwordReset();
    }

    public void handlePasswordResetOnEnterPressed(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.ENTER)
        {
            passwordReset();
        }
    }

    public void handleReturn(ActionEvent actionEvent) {
        SceneManager.renderScene("login");
    }

    public void handleExit(ActionEvent actionEvent) {
        Stage stage = (Stage) passwordResetReturnButtonButton.getScene().getWindow();
        stage.close();
    }

    public void clearErrorsOnEmail(KeyEvent keyEvent) {
    }

    public void clearErrorsOnVerificationCode(KeyEvent keyEvent) {
    }

    public void clearErrorsOnPassword(KeyEvent keyEvent) {
    }

    public void clearErrorsOnRepeatPassword(KeyEvent keyEvent) {
    }
}
