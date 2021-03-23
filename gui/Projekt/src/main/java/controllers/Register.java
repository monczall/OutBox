package main.java.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import main.java.SceneManager;

public class Register {

    @FXML
    private Button registerExitButtonButton;

    public void handleRegister(MouseEvent mouseEvent) {
    }

    public void handleRegisterOnEnterPressed(KeyEvent keyEvent) {
    }

    public void handleReturn(ActionEvent actionEvent) {
        SceneManager.renderScene("login");
    }

    public void handleExit(ActionEvent actionEvent) {
        Stage stage = (Stage) registerExitButtonButton.getScene().getWindow();
        stage.close();
    }

}
