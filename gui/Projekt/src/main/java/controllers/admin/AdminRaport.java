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

import java.text.DateFormat;
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

    public void generateRaport(ActionEvent actionEvent) {

        LocalDate startDataValue = dateFrom.getValue();
        LocalDate endDataValue = dateTo.getValue().plusDays(1);

        Date startValue = java.sql.Date.valueOf(startDataValue);
        Date endValue = java.sql.Date.valueOf(endDataValue);

        try {
            PdfGenerator.createPdf(startValue, endValue);
            Alerts.createCustomAlert(RightPaneAnchorPane, raportButton,"CHECK",
                    App.getLanguageProperties("adminGeneratePDF"), 350, 86, "alertSuccess");
        } catch (Exception e) {
            System.out.println("Błąd przy tworzeniu raportu PDF");
            Alerts.createCustomAlert(RightPaneAnchorPane, raportButton,"WARNING",
                    App.getLanguageProperties("adminGeneratePDFFailure"), 370, 86, "alertFailure");
            e.printStackTrace();
        }

    }


    public void raportLastDay(ActionEvent actionEvent) {


        LocalDate startDataValue = LocalDate.now();
        LocalDate endDataValue = startDataValue;

        Date startValue = java.sql.Date.valueOf(startDataValue);
        Date endValue = java.sql.Date.valueOf(endDataValue);

        try {
            PdfGenerator.createPdf(startValue, endValue);
//            Alerts.createCustomAlert(RightPaneAnchorPane, raportButton,"CHECK",
//                    App.getLanguageProperties("adminGeneratePDF"), 350, 86, "alertSuccess");
        } catch (Exception e) {
            System.out.println("Błąd przy tworzeniu raportu PDF");
            Alerts.createCustomAlert(RightPaneAnchorPane, raportButton,"WARNING",
                    App.getLanguageProperties("adminGeneratePDFFailure"), 370, 86, "alertFailure");
            e.printStackTrace();
        }

    }

    @FXML
    public void raportLastMonth(MouseEvent event) {
        LocalDate startDataValue = LocalDate.now();
        LocalDate endDataValue = startDataValue;

        Date startValue = java.sql.Date.valueOf(startDataValue);
        Date endValue = java.sql.Date.valueOf(endDataValue);

        try {
            PdfGenerator.createPdf(startValue, endValue);
//            Alerts.createCustomAlert(RightPaneAnchorPane, raportButton,"CHECK",
//                    App.getLanguageProperties("adminGeneratePDF"), 350, 86, "alertSuccess");
        } catch (Exception e) {
            System.out.println("Błąd przy tworzeniu raportu PDF");
            Alerts.createCustomAlert(RightPaneAnchorPane, raportButton,"WARNING",
                    App.getLanguageProperties("adminGeneratePDFFailure"), 370, 86, "alertFailure");
            e.printStackTrace();
        }


    }

    @FXML
    public void raportLastWeek(MouseEvent event) {
        LocalDate today = LocalDate.now();
        LocalDate lastWeek = today.minusDays(7);


    }
}
