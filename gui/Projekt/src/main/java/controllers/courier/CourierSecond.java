package main.java.controllers.courier;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import main.java.dao.PackagesDAO;
import main.java.entity.Packages;
import org.controlsfx.control.table.TableRowExpanderColumn;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CourierSecond implements Initializable {

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

    Pane pane;
    private static int id;
    private static String comment;

    public static int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static String getComment() {
        return comment;
    }

    public static void setComment(String comment) {
        CourierSecond.comment = comment;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb){
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
    }

    private Pane createEditor(TableRowExpanderColumn.TableRowDataFeatures<Packages> arg){
        try{
            table.getSelectionModel().select(arg.getTableRow().getIndex());
            setId(table.getItems().get(arg.getTableRow().getIndex()).getUserId());
            setComment(table.getItems().get(arg.getTableRow().getIndex()).getAdditionalComment());
            pane = FXMLLoader.load(getClass().getResource("../../../resources/view/courier/expandableRow.fxml"));
        }catch(IOException e){
            e.printStackTrace();
        }
        return pane;
    }
}
