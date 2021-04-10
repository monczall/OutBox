package main.java.controllers.manager;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class ManagerCouriersAdd implements Initializable {

    @FXML
    private TextField name;

    @FXML
    private TextField surname;

    @FXML
    private TextField street;

    @FXML
    private TextField city;

    @FXML
    private TextField pesel;

    @FXML
    private TextField numberPhone;

    public void addCourier(MouseEvent mouseEvent) {
        if(name.getText().toString().equals("") ||
                surname.getText().toString().equals("") ||
                street.getText().toString().equals("") ||
                city.getText().toString().equals("") ||
                pesel.getText().toString().equals("") ||
                numberPhone.getText().toString().equals("")){
            System.out.println("error");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
