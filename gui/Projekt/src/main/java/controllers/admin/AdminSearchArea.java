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
import main.java.dao.AreasDAO;

import main.java.entity.Areas;
import main.java.entity.AreasDTO;

import main.java.features.Alerts;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminSearchArea implements Initializable {

    @FXML
    private AnchorPane edit;
    @FXML
    private TableView<AreasDTO> table;
    @FXML
    private TableColumn<Areas, String> name;
    @FXML
    private TableColumn<Areas, String> street;
    @FXML
    private TableColumn<Areas, String> city;
    @FXML
    private TableColumn<Areas, String> voivodeship;

    @FXML
    private Button editTable;
    @FXML
    private TextField parametr;

    public static int getAreaID() {
        return areaID;
    }

    private static int areaID;

    public static void setAreaID(int areaID) {
        AdminSearchArea.areaID = areaID;
    }

    private final ObservableList<AreasDTO> searchList = AreasDAO.getAreaEdit();

    public void showEdit(MouseEvent mouseEvent) throws IOException {
        if (table.getSelectionModel().getSelectedIndex() == -1) {
            Alerts.createCustomAlert(edit, editTable, "WARNING",
                    App.getLanguageProperties("adminNotChosen"), 250, 86, "alertFailure");
        } else {
            setAreaID(table.getSelectionModel().getSelectedItem().getAreaID());
            SceneManager.loadScene("main/resources/view/admin/adminAreaEdit.fxml", edit);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        ObservableList<AreasDTO> areasList = AreasDAO.getAreaEdit();
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        street.setCellValueFactory(new PropertyValueFactory<>("street"));
        city.setCellValueFactory(new PropertyValueFactory<>("city"));
        voivodeship.setCellValueFactory(new PropertyValueFactory<>("voivodeship"));


        table.setItems(areasList);


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
                    searchList.get(i).getStreet().toLowerCase().contains(searchedWord) ||
                    searchList.get(i).getCity().toLowerCase().contains(searchedWord) ||
                    searchList.get(i).getVoivodeship().toLowerCase().contains(searchedWord)
            ) {
                table.getItems().add(searchList.get(i));

            }
        }
    }
}
