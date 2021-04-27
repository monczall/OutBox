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
import main.java.entity.Packages;
import org.controlsfx.control.table.TableRowExpanderColumn;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CourierSecond implements Initializable {

    private static int id;
    private static String comment;
    Pane pane;
    private final ObservableList<Packages> packages = PackagesDAO.addTable();
    @FXML
    private TableView<Packages> table;
    @FXML
    private TableColumn<?, ?> packageNumber;
    @FXML
    private TableColumn<?, ?> name;
    @FXML
    private TableColumn<?, ?> surname;
    @FXML
    private TableColumn<?, ?> city;
    @FXML
    private TableColumn<?, ?> address;
    @FXML
    private TableColumn<?, ?> telephone;
    @FXML
    private TableColumn<?, ?> state;
    @FXML
    private TableColumn<?, ?> time;
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        TableRowExpanderColumn<Packages> expanderRow = new TableRowExpanderColumn<Packages>(this::createEditor);

        packageNumber.setCellValueFactory(new PropertyValueFactory<>("packageNumber"));
        time.setCellValueFactory(new PropertyValueFactory<>("timeOfPlannedDelivery"));
        state.setCellValueFactory(new PropertyValueFactory<>("status"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        surname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        city.setCellValueFactory(new PropertyValueFactory<>("city"));
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
        telephone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        table.getColumns().add(expanderRow);
        table.setItems(PackagesDAO.addTable());
        table.getSelectionModel().select(0);
    }

    @FXML
    void search(KeyEvent event) {
        table.getItems().clear();
        for (int i = 0; i < packages.size(); i++) {
            if (packages.get(i).getPackageNumber().contains(searchField.getText()) ||
                    packages.get(i).getName().contains(searchField.getText()) ||
                    packages.get(i).getSurname().contains(searchField.getText()) ||
                    packages.get(i).getCity().contains(searchField.getText()) ||
                    packages.get(i).getAddress().contains(searchField.getText()) ||
                    packages.get(i).getPhone().contains(searchField.getText()) ||
                    packages.get(i).getStatus().contains(searchField.getText()) ||
                    packages.get(i).getTimeOfPlannedDelivery().contains(searchField.getText())) {
                table.getItems().add(packages.get(i));
            }
        }
    }

    private Pane createEditor(TableRowExpanderColumn.TableRowDataFeatures<Packages> arg) {
        try {
            table.getSelectionModel().select(arg.getTableRow().getIndex());
            setId(table.getItems().get(arg.getTableRow().getIndex()).getUserId());
            setComment(table.getItems().get(arg.getTableRow().getIndex()).getAdditionalComment());
            pane = FXMLLoader.load(getClass().getResource("../../../resources/view/courier/expandableRow.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pane;
    }
}
