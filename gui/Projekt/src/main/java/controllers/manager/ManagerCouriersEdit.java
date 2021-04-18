package main.java.controllers.manager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import main.java.features.Alerts;

import java.net.URL;
import java.util.ResourceBundle;

public class ManagerCouriersEdit implements Initializable {

    @FXML
    private AnchorPane appWindow;

    @FXML
    private TextField name;

    @FXML
    private TextField surname;

    @FXML
    private TextField pesel;

    @FXML
    private Button findCourierButton;

    @FXML
    private Button saveEditCourierButton;

    @FXML
    private VBox packageLayout;

    @FXML
    private TextField nameInput;

    @FXML
    private TextField surnameInput;

    @FXML
    private TextField peselInput;

    @FXML
    private TextField cityInput;

    @FXML
    private TextField streetInput;

    @FXML
    private TextField inputNumber;

    @FXML
    private TextField inputVoivodeship;

    @FXML
    private TextField inputEmail;

    @FXML
    private ComboBox<String> regionName;

    private ObservableList<String> statusObservable = FXCollections.observableArrayList("Rzeszów centrum","Rzeszów Rejtana");

    public void findCourier(MouseEvent mouseEvent) {

        if(name.getText().toString().equals("") &&
                surname.getText().toString().equals("") &&
                pesel.getText().toString().equals("")){
            Alerts.createAlert(appWindow, findCourierButton, "WARNING", "PODAJ JAKIŚ PARAMETR");
        }
        else
        {
            if(pesel.getText().toString().equals("")){
                System.out.println("Pesel pusty");
            }
            else if (pesel.getText().matches("[0-9]*") && pesel.getText().length() == 11)
            {
                System.out.println("Pesel poprawny");
            }
            else
            {
                System.out.println("Pesel niepoprawny");
                Alerts.createAlert(appWindow, findCourierButton, "WARNING", "POPRAW DANE");
            }
        }
    }

    @FXML
    void saveEditCourier(MouseEvent event) {
        boolean status = true;
        if(nameInput.getText().toString().equals("") ||
                surnameInput.getText().toString().equals("") ||
                streetInput.getText().toString().equals("") ||
                cityInput.getText().toString().equals("") ||
                peselInput.getText().toString().equals("") ||
                inputEmail.getText().toString().equals("") ||
                inputVoivodeship.getText().toString().equals("") ||
                inputNumber.getText().toString().equals("")){
            Alerts.createAlert(appWindow, saveEditCourierButton,"WARNING","UZUPEŁNIJ WSZYSTKIE POLA");
        }
        else {
            if (inputEmail.getText().matches("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}"))
            {
                System.out.println("mail poprawne");
            }
            else
            {
                status = false;
                System.out.println("mail niepoprawne");
            }
            if (nameInput.getText().matches("[a-zA-Z]+"))
            {
                System.out.println("Imie poprawne");
            }
            else
            {
                status = false;
                System.out.println("Imie niepoprawne");
            }
            if (surnameInput.getText().toString().matches("[a-zA-Z]+"))
            {
                System.out.println("Nazwisko poprawne");
            }
            else
            {
                status = false;
                System.out.println("Nazwisko niepoprawne");
            }
            if (cityInput.getText().matches("[A-Za-z]+"))
            {
                System.out.println("Miasto poprawne");
            }
            else
            {
                status = false;
                System.out.println("Miasto niepoprawne");
            }
            if (streetInput.getText().matches("[A-Za-z]{0,2}\\.?\\s?[A-Za-z]{2,40}\\s?\\-?[A-Za-z]{0,40}?\\s?\\-?[A-Za-z]{0,40}?\\s[0-9]{1,4}\\s?[A-Za-z]?\\s?\\/?\\s?[0-9]{0,5}"))
            {
                System.out.println("Ulica poprawna");
            }
            else {
                status = false;
                System.out.println("Ulica niepoprawna");
            }
            if (peselInput.getText().matches("[0-9]*") && peselInput.getText().length() == 11)
            {
                System.out.println("Pesel poprawny");
            }
            else
            {
                status = false;
                System.out.println("Pesel niepoprawny");
            }
            if (inputNumber.getText().matches("[0-9]*") && inputNumber.getText().length() == 9)
            {
                System.out.println("Telefon poprawny");
            }
            else
            {
                status = false;
                System.out.println("Telefon niepoprawny");
            }
            if (inputVoivodeship.getText().matches("[a-zA-Z]+"))
            {
                System.out.println("Województwo poprawne");
            }
            else
            {
                status = false;
                System.out.println("Województwo niepoprawne");
            }
            if (!status) {
                Alerts.createAlert(appWindow, saveEditCourierButton, "WARNING", "POPRAW POLA");
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        regionName.setItems(statusObservable);
    }
}
