package main.java.controllers.admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
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



    public void generateRaport(ActionEvent actionEvent) {

        LocalDate startDataValue = dateFrom.getValue();
        LocalDate endDataValue = dateTo.getValue().plusDays(1);

        Date startValue = java.sql.Date.valueOf(startDataValue);
        Date endValue = java.sql.Date.valueOf(endDataValue);

        try {
            PdfGenerator.createPdf(startValue, endValue);
        } catch (Exception e) {
            System.out.println("Błąd przy tworzeniu raportu PDF");
            e.printStackTrace();
        }

    }
}
