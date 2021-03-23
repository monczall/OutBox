package main.java.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import main.java.SceneManager;

public class PasswordReset {

    @FXML
    private Button passwordResetReturnButtonButton;

    public void handleReturn(ActionEvent actionEvent) {
        SceneManager.renderScene("login");
    }

    public void handleExit(ActionEvent actionEvent) {
        Stage stage = (Stage) passwordResetReturnButtonButton.getScene().getWindow();
        stage.close();
    }
}
