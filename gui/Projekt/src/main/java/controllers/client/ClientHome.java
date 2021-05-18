package main.java.controllers.client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.Axis;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;
import main.java.SceneManager;
import main.java.dao.PackageHistoryDAO;
import main.java.dao.PackagesDAO;
import main.java.entity.BarChartDTO;
import main.java.features.Charts;

import java.io.IOException;
import java.net.URL;
import java.time.MonthDay;
import java.time.Year;
import java.time.YearMonth;
import java.util.List;
import java.util.ResourceBundle;


public class ClientHome implements Initializable {

    @FXML
    private AnchorPane mainWindow;

    @FXML
    private PieChart pieChart;

    @FXML
    private BarChart<String, Long> barChart;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Charts.createBarChart(mainWindow,"LICZBA PRZESYŁEK", "04", 61, 14, 952, 644);

        Charts.createPieChart(mainWindow,"WYKRES ILOŚCI PRZESYŁEK",277,47,500,400);

    }

    @FXML
    void logout(ActionEvent event) {
        SceneManager.renderScene("login");
    }

    @FXML
    void viewHistory(ActionEvent event) throws IOException {
        SceneManager.loadScene("../../../resources/view/client/clientHistoryPackage.fxml", mainWindow);
    }

    @FXML
    void viewRegisterPackage(ActionEvent event) throws IOException {
        SceneManager.loadScene("../../../resources/view/client/clientRegisterPackage.fxml", mainWindow);
    }

    @FXML
    void viewSettings(ActionEvent event) throws IOException {
        SceneManager.loadScene("../../../resources/view/client/clientSettings.fxml", mainWindow);
    }

    @FXML
    void viewTrackPackage(ActionEvent event) throws IOException {
        SceneManager.loadScene("../../../resources/view/client/clientTrackPackage.fxml", mainWindow);
    }
}
