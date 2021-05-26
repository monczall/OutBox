package main.java.controllers.manager;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import main.java.App;
import main.java.controllers.auth.Login;
import main.java.dao.PackagesDAO;
import main.java.dao.UserInfosDAO;
import main.java.dao.UsersDAO;
import main.java.entity.*;
import main.java.features.Preference;
import org.controlsfx.control.table.TableRowExpanderColumn;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class ManagerPackages implements Initializable {

    private static int id;
    private static String comment;
    private static int packageId;
    private static String status;

    /**
     * reurn package id
     * @return id
     */
    public static int getPackageId() {
        return packageId;
    }

    /**
     * set package id
     * @param packageId
     */
    public static void setPackageId(int packageId) {
        ManagerPackages.packageId = packageId;
    }

    Pane pane;

    private final ObservableList<PackagesDTO> packages = changeLanguage();

    @FXML
    private TableView<PackagesDTO> table;
    @FXML
    private TableColumn<Packages, String> packageNumber;
    @FXML
    private TableColumn<UserInfos, String> name;
    @FXML
    private TableColumn<UserInfos, String> surname;
    @FXML
    private TableColumn<UserInfos, String> city;
    @FXML
    private TableColumn<UserInfos, String> address;
    @FXML
    private TableColumn<UserInfos, String> telephone;
    @FXML
    private TableColumn<PackageHistory, String> state;
    @FXML
    private TableColumn<Packages, Timestamp> time;
    @FXML
    private TextField searchField;

    /**
     *
     * @return id
     */
    public static int getId() {
        return id;
    }

    /**
     * set id
     * @param id
     */
    public void setId(int id) {
        ManagerPackages.id = id;
    }

    /**
     *
     * @return comment
     */
    public static String getComment() {
        return comment;
    }

    /**
     * set comment
     * @param comment
     */
    public static void setComment(String comment) {
        ManagerPackages.comment = comment;
    }

    /**
     *
     * @return status
     */
    public static String getStatus() {
        return status;
    }

    /**
     * set status
     * @param status
     */
    public static void setStatus(String status) {
        ManagerPackages.status = status;
    }

    private ObservableList<PackagesDTO> changeLanguage(){
        String voivodeship = UserInfosDAO.getUserInfoByID(Login.getUserInfoID()).get(0).getVoivodeship();
        ObservableList<PackagesDTO> translatedPackages = PackagesDAO.getPackagesWithStatus();

        for (int i = 0; i < translatedPackages.size(); i++) {

            if(!translatedPackages.get(i).getVoivodeship().equals(voivodeship) &&
                    !UsersDAO.readUserInfoById(translatedPackages.get(i).
                    getUserInfosId()).get(0).getVoivodeship().equals(voivodeship)){
                System.out.println(translatedPackages.get(i).getPackagesId());
                translatedPackages.remove(i);
            }
        }
        PackageStatus[] status = PackageStatus.values();
        if (Preference.readPreference("language").equals("english")) {
            for (PackagesDTO packagesDTO : translatedPackages) {
                for (PackageStatus packageStatus : status) {
                    if (packagesDTO.getStatus().equals(packageStatus.displayName())) {
                        packagesDTO.setStatus(packageStatus.engDisplayName());
                    }
                }
            }
        }
        return translatedPackages;
    }
    /**
     * update data from table
     */
    public void updateTable()
    {
        table.getItems().clear();
        table.setItems(changeLanguage());
    }
    TableRowExpanderColumn<PackagesDTO> expanderRow = new TableRowExpanderColumn<PackagesDTO>(this::createEditor);

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //set data in table
        table.setPlaceholder(new Label(App.getLanguageProperties("noDataToDisplay")));
        packageNumber.setCellValueFactory(new PropertyValueFactory<>("packageNumber"));
        time.setCellValueFactory(new PropertyValueFactory<>("timeOfPlannedDelivery"));
        state.setCellValueFactory(new PropertyValueFactory<>("status"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        surname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        city.setCellValueFactory(new PropertyValueFactory<>("city"));
        address.setCellValueFactory(new PropertyValueFactory<>("streetAndNumber"));
        telephone.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        table.getColumns().add(expanderRow);
        updateTable();
        table.getSelectionModel().select(0);
    }

    /**
     * the method finds packages after each click
     */
    @FXML
    void search(KeyEvent event) {
        table.getItems().clear();
        String searchedWord = searchField.getText().toLowerCase();
        for (int i = 0; i < packages.size(); i++) {
            if (packages.get(i).getPackageNumber().toLowerCase().contains(searchedWord) ||
                    packages.get(i).getName().toLowerCase().contains(searchedWord) ||
                    packages.get(i).getSurname().toLowerCase().contains(searchedWord) ||
                    packages.get(i).getCity().toLowerCase().contains(searchedWord) ||
                    packages.get(i).getStreetAndNumber().toLowerCase().contains(searchedWord) ||
                    packages.get(i).getPhoneNumber().toLowerCase().contains(searchedWord) ||
                    packages.get(i).getStatus().toLowerCase().contains(searchedWord) ||
                    packages.get(i).getTimeOfPlannedDelivery().toLowerCase().contains(searchedWord)) {
                table.getItems().add(packages.get(i));
            }
        }
    }

    /**
     * the method returns a panel with additional information about the package
     * @param arg
     * @return pane
     */
    private Pane createEditor(TableRowExpanderColumn.TableRowDataFeatures<PackagesDTO> arg) {
        try {
            table.getSelectionModel().select(arg.getTableRow().getIndex());
            setId(table.getItems().get(arg.getTableRow().getIndex()).getUserInfosId());
            setPackageId(table.getItems().get(arg.getTableRow().getIndex()).getPackagesId());
            setComment(table.getItems().get(arg.getTableRow().getIndex()).getAdditionalComment());
            setStatus(table.getItems().get(arg.getTableRow().getIndex()).getStatus());
            pane = FXMLLoader.load(getClass().getResource("../../../resources/view/manager/expandableRow.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        arg.isExpanded();

        for (int i = 0; i < table.getItems().size(); i++)
        {
            if(expanderRow.getExpandedProperty(table.getItems().get(i)).getValue() &&
                    arg.getTableRow().getIndex() != i)
            {
                expanderRow.toggleExpanded(i);
            }
        }

        return pane;
    }
}