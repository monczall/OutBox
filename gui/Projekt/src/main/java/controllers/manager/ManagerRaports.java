package main.java.controllers.manager;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import main.java.features.Alerts;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import java.time.temporal.ChronoUnit;

import static java.time.temporal.ChronoUnit.DAYS;

public class ManagerRaports implements Initializable {

    @FXML
    private DatePicker startData;

    @FXML
    private DatePicker endData;

    @FXML
    private Button createCustomRaportButton;

    @FXML
    private AnchorPane appWindow,infoConfirmRaport,oneDayRaport;

    @FXML
    private TextField days;

    @FXML
    private Label textDateStart;

    @FXML
    private Label textDateEnd;

    @FXML
    private Label textDateDays;

    @FXML
    private Label textOneDate;

    String confirmText = "Potwierdzenie wygenerowania raportu z dnia ";
    /*
    LocalDate today = LocalDate.now()
    LocalDate yesterday = today.minusDays(1);
    // Duration oneDay = Duration.between(today, yesterday); // throws an exception
    Duration.between(today.atStartOfDay(), yesterday.atStartOfDay()).toDays() // another option
     */
    @FXML
    public void raportCustom(MouseEvent event) {
        if(startData.getValue() != null && endData.getValue() == null){
            LocalDate startDataValue = startData.getValue();
            LocalDate today = LocalDate.now();
            long daysBetween = DAYS.between(startDataValue, today);
            long daysFuture = DAYS.between(today, startDataValue);

            if(daysBetween >= 0 && daysFuture <=0) {
                System.out.println("Generowanie raportu z dnia " + startDataValue);
                textOneDate.setText(confirmText + startDataValue);
                oneDayRaport.setVisible(true);
            }
            else{
                Alerts.createAlert(appWindow, createCustomRaportButton,"WARNING","NIEPRAWIDŁOWA DATA");
            }
        }
        else if (startData.getValue() == null || endData.getValue() == null) {

            Alerts.createAlert(appWindow, createCustomRaportButton,"WARNING","WYBIERZ PRZEDZIAŁ CZASU");
        }
        else{
            LocalDate startDataValue = startData.getValue();
            LocalDate endDataValue = endData.getValue();
            LocalDate today = LocalDate.now();

            long daysBetween = DAYS.between(startDataValue, endDataValue);
            long daysFuture = DAYS.between(today, endDataValue);

            if(daysBetween == 0){
                System.out.println("Generowanie raportu z dnia " + startDataValue);
                textOneDate.setText(confirmText + startDataValue);
                oneDayRaport.setVisible(true);
            }
            else if(daysBetween < 0 || daysFuture > 0){
                Alerts.createAlert(appWindow, createCustomRaportButton,"WARNING","BŁĘDNY PRZEDZIAŁ CZASU");
            }
            else{
                System.out.println("Od: " + startDataValue + "\nDo: " + endDataValue);
                System.out.println("Generowanie raportu z " + daysBetween +" dni");

                textDateStart.setText(startDataValue.toString());
                textDateEnd.setText(endDataValue.toString());
                textDateDays.setText(daysBetween+" ");

                infoConfirmRaport.setVisible(true);
            }
        }
    }


    @FXML
    public void raportLastDay(MouseEvent event) {
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);
        System.out.println("raportLastDay: " + yesterday);
        textOneDate.setText(confirmText + yesterday.toString());
        oneDayRaport.setVisible(true);
    }

    @FXML
    public void raportLastMonth(MouseEvent event) {
        LocalDate today = LocalDate.now();
        LocalDate lastMonth = today.minusDays(30);
        System.out.println("raportLastMonth: " + lastMonth);
        textOneDate.setText("Potwierdzenie wygenerowania raportu z ostatniego miesiąca, od dnia: " + lastMonth.toString());
        oneDayRaport.setVisible(true);
    }

    @FXML
    public void raportLastWeek(MouseEvent event) {
        LocalDate today = LocalDate.now();
        LocalDate lastWeek = today.minusDays(7);
        System.out.println("raportLastWeek: " + lastWeek);
        textOneDate.setText("Potwierdzenie wygenerowania raportu z ostatniego tygodnia, od dnia: " + lastWeek.toString());
        oneDayRaport.setVisible(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void confirmRaport(MouseEvent mouseEvent) {
        //here generate raport
    }

    public void cancelRaport(MouseEvent mouseEvent) {
        infoConfirmRaport.setVisible(false);
    }

    public void confirmOneDayRaport(MouseEvent mouseEvent) {
        //here generate raport
    }

    public void cancelOneDayRaport(MouseEvent mouseEvent) {
        oneDayRaport.setVisible(false);
    }
}
