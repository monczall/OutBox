package main.java.controllers.admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import main.java.SceneManager;
import main.java.dao.PackagesDAO;
import main.java.dao.UsersDAO;
import main.java.entity.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class AdminEditEmployee implements Initializable {

    private static int userID;

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
    private TextField parametr;

    public static int getUserID() {
        return userID;
    }

    public static void setUserID(int userID) {
        AdminEditEmployee.userID = userID;
    }

    public void showEdit(MouseEvent mouseEvent)throws IOException
        {

            setUserID(table.getSelectionModel().getSelectedItem().getUserID());
            SceneManager.loadScene("../../../resources/view/admin/adminEdit.fxml", edit);
        }

    private final ObservableList<UsersDTO> userList = FXCollections.observableArrayList();

    private ObservableList<UsersDTO> searchList;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        ObservableList<Users> usersList = UsersDAO.getUserEdit();




        for (int i = 0; i < usersList.size(); i++) {
            userList.add(new UsersDTO(usersList.get(i).getId(), usersList.get(i).getUserInfosByUserInfoId().getName(), usersList.get(i).getUserInfosByUserInfoId().getSurname(),
                    usersList.get(i).getUserInfosByUserInfoId().getPhoneNumber(), usersList.get(i).getUserInfosByUserInfoId().getCity(),
                    usersList.get(i).getEmail()));
        }

        searchList = userList;

        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        surname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        city.setCellValueFactory(new PropertyValueFactory<>("city"));
        mail.setCellValueFactory(new PropertyValueFactory<>("email"));
        phone.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        table.setItems(userList);


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
        System.out.println(searchedWord);
        System.out.println(searchList.size());
    }
}
