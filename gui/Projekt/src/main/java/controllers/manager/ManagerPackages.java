package main.java.controllers.manager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import main.java.features.Alerts;

import java.net.URL;
import java.util.ResourceBundle;

public class ManagerPackages implements Initializable {

    @FXML
    private AnchorPane appWindow;

    @FXML
    private Button findPackageButton;

    @FXML
    private TableView<?> tableView;

    @FXML
    private TextField numerPackages;

    @FXML
    private TextField name;

    @FXML
    private TextField surname;

    @FXML
    private DatePicker datePosting;

    @FXML
    private ComboBox<String> status;

    @FXML
    private DatePicker dateReceipt;

    @FXML
    private TextField city;

    @FXML
    private ComboBox<String> typeDelivery;

    private ObservableList<String> statusObservable = FXCollections.observableArrayList("Odebrana od klienta","W transporcie","Dostarczona do oddziału","Oderbana z oddziału","W transporcie do innego oddziału","Dostarczona do odbiorcy");
    private ObservableList<String> deliveryObservable = FXCollections.observableArrayList("Mała","Średnia","Duża");

    public void findPackages(MouseEvent mouseEvent) {

        if(numerPackages.getText().toString().equals("") &&
                name.getText().toString().equals("") &&
                surname.getText().toString().equals("") &&
                city.getText().toString().equals("") &&
                datePosting.getValue() == null &&
                dateReceipt.getValue() == null &&
                status.getValue() == null &&
                typeDelivery.getValue() == null){
            Alerts.createAlert(appWindow, findPackageButton,"WARNING","PODAJ JAKIŚ PARAMETR");
        }
        else{
            System.out.println(SQLquery());
        }
    }

    String SQLquery(){
        String SQLqueryString = "SELECT * FROM PACKAGES WHERE ";

        if(!numerPackages.getText().toString().equals("")){
            SQLqueryString += "numberpackages="+numerPackages.getText().toString();
            return SQLqueryString;
        }

        return SQLqueryString;
    }
    @Override
    public void initialize(URL url, ResourceBundle rb){
        tableView.setPlaceholder(new Label("Brak danych"));

        status.setItems(statusObservable);
        typeDelivery.setItems(deliveryObservable);
    }
}
