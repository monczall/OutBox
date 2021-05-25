package main.java.controllers.courier;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import main.java.App;
import main.java.SceneManager;
import main.java.controllers.auth.Login;
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

public class CourierSecond implements Initializable {

    private static int id;
    private static String comment;
    private static int packageId;
    private static String status;
    private final ObservableList<PackagesDTO> packages = changeLanguage();
    private Pane pane;

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

    public static int getPackageId() {
        return packageId;
    }

    public static void setPackageId(int packageId) {
        CourierSecond.packageId = packageId;
    }

    public static int getId() {
        return id;
    }

    public void setId(int id) {
        CourierSecond.id = id;
    }

    public static String getComment() {
        return comment;
    }

    public static void setComment(String comment) {
        CourierSecond.comment = comment;
    }

    public static String getStatus() {
        return status;
    }

    public static void setStatus(String status) {
        CourierSecond.status = status;
    }


    /**
     * method that clears table and populating it again
     */
    public void updateTable() {
        ObservableList<PackagesDTO> statuses = changeLanguage();
        table.getItems().clear();
        table.setItems(statuses);
    }

    private ObservableList<PackagesDTO> changeLanguage(){
        ObservableList<PackagesDTO> statuses = PackagesDAO.getPackagesWithStatusById(Login.getUserID());
        PackageStatus[] status = PackageStatus.values();
        if (Preference.readPreference("language").equals("english")) {
            for (int i = 0; i < statuses.size(); i++) {
                for (int j = 0; j < status.length; j++) {
                    if (statuses.get(i).getStatus().equals(status[j].displayName())) {
                        statuses.get(i).setStatus(status[j].engDisplayName());
                    }
                }
            }
        }
        return statuses;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

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


    }

    /**
     * this method searches for inserted word in whole table after every key released
     *
     * @param event
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
     * method that loads expanded row to table and populating it with data from database
     *
     * @param arg
     * @return Pane with information of package
     */
    private Pane createEditor(TableRowExpanderColumn.TableRowDataFeatures<PackagesDTO> arg) {
        try {
            int selectedIndex = arg.getTableRow().getIndex();
            table.getSelectionModel().select(selectedIndex);
            setId(table.getItems().get(selectedIndex).getUserInfosId());
            setPackageId(table.getItems().get(arg.getTableRow().getIndex()).getPackagesId());
            setComment(table.getItems().get(arg.getTableRow().getIndex()).getAdditionalComment());
            setStatus(table.getItems().get(arg.getTableRow().getIndex()).getStatus());
            FXMLLoader loader = new FXMLLoader();
            ResourceBundle resourceBundle;
            Preference pref = new Preference();
            if(pref.readPreference("language").equals("english"))
                resourceBundle = ResourceBundle.getBundle("main.resources.languages.lang_en");
            else {
                resourceBundle = ResourceBundle.getBundle("main.resources.languages.lang_pl");
            }
            loader.setLocation(getClass().getResource("../../../resources/view/courier/expandableRow.fxml"));
            loader.setResources(resourceBundle);
            pane = loader.load();
            Button button = new Button(App.getLanguageProperties("confirmText"));
            button.setLayoutX(455);
            button.setLayoutY(76);
            button.setPrefHeight(25);
            button.setPrefWidth(90);
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
                    if (!status.equals("") && !status.equals(arg.getValue().getStatus())) {
                        PackageHistoryDAO.updateStatus(getPackageId(), status,
                                Timestamp.valueOf(dateTimeFormatter.format(now)));
                        if (status.equals(PackageStatus.IN_SORTING_DEPARTMENT.displayName())) {
                            List<Users> usersList = UsersDAO.getCouriers("Kurier Międzyoddziałowy");
                            for (int i = 0; i < usersList.size(); i++) {
                                if (UsersDAO.readUserInfoById(PackagesDAO.getPackagesById(table.getItems().get(selectedIndex)
                                        .getPackagesId()).get(0).getUserId()).get(0).getVoivodeship().equals(usersList.get(i).
                                        getAreasByAreaId().getVoivodeship())) {
                                    PackagesDAO.updateCourierId(table.getItems().get(selectedIndex).getPackagesId(),
                                            usersList.get(i).getId());
                                }
                            }
                        }
                        updateTable();
                    }
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

        for (int i = 0; i < table.getItems().size(); i++) {
            if (expanderRow.getExpandedProperty(table.getItems().get(i)).getValue() &&
                    arg.getTableRow().getIndex() != i) {
                expanderRow.toggleExpanded(i);
            }
        }
        return pane;
    }
}