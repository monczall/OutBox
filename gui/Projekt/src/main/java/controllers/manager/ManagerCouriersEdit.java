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
    private VBox packageLayout;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        regionName.setItems(statusObservable);
    }
}
