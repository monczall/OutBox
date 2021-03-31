package main.java.controllers.courier;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import org.controlsfx.control.table.TableRowExpanderColumn;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CourierSecond implements Initializable {

    @FXML
    private TableView<Test> table;
    @FXML
    private TableColumn<?, ?> id;

    @FXML
    private TableColumn<?, ?> name;

    @FXML
    private TableColumn<?, ?> city;

    @FXML
    private TableColumn<?, ?> address;

    @FXML
    private TableColumn<?, ?> telephone;

    @FXML
    private TableColumn<?, ?> time;

    Pane pane;

    @Override
    public void initialize(URL url, ResourceBundle rb){
        TableRowExpanderColumn<Test> expanderRow = new TableRowExpanderColumn<Test>(this::createEditor);

        id.setCellValueFactory(new PropertyValueFactory<>("number"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        city.setCellValueFactory(new PropertyValueFactory<>("city"));
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
        telephone.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        time.setCellValueFactory(new PropertyValueFactory<>("date"));
        table.getColumns().add(expanderRow);

        ObservableList<Test> ov = FXCollections.observableArrayList();
        Test item = null;
        for(int i = 1; i < 10; i++)
        {
            item = new Test(i,"name" , "city", "address", "telephone", "date");
            ov.add(item);
        }
        ov.add(item);
        table.setItems(ov);
    }

    private Pane createEditor(TableRowExpanderColumn.TableRowDataFeatures<Test> arg){
        try{
            pane = FXMLLoader.load(getClass().getResource("../../../resources/view/courier/expandableRow.fxml"));
        }catch(IOException e){
            e.printStackTrace();
        }
        return pane;
    }
}
