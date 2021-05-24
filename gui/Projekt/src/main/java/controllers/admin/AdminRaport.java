package main.java.controllers.admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import main.java.App;
import main.java.features.Alerts;
import main.java.features.PdfGenerator;

import java.time.LocalDate;
import java.util.Date;

public class AdminRaport {


    @FXML
    private Button raportButton;

    @FXML
    private DatePicker dateFrom;

    @FXML
    private DatePicker dateTo;

    @FXML
    private AnchorPane RightPaneAnchorPane;

    /**
     * Method that generate report to PDF file from date to date choose from DataPicker
     * @param actionEvent action event
     */

    public void generateRaport(ActionEvent actionEvent) {

        try {
            if (dateFrom.getValue() == null || dateTo.getValue() == null) {
                Alerts.createCustomAlert(RightPaneAnchorPane, raportButton, "WARNING",
                        App.getLanguageProperties("adminGeneratePDFFailure"), 370, 86, "alertFailure");
            } else {
                LocalDate startDataValue = dateFrom.getValue();
                LocalDate endDataValue = dateTo.getValue().plusDays(1);

                Date startValue = java.sql.Date.valueOf(startDataValue);
                Date endValue = java.sql.Date.valueOf(endDataValue);

                if (startDataValue != null && endDataValue != null) {

                    PdfGenerator.createPdf(startValue, endValue);
                    Alerts.createCustomAlert(RightPaneAnchorPane, raportButton, "CHECK",
                            App.getLanguageProperties("adminGeneratePDF"), 350, 86, "alertSuccess");
                }
            }
        } catch (Exception e) {
            System.out.println("Błąd przy tworzeniu raportu PDF");
            e.printStackTrace();
        }


    }

    /**
     * Method that generate report to PDF file from last day
     * @param actionEvent action event
     */
    public void raportLastDay(ActionEvent actionEvent) {


        LocalDate startDataValue = LocalDate.now().minusDays(1);
        LocalDate endDataValue = startDataValue.plusDays(1);

        Date startValue = java.sql.Date.valueOf(startDataValue);
        Date endValue = java.sql.Date.valueOf(endDataValue);

        try {
            PdfGenerator.createPdf(startValue, endValue);
            Alerts.createCustomAlert(RightPaneAnchorPane, raportButton, "CHECK",
                    App.getLanguageProperties("adminGeneratePDF"), 350, 86, "alertSuccess");
        } catch (Exception e) {
            System.out.println("Błąd przy tworzeniu raportu PDF");
            e.printStackTrace();
        }

    }

    /**
     * Method that generate report to PDF file from last month
     * @param actionEvent action event
     */
    public void raportLastMonth(ActionEvent actionEvent) {
        LocalDate startDataValue = LocalDate.now();
        LocalDate endDataValue = startDataValue.minusMonths(1).plusDays(1);

        Date startValue = java.sql.Date.valueOf(startDataValue);
        Date endValue = java.sql.Date.valueOf(endDataValue);

        try {
            PdfGenerator.createPdf(startValue, endValue);
            Alerts.createCustomAlert(RightPaneAnchorPane, raportButton, "CHECK",
                    App.getLanguageProperties("adminGeneratePDF"), 350, 86, "alertSuccess");
        } catch (Exception e) {
            System.out.println("Błąd przy tworzeniu raportu PDF");
            e.printStackTrace();
        }


    }

    /**
     * Method that generate report to PDF file from last week
     * @param actionEvent action event
     */
    public void raportLastWeek(ActionEvent actionEvent) {
        LocalDate startDataValue = LocalDate.now();
        LocalDate endDataValue = startDataValue.minusDays(6);

        Date startValue = java.sql.Date.valueOf(startDataValue);
        Date endValue = java.sql.Date.valueOf(endDataValue);

        try {
            PdfGenerator.createPdf(startValue, endValue);
            Alerts.createCustomAlert(RightPaneAnchorPane, raportButton, "CHECK",
                    App.getLanguageProperties("adminGeneratePDF"), 350, 86, "alertSuccess");
        } catch (Exception e) {
            System.out.println("Błąd przy tworzeniu raportu PDF");
            e.printStackTrace();
        }


    }
}
