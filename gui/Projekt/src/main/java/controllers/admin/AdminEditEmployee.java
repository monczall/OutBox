package main.java.controllers.admin;

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
import main.java.App;
import main.java.SceneManager;
import main.java.dao.UsersDAO;
import main.java.entity.Users;
import main.java.entity.UsersDTO;
import main.java.features.Alerts;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class AdminEditEmployee implements Initializable {

    private static int userID;

    private static int userInfoID;
    private final ObservableList<UsersDTO> searchList = UsersDAO.getUserEdit();
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

    /**
     * Method that return userID
     *
     * @return return userID
     */
    public static int getUserID() {
        return userID;
    }

    /**
     * Method that set userID
     *
     * @param userID set userID
     */
    public static void setUserID(int userID) {
        AdminEditEmployee.userID = userID;
    }

    /**
     * Method that return userInfoID
     *
     * @return return userInfoID
     */
    public static int getUserInfoID() {
        return userInfoID;
    }

    /**
     * Method that set userInfoID
     *
     * @param userInfoID userInfoID
     */
    public static void setUserInfoID(int userInfoID) {
        AdminEditEmployee.userInfoID = userInfoID;
    }

    /**
     * Method that move as to editing employee scene after chose an employee
     *
     * @param mouseEvent mose event
     * @throws IOException if doesn't find a scene then throw IOException
     */
    public void showEdit(MouseEvent mouseEvent) throws IOException {
        if (table.getSelectionModel().getSelectedIndex() == -1) {
            Alerts.createCustomAlert(edit, editTable, "WARNING",
                    App.getLanguageProperties("adminNotChosen"), 250, 86, "alertFailure");
        } else {
            setUserID(table.getSelectionModel().getSelectedItem().getUserID());
            setUserInfoID(table.getSelectionModel().getSelectedItem().getUserInfoID());
            SceneManager.loadScene("main/resources/view/admin/adminEdit.fxml", edit);
        }
    }

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

    /**
     * Method that after typing character will be searching any parameters from base
     *
     * @param event key event
     */
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

