package main.java.controllers.manager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    private Pane dataPane,paneResults;

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
    private TextField inputVoivodeship;

    @FXML
    private TextField inputEmail;

    @FXML
    private Button button1;

    @FXML
    private Button button2;

    List<UserInfos> dataUserInfos;
    List<Users> dataUser;
    int dataIndex = 0;

    public void findCourier(MouseEvent mouseEvent) {

        if(name.getText().toString().equals("") &&
                surname.getText().toString().equals("")){
            Alerts.createAlert(appWindow, findCourierButton, "WARNING", "Podaj imie i nazwisko!");
        }else{
            dataUserInfos = UserInfosDAO.getUserInfoByNameAndSurname(name.getText(), surname.getText());
            setDataEdit();
        }
    }

    @FXML
    void saveEditCourier(MouseEvent event) {
        boolean status = true;
        if(nameInput.getText().toString().equals("") ||
                surnameInput.getText().toString().equals("") ||
                streetInput.getText().toString().equals("") ||
                cityInput.getText().toString().equals("") ||
                inputEmail.getText().toString().equals("") ||
                inputVoivodeship.getText().toString().equals("") ||
                inputNumber.getText().toString().equals("")){
            Alerts.createAlert(appWindow, saveEditCourierButton,"WARNING","UZUPEŁNIJ WSZYSTKIE POLA");
        }
        else {
            if (inputEmail.getText().matches("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}"))
            {
                System.out.println("mail poprawne");
            }
            else
            {
                status = false;
                System.out.println("mail niepoprawne");
            }
            if (nameInput.getText().matches("[a-zA-Z]+"))
            {
                System.out.println("Imie poprawne");
            }
            else
            {
                status = false;
                System.out.println("Imie niepoprawne");
            }
            if (surnameInput.getText().toString().matches("[a-zA-Z]+"))
            {
                System.out.println("Nazwisko poprawne");
            }
            else
            {
                status = false;
                System.out.println("Nazwisko niepoprawne");
            }
            if (cityInput.getText().matches("[A-Za-z]+"))
            {
                System.out.println("Miasto poprawne");
            }
            else
            {
                status = false;
                System.out.println("Miasto niepoprawne");
            }
            if (streetInput.getText().matches("[A-Za-z]{0,2}\\.?\\s?[A-Za-z]{2,40}\\s?\\-?[A-Za-z]{0,40}?\\" +
                    "s?\\-?[A-Za-z]{0,40}?\\s[0-9]{1,4}\\s?[A-Za-z]?\\s?\\/?\\s?[0-9]{0,5}"))
            {
                System.out.println("Ulica poprawna");
            }
            else {
                status = false;
                System.out.println("Ulica niepoprawna");
            }
            if (inputNumber.getText().matches("[0-9]*") && inputNumber.getText().length() == 9)
            {
                System.out.println("Telefon poprawny");
            }
            else
            {
                status = false;
                System.out.println("Telefon niepoprawny");
            }
            if (inputVoivodeship.getText().matches("[a-zA-Z]+"))
            {
                System.out.println("Województwo poprawne");
            }
            else
            {
                status = false;
                System.out.println("Województwo niepoprawne");
            }
            if (!status) {
                Alerts.createAlert(appWindow, saveEditCourierButton, "WARNING", "POPRAW POLA");
            }else{
                System.out.println(dataUser.get(0).getPassword()+" " +
                        ""+dataUser.get(0).getAreaId()+" "+dataUser.get(0).getUserInfoId());
                UserInfosDAO.updateUser(dataUserInfos.get(dataIndex).getId(), dataUser.get(0).getId(),
                        nameInput.getText(), surnameInput.getText(), inputNumber.getText(), cityInput.getText(),
                        streetInput.getText(), inputVoivodeship.getText(), inputEmail.getText(),
                        dataUser.get(0).getPassword(),
                        "Kurier",
                        //dataUser.get(0).getAreaId(),
                        1,
                        dataUser.get(0).getUserInfoId());

                alertPane.setVisible(true);
            }
        }
    }

    public void setDataEdit(){
        System.out.println("DataIndex: " + dataIndex + " DataUserInfosSize: "+dataUserInfos.size() +
                " DataUserInfosGetId: "+dataUserInfos.get(dataIndex).getId());
        System.out.println("ZMIENIAM NA KOLEJNE ID INDEX("+dataIndex+") = " +dataUserInfos.get(dataIndex).getId());
        dataUser = UsersDAO.getUsersId(dataUserInfos.get(dataIndex).getId());
        System.out.println("DataUserSIZSE: " + dataUser.size() + " DataUserID: " + dataUser.get(0).getId());

        nameInput.setText(dataUserInfos.get(dataIndex).getName());
        surnameInput.setText(dataUserInfos.get(dataIndex).getSurname());
        inputNumber.setText(dataUserInfos.get(dataIndex).getPhoneNumber());
        cityInput.setText(dataUserInfos.get(dataIndex).getCity());
        streetInput.setText(dataUserInfos.get(dataIndex).getStreetAndNumber());
        inputVoivodeship.setText(dataUserInfos.get(dataIndex).getVoivodeship());
        inputEmail.setText(dataUser.get(0).getEmail());
        String role = dataUser.get(0).getRole();

        if(dataUserInfos.size() > 1 && (role.equals("Kurier")))
        {
            dataPane.setVisible(true);
            button1.setVisible(true);
            button2.setVisible(true);
            notDataLabel.setVisible(false);
        }
        else if(dataUserInfos.size() == 1 && (role.equals("Kurier")))
        {
            button1.setVisible(false);
            button2.setVisible(false);
            dataPane.setVisible(true);
            notDataLabel.setVisible(false);
        }
        else{
            System.out.println("NIE");
            dataPane.setVisible(false);
            notDataLabel.setVisible(true);
        }
    }

    @FXML
    void buttonBack(MouseEvent event) {
        dataIndex--;

        if(dataIndex<0){
            dataIndex=0;
        }
        setDataEdit();
    }

    @FXML
    void buttonNext(MouseEvent event) {
        dataIndex++;

        if(dataIndex>dataUserInfos.size()-1){
            dataIndex=dataUserInfos.size()-1;
        }
        setDataEdit();
    }

    @FXML
    void confirmButton(MouseEvent event) {
        dataPane.setVisible(false);
        name.setText("");
        surname.setText("");
        notDataLabel.setVisible(true);
        button1.setVisible(false);
        button2.setVisible(false);
        alertPane.setVisible(false);
        notDataLabel.setVisible(true);
        dataIndex = 0;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dataPane.setVisible(false);
        button1.setVisible(false);
        button2.setVisible(false);
    }
}
