package main.java.features;

import com.sun.prism.paint.Color;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.chart.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import main.java.dao.PackagesDAO;
import main.java.entity.BarChartDTO;

import java.time.YearMonth;
import java.util.List;

public class Charts {
    //ObservableList<PieChart.Data> data,
    public static void createPieChart(AnchorPane createPlace, String title,
                                      int layoutX, int layoutY, int width, int height){

        List<Long> quantityByTypes = PackagesDAO.quantityOfPackagesType();

        ObservableList<PieChart.Data> data = FXCollections.observableArrayList(
                new PieChart.Data("DUŻA - "+quantityByTypes.get(0),quantityByTypes.get(0)),
                new PieChart.Data("MAŁA - "+quantityByTypes.get(1),quantityByTypes.get(1)),
                new PieChart.Data("ŚREDNIA - "+quantityByTypes.get(2),quantityByTypes.get(2))
        );

        PieChart pieChart = new PieChart();

        pieChart.setTitle(title);
        pieChart.setData(data);
        pieChart.setClockwise(true);
        pieChart.getStyleClass().add("pieChart");

        pieChart.setLayoutX(layoutX);
        pieChart.setLayoutY(layoutY);
        pieChart.setPrefWidth(width);
        pieChart.setPrefHeight(height);

        createPlace.getChildren().add(pieChart);
    }


    public static void createBarChart(AnchorPane createPlace, String legend, String month,
                                      int layoutX, int layoutY, int width, int height){
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Dzień");
        xAxis.setTickLabelFill(Paint.valueOf("white"));
        xAxis.setId("xAxis");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Ilość");
        yAxis.setTickLabelFill(Paint.valueOf("white"));
        yAxis.setId("yAxis");


        BarChart<String, Long> barChart = new BarChart(xAxis,yAxis);

        XYChart.Series dataXY = new XYChart.Series();
        dataXY.setName(legend);

        System.out.println(month);
        List<BarChartDTO> listTest = PackagesDAO.quantityOfPackagesMonthly(month);

        int j = 1;

        YearMonth yearMonth = YearMonth.of(2021,Integer.valueOf(month));

        for(int i = 0; i < listTest.size(); i++) {
            for( ; j <= yearMonth.lengthOfMonth(); j++){
                if(listTest.get(i).getDay().equals(String.valueOf(j))) {

                    dataXY.getData().add(new XYChart.Data(listTest.get(i).getDay(),
                            listTest.get(i).getQuantity()));
                    j = Integer.valueOf(listTest.get(i).getDay()) + 1;

                    if(i!= listTest.size() - 1) {
                        break;
                    }

                }
                else {
                    dataXY.getData().add(new XYChart.Data(String.valueOf(j), 0));
                }
            }
        }

        barChart.getData().add(dataXY);

        barChart.setLayoutX(layoutX);
        barChart.setLayoutY(layoutY);
        barChart.setPrefWidth(width);
        barChart.setPrefHeight(height);
        barChart.getStyleClass().add("barChart");

        createPlace.getChildren().add(barChart);
    }
}
