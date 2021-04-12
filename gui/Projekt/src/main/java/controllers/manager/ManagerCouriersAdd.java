package main.java.controllers.manager;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import main.java.features.Alerts;

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

    @FXML
    private AnchorPane appWindow;

    @FXML
    private Button addCourierButton;


    public void addCourier(MouseEvent mouseEvent) {
        boolean status = false;
        if(name.getText().toString().equals("") ||
                surname.getText().toString().equals("") ||
                street.getText().toString().equals("") ||
                city.getText().toString().equals("") ||
                pesel.getText().toString().equals("") ||
                numberPhone.getText().toString().equals("")){
            Alerts.createAlert(appWindow, addCourierButton,"WARNING","UZUPEŁNIJ WSZYSTKIE POLA");
        }
        else {
            if (name.getText().matches("[a-zA-Z]+"))
            {
                status = true;
                System.out.println("Imie poprawne");
                goodValidation(name);
            }
            else
            {
                status = false;
                System.out.println("Imie niepoprawne");
                errorValidation(name);
            }
            if (surname.getText().toString().matches("[a-zA-Z]+"))
            {
                status = true;
                System.out.println("Nazwisko poprawne");
                goodValidation(surname);
            }
            else
            {
                status = false;
                System.out.println("Nazwisko niepoprawne");
                errorValidation(surname);
            }
            if (city.getText().matches("[A-Za-z]+"))
            {
                status = true;
                System.out.println("Miasto poprawne");
                goodValidation(city);
            }
            else
            {
                status = false;
                System.out.println("Miasto niepoprawne");
                errorValidation(city);
            }
            if (pesel.getText().matches("[0-9]*") && pesel.getText().length() == 11)
            {
                status = true;
                System.out.println("Pesel poprawny");
                goodValidation(pesel);
            }
            else
            {
                status = false;
                System.out.println("Pesel niepoprawny");
                errorValidation(pesel);
            }
            if (numberPhone.getText().matches("[0-9]*") && numberPhone.getText().length() == 9)
            {
                status = true;
                System.out.println("Telefon poprawny");
                goodValidation(numberPhone);
            }
            else
            {
                status = false;
                System.out.println("Telefon niepoprawny");
                errorValidation(numberPhone);
            }

            if (!status) {
                Alerts.createAlert(appWindow, addCourierButton, "WARNING", "POPRAW POLA");
            }
        }
    }

    void goodValidation(TextField name){
        name.getStyleClass().clear();
        name.getStyleClass().add("inputBox");
    }

    void errorValidation(TextField name){
        name.getStyleClass().clear();
        name.getStyleClass().add("inputBoxError");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        goodValidation(name);
        goodValidation(surname);
        goodValidation(street);
        goodValidation(city);
        goodValidation(pesel);
        goodValidation(numberPhone);
    }
}
