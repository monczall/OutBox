package main.java.controllers.manager;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import main.java.App;
import main.java.SceneManager;
import main.java.features.Alerts;
import main.java.features.PdfGeneratorManager;

import java.io.File;
import java.time.LocalDate;
import java.util.Date;

import static java.time.temporal.ChronoUnit.DAYS;

public class ManagerRaports {

    boolean display = false;
    String confirmText = App.getLanguageProperties("confirmRaportFromDay");
    LocalDate today;
    LocalDate past;
    @FXML
    private DatePicker startData;
    @FXML
    private DatePicker endData;
    @FXML
    private TextField fileName;
    @FXML
    private Button createCustomRaportButton;
    @FXML
    private AnchorPane appWindow, infoConfirmRaport, oneDayRaport;
    @FXML
    private Label textDateStart;
    @FXML
    private Label textDateEnd;
    @FXML
    private Label textDateDays;
    @FXML
    private Label textOneDate;

    /**
     * date validation and report generation selection
     */
    @FXML
    public void raportCustom(MouseEvent event) {
        if (startData.getValue() != null && endData.getValue() == null) {
            past = startData.getValue();
            today = LocalDate.now();
            display = true;

            long daysBetween = DAYS.between(past, today);
            long daysFuture = DAYS.between(today, past);

            if (daysBetween >= 0 && daysFuture <= 0) {
                textOneDate.setText(confirmText + past);
                today = past;
                oneDayRaport.setVisible(true);
            } else {
                Alerts.createAlert(appWindow, createCustomRaportButton, "WARNING", App.getLanguageProperties("incorrectDate"));
            }
        } else if (startData.getValue() == null || endData.getValue() == null) {
            Alerts.createAlert(appWindow, createCustomRaportButton, "WARNING", App.getLanguageProperties("chooseTimePeriod"));
        } else {
            LocalDate startDataValue = startData.getValue();
            LocalDate endDataValue = endData.getValue();
            today = LocalDate.now();

            long daysBetween = DAYS.between(startDataValue, endDataValue);
            long daysFuture = DAYS.between(today, endDataValue);

            if (daysBetween == 0) {
                textOneDate.setText(confirmText + startDataValue);
                today = past;
                display = true;
                oneDayRaport.setVisible(true);
            } else if (daysBetween < 0 || daysFuture > 0) {
                Alerts.createAlert(appWindow, createCustomRaportButton, "WARNING", App.getLanguageProperties("incorrectTimeRange"));
            } else {
                display = false;
                textDateStart.setText(startDataValue.toString());
                textDateEnd.setText(endDataValue.toString());
                textDateDays.setText(daysBetween + 1 + " ");
                today = endDataValue;
                past = startDataValue;
                infoConfirmRaport.setVisible(true);

            }
        }
    }

    /**
     * generating a report from the last day
     */
    @FXML
    public void raportLastDay(MouseEvent event) {
        today = LocalDate.now().plusDays(1);
        past = today.minusDays(1);
        display = true;
        textOneDate.setText(confirmText + past.toString());
        oneDayRaport.setVisible(true);
    }

    /**
     * generating a report for the last 30 days
     */
    @FXML
    public void raportLastMonth(MouseEvent event) {
        today = LocalDate.now().plusDays(1);
        past = today.minusMonths(1).minusDays(1);
        display = false;
        textOneDate.setText(App.getLanguageProperties("confirmRaportLastMonth") +
                past.toString());
        oneDayRaport.setVisible(true);
    }

    /**
     * generating a report for the last 7 days
     */
    @FXML
    public void raportLastWeek(MouseEvent event) {
        today = LocalDate.now().plusDays(1);
        past = today.minusWeeks(1);
        display = false;
        textOneDate.setText(App.getLanguageProperties("confirmRaportLastWeek") +
                past.toString());
        oneDayRaport.setVisible(true);
    }

