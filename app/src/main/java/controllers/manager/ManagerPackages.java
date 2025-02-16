package main.java.controllers.manager;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import main.java.App;
import main.java.controllers.auth.Login;
import main.java.controllers.courier.ExpendableRow;
import main.java.dao.PackageHistoryDAO;
import main.java.dao.PackagesDAO;
import main.java.dao.UsersDAO;
import main.java.entity.*;
import main.java.features.Preference;
import org.controlsfx.control.table.TableRowExpanderColumn;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

public class ManagerPackages implements Initializable {

    private static int id;
    private static String comment;
    private static int packageId;
    private static String status;
    private final ObservableList<PackagesDTO> packages = changeLanguage();
    Pane pane;
    @FXML
    private TableView<PackagesDTO> table;
    TableRowExpanderColumn<PackagesDTO> expanderRow = new TableRowExpanderColumn<PackagesDTO>(this::createEditor);
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
     * reurn package id
     *
     * @return id
     */
    public static int getPackageId() {
        return packageId;
    }

    /**
     * set package id
     *
     * @param packageId packageid
     */
    public static void setPackageId(int packageId) {
        ManagerPackages.packageId = packageId;
    }

    /**
     * @return id
     */
    public static int getId() {
        return id;
    }

    /**
     * set id
     *
     * @param id id
     */
    public void setId(int id) {
        ManagerPackages.id = id;
    }

    /**
     * @return comment
     */
    public static String getComment() {
        return comment;
    }

    /**
     * set comment
     *
     * @param comment comment
     */
    public static void setComment(String comment) {
        ManagerPackages.comment = comment;
    }

    /**
     * @return status
     */
    public static String getStatus() {
        return status;
    }

    /**
     * set status
     *
     * @param status status
     */
    public static void setStatus(String status) {
        ManagerPackages.status = status;
    }

    private ObservableList<PackagesDTO> changeLanguage() {
        String areaName = UsersDAO.getUsersById(Login.getUserID()).get(0).getAreasByAreaId().getName();
        ObservableList<PackagesDTO> translatedPackages =
                PackagesDAO.getPackagesByVoivodeship(UsersDAO.getUsersById(Login.getUserID()).get(0).getAreasByAreaId().getVoivodeship());

        if (!areaName.contains(UsersDAO.getUsersById(Login.getUserID()).get(0).getAreasByAreaId().getVoivodeship())) {
            for (int i = 0; i < translatedPackages.size(); i++) {
                int packId = translatedPackages.get(i).getPackagesId();

                if (!PackagesDAO.getPackagesById(packId).get(0).getUsersByUserId().getUserInfosByUserInfoId().getCity().equals(areaName)
                        && !PackagesDAO.getPackagesById(packId).get(0).getUserInfosByUserInfoId().getCity().equals(areaName)) {
                    translatedPackages.remove(i);
                    i--;
                }
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
    public void updateTable() {
        table.getItems().clear();
        table.setItems(changeLanguage());
    }

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
     *
     * @return pane
     */
    private Pane createEditor(TableRowExpanderColumn.TableRowDataFeatures<PackagesDTO> arg) {
        try {
            int selectedIndex = arg.getTableRow().getIndex();
            table.getSelectionModel().select(arg.getTableRow().getIndex());
            setId(table.getItems().get(arg.getTableRow().getIndex()).getUserInfosId());
            setPackageId(table.getItems().get(arg.getTableRow().getIndex()).getPackagesId());
            setComment(table.getItems().get(arg.getTableRow().getIndex()).getAdditionalComment());
            setStatus(table.getItems().get(arg.getTableRow().getIndex()).getStatus());
            // pane = FXMLLoader.load(getClass().getClassLoader().getResource("main/resources/view/manager/expandableRow.fxml"));
            FXMLLoader loader = new FXMLLoader();
            ResourceBundle resourceBundle;
            Preference pref = new Preference();
            if (Preference.readPreference("language").equals("english"))
                resourceBundle = ResourceBundle.getBundle("main.resources.languages.lang_en");
            else {
                resourceBundle = ResourceBundle.getBundle("main.resources.languages.lang_pl");
            }
            loader.setLocation(getClass().getClassLoader().getResource("main/resources/view/manager/expandableRow.fxml"));
            loader.setResources(resourceBundle);
            pane = loader.load();
            Button button = new Button(App.getLanguageProperties("deliver"));
            button.setLayoutX(390);
            button.setLayoutY(76);
            button.setPrefHeight(25);
            button.setPrefWidth(130);
            if(!table.getItems().get(selectedIndex).getStatus().equals(PackageStatus.WAITING_IN_BRANCH.toString())){
                button.setVisible(false);
            }
            button.setOnAction(new EventHandler<ActionEvent>() {
                /**
                 * method which updates status in packages and if specified conditions are
                 * fulfilled then updates courier which is assigned to package
                 * @param event
                 */
                @Override
                public void handle(ActionEvent event) {
                    String status = ExpendableRow.getStatusReturned();
                    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    LocalDateTime now = LocalDateTime.now();
                    PackageHistoryDAO.updateStatus(getPackageId(), PackageStatus.DELIVERED.displayName(),
                                Timestamp.valueOf(dateTimeFormatter.format(now)));
                    updateTable();
                }
            });

            pane.getChildren().add(button);
            FontAwesomeIconView arrow = new FontAwesomeIconView();      //Creating icon
            arrow.setGlyphName("CHECK");
            arrow.setSize("12");
            arrow.getStyleClass().add("iconNext");
            button.setGraphic(arrow);
        } catch (IOException e) {
            e.printStackTrace();
        }
        arg.isExpanded();

        for (int i = 0; i < table.getItems().size(); i++) {
            if (expanderRow.getExpandedProperty(table.getItems().get(i)).getValue() &&
                    arg.getTableRow().getIndex() != i) {
                expanderRow.toggleExpanded(i);
            }
        }

        return pane;
    }
}