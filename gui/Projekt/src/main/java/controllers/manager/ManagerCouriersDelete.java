package main.java.controllers.manager;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import main.java.App;
import main.java.dao.UserInfosDAO;
import main.java.dao.UsersDAO;
import main.java.entity.UserInfos;
import main.java.entity.Users;
import main.java.features.Alerts;

import java.net.URL;
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

    @FXML
    private Pane alertPane;

    List<UserInfos> dataUserInfos;
    List<Users> dataUser;
    int dataIndex = 0;

    @FXML
    public void confirmDeleteCourierButton(javafx.event.ActionEvent actionEvent) {
        UserInfosDAO.deleteUser(dataUserInfos.get(dataIndex).getId());
        alertPane.setVisible(true);
    }


    public void findCourier(MouseEvent mouseEvent) {

        if(name.getText().toString().equals("") &&
                surname.getText().toString().equals("")){
            Alerts.createAlert(appWindow, findCourierButton, "WARNING",
                    App.getLanguageProperties("completeAllFields"));
        }
        else
        {
            dataUserInfos = UserInfosDAO.getUserInfoByNameAndSurname(name.getText(), surname.getText());
            setDataLabel();
        }
    }

    public void setDataLabel(){
        dataUser = UsersDAO.getUsersId(dataUserInfos.get(dataIndex).getId());
        fullName.setText(dataUserInfos.get(dataIndex).getName() + " "+dataUserInfos.get(dataIndex).getSurname());
        cityAndStreet.setText(dataUserInfos.get(dataIndex).getCity() + " "
                +dataUserInfos.get(dataIndex).getStreetAndNumber());
        phoneNumber.setText(dataUserInfos.get(dataIndex).getPhoneNumber());
        voivodeship.setText(dataUserInfos.get(dataIndex).getVoivodeship());
        email.setText(dataUser.get(0).getEmail());
        String role = dataUser.get(0).getRole();

        if(dataUserInfos.size() > 1 && (role.equals("Kurier")))
        {
            paneResults.setVisible(true);
            button1.setVisible(true);
            button2.setVisible(true);
            noDataText.setVisible(false);
        }
        else if(dataUserInfos.size() == 1 && (role.equals("Kurier")))
        {
            button1.setVisible(false);
            button2.setVisible(false);
            paneResults.setVisible(true);
            noDataText.setVisible(false);
        }
        else{
            paneResults.setVisible(false);
            noDataText.setVisible(true);
        }
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

    @FXML
    void confirmButton(MouseEvent event) {
        paneResults.setVisible(false);
        name.setText("");
        surname.setText("");
        noDataText.setVisible(true);
        button1.setVisible(false);
        button2.setVisible(false);
        alertPane.setVisible(false);
        dataIndex = 0;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        button1.setVisible(false);
        button2.setVisible(false);
        paneResults.setVisible(false);
    }

}