    /**
     * generating a report from a selected date range
     */
    public void confirmRaport(MouseEvent mouseEvent) {

        String pathFile;

        if (validateFileName()) {
            Alerts.createAlert(appWindow, createCustomRaportButton, "WARNING",
                    App.getLanguageProperties("nameFile"));
        } else {
            File selectedDirectory = filePathSelection();
            if (selectedDirectory != null) {

                if (selectedDirectory.toString().endsWith("\\")) {
                    pathFile = selectedDirectory + fileName.getText() + ".pdf";
                } else {
                    pathFile = selectedDirectory + "\\" + fileName.getText() + ".pdf";
                }
                File f = new File(pathFile);
                if (f.exists() && f.isFile()) {
                    Alerts.createAlert(appWindow, createCustomRaportButton, "WARNING",
                            App.getLanguageProperties("fileExists"));
                } else {
                    Date startValue = java.sql.Date.valueOf(past);
                    Date endValue = java.sql.Date.valueOf(today.plusDays(1));


                    try {
                        PdfGeneratorManager.createPdf(startValue, endValue, display, pathFile);
                        Alerts.createAlert(appWindow, createCustomRaportButton, "WARNING",
                                App.getLanguageProperties("reportSuccess"));
                    } catch (Exception e) {
                        Alerts.createAlert(appWindow, createCustomRaportButton, "WARNING",
                                App.getLanguageProperties("raportError"));
                        e.printStackTrace();
                    }
                }
            } else {
                Alerts.createAlert(appWindow, createCustomRaportButton, "WARNING",
                        App.getLanguageProperties("fileSaveLocationNotSelected"));
            }
        }

        infoConfirmRaport.setVisible(false);
    }

    /**
     * checks that a file name has been given
     *
     * @return boolena
     */
    boolean validateFileName() {
        return fileName.getText().isEmpty();
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
    public void confirmOneDayReport(MouseEvent mouseEvent) {

        String pathFile;

        if (validateFileName()) {
            Alerts.createAlert(appWindow, createCustomRaportButton, "WARNING",
                    App.getLanguageProperties("nameFile"));
        } else {
            File selectedDirectory = filePathSelection();
            if (selectedDirectory != null) {

                if (selectedDirectory.toString().endsWith("\\")) {
                    pathFile = selectedDirectory + fileName.getText() + ".pdf";
                } else {
                    pathFile = selectedDirectory + "\\" + fileName.getText() + ".pdf";
                }
                File f = new File(pathFile);
                if (f.exists() && f.isFile()) {
                    Alerts.createAlert(appWindow, createCustomRaportButton, "WARNING",
                            App.getLanguageProperties("fileExists"));
                } else {

                    Date startValue = java.sql.Date.valueOf(past);
                    Date endValue = java.sql.Date.valueOf(today);


                    try {
                        PdfGeneratorManager.createPdf(startValue, endValue, display, pathFile);
                        Alerts.createAlert(appWindow, createCustomRaportButton, "WARNING",
                                App.getLanguageProperties("reportSuccess"));
                    } catch (Exception e) {
                        Alerts.createAlert(appWindow, createCustomRaportButton, "WARNING",
                                App.getLanguageProperties("raportError"));
                        e.printStackTrace();
                    }
                }
            } else {
                Alerts.createAlert(appWindow, createCustomRaportButton, "WARNING",
                        App.getLanguageProperties("fileSaveLocationNotSelected"));
            }
        }
        oneDayRaport.setVisible(false);
    }

    /**
     * cancellation of report generation
     */
    public void cancelOneDayRaport(MouseEvent mouseEvent) {
        oneDayRaport.setVisible(false);
    }

    /**
     * Choose where to save the file
     *
     * @return File path
     */
    public File filePathSelection() {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle(App.getLanguageProperties("titleSaveRaport"));
        File defaultDirectory = new File("c:/");
        chooser.setInitialDirectory(defaultDirectory);
        File selectedDirectory = chooser.showDialog(SceneManager.getStage());

        return selectedDirectory;

    }
}
