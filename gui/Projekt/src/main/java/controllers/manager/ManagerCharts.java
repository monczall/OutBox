package main.java.controllers.manager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import main.java.features.Animations;
import main.java.features.Charts;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class ManagerCharts implements Initializable {

    @FXML
    private AnchorPane courierOptions;

    @FXML
    private ToggleButton barChartBtn;

    @FXML
    private ToggleButton pieChartBtn;

    @FXML
    private AnchorPane barCharPane;

    @FXML
    private BarChart<String, Long> barChart;

    @FXML
    private AnchorPane pieChartPane;

    @FXML
    private PieChart pieChart;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ToggleGroup group = new ToggleGroup();
        barChartBtn.setToggleGroup(group);
        pieChartBtn.setToggleGroup(group);

        barChartBtn.setSelected(true);
        barChartBtn.setDisable(true);
        barChartBtn.setOpacity(1);

        pieChartPane.setTranslateX(-1600);

        barChart.getXAxis().setLabel("Dzień");
        barChart.getXAxis().setId("xAxis");

        barChart.getYAxis().setLabel("Ilość przysyłek");
        barChart.getYAxis().setId("xAxis");

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM");

        LocalDateTime now = LocalDateTime.now();

        Charts.createBarChart(barChart, dateTimeFormatter.format(now));

        Charts.createPieChart(pieChart, dateTimeFormatter.format(now));

    }


    @FXML
        // bar - 0 pie - -1600
    void changeBarChart(ActionEvent event) {
        Animations.moveByX(barCharPane, -1600, 0.5);
        Animations.moveByX(pieChartPane, -1600, 0.5);
        barChartBtn.setDisable(true);
        barChartBtn.setOpacity(1);
        pieChartBtn.setDisable(false);
    }

    @FXML
    void changePieChart(ActionEvent event) {
        Animations.moveByX(pieChartPane, +1600, 0.5);
        Animations.moveByX(barCharPane, +1600, 0.5);
        pieChartBtn.setDisable(true);
        pieChartBtn.setOpacity(1);
        barChartBtn.setDisable(false);
    }
}
