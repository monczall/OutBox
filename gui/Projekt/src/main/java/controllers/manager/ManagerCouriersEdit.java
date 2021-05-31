package main.java.controllers.manager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import main.java.App;
import main.java.SceneManager;
import main.java.controllers.auth.Login;
import main.java.dao.UserInfosDAO;
import main.java.dao.UsersDAO;
import main.java.entity.UserInfos;
import main.java.entity.Users;
import main.java.features.Alerts;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ManagerCouriersEdit implements Initializable {

    @FXML
    private AnchorPane appWindow;

    @FXML
    private Pane alertPane;

    @FXML
    private Label labelAlertPane;

    @FXML
    private TextField name;

    @FXML
    private TextField surname;

    @FXML
    private Button findCourierButton;

    @FXML
    private Label notDataLabel;

    @FXML
    private Label howManyPackages;

    @FXML
    private Pane dataPane;

    @FXML
    private TextField nameInput;

    @FXML
    private Button saveEditCourierButton;

    @FXML
    private TextField surnameInput;

    @FXML
    private TextField cityInput;

    @FXML
    private TextField streetInput;

    @FXML
    private TextField inputNumber;

    @FXML
    private TextField inputEmail;

    @FXML
    private Button button1;

    @FXML
    private Button button2;

    @FXML
    private ComboBox<String> comboRole;

    @FXML
    private ObservableList<String> role = FXCollections.observableArrayList("Kurier","Kurier Międzyoddziałowy");

    Users uu = UsersDAO.getUsersId(Login.getUserID()).get(0);
    List<UserInfos> dataUserInfos;
    List<Users> dataUser;

    int dataIndex = 0;
    String roleString;

    /**
     * Choosing the role of the courier
     */
    @FXML
    void changeRole(ActionEvent event) {
        if(comboRole.getValue().equals("Kurier")) {
            roleString = "Kurier";
        }
        else {
            roleString = "Kurier Międzyoddziałowy";
        }
    }

    /**
     * The method responsible for finding a courier with the given data
     */
    public void findCourier(MouseEvent mouseEvent) {

        if(name.getText().toString().equals("") &&
                surname.getText().toString().equals("")){
            Alerts.createAlert(appWindow, findCourierButton, "WARNING", App.getLanguageProperties("completeAllFields"));
        }else{
            dataUserInfos = UserInfosDAO.getUserInfoByNameAndSurname(name.getText(), surname.getText());
            clearDataCouriesFind();
        }
    }

    public void clearDataCouriesFind(){
        for(int i=0; i<dataUser.size(); i++){
            System.out.println("("+i+")"+dataUser.get(i).getUserInfoId() +" : "+dataUserInfos.get(0).getId());
            if(dataUser.get(i).getUserInfoId() == dataUserInfos.get(0).getId()){
                dataIndex = i;
                setDataEdit();
                break;
            }
        }
    }

    /**
     * The method responsible for saving the data
     */
    @FXML
    void saveEditCourier(MouseEvent event) {

        if(nameInput.getText().toString().equals("") ||
                surnameInput.getText().toString().equals("") ||
                streetInput.getText().toString().equals("") ||
                cityInput.getText().toString().equals("") ||
                inputEmail.getText().toString().equals("") ||
                inputNumber.getText().toString().equals("")){
            Alerts.createAlert(appWindow, saveEditCourierButton,"WARNING",App.getLanguageProperties("completeAllFields"));
        }
        else {

            if (!validation()) {
                Alerts.createAlert(appWindow, saveEditCourierButton, "WARNING", App.getLanguageProperties("correctFields"));
            }else{
                System.out.println(roleString);
                UserInfosDAO.updateUser(dataUserInfos.get(0).getId(), dataUser.get(dataIndex).getId(),
                        nameInput.getText(), surnameInput.getText(), inputNumber.getText(), cityInput.getText(),
                        streetInput.getText(), dataUserInfos.get(0).getVoivodeship(), inputEmail.getText(),
                        dataUser.get(dataIndex).getPassword(),
                        roleString,
                        dataUser.get(dataIndex).getAreaId(),
                        dataUser.get(dataIndex).getUserInfoId());

                alertPane.setVisible(true);
                clearDataCouries();
            }
        }
    }

    /**
     * The method validates the entered data
     * true - all fields are correct
     * false - at least one field is incorrect
     * @return boolean
     */
    boolean validation(){

        boolean status = true;

        if (!inputEmail.getText().matches("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}"))
        {
            status = false;
        }
        if (!nameInput.getText().matches("[a-zA-Z]+"))
        {
            status = false;
        }
        if (!surnameInput.getText().toString().matches("[a-zA-Z]+"))
        {
            status = false;

        }
        if (!cityInput.getText().matches("[A-Za-z]+"))
        {
            status = false;
        }
        if (!streetInput.getText().matches("[A-Za-z]{0,2}\\.?\\s?[A-Za-z]{2,40}\\s?\\-?[A-Za-z]{0,40}?\\" +
                "s?\\-?[A-Za-z]{0,40}?\\s[0-9]{1,4}\\s?[A-Za-z]?\\s?\\/?\\s?[0-9]{0,5}"))
        {
            status = false;
        }
        if (!inputNumber.getText().matches("[0-9]*") || inputNumber.getText().length() != 9)
        {
            status = false;
        }

        return status;
    }

    /**
     * Method responsible for setting the data
     */
    public void setDataEdit(){
        howManyPackages.setText(dataIndex+1 + "/" + dataUser.size());
        dataUserInfos = UserInfosDAO.getUserInfoByID(dataUser.get(dataIndex).getId());
        nameInput.setText(dataUserInfos.get(0).getName());
        surnameInput.setText(dataUserInfos.get(0).getSurname());
        inputNumber.setText(dataUserInfos.get(0).getPhoneNumber());
        cityInput.setText(dataUserInfos.get(0).getCity());
        streetInput.setText(dataUserInfos.get(0).getStreetAndNumber());
        inputEmail.setText(dataUser.get(dataIndex).getEmail());

        if(dataUser.get(dataIndex).getRole().equals("Kurier")){
            comboRole.setValue(role.get(0));
        }
        else
        {
            comboRole.setValue(role.get(1));
        }

        if(comboRole.getValue().equals("Kurier")) {
            roleString = "Kurier";
        }
        else{
            roleString = "Kurier Międzyoddziałowy";
        }
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
        setDataEdit();
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
        setDataEdit();
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
        dataUser = UsersDAO.getUsers();
        dataIndex=0;
        for(int i=0; i<dataUser.size(); i++) {
            if (dataUser.get(i).getAreaId() != uu.getAreaId()) {
                dataUser.remove(i);
                i--;
            }
        }

        for(int i=0; i<dataUser.size(); i++) {
            if (!dataUser.get(i).getRole().equals("Kurier") && !dataUser.get(i).getRole().equals("Kurier Międzyoddziałowy")) {
                dataUser.remove(i);
                i--;
            }
        }

        if(dataUser.size()==0){
            howManyPackages.setText("Brak");
            notDataLabel.setVisible(true);
            button1.setVisible(false);
            button2.setVisible(false);
            dataPane.setVisible(false);
        }
        else{
            notDataLabel.setVisible(false);
            button1.setVisible(true);
            button2.setVisible(true);
            dataPane.setVisible(true);
            setDataEdit();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        clearDataCouries();

        //set default visible
        button1.setVisible(true);
        button2.setVisible(true);
        dataPane.setVisible(true);

        comboRole.setItems(role);
    }

}