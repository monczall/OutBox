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
import main.java.dao.PackagesDAO;
import main.java.dao.UserInfosDAO;
import main.java.dao.UsersDAO;
import main.java.entity.PackageStatus;
import main.java.entity.UserInfos;

import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class ExpendableRow implements Initializable {

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

        //changeStatus.getItems().add(PackageStatus.values()));
        //System.out.println(PackageStatus.values().toString());
        //System.out.println(FXCollections.observableArrayList(PackageHistoryDAO.getStatuses()));
        ObservableList<PackageStatus> ol = FXCollections.observableArrayList(PackageStatus.values());
        changeStatus.setItems(ol);
    }

    @FXML
    void updateStatus(ActionEvent event) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        PackageHistoryDAO.updateStatus(CourierSecond.getPackageId(), changeStatus.getValue().toString(),Timestamp.valueOf(dateTimeFormatter.format(now)));
    }
}
