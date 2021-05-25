package main.java.features;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import main.java.App;
import main.java.dao.PackagesDAO;
import main.java.entity.BarChartDTO;

import java.time.YearMonth;
import java.util.List;

public class Charts {

    //ObservableList<PieChart.Data> data,

    /**
     * Method that creates a pie chart it takes a six arguments
     * First is the place where chart will be created
     * Second is the title of chart
     * Third and fourth are layout of chart
     * Fifth and sixth is the width and height of chart
     */
    public static void createPieChart(PieChart pieChart){

        List<Long> quantityByTypes = PackagesDAO.quantityOfPackagesType();

        ObservableList<PieChart.Data> data = FXCollections.observableArrayList(
                new PieChart.Data(App.getLanguageProperties("small") + " - "+quantityByTypes.get(0),quantityByTypes.get(0)),
                new PieChart.Data( App.getLanguageProperties("medium") + " - "+quantityByTypes.get(1),quantityByTypes.get(1)),
                new PieChart.Data(App.getLanguageProperties("big") + " - "+quantityByTypes.get(2),quantityByTypes.get(2))
        );

        pieChart.setData(data);
        pieChart.setClockwise(true);

    }


    public static void createBarChart(BarChart<String,Long> barChart, String month){

        barChart.setTitle(App.getLanguageProperties("numberOfPackagesInMonth"));

        XYChart.Series dataXY = new XYChart.Series();
        dataXY.setName(App.getLanguageProperties("numberOfPackagesChart"));

        barChart.getXAxis().setLabel(App.getLanguageProperties("dayChart"));

        barChart.getYAxis().setLabel(App.getLanguageProperties("quantityOfPackages"));

        List<BarChartDTO> listTest = PackagesDAO.quantityOfPackagesMonthly(month);

        int j = 1;

        YearMonth yearMonth = YearMonth.of(2021,Integer.valueOf(month));

        for(int i = 0; i < listTest.size(); i++) {
            for( ; j <= yearMonth.lengthOfMonth(); j++){
                if(Long.valueOf(listTest.get(i).getDay()) == j) {
                    dataXY.getData().add(new XYChart.Data(String.valueOf(Long.valueOf(listTest.get(i).getDay())),
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
    }
}
