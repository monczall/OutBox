package main.java.controllers.manager;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import main.java.App;
import main.java.features.Alerts;
import main.java.features.PdfGeneratorManager;

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

    boolean display = false;

    String confirmText = App.getLanguageProperties("confirmRaportFromDay");

    LocalDate today;
    LocalDate past;

    /**
     * date validation and report generation selection
     */
    @FXML
    public void raportCustom(MouseEvent event) {
        if(startData.getValue() != null && endData.getValue() == null){
            past = startData.getValue();
            today = LocalDate.now();
            display=true;

            long daysBetween = DAYS.between(past, today);
            long daysFuture = DAYS.between(today, past);

            if(daysBetween >= 0 && daysFuture <=0) {
                System.out.println();
                textOneDate.setText(confirmText + past);
                today = past;
                System.out.println(today+ " : " + past);
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
                today=past;
                display=true;
                oneDayRaport.setVisible(true);
            }
            else if(daysBetween < 0 || daysFuture > 0){
               Alerts.createAlert(appWindow, createCustomRaportButton,"WARNING",App.getLanguageProperties("incorrectTimeRange"));
            }
            else{
                display=false;
                textDateStart.setText(startDataValue.toString());
                textDateEnd.setText(endDataValue.toString());
                textDateDays.setText(daysBetween+" ");

                infoConfirmRaport.setVisible(true);
            }
        }
    }

    /**
     * generating a report from the last day
     */
    @FXML
    public void raportLastDay(MouseEvent event) {
        today = LocalDate.now();
        past = today.minusDays(1);
        display=true;
        textOneDate.setText(confirmText + past.toString());
        oneDayRaport.setVisible(true);
    }

    /**
     * generating a report for the last 30 days
     */
    @FXML
    public void raportLastMonth(MouseEvent event) {
        today = LocalDate.now();
        past = today.minusDays(30);
        display=false;
        textOneDate.setText(App.getLanguageProperties("confirmRaportLastMonth") +
                past.toString());
        oneDayRaport.setVisible(true);
    }

    /**
     * generating a report for the last 7 days
     */
    @FXML
    public void raportLastWeek(MouseEvent event) {
        today = LocalDate.now();
        past = today.minusDays(7);
        display=false;
        textOneDate.setText(App.getLanguageProperties("confirmRaportLastWeek") +
                past.toString());
        oneDayRaport.setVisible(true);
    }

    /**
     * generating a report from a selected date range
     */
    public void confirmRaport(MouseEvent mouseEvent) {
        LocalDate startDataValue = startData.getValue();
        LocalDate endDataValue = endData.getValue().plusDays(1);

        Date startValue = java.sql.Date.valueOf(startDataValue);
        Date endValue = java.sql.Date.valueOf(endDataValue);

        try {
            PdfGeneratorManager.createPdf(startValue, endValue, display);
            Alerts.createAlert(appWindow, createCustomRaportButton,"WARNING",App.getLanguageProperties("reportSuccess"));
        } catch (Exception e) {
            Alerts.createAlert(appWindow, createCustomRaportButton,"WARNING",App.getLanguageProperties("raportError"));
            e.printStackTrace();
        }
        infoConfirmRaport.setVisible(false);
    }

    /**
     * cancellation of report generation
     */
    public void cancelRaport(MouseEvent mouseEvent) {
        infoConfirmRaport.setVisible(false);
    }

    /**
     * one day report generation confirmation
     */
    public void confirmOneDayRaport(MouseEvent mouseEvent) {

        System.out.println(today+ " : " + past);
        Date startValue = java.sql.Date.valueOf(past);
        Date endValue = java.sql.Date.valueOf(today);

        try {
            PdfGeneratorManager.createPdf(startValue, endValue, display);
            Alerts.createAlert(appWindow, createCustomRaportButton,"WARNING",App.getLanguageProperties("reportSuccess"));
        } catch (Exception e) {
            Alerts.createAlert(appWindow, createCustomRaportButton,"WARNING",App.getLanguageProperties("raportError"));
            e.printStackTrace();
        }
        oneDayRaport.setVisible(false);
    }

    /**
     * cancellation of report generation
     */
    public void cancelOneDayRaport(MouseEvent mouseEvent) {
        oneDayRaport.setVisible(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
