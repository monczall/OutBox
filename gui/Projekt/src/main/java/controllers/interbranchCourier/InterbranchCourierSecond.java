package main.java.controllers.interbranchCourier;

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
import main.java.controllers.auth.Login;
import main.java.dao.PackageHistoryDAO;
import main.java.dao.PackagesDAO;
import main.java.dao.UsersDAO;
import main.java.entity.*;
import org.controlsfx.control.table.TableRowExpanderColumn;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

public class InterbranchCourierSecond implements Initializable {

    private static int id;
    private static String comment;
    private static int packageId;
    private static String status;
    private Pane pane;

    @FXML
    private TableView<PackagesDTO> table;
    TableRowExpanderColumn<PackagesDTO> expanderRow = new TableRowExpanderColumn<PackagesDTO>(this::createEditor);
    @FXML
    private TableColumn<?, ?> packageNumber;

    @FXML
    private TableColumn<?, ?> packageType;

    @FXML
    private TableColumn<?, ?> senderName;

    @FXML
    private TableColumn<?, ?> senderSurname;

    @FXML
    private TableColumn<?, ?> recipentName;

    @FXML
    private TableColumn<?, ?> recipentSurname;

    @FXML
    private TableColumn<?, ?> packageStatus;
    @FXML
    private TextField searchField;

    public static int getPackageId() {
        return packageId;
    }

    public static void setPackageId(int packageId) {
        InterbranchCourierSecond.packageId = packageId;
    }

    public static int getId() {
        return id;
    }

    public void setId(int id) {
        InterbranchCourierSecond.id = id;
    }

    public static String getComment() {
        return comment;
    }

    public static void setComment(String comment) {
        InterbranchCourierSecond.comment = comment;
    }

    public static String getStatus() {
        return status;
    }

    public static void setStatus(String status) {
        InterbranchCourierSecond.status = status;
    }

    public void updateTable() {
        table.getItems().clear();

        table.setItems(getPackages());
    }

    private ObservableList<PackagesDTO> getPackages(){
        ObservableList<PackagesDTO> packList = PackagesDAO.getPackagesWithStatusAndNames(Login.getUserID());

        return packList;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        packageNumber.setCellValueFactory(new PropertyValueFactory<>("packageNumber"));
        packageType.setCellValueFactory(new PropertyValueFactory<>("sizeName"));
        senderName.setCellValueFactory(new PropertyValueFactory<>("name"));
        senderSurname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        recipentName.setCellValueFactory(new PropertyValueFactory<>("recipentName"));
        recipentSurname.setCellValueFactory(new PropertyValueFactory<>("recipentSurname"));
        packageStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        table.getColumns().add(expanderRow);
        updateTable();
    }


    @FXML
    void search(KeyEvent event) {
        table.getItems().clear();
        ObservableList<PackagesDTO> packages = getPackages();
        String searchedWord = searchField.getText().toLowerCase();
        for (int i = 0; i < packages.size(); i++) {
            if (packages.get(i).getPackageNumber().toLowerCase().contains(searchedWord) ||
                    packages.get(i).getSizeName().toLowerCase().contains(searchedWord) ||
                    packages.get(i).getName().toLowerCase().contains(searchedWord) ||
                    packages.get(i).getSurname().toLowerCase().contains(searchedWord) ||
                    packages.get(i).getRecipentName().toLowerCase().contains(searchedWord) ||
                    packages.get(i).getRecipentSurname().toLowerCase().contains(searchedWord) ||
                    packages.get(i).getStatus().toLowerCase().contains(searchedWord)){
                table.getItems().add(packages.get(i));
            }
        }
    }

    private Pane createEditor(TableRowExpanderColumn.TableRowDataFeatures<PackagesDTO> arg) {
        try {
            int selectedIndex = arg.getTableRow().getIndex();
            table.getSelectionModel().select(selectedIndex);
            setId(table.getItems().get(arg.getTableRow().getIndex()).getUserInfosId());
            setPackageId(table.getItems().get(arg.getTableRow().getIndex()).getPackagesId());

            setStatus(table.getItems().get(arg.getTableRow().getIndex()).getStatus());

            pane = FXMLLoader.load(getClass().getResource("../../../resources/view/interbranchCourier/interbranchCourierExpandableRow.fxml"));
            Button button = new Button("Zatwierdź");
            button.setLayoutX(616);
            button.setLayoutY(78);
            button.setPrefHeight(25);
            button.setPrefWidth(90);
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    String status = InterbranchExpendableRow.getStatusReturned();
                    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    LocalDateTime now = LocalDateTime.now();
                    if (!status.equals("") && !status.equals(arg.getValue().getStatus())) {
                        PackageHistoryDAO.updateStatus(getPackageId(), status,
                                Timestamp.valueOf(dateTimeFormatter.format(now)));
                        if (status.equals(PackageStatus.IN_SORTING_DEPARTMENT.displayName())) {
                            List<Users> usersList = UsersDAO.getCouriers("Kurier");
                            for (int i = 0; i < usersList.size(); i++) {
                                if (table.getItems().get(selectedIndex).getVoivodeship().equals(usersList.get(i).getAreasByAreaId().getVoivodeship())) {
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