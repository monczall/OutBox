package main.java.controllers.manager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import main.java.SceneManager;
import main.java.dao.PackageHistoryDAO;
import main.java.dao.PackagesDAO;
import main.java.dao.UserInfosDAO;
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

    String status = ManagerPackages.getStatus();

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
        ui = UsersDAO.readUserInfoById(ManagerPackages.getId()).get(0);
        city.setText(ui.getCity());
        name.setText(ui.getName());
        surname.setText(ui.getSurname());
        phone.setText(ui.getPhoneNumber());
        address.setText(ui.getStreetAndNumber());
        comments.setText(ManagerPackages.getComment());

        ObservableList<PackageStatus> ol = FXCollections.observableArrayList(PackageStatus.values());
        ol.remove(0);
        changeStatus.setItems(ol);

        IntStream.range(0, ol.size()).filter(i -> ol.get(i).toString().contains(status)).findFirst().
                ifPresent(i -> changeStatus.getSelectionModel().select(i));
    }

    @FXML
    void updateStatus(ActionEvent event) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        if(changeStatus.getSelectionModel().getSelectedIndex() != -1){
            if (!changeStatus.getValue().toString().equals(status)){
                PackageHistoryDAO.updateStatus(ManagerPackages.getPackageId(), changeStatus.getValue().toString(),
                        Timestamp.valueOf(dateTimeFormatter.format(now)));
            }
        }
    }
}
