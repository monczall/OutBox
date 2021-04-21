package main.java.controllers.courier;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import main.java.dao.PackageHistoryDAO;
import main.java.dao.UserInfosDAO;
import main.java.dao.UsersDAO;
import main.java.entity.UserInfos;

import java.net.URL;
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

        System.out.println(PackageHistoryDAO.getStatuses().size());
    }
}
