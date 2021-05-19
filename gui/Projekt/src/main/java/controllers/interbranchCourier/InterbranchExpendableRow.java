package main.java.controllers.interbranchCourier;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;
import main.java.dao.AreasDAO;
import main.java.dao.PackageHistoryDAO;
import main.java.dao.PackagesDAO;
import main.java.entity.Areas;
import main.java.entity.PackageStatus;
import java.net.URL;

import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.IntStream;

public class InterbranchExpendableRow implements Initializable {

    String status = InterbranchCourierSecond.getStatus();
    private static String statusReturned;

    @FXML
    private Text street1;

    @FXML
    private Text street2;

    @FXML
    private ComboBox<PackageStatus> changeStatus;

    public static String getStatusReturned() {
        return statusReturned;
    }

    public static void setStatusReturned(String statusReturned) {
        InterbranchExpendableRow.statusReturned = statusReturned;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {



        ObservableList<PackageStatus> ol = FXCollections.observableArrayList(PackageStatus.values());
        ol.remove(0, 2);
        ol.remove(3, 10);
        changeStatus.setItems(ol);

        IntStream.range(0, ol.size()).filter(i -> ol.get(i).toString().contains(status)).findFirst().
                ifPresent(i -> changeStatus.getSelectionModel().select(i));
        if(changeStatus.getSelectionModel().getSelectedIndex() != -1) {
            setStatusReturned(changeStatus.getSelectionModel().getSelectedItem().toString());
        }else{
            setStatusReturned("");
        }


        int area = PackagesDAO.getPackagesById(InterbranchCourierSecond.getPackageId()).get(0).getUsersByCourierId().getAreaId();

        Areas mainArea = AreasDAO.getAreas().get(0);
        Areas areasList = AreasDAO.getAreasById(area).get(0);
        if(statusReturned.equals(PackageStatus.IN_SORTING_DEPARTMENT.displayName())){

            street1.setText(areasList.getDepartmentStreetAndNumber() + ", " + areasList.getCity() +
                    ", " + areasList.getVoivodeship());
            street2.setText(mainArea.getDepartmentStreetAndNumber() + ", " + mainArea.getCity() +
                    ", " + mainArea.getVoivodeship());
        }
        else if(statusReturned.equals(PackageStatus.IN_MAIN_SORTING_DEPARTMENT.displayName())){

            street1.setText(mainArea.getDepartmentStreetAndNumber() + ", " + mainArea.getCity() +
                    ", " + mainArea.getVoivodeship());
            street2.setText(areasList.getDepartmentStreetAndNumber() + ", " + areasList.getCity() +
                    ", " + areasList.getVoivodeship());
        }
        else if(statusReturned.equals(PackageStatus.TRANSPORTING.displayName())){
            List<String> packageStatus = PackageHistoryDAO.getStatusById(InterbranchCourierSecond.getPackageId());
            String secondaryArea = areasList.getDepartmentStreetAndNumber() + ", " + areasList.getCity() +
                    ", " + areasList.getVoivodeship();
            String primaryArea = mainArea.getDepartmentStreetAndNumber() + ", " + mainArea.getCity() +
                    ", " + mainArea.getVoivodeship();
            if(packageStatus.get(packageStatus.size() - 2).equals(PackageStatus.IN_SORTING_DEPARTMENT.displayName())){
                street1.setText(secondaryArea);
                street2.setText(primaryArea);
            }
            else  if(packageStatus.get(packageStatus.size() - 2).equals(PackageStatus.IN_MAIN_SORTING_DEPARTMENT.displayName())){
                street1.setText(primaryArea);
                street2.setText(secondaryArea);
            }

        }
    }

    @FXML
    void setStatus(ActionEvent event) {
        setStatusReturned(changeStatus.getSelectionModel().getSelectedItem().toString());
    }
}