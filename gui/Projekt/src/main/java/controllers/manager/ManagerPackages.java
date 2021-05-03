package main.java.controllers.manager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import main.java.dao.*;
import main.java.entity.PackageHistory;
import main.java.entity.PackageType;
import main.java.entity.UserInfos;
import main.java.entity.Users;
import main.java.features.Alerts;

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
    private TableView<PackagesManager> tableView;

    @FXML
    private TableColumn<?, ?> tableNumber,tableStatus,tableType;

    @FXML
    private TableColumn<?, ?> tableSenderName,tableSenderSurname,tableSenderCity,tableSenderPhone;

    @FXML
    private TableColumn<?, ?> tableRecipientName,tableRecipientSurname,tableRecipientCity,tableRecipientPhone;

    @FXML
    private ComboBox<String> typeDelivery;

    List<PackageType> packageTypes;
    List<main.java.entity.Packages> packages;
    List<PackageHistory> packageHistory;
    List<UserInfos> recipient;
    List<UserInfos> sender;
    List<Users> users;

    ObservableList<PackagesManager> getDataPackages() {
        ObservableList<PackagesManager> packages = FXCollections.observableArrayList();/*
            packages.add(new PackagesManager("00000001", "W transporcie", "Duża", "Patryk",
                    "Kosiarz","Rzeszów","111222333","Filip",
                    "Carteloo","Nie Rzeszów","555444333"));

            packages.add(new PackagesManager("00000002", "Dostarczona do odbiorcy", "Mała",
                    "Adam", "Madam","Warszawa","123456123",
                    "Damian","Blade","Wrocław",
                    "444555111"));

            packages.add(new PackagesManager("00000003", "Oderbana z oddziału", "Średnia",
                    "Łukasz", "Monczall","Poznań","222999232",
                    "Adam","Madam","Warszawa",
                    "123456123"));

            packages.add(new PackagesManager("00000004", "Odebrana od klienta", "Duża", "Damian",
                    "Blade","Wrocław","444555111","Patryk",
                    "Kosiarz","Rzeszów","111222333"));*/
            return packages;
    }

    public void pomocna(){

        packages = PackagesDAO.readPackages();


        System.out.println("===================TYPY PACZEK====================");
        packageTypes = PackageTypeDAO.getTypeInfo();
        for(int i=0; i<packageTypes.size(); i++){
            System.out.println("Typ paczki (id:"+packageTypes.get(i).getId()+"): "+packageTypes.get(i).getSizeName());
        }

        System.out.println("===================INFO PACZKI====================");

        for(int i=0; i<packages.size(); i++){
            System.out.println("Numer paczki (id:"+packages.get(i).getId()+"): "+packages.get(i).getPackageNumber() + "IDodbiorcy("+ packages.get(i).getUserInfoId()+")");
        }

        System.out.println("===================STATUS PACZKI====================");
        packageHistory = PackageHistoryDAO.getHistoryForManager();

        for(int i=0; i<packageHistory.size(); i++){
            System.out.println("Status (id:"+packageHistory.get(i).getId()+"): "+packageHistory.get(i).getStatus());
        }

        System.out.println("===================NADAWCA====================");
        for(int i=0; i<packages.size(); i++){
            users = UsersDAO.getUsersId(packages.get(i).getUserId());
            sender = UserInfosDAO.getUserInfoByID(users.get(0).getUserInfoId());

            System.out.println("userinfoID: " + sender.get(0).getName() +" "+sender.get(0).getSurname()+" "+sender.get(0).getCity()+" "+sender.get(0).getPhoneNumber());
        }

        System.out.println("===================ODBIORCA====================");
        for(int i=0; i<packages.size(); i++){
            recipient = UserInfosDAO.getUserInfoByID(packages.get(i).getUserInfoId());
            System.out.println("ODBIORCA("+recipient.get(0).getId()+"): "+recipient.get(0).getName() + " " +recipient.get(0).getSurname()+ " " +recipient.get(0).getCity()+ " " +recipient.get(0).getPhoneNumber());
        }

        for(int i=0; i<packages.size(); i++){
            PackagesManager pack = new PackagesManager();

            System.out.println(packages.get(i).getPackageNumber());
            pack.setTableNumber(packages.get(i).getPackageNumber());
            pack.setTableStatus(PackageHistoryDAO.getStatusById(packages.get(i).getId()).get(0));
            pack.setTableType(PackageTypeDAO.getTypeById(packages.get(i).getTypeId()).get(0));

           // users = UsersDAO.getUsersId(packages.get(i).getUserId());
            pack.setTableSenderName(UserInfosDAO.getUserInfoByID(UsersDAO.getUsersId(packages.get(i).getUserId()).get(0).getUserInfoId()).get(0).getName());
            pack.setTableSenderSurname(UserInfosDAO.getUserInfoByID(UsersDAO.getUsersId(packages.get(i).getUserId()).get(0).getUserInfoId()).get(0).getSurname());
            pack.setTableSenderCity(UserInfosDAO.getUserInfoByID(UsersDAO.getUsersId(packages.get(i).getUserId()).get(0).getUserInfoId()).get(0).getCity());
            pack.setTableSenderPhone(UserInfosDAO.getUserInfoByID(UsersDAO.getUsersId(packages.get(i).getUserId()).get(0).getUserInfoId()).get(0).getPhoneNumber());

            pack.setTableRecipientName(UserInfosDAO.getUserInfoByID(packages.get(i).getUserInfoId()).get(0).getName());
            pack.setTableRecipientSurname(UserInfosDAO.getUserInfoByID(packages.get(i).getUserInfoId()).get(0).getSurname());
            pack.setTableRecipientCity(UserInfosDAO.getUserInfoByID(packages.get(i).getUserInfoId()).get(0).getCity());
            pack.setTableRecipientPhone(UserInfosDAO.getUserInfoByID(packages.get(i).getUserInfoId()).get(0).getPhoneNumber());

            tableView.getItems().add(pack);
        }
    }

    private ObservableList<String> statusObservable = FXCollections.observableArrayList("Odebrana od klienta","W transporcie","Dostarczona do oddziału","Oderbana z oddziału","W transporcie do innego oddziału","Dostarczona do odbiorcy");
    private ObservableList<String> deliveryObservable = FXCollections.observableArrayList("Mała","Średnia","Duża");

    public void findPackages(MouseEvent mouseEvent) {

        if(numerPackages.getText().toString().equals("") &&
                name.getText().toString().equals("") &&
                surname.getText().toString().equals("")){
            Alerts.createAlert(appWindow, findPackageButton,"WARNING","PODAJ JAKIŚ PARAMETR");
        }
        else{
            String sqlQuery ="FROM Packages WHERE package_number="+numerPackages.getText();
            /*
            if(!numerPackages.getText().toString().equals("")){
                sqlQuery+="packages AND ";
            }
            if(!name.getText().toString().equals("")){
                sqlQuery+="name AND ";
            }
            if(!surname.getText().toString().equals("")){
                sqlQuery+="surname";
            }
            */
            System.out.println("SZUKAM");
            packages = PackagesDAO.readPackagesForManager(sqlQuery);
            findPackages();
        }
    }

    public void findPackages(){
        System.out.println("ZNALAZłEM "+packages.size());

        for(int i=0; i<packages.size(); i++){
            PackagesManager pack = new PackagesManager();

            System.out.println(packages.get(i).getPackageNumber());
            pack.setTableNumber(packages.get(i).getPackageNumber());
            pack.setTableStatus(PackageHistoryDAO.getStatusById(packages.get(i).getId()).get(0));
            pack.setTableType(PackageTypeDAO.getTypeById(packages.get(i).getTypeId()).get(0));

            // users = UsersDAO.getUsersId(packages.get(i).getUserId());
            pack.setTableSenderName(UserInfosDAO.getUserInfoByID(UsersDAO.getUsersId(packages.get(i).getUserId()).get(0).getUserInfoId()).get(0).getName());
            pack.setTableSenderSurname(UserInfosDAO.getUserInfoByID(UsersDAO.getUsersId(packages.get(i).getUserId()).get(0).getUserInfoId()).get(0).getSurname());
            pack.setTableSenderCity(UserInfosDAO.getUserInfoByID(UsersDAO.getUsersId(packages.get(i).getUserId()).get(0).getUserInfoId()).get(0).getCity());
            pack.setTableSenderPhone(UserInfosDAO.getUserInfoByID(UsersDAO.getUsersId(packages.get(i).getUserId()).get(0).getUserInfoId()).get(0).getPhoneNumber());

            pack.setTableRecipientName(UserInfosDAO.getUserInfoByID(packages.get(i).getUserInfoId()).get(0).getName());
            pack.setTableRecipientSurname(UserInfosDAO.getUserInfoByID(packages.get(i).getUserInfoId()).get(0).getSurname());
            pack.setTableRecipientCity(UserInfosDAO.getUserInfoByID(packages.get(i).getUserInfoId()).get(0).getCity());
            pack.setTableRecipientPhone(UserInfosDAO.getUserInfoByID(packages.get(i).getUserInfoId()).get(0).getPhoneNumber());

            tableView.getItems().add(pack);
        }
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
        tableView.setItems(getDataPackages());
    }


    @Override
    public void initialize(URL url, ResourceBundle rb){
        tableView.setPlaceholder(new Label("Brak danych"));

        status.setItems(statusObservable);
        typeDelivery.setItems(deliveryObservable);

        tableView.setEditable(true);

        setTablePackages();

        pomocna();

    }
}
