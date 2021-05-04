package main.java.controllers.courier;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import main.java.dao.PackagesDAO;
import main.java.entity.*;
import org.controlsfx.control.table.TableRowExpanderColumn;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class CourierSecond implements Initializable {

    private static int id;
    private static String comment;
    private static int packageId;
    private static String status;

    public static int getPackageId() {
        return packageId;
    }

    public static void setPackageId(int packageId) {
        CourierSecond.packageId = packageId;
    }

    Pane pane;
    private final ObservableList<PackagesDTO> packages = PackagesDAO.addTable();

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

    public void updateTable()
    {
        table.getItems().clear();
        table.setItems(PackagesDAO.addTable());
    }
    TableRowExpanderColumn<PackagesDTO> expanderRow = new TableRowExpanderColumn<PackagesDTO>(this::createEditor);

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
        table.getSelectionModel().select(0);

    }


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

    private Pane createEditor(TableRowExpanderColumn.TableRowDataFeatures<PackagesDTO> arg) {
        try {
            table.getSelectionModel().select(arg.getTableRow().getIndex());
            setId(table.getItems().get(arg.getTableRow().getIndex()).getUserInfosId());
            setPackageId(table.getItems().get(arg.getTableRow().getIndex()).getPackagesId());
            setComment(table.getItems().get(arg.getTableRow().getIndex()).getAdditionalComment());
            setStatus(table.getItems().get(arg.getTableRow().getIndex()).getStatus());
            pane = FXMLLoader.load(getClass().getResource("../../../resources/view/courier/expandableRow.fxml"));
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

//        for (PackagesExtended ent : table.getItems()
//             ) {
//            if(expanderRow.getExpandedProperty(ent).getValue() == true){
//                expanderRow.toggleExpanded(i);
//            }
//            i++;
//        }


        return pane;
    }
}
