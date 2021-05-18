package main.java.controllers.interbranchCourier;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;
import main.java.dao.AreasDAO;
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
    private Text address;

    @FXML
    private Text city1;

    @FXML
    private Text voivodeship1;

    @FXML
    private Text street2;

    @FXML
    private Text city2;

    @FXML
    private Text voivodeship2;
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
        InterbranchCourierSecond.getPackageId();
        List<Areas> areasList = AreasDAO.getAreas();
        if(statusReturned.equals(PackageStatus.IN_SORTING_DEPARTMENT.displayName())){
            street1.setText(areasList.get(3).getDepartmentStreetAndNumber() + ", " + areasList.get(3).getCity() + ", " + areasList.get(3).getVoivodeship());
            street2.setText(areasList.get(0).getDepartmentStreetAndNumber() + ", " + areasList.get(0).getCity() + ", " + areasList.get(0).getVoivodeship());
        }
        else if(statusReturned.equals(PackageStatus.IN_MAIN_SORTING_DEPARTMENT.displayName())){
            street1.setText(areasList.get(0).getDepartmentStreetAndNumber() + ", " + areasList.get(0).getCity() + ", " + areasList.get(0).getVoivodeship());
            street2.setText(areasList.get(2).getDepartmentStreetAndNumber() + ", " + areasList.get(2).getCity() + ", " + areasList.get(2).getVoivodeship());
        }
        else if(statusReturned.equals(PackageStatus.TRANSPORTING.displayName())){
            street1.setText(areasList.get(0).getDepartmentStreetAndNumber() + ", " + areasList.get(0).getCity() + ", " + areasList.get(0).getVoivodeship());
            street2.setText(areasList.get(2).getDepartmentStreetAndNumber() + ", " + areasList.get(2).getCity() + ", " + areasList.get(2).getVoivodeship());
        }
    }

    @FXML
    void setStatus(ActionEvent event) {
        setStatusReturned(changeStatus.getSelectionModel().getSelectedItem().toString());
    }
}
