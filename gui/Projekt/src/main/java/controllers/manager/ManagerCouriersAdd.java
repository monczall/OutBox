package main.java.controllers.manager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import main.java.controllers.auth.Encryption;
import main.java.dao.UserInfosDAO;
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
    private TextField email;

    @FXML
    private TextField numberPhone,voivodeship;

    @FXML
    private AnchorPane appWindow;

    @FXML
    private Button addCourierButton;

    @FXML
    private Pane alertPane;

    @FXML
    private ComboBox<String> regionName;

    private ObservableList<String> regions = FXCollections.observableArrayList("Rzeszów","Rzeszów Rejtana");

    public void addCourier(MouseEvent mouseEvent) {
        boolean status = true;
        if(name.getText().toString().equals("") ||
                surname.getText().toString().equals("") ||
                street.getText().toString().equals("") ||
                city.getText().toString().equals("") ||
                //pesel.getText().toString().equals("") ||
                email.getText().toString().equals("") ||
                voivodeship.getText().toString().equals("") ||
                numberPhone.getText().toString().equals("")){
            Alerts.createAlert(appWindow, addCourierButton,"WARNING","UZUPEŁNIJ WSZYSTKIE POLA");
        }
        else {
            if (email.getText().matches("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}"))
            {
                System.out.println("mail poprawne");
                goodValidation(email);
            }
            else
            {
                status = false;
                System.out.println("mail niepoprawne");
                errorValidation(email);
            }
            if (name.getText().matches("[a-zA-Z]+"))
            {
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
                System.out.println("Miasto poprawne");
                goodValidation(city);
            }
            else
            {
                status = false;
                System.out.println("Miasto niepoprawne");
                errorValidation(city);
            }
            if (street.getText().matches("[A-Za-z]{0,2}\\.?\\s?[A-Za-z]{2,40}\\s?\\-?[A-Za-z]{0,40}?\\s?" +
                    "\\-?[A-Za-z]{0,40}?\\s[0-9]{1,4}\\s?[A-Za-z]?\\s?\\/?\\s?[0-9]{0,5}"))
            {
                System.out.println("Ulica poprawna");
                goodValidation(street);
            }
            else {
                status = false;
                System.out.println("Ulica niepoprawna");
                errorValidation(street);
            }
            /*if (pesel.getText().matches("[0-9]*") && pesel.getText().length() == 11)
            {
                System.out.println("Pesel poprawny");
                goodValidation(pesel);
            }
            else
            {
                status = false;
                System.out.println("Pesel niepoprawny");
                errorValidation(pesel);
            }*/
            if (numberPhone.getText().matches("[0-9]*") && numberPhone.getText().length() == 9)
            {
                System.out.println("Telefon poprawny");
                goodValidation(numberPhone);
            }
            else
            {
                status = false;
                System.out.println("Telefon niepoprawny");
                errorValidation(numberPhone);
            }
            if (voivodeship.getText().matches("[a-zA-Z]+"))
            {
                System.out.println("Województwo poprawne");
                goodValidation(voivodeship);
            }
            else
            {
                status = false;
                System.out.println("Województwo niepoprawne");
                errorValidation(voivodeship);
            }
            if (!status) {
                Alerts.createAlert(appWindow, addCourierButton, "WARNING", "POPRAW POLA");
            }
            else{
                String nameString = name.getText();
                String emailString = email.getText();
                String phoneString = numberPhone.getText();
                String streetString = street.getText();
                String surnameString = surname.getText();
                String cityString = city.getText();
                String voivodeshipString = voivodeship.getText();
                String password = Encryption.encrypt("test");
                String role = "Kurier";

                //System.out.println("name: " + nameString + "surname: " + surnameString + "email: " + emailString +
                // "phone: " + phoneString + "street: " + streetString + "city: " + cityString + "wojewodztow: " +
                // voivodeshipString + "password: " + password);



                UserInfosDAO.addUserInfo(nameString, surnameString, emailString, phoneString, streetString, cityString,
                        voivodeshipString, password, role);
                alertPane.setVisible(true);
            }
        }
    }

    void goodValidation(TextField name){
        name.getStyleClass().clear();
        name.getStyleClass().add("inputBoxCourier");
    }

    void errorValidation(TextField name){
        name.getStyleClass().clear();
        name.getStyleClass().add("inputBoxCourierError");
    }

    @FXML
    void confirmButton(MouseEvent event) {
        name.setText("");
        surname.setText("");
        street.setText("");
        city.setText("");
        email.setText("");
        voivodeship.setText("");
        numberPhone.setText("");
        alertPane.setVisible(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        goodValidation(name);
        goodValidation(surname);
        goodValidation(street);
        goodValidation(city);
        //goodValidation(pesel);
        goodValidation(numberPhone);
        goodValidation(voivodeship);
        goodValidation(email);


        regionName.setItems(regions);
        regionName.setValue(regions.get(0));
    }
}
