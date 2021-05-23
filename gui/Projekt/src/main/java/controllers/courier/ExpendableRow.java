package main.java.controllers.courier;

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

public class ExpendableRow implements Initializable {

    String status = CourierSecond.getStatus();
    private static String statusReturned;

    @FXML
    private Text name;

    @FXML
    private Text mail;

    @FXML
    private Text city;

    @FXML
    private Text surname;

    @FXML
    private Text phone;

    @FXML
    private Text address;

    @FXML
    private TextArea comments;

    @FXML
    private ComboBox<PackageStatus> changeStatus;

    public static String getStatusReturned() {
        return statusReturned;
    }

    public static void setStatusReturned(String statusReturned) {
        ExpendableRow.statusReturned = statusReturned;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        UserInfos ui = new UserInfos();
        ui = UsersDAO.readUserInfoById(CourierSecond.getId()).get(0);
        city.setText(ui.getCity());
        name.setText(ui.getName());
        surname.setText(ui.getSurname());
        phone.setText(ui.getPhoneNumber());
        address.setText(ui.getStreetAndNumber());
        comments.setText(CourierSecond.getComment());

        ObservableList<PackageStatus> ol = FXCollections.observableArrayList(PackageStatus.values());
        ol.remove(0);
        ol.remove(3);
        changeStatus.setItems(ol);

        IntStream.range(0, ol.size()).filter(i -> ol.get(i).toString().contains(status)).findFirst().
                ifPresent(i -> changeStatus.getSelectionModel().select(i));
        if(changeStatus.getSelectionModel().getSelectedIndex() != -1) {
            setStatusReturned(changeStatus.getSelectionModel().getSelectedItem().toString());
        }else{
            setStatusReturned("");
        }
    }

    /**
     * method that changes status of package in courier panel after each change in combo box
     * @param event
     */
    @FXML
    void setStatus(ActionEvent event) {
        setStatusReturned(changeStatus.getSelectionModel().getSelectedItem().toString());
    }
}
