package main.java.controllers.manager;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import main.java.App;
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
    private Label textDateStart;

    @FXML
    private Label textDateEnd;

    @FXML
    private Label textDateDays;

    @FXML
    private Label textOneDate;

    String confirmText = App.getLanguageProperties("confirmRaportFromDay");

    @FXML
    public void raportCustom(MouseEvent event) {
        if(startData.getValue() != null && endData.getValue() == null){
            LocalDate startDataValue = startData.getValue();
            LocalDate today = LocalDate.now();

            long daysBetween = DAYS.between(startDataValue, today);
            long daysFuture = DAYS.between(today, startDataValue);

            if(daysBetween >= 0 && daysFuture <=0) {

                textOneDate.setText(confirmText + startDataValue);
                oneDayRaport.setVisible(true);
            }
            else{
                Alerts.createAlert(appWindow, createCustomRaportButton,"WARNING",App.getLanguageProperties("incorrectDate"));
            }
        }
        else if (startData.getValue() == null || endData.getValue() == null) {
            Alerts.createAlert(appWindow, createCustomRaportButton,"WARNING",App.getLanguageProperties("chooseTimePeriod"));
        }
        else{
            LocalDate startDataValue = startData.getValue();
            LocalDate endDataValue = endData.getValue();
            LocalDate today = LocalDate.now();

            long daysBetween = DAYS.between(startDataValue, endDataValue);
            long daysFuture = DAYS.between(today, endDataValue);

            if(daysBetween == 0){
                textOneDate.setText(confirmText + startDataValue);
                oneDayRaport.setVisible(true);
            }
            else if(daysBetween < 0 || daysFuture > 0){
               Alerts.createAlert(appWindow, createCustomRaportButton,"WARNING",App.getLanguageProperties("incorrectTimeRange"));
            }
            else{
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

        textOneDate.setText(confirmText + yesterday.toString());
        oneDayRaport.setVisible(true);
    }

    @FXML
    public void raportLastMonth(MouseEvent event) {
        LocalDate today = LocalDate.now();
        LocalDate lastMonth = today.minusDays(30);

        textOneDate.setText(App.getLanguageProperties("confirmRaportLastMonth") +
                lastMonth.toString());
        oneDayRaport.setVisible(true);
    }

    @FXML
    public void raportLastWeek(MouseEvent event) {
        LocalDate today = LocalDate.now();
        LocalDate lastWeek = today.minusDays(7);

        textOneDate.setText(App.getLanguageProperties("confirmRaportLastWeek") +
                lastWeek.toString());
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
