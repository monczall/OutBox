package main.java.controllers.manager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import main.java.features.Alerts;

import javax.mail.Session;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ManagerPackages implements Initializable {

    @FXML
    private AnchorPane appWindow;

    @FXML
    private Button findPackageButton;

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
    private TableView<Packages> tableView;

    @FXML
    private TableColumn<?, ?> tableNumber,tableStatus,tableType;

    @FXML
    private TableColumn<?, ?> tableSenderName,tableSenderSurname,tableSenderCity,tableSenderPhone;

    @FXML
    private TableColumn<?, ?> tableRecipientName,tableRecipientSurname,tableRecipientCity,tableRecipientPhone;

    Packages package1s = new Packages("00000001", "W transporcie", "Duża", "Patryk", "Kosiarz","Rzeszów","111222333","Filip","Carteloo","Nie Rzeszów","555444333");
    final ObservableList<Packages> data = FXCollections.observableArrayList(
            new Packages("00000001", "W transporcie", "Duża", "Patryk", "Kosiarz","Rzeszów","111222333","Filip","Carteloo","Nie Rzeszów","555444333"),
            new Packages("00000002", "Dostarczona do odbiorcy", "Mała", "Adam", "Madam","Warszawa","123456123","Damian","Blade","Wrocław","444555111"),
            new Packages("00000003", "Oderbana z oddziału", "Średnia", "Łukasz", "Monczall","Poznań","222999232","Adam","Madam","Warszawa","123456123"),
            new Packages("00000004", "Odebrana od klienta", "Duża", "Damian", "Blade","Wrocław","444555111","Patryk","Kosiarz","Rzeszów","111222333")
    );
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
            //here find package
            //System.out.println(SQLquery());
        }
    }

    //test
    String SQLquery(){
        String SQLqueryString = "SELECT * FROM PACKAGES WHERE ";

        if(!numerPackages.getText().toString().equals("")){
            SQLqueryString += "numberpackages="+numerPackages.getText().toString();
            return SQLqueryString;
        }

        return SQLqueryString;
    }

    public void setTablePackages()
    {

        tableNumber.setCellValueFactory(new PropertyValueFactory<>("tableNumber"));
        tableStatus.setCellValueFactory(new PropertyValueFactory<>("tableStatus"));
        tableType.setCellValueFactory(new PropertyValueFactory<>("tableType"));
        tableSenderName.setCellValueFactory(new PropertyValueFactory<>("tableSenderName"));
        tableSenderSurname.setCellValueFactory(new PropertyValueFactory<>("tableSenderSurname"));
        tableSenderCity.setCellValueFactory(new PropertyValueFactory<>("tableSenderCity"));
        tableSenderPhone.setCellValueFactory(new PropertyValueFactory<>("tableSenderPhone"));
        tableRecipientName.setCellValueFactory(new PropertyValueFactory<>("tableRecipientName"));
        tableRecipientSurname.setCellValueFactory(new PropertyValueFactory<>("tableRecipientSurname"));
        tableRecipientCity.setCellValueFactory(new PropertyValueFactory<>("tableRecipientCity"));
        tableRecipientPhone.setCellValueFactory(new PropertyValueFactory<>("tableRecipientPhone"));
    }


    @Override
    public void initialize(URL url, ResourceBundle rb){
        tableView.setPlaceholder(new Label("Brak danych"));

        status.setItems(statusObservable);
        typeDelivery.setItems(deliveryObservable);

        tableView.setEditable(true);

        setTablePackages();

        tableView.setItems(data);

    }
}
