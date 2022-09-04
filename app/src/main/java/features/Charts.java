package main.java.features;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import main.java.App;
import main.java.dao.PackagesDAO;
import main.java.entity.BarChartDTO;
import main.java.entity.PieChartDTO;

import java.time.YearMonth;
import java.util.List;

public class Charts {

    /**
     * Method that populate a pie chart it has two arguments. takes information from current month
     *
     * @param pieChart pieChart from FXML that will be populated
     * @param month    month that will be used to query in DB
     */
    public static void createPieChart(PieChart pieChart, String month) {

        List<PieChartDTO> quantityByTypes = PackagesDAO.quantityOfPackagesType(month);
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();

        for (int i = 0; i < quantityByTypes.size(); i++) {
            if (quantityByTypes.get(i).getType().equals("mala")) {
                data.add(new PieChart.Data(App.getLanguageProperties("small") +
                        " - " + quantityByTypes.get(i).getQuantity(),
                        quantityByTypes.get(i).getQuantity()));
            } else if (quantityByTypes.get(i).getType().equals("srednia")) {
                data.add(new PieChart.Data(App.getLanguageProperties("medium") +
                        " - " + quantityByTypes.get(i).getQuantity(),
                        quantityByTypes.get(i).getQuantity()));
            } else {
                data.add(new PieChart.Data(App.getLanguageProperties("big") +
                        " - " + quantityByTypes.get(i).getQuantity(),
                        quantityByTypes.get(i).getQuantity()));
            }
        }

        pieChart.setData(data);
        pieChart.setClockwise(true);

    }


    /**
     * This method populate a barChart it takes information from current month
     *
     * @param barChart chart from FXML
     * @param month    month that will be used to query in DB
     */
    public static void createBarChart(BarChart<String, Long> barChart, String month) {

        barChart.setTitle(App.getLanguageProperties("numberOfPackagesInMonth"));

        XYChart.Series dataXY = new XYChart.Series();
        dataXY.setName(App.getLanguageProperties("numberOfPackagesChart"));

        barChart.getXAxis().setLabel(App.getLanguageProperties("dayChart"));

        barChart.getYAxis().setLabel(App.getLanguageProperties("quantityOfPackages"));

        List<BarChartDTO> listTest = PackagesDAO.quantityOfPackagesMonthly(month);

        int j = 1;

        YearMonth yearMonth = YearMonth.of(2021, Integer.valueOf(month));

        for (int i = 0; i < listTest.size(); i++) {
            for (; j <= yearMonth.lengthOfMonth(); j++) {
                if (Long.valueOf(listTest.get(i).getDay()) == j) {
                    dataXY.getData().add(new XYChart.Data(String.valueOf(Long.valueOf(listTest.get(i).getDay())),
                            listTest.get(i).getQuantity()));
                    j = Integer.valueOf(listTest.get(i).getDay()) + 1;

                    if (i != listTest.size() - 1) {
                        break;
                    }

                } else {
                    dataXY.getData().add(new XYChart.Data(String.valueOf(j), 0));
                }
            }
        }

        barChart.getData().add(dataXY);
    }
}
