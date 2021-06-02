package main.java.controllers.manager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.java.App;
import main.java.controllers.auth.Login;
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
    private Label role;

    @FXML
    private Label voivodeship;

    @FXML
    private Label howManyPackages;

    @FXML
    private Pane alertPane;

    Users uu = UsersDAO.getUsersId(Login.getUserID()).get(0);
    List<UserInfos> dataUserInfos;
    List<Users> dataUser;
    int dataIndex = 0;

    /**
     * Confirmation of removing the courier
     */
    @FXML
    public void confirmDeleteCourierButton(javafx.event.ActionEvent actionEvent) {
        UserInfosDAO.deleteUser(dataUser.get(dataIndex).getUserInfoId());
        dataUser.remove(dataIndex);
        alertPane.setVisible(true);
    }

    /**
     * Choosing find couriers that meet the relevant criteria
     */
    public void findCourier(MouseEvent mouseEvent) {

        if(name.getText().toString().equals("") &&
                surname.getText().toString().equals("")){
            Alerts.createAlert(appWindow, findCourierButton, "WARNING",
                    App.getLanguageProperties("completeAllFields"));
        }
        else
        {
            dataUserInfos = UserInfosDAO.getUserInfoByNameAndSurname(name.getText(), surname.getText());
            clearDataCouriesFind();
        }
    }

    /**
     * method that changes status of package in courier panel after each change in combo box
     */
    public void clearDataCouriesFind(){
       for(int i=0; i<dataUser.size(); i++){
           System.out.println("("+i+")"+dataUser.get(i).getUserInfoId() +" : "+dataUserInfos.get(0).getId());
            if(dataUser.get(i).getUserInfoId() == dataUserInfos.get(0).getId()){
                dataIndex = i;
                setDataLabel();
                break;
            }
       }
    }

    /**
     *  Setting the requested data
     */
    public void setDataLabel(){
        howManyPackages.setText(dataIndex+1 + "/" + dataUser.size());
        dataUserInfos = UserInfosDAO.getUserInfoByID(dataUser.get(dataIndex).getId());
        fullName.setText(dataUserInfos.get(0).getName() + " "+dataUserInfos.get(0).getSurname());
        cityAndStreet.setText(dataUserInfos.get(0).getCity() + " "
                +dataUserInfos.get(0).getStreetAndNumber());
        phoneNumber.setText(dataUserInfos.get(0).getPhoneNumber());
        voivodeship.setText(dataUserInfos.get(0).getVoivodeship());
        email.setText(dataUser.get(dataIndex).getEmail());
        role.setText(dataUser.get(dataIndex).getRole());
    }

    /**
     * button to change the courier
     */
    @FXML
    void buttonBack(MouseEvent event) {
        dataIndex--;

        if(dataIndex<0){
            dataIndex=0;
        }
        setDataLabel();
    }

    /**
     * button to change the courier
     */
    @FXML
    void buttonNext(MouseEvent event) {
        dataIndex++;

        if(dataIndex>dataUser.size()-1){
            dataIndex=dataUser.size()-1;
        }
        setDataLabel();
    }

    /**
     * Window confirming the removal of the courier and setting the default settings
     */
    @FXML
    void confirmButton(MouseEvent event) {
        name.setText("");
        surname.setText("");
        dataIndex = 0;
        clearDataCouries();
        alertPane.setVisible(false);
    }

    /**
     * Choosing couriers that meet the relevant criteria
     */
    void clearDataCouries(){
        for(int i=0; i<dataUser.size(); i++) {
            if (dataUser.get(i).getAreaId() != uu.getAreaId()) {
                dataUser.remove(i);
                i--;
            }
        }

        for(int i=0; i<dataUser.size(); i++) {
            if (!dataUser.get(i).getRole().equals("Kurier") && !dataUser.get(i).getRole().equals("Kurier Miedzyoddzialowy")) {
                dataUser.remove(i);
                i--;
            }
        }

        if(dataUser.size()==0){
            howManyPackages.setText("Brak");
            noDataText.setVisible(true);
            button1.setVisible(false);
            button2.setVisible(false);
            paneResults.setVisible(false);
        }
        else{
            noDataText.setVisible(false);
            button1.setVisible(true);
            button2.setVisible(true);
            paneResults.setVisible(true);
            setDataLabel();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dataUser = UsersDAO.getUsers();
        clearDataCouries();

        //set default visible
        button1.setVisible(true);
        button2.setVisible(true);
        paneResults.setVisible(true);
    }

}
