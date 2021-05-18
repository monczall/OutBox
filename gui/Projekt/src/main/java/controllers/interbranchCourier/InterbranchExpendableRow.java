package main.java.controllers.interbranchCourier;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import main.java.dao.PackageHistoryDAO;
import main.java.dao.UsersDAO;
import main.java.entity.PackageStatus;
import main.java.entity.UserInfos;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.stream.IntStream;

public class InterbranchExpendableRow implements Initializable {

    String status = InterbranchCourierSecond.getStatus();
    private static String statusReturned;

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
        ol.remove(0);
        changeStatus.setItems(ol);

        IntStream.range(0, ol.size()).filter(i -> ol.get(i).toString().contains(status)).findFirst().
                ifPresent(i -> changeStatus.getSelectionModel().select(i));
        if(changeStatus.getSelectionModel().getSelectedIndex() != -1) {
            setStatusReturned(changeStatus.getSelectionModel().getSelectedItem().toString());
        }else{
            setStatusReturned("");
        }
    }

    @FXML
    void setStatus(ActionEvent event) {
        setStatusReturned(changeStatus.getSelectionModel().getSelectedItem().toString());
    }
}
