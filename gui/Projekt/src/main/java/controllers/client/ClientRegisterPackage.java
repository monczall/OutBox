package main.java.controllers.client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import main.java.features.Alerts;
import main.java.features.Animations;
import main.java.features.ErrorHandler;
import org.controlsfx.control.textfield.CustomTextField;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ClientRegisterPackage implements Initializable {

    @FXML
    private AnchorPane appWindow;

    @FXML
    private AnchorPane packageSizePane;

    @FXML
    private ToggleButton smallPackage;

    @FXML
    private ToggleButton mediumPackage;

    @FXML
    private ToggleButton bigPackage;

    @FXML
    private Button btnNextRecipient;

    @FXML
    private AnchorPane recipientDetailsPane;

    @FXML
    private CustomTextField nameInput;

    @FXML
    private CustomTextField surnameInput;

    @FXML
    private CustomTextField emailInput;

    @FXML
    private CustomTextField streetInput;

    @FXML
    private CustomTextField cityInput;

    @FXML
    private CustomTextField provinceInput;

    @FXML
    private CustomTextField numberInput;

    @FXML
    private Button btnNextTime;

    @FXML
    private Button btnBackSize;

    @FXML
    private AnchorPane deliveryTimePane;

    @FXML
    private ComboBox<String> pickTimeOfDelivery;

    @FXML
    private TextArea additionalComment;

    @FXML
    private AnchorPane registerSummaryPane;

    @FXML
    private TextArea sumComment;

    @FXML
    private TextField sumType;

    @FXML
    private TextField sumSize;

    @FXML
    private TextField sumName;

    @FXML
    private TextField sumSurname;

    @FXML
    private TextField sumEmail;

    @FXML
    private TextField sumStreet;

    @FXML
    private TextField sumCity;

    @FXML
    private TextField sumProvince;

    @FXML
    private TextField sumNumber;

    @FXML
    private TextField sumTime;

    @FXML
    private Circle navCircle;

    @FXML
    private Button btnNextSummary;


    private ToggleGroup packageGroup = new ToggleGroup();

    ArrayList<CustomTextField> list = new ArrayList<CustomTextField>();

    ObservableList<String> timeOfDeliveryList = FXCollections.observableArrayList("10:30 - 15:30", "15:30 - 17:30", "17:30 - 21:00");

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        list.add(nameInput);
        list.add(surnameInput);
        list.add(emailInput);
        list.add(streetInput);
        list.add(cityInput);
        list.add(provinceInput);
        list.add(numberInput);

        pickTimeOfDelivery.setItems(timeOfDeliveryList);

        // Added three buttons to the group
        smallPackage.setToggleGroup(packageGroup);
        smallPackage.setUserData("Mała");

        mediumPackage.setToggleGroup(packageGroup);
        mediumPackage.setUserData("Średnia");

        bigPackage.setToggleGroup(packageGroup);
        bigPackage.setUserData("Duża");

        recipientDetailsPane.setTranslateX(+800);           // After panel is initialized three panes are moved 800 pixels to the right
        deliveryTimePane.setTranslateX(+800);               // for animation purposes
        registerSummaryPane.setTranslateX(+800);

        ErrorHandler.checkInputs(nameInput, "[a-zA-Z]+", "Imie powinno zawierać tylko litery");
        ErrorHandler.checkInputs(surnameInput, "[a-zA-Z]+", "Nazwisko powinno zawierać tylko litery");
        ErrorHandler.checkInputs(emailInput,"[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}", "Email powinien mieć poprawny format");
        ErrorHandler.checkInputs(streetInput, "[A-Za-z]{0,2}\\.?\\s?[A-Za-z]{2,40}\\s?\\-?[A-Za-z]{0,40}?\\s?\\-?[A-Za-z]{0,40}?\\s[0-9]{1,4}\\s?[A-Za-z]?\\s?\\/?\\s?[0-9]{0,5}", "Ulica powinna miec poprawny format");
        ErrorHandler.checkInputs(cityInput, "[A-Za-z]{2,40}\\s?\\-?\\s?[A-Za-z]{0,40}\\s?\\-?\\s?[A-Za-z]{0,40}", "Miasto powinno zawierać tylko litery");
        ErrorHandler.checkInputs(provinceInput, "[A-Za-z]{7,40}\\s?\\-?\\s?[A-Za-z]{0,40}", "Województwo powinno zawierać tylko litery");
        ErrorHandler.checkInputs(numberInput, "\\+?[0-9]{0,2}\\s?[0-9]{3}\\s?[0-9]{3}\\s?[0-9]{3}", "Imie powinno zawierać tylko cyfry");
    }



    // Button actions that leads forward
    @FXML
    void fromSizeToRecipient(ActionEvent event) {
        if(packageGroup.getSelectedToggle() != null){
            //System.out.println(packageGroup.getSelectedToggle().getUserData().toString());
            Animations.changePane(packageSizePane,recipientDetailsPane,-800,0.7);
            Animations.moveByX(navCircle, +114,0.7);
        }
        else{
            Alerts.createAlert(appWindow, btnNextRecipient,"WARNING","WYBIERZ ROZMIAR PACZKI");
        }
    }

    @FXML
    void fromRecipientToTime(ActionEvent event) {
        ErrorHandler.checkIfEmpty(list);
        if(!nameInput.getRight().isVisible() && !surnameInput.getRight().isVisible() && !emailInput.getRight().isVisible()
           && !streetInput.getRight().isVisible() && !cityInput.getRight().isVisible() && !provinceInput.getRight().isVisible()
           && !numberInput.getRight().isVisible()){
            Animations.changePane(recipientDetailsPane,deliveryTimePane,-800,0.7);
            Animations.moveByX(navCircle,+114,0.7);
        }
        else
            Alerts.createAlert(appWindow, btnNextTime,"WARNING","UZUPEŁNIJ LUB POPRAW POLA");
    }

    @FXML
    void fromTimeToSummary(ActionEvent event) {
        if(!pickTimeOfDelivery.getSelectionModel().isEmpty()){

            Animations.changePane(deliveryTimePane,registerSummaryPane,-800,0.7);
            Animations.moveByX(navCircle,+114,0.7);

            sumType.setText(packageGroup.getSelectedToggle().getUserData().toString());
            sumName.setText(nameInput.getText());
            sumSurname.setText(surnameInput.getText());
            sumCity.setText(cityInput.getText());
            sumComment.setText(additionalComment.getText());
            sumProvince.setText(provinceInput.getText());
            sumStreet.setText(streetInput.getText());
            sumTime.setText(pickTimeOfDelivery.getValue().toString());
            sumEmail.setText(emailInput.getText());
            sumNumber.setText(numberInput.getText());
        }
        else
            Alerts.createAlert(appWindow, btnNextSummary,"WARNING","WYBIECZ CZAS PRZYJAZDU");

    }

    // Button actions that leads backward
    @FXML
    void fromRecipientToSize(ActionEvent event) {
        Animations.changePane(recipientDetailsPane,packageSizePane,+800,0.7);
        Animations.moveByX(navCircle,-114,0.7);
    }

    @FXML
    void fromTimeToRecipient(ActionEvent event) {
        Animations.changePane(deliveryTimePane,recipientDetailsPane,+800,0.7);
        Animations.moveByX(navCircle,-114,0.7);
    }

    @FXML
    void fromSummaryToTime(ActionEvent event) {
        Animations.changePane(registerSummaryPane,deliveryTimePane,+800,0.7);
        Animations.moveByX(navCircle,-114,0.7);
    }

    @FXML
    void registerPackage(ActionEvent event) {
        Alerts.createAlert(appWindow, btnNextSummary,"CHECK","POMYŚLNIE ZAREJESTROWANO");
        //Database insert query here
    }
}
