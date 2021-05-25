package main.java.controllers.client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import main.java.controllers.auth.Login;
import main.java.dao.HibernateUtil;
import main.java.dao.PackageTypeDAO;
import main.java.entity.PackageHistory;
import main.java.entity.PackageType;
import main.java.entity.Packages;
import main.java.entity.UserInfos;
import main.java.features.Alerts;
import main.java.features.Animations;
import main.java.features.ErrorHandler;
import org.controlsfx.control.textfield.CustomTextField;
import org.hibernate.Session;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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

    @FXML
    private Text smallSize;

    @FXML
    private Text smallWeight;

    @FXML
    private Text smallPrice;

    @FXML
    private Text medSize;

    @FXML
    private Text medWeight;

    @FXML
    private Text medPrice;

    @FXML
    private Text bigSize;

    @FXML
    private Text bigWeight;

    @FXML
    private Text bigPrice;

    @FXML
    private Button btnRegister;

    private ToggleGroup packageGroup = new ToggleGroup();

    ArrayList<CustomTextField> list = new ArrayList<CustomTextField>();

    ObservableList<String> timeOfDeliveryList = FXCollections.observableArrayList("10:30 - 15:30", "15:30 - 17:30", "17:30 - 21:00", "Dowolny");

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        List<PackageType> listOfTypeInfo = PackageTypeDAO.getPackageTypes();

        // Reading a list with types of packages and converting them into right string
        // Size, weight and price
        smallSize.setText(listOfTypeInfo.get(0).getSize().replaceAll("x"," x ") + " cm");
        medSize.setText(listOfTypeInfo.get(1).getSize().replaceAll("x"," x ") + " cm");
        bigSize.setText(listOfTypeInfo.get(2).getSize().replaceAll("x"," x  ") + " cm");

        smallWeight.setText(listOfTypeInfo.get(0).getWeight() + " kg");
        medWeight.setText(listOfTypeInfo.get(1).getWeight() + " kg");
        bigWeight.setText(listOfTypeInfo.get(2).getWeight() + " kg");

        smallPrice.setText(listOfTypeInfo.get(0).getPrice() + " zł");
        medPrice.setText(listOfTypeInfo.get(1).getPrice() + " zł");
        bigPrice.setText(listOfTypeInfo.get(2).getPrice() + " zł");

        // Adding all the inputs for changing if they are empty
        list.add(nameInput);
        list.add(surnameInput);
        list.add(emailInput);
        list.add(streetInput);
        list.add(cityInput);
        list.add(provinceInput);
        list.add(numberInput);

        // Adding values inside Combobox and setting a start value to 'dowolny' type
        pickTimeOfDelivery.setItems(timeOfDeliveryList);
        pickTimeOfDelivery.setValue(timeOfDeliveryList.get(3));

        // Added three buttons to the group (it allows user to only pick one)
        smallPackage.setToggleGroup(packageGroup);
        smallPackage.setUserData("Mała");

        mediumPackage.setToggleGroup(packageGroup);
        mediumPackage.setUserData("Średnia");

        bigPackage.setToggleGroup(packageGroup);
        bigPackage.setUserData("Duża");

        // After panel is initialized three panes are moved 800 pixels to the right
        // for animation purposes
        recipientDetailsPane.setTranslateX(+800);
        deliveryTimePane.setTranslateX(+800);
        registerSummaryPane.setTranslateX(+800);

        //Checking all the inputs given while registering package
        ErrorHandler.checkInputs(nameInput, "[a-zA-Z]+",
                "Imie powinno zawierać tylko litery");

        ErrorHandler.checkInputs(surnameInput, "[a-zA-Z]+",
                "Nazwisko powinno zawierać tylko litery");

        ErrorHandler.checkInputs(emailInput,"[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}",
                "Email powinien mieć poprawny format");

        ErrorHandler.checkInputs(streetInput,
                "[A-Za-z]{0,2}\\.?\\s?[A-Za-z]{2,40}\\s?\\-?[A-Za-z]{0,40}?\\s?\\-?[A-Za-z]{0,40}?" +
                        "\\s[0-9]{1,4}\\s?[A-Za-z]?\\s?\\/?\\s?[0-9]{0,5}",
                "Ulica powinna miec poprawny format");

        ErrorHandler.checkInputs(cityInput, "[A-Za-z]{2,40}\\s?\\-?\\s?[A-Za-z]{0,40}\\s?\\-?\\s?[A-Za-z]{0,40}",
                "Miasto powinno zawierać tylko litery");

        ErrorHandler.checkInputs(provinceInput, "[A-Za-z]{7,40}\\s?\\-?\\s?[A-Za-z]{0,40}",
                "Województwo powinno zawierać tylko litery");

        ErrorHandler.checkInputs(numberInput, "\\+?[0-9]{0,2}\\s?[0-9]{3}\\s?[0-9]{3}\\s?[0-9]{3}",
                "Imie powinno zawierać tylko cyfry");
    }



    // Method handles moving pane from Size pane to Recipient pane
    @FXML
    void fromSizeToRecipient(ActionEvent event) {
        if(packageGroup.getSelectedToggle() != null) {
            Animations.changePane(packageSizePane,recipientDetailsPane,-800,0.7);
            Animations.moveByX(navCircle, +114,0.7);
        }
        else {
            Alerts.createAlert(appWindow, btnNextRecipient,"WARNING","WYBIERZ ROZMIAR PACZKI");
        }
    }

    // Method handles moving pane from Recipient pane to Time pane
    @FXML
    void fromRecipientToTime(ActionEvent event) {
        ErrorHandler.checkIfEmpty(list);
        if(!nameInput.getRight().isVisible() && !surnameInput.getRight().isVisible() && !emailInput.getRight().isVisible()
           && !streetInput.getRight().isVisible() && !cityInput.getRight().isVisible() && !provinceInput.getRight().isVisible()
           && !numberInput.getRight().isVisible()) {
            Animations.changePane(recipientDetailsPane,deliveryTimePane,-800,0.7);
            Animations.moveByX(navCircle,+114,0.7);
        }
        else {
            Alerts.createAlert(appWindow, btnNextTime,"WARNING","UZUPEŁNIJ LUB POPRAW POLA");
        }

    }

    // Method handles moving pane from Time pane to Summary pane
    @FXML
    void fromTimeToSummary(ActionEvent event) {
        if(!pickTimeOfDelivery.getSelectionModel().isEmpty()){

            Animations.changePane(deliveryTimePane,registerSummaryPane,-800,0.7);
            Animations.moveByX(navCircle,+114,0.7);

            // Setting all the inputs from previous panes to the final one
            // It allows use to check if everything was inputted correctly in one place
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

            if(sumType.getText().equals("Mała")) {
                sumSize.setText(smallSize.getText());
            }
            else if(sumType.getText().equals("Średnia")) {
                sumSize.setText(medSize.getText());
            }
            else {
                sumSize.setText(bigSize.getText());
            }

        }
        else {
            Alerts.createAlert(appWindow, btnNextSummary,"WARNING","WYBIECZ CZAS PRZYJAZDU");
        }

    }

    // Method handles moving pane from Recipient pane to Size pane
    @FXML
    void fromRecipientToSize(ActionEvent event) {
        Animations.changePane(recipientDetailsPane,packageSizePane,+800,0.7);
        Animations.moveByX(navCircle,-114,0.7);
    }

    // Method handles moving pane from Time pane to Recipient pane
    @FXML
    void fromTimeToRecipient(ActionEvent event) {
        Animations.changePane(deliveryTimePane,recipientDetailsPane,+800,0.7);
        Animations.moveByX(navCircle,-114,0.7);
    }

    // Method handles moving pane from Summary pane to Time pane
    @FXML
    void fromSummaryToTime(ActionEvent event) {
        Animations.changePane(registerSummaryPane,deliveryTimePane,+800,0.7);
        Animations.moveByX(navCircle,-114,0.7);
    }

    // Method handles moving pane from Summary pane to Time pane
    @FXML
    void registerPackage(ActionEvent event) {
        btnRegister.setDisable(true);

        //Generating package number
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        LocalDateTime now = LocalDateTime.now();
        Random rand = new Random();

        String packageNumber = dateTimeFormatter.format(now) + "/" + String.format("%06d", rand.nextInt(1000000));

        //Showing alert that everything went well
        Alerts.createAlert(appWindow, btnNextSummary,"CHECK","POMYŚLNIE ZAREJESTROWANO");

        //Executing database queries
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        Packages packages = new Packages();
        UserInfos userInfos = new UserInfos();
        PackageHistory packageHistory = new PackageHistory();

        userInfos.setName(sumName.getText());
        userInfos.setSurname(sumSurname.getText());
        userInfos.setPhoneNumber(sumNumber.getText());
        userInfos.setStreetAndNumber(sumStreet.getText());
        userInfos.setCity(sumCity.getText());
        userInfos.setVoivodeship(sumProvince.getText());

        session.save(userInfos);

        if(sumType.getText().equals("Mała")) {
            packages.setTypeId(1);
        }
        else if(sumType.getText().equals("Średnia")) {
            packages.setTypeId(2);
        }
        else {
            packages.setTypeId(3);;
        }

        packages.setUserId(Login.getUserID());
        packages.setUserInfoId(userInfos.getId());
        packages.setPackageNumber(packageNumber);
        packages.setEmail(sumEmail.getText());
        packages.setTimeOfPlannedDelivery(sumTime.getText());
        packages.setCourierId(null);
        packages.setAdditionalComment(additionalComment.getText());

        session.save(packages);

        dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        packageHistory.setPackageId(packages.getId());
        packageHistory.setStatus("Zarejestrowana");
        packageHistory.setDate(Timestamp.valueOf(dateTimeFormatter.format(now)));

        session.save(packageHistory);

        session.getTransaction().commit();
        session.close();
    }
}
