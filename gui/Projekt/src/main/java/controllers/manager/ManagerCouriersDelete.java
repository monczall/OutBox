package main.java.controllers.manager;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import main.java.controllers.client.PackageItem;
import main.java.controllers.client.PackageTest;
import main.java.dao.UserInfosDAO;
import main.java.dao.UsersDAO;
import main.java.entity.UserInfos;
import main.java.entity.Users;
import main.java.features.Alerts;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ManagerCouriersDelete implements Initializable {

    @FXML
    private AnchorPane appWindow;

    @FXML
    private TextField name;

    @FXML
    private TextField surname;

    @FXML
    private Button findCourierButton,button1,button2;

    @FXML
    private Pane paneResults;

    @FXML
    private Label fullName;

    @FXML
    private Label noDataText;

    @FXML
    private Label cityAndStreet;

    @FXML
    private Label phoneNumber;

    @FXML
    private Label email;

    @FXML
    private Label voivodeship;

    List<UserInfos> dataUserInfos;
    List<Users> dataUser;
    int dataIndex = 0;

    @FXML
    public void confirmDeleteCourierButton(javafx.event.ActionEvent actionEvent) {
        UserInfosDAO.deleteUser(dataUserInfos.get(dataIndex).getId());
        Alerts.createAlert(appWindow, findCourierButton, "WARNING", "Usunięto");
    }


    public void findCourier(MouseEvent mouseEvent) {

        if(name.getText().toString().equals("") &&
                surname.getText().toString().equals("")){
            Alerts.createAlert(appWindow, findCourierButton, "WARNING", "PODAJ JAKIŚ PARAMETR");
        }
        else
        {
            dataUserInfos = UserInfosDAO.getUserInfoByNameAndSurname(name.getText(), surname.getText());

            if(dataUserInfos.size() > 1)
            {
                setDataLabel();
                paneResults.setVisible(true);
                button1.setVisible(true);
                button2.setVisible(true);
                noDataText.setVisible(false);
            }
            else if(dataUserInfos.size() == 1)
            {
                setDataLabel();
                button1.setVisible(false);
                button2.setVisible(false);
                paneResults.setVisible(true);
                noDataText.setVisible(false);
            }
            else{
                System.out.println("NIE");
                paneResults.setVisible(false);
                noDataText.setVisible(true);
            }
        }
    }

    public void setDataLabel(){
        System.out.println("DataIndex: " + dataIndex + " DataUserInfosSize: "+dataUserInfos.size() + " DataUserInfosGetId: "+dataUserInfos.get(dataIndex).getId());
        System.out.println("ZMIENIAM NA KOLEJNE ID INDEX("+dataIndex+") = " +dataUserInfos.get(dataIndex).getId());
        dataUser = UsersDAO.getUsersId(dataUserInfos.get(dataIndex).getId());
        System.out.println("DataUserSIZSE: " + dataUser.size() + " DataUserID: " + dataUser.get(dataIndex).getId());

        fullName.setText(dataUserInfos.get(dataIndex).getName() + " "+dataUserInfos.get(dataIndex).getSurname());
        cityAndStreet.setText(dataUserInfos.get(dataIndex).getCity() + " "+dataUserInfos.get(dataIndex).getStreetAndNumber());
        phoneNumber.setText(dataUserInfos.get(dataIndex).getPhoneNumber());
        voivodeship.setText(dataUserInfos.get(dataIndex).getVoivodeship());
        email.setText(dataUser.get(dataIndex).getEmail());
    }

    @FXML
    void buttonBack(MouseEvent event) {
        dataIndex--;

        if(dataIndex<0){
            dataIndex=0;
        }
        setDataLabel();
    }

    @FXML
    void buttonNext(MouseEvent event) {
        dataIndex++;

        if(dataIndex>dataUserInfos.size()-1){
            dataIndex=dataUserInfos.size()-1;
        }
        setDataLabel();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        button1.setVisible(false);
        button2.setVisible(false);
        paneResults.setVisible(false);
    }

}
