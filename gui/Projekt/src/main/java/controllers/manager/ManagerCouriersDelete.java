package main.java.controllers.manager;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import main.java.controllers.client.PackageItem;
import main.java.controllers.client.PackageTest;
import main.java.features.Alerts;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ManagerCouriersDelete implements Initializable {

    @FXML
    private AnchorPane appWindow;

    @FXML
    private Button findCourierButton;

    @FXML
    private TextField name;

    @FXML
    private TextField surname;

    @FXML
    private TextField pesel;


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

    }
}
