package main.java.controllers.manager;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.input.MouseEvent;
import main.java.features.Alerts;

import java.net.URL;
import java.util.ResourceBundle;

public class ManagerRaports implements Initializable {

    @FXML
    private DatePicker startData;

    @FXML
    private DatePicker endData;

    @FXML
    public void raportCustom(MouseEvent event) {
        System.out.println("raportCustom");
        if (startData.getValue() == null || endData.getValue() == null) {
           // Alerts.createAlert(appWindow, btnNextTime,"WARNING","WYBIERZ PRZEDZIAŁ CZASU");
        }
    }

    @FXML
    public void raportLastDay(MouseEvent event) {
        System.out.println("raportLastDay");
    }

    @FXML
    public void raportLastMonth(MouseEvent event) {
        System.out.println("raportLastMonth");
    }

    @FXML
    public void raportLastWeek(MouseEvent event) {
        System.out.println("raportLastWeek");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
