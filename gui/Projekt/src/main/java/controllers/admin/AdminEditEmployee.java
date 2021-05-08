package main.java.controllers.admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import main.java.App;
import main.java.SceneManager;
import main.java.dao.PackagesDAO;
import main.java.dao.UsersDAO;
import main.java.entity.*;
import main.java.features.Alerts;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class AdminEditEmployee implements Initializable {

    private static int userID;

    private static int userInfoID;

    @FXML
    private AnchorPane edit;

    @FXML
    private TableView<UsersDTO> table;
    @FXML
    private TableColumn<Users, String> name;
    @FXML
    private TableColumn<Users, String> surname;
    @FXML
    private TableColumn<Users, String> city;
    @FXML
    private TableColumn<Users, String> mail;
    @FXML
    private TableColumn<Users, String> phone;
    @FXML
    private Button editTable;
    @FXML
    private TextField parametr;

    public static int getUserID() {
        return userID;
    }

    public static void setUserID(int userID) {
        AdminEditEmployee.userID = userID;
    }

    public static int getUserInfoID() {
        return userInfoID;
    }

    public static void setUserInfoID(int userInfoID) {
        AdminEditEmployee.userInfoID = userInfoID;
    }

    public void showEdit(MouseEvent mouseEvent) throws IOException {
        if (table.getSelectionModel().getSelectedIndex() == -1) {
            Alerts.createCustomAlert(edit, editTable, "WARNING",
                    App.getLanguageProperties("adminNotChosen"), 250, 86, "alertFailure");
        } else {
            setUserID(table.getSelectionModel().getSelectedItem().getUserID());
            setUserInfoID(table.getSelectionModel().getSelectedItem().getUserInfoID());
            SceneManager.loadScene("../../../resources/view/admin/adminEdit.fxml", edit);
        }
    }

    private final ObservableList<UsersDTO> searchList = UsersDAO.getUserEdit();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        ObservableList<UsersDTO> usersList = UsersDAO.getUserEdit();
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        surname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        city.setCellValueFactory(new PropertyValueFactory<>("city"));
        mail.setCellValueFactory(new PropertyValueFactory<>("email"));
        phone.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        table.setItems(usersList);


        table.getSelectionModel().select(0);
    }

    @FXML
    void search(KeyEvent event) {
        table.getItems().clear();
        String searchedWord = parametr.getText().toLowerCase();
        for (int i = 0; i < searchList.size(); i++) {
            if (searchList.get(i).getName().toLowerCase().contains(searchedWord) ||
                    searchList.get(i).getSurname().toLowerCase().contains(searchedWord) ||
                    searchList.get(i).getCity().toLowerCase().contains(searchedWord) ||
                    searchList.get(i).getEmail().toLowerCase().contains(searchedWord) ||
                    searchList.get(i).getPhoneNumber().toLowerCase().contains(searchedWord)
            ) {
                table.getItems().add(searchList.get(i));

            }
        }
    }
}

