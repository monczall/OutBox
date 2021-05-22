package main.java.controllers.client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import main.java.App;
import main.java.SceneManager;
import main.java.controllers.auth.Encryption;
import main.java.controllers.auth.Login;
import main.java.dao.UserInfosDAO;
import main.java.dao.UsersDAO;
import main.java.entity.UserInfos;
import main.java.features.Alerts;
import main.java.features.Animations;
import main.java.features.ErrorHandler;
import main.java.features.Preference;
import org.controlsfx.control.textfield.CustomPasswordField;
import org.controlsfx.control.textfield.CustomTextField;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ClientSettings implements Initializable {

    @FXML
    private AnchorPane settingsPane;

    @FXML
    private ToggleButton appSettings;

    @FXML
    private ToggleButton userSettings;

    @FXML
    private AnchorPane appSettingsPane;

    @FXML
    private AnchorPane userInformationPane;

    @FXML
    private ComboBox<String> pickColor;

    @FXML
    private ComboBox<String> pickLanguage;

    @FXML
    private CustomTextField settStreet;

    @FXML
    private CustomTextField settCity;

    @FXML
    private CustomTextField settNumber;

    @FXML
    private CustomPasswordField settOldPassword;

    @FXML
    private CustomPasswordField settPassword;

    @FXML
    private CustomPasswordField settRepeatPassword;

    @FXML
    private ComboBox<String> settProvince;

    @FXML
    private Button saveInformation;

    @FXML
    private Pane alertPane;

    @FXML
    private CustomPasswordField deletePassword;

    // List of colors for combobox
    private ObservableList<String> colors = FXCollections.observableArrayList(App.getLanguageProperties("colorOrange"), App.getLanguageProperties("colorRed"),
            App.getLanguageProperties("colorWhite"));



    // List of languages for combobox
    private ObservableList<String> languages = FXCollections.observableArrayList("Polski", "English");

    // List of provinces for combobox
    private ObservableList<String> provinces = FXCollections.observableArrayList( "Dolnośląskie",
            "Kujawsko-pomorskie", "Lubelskie", "Lubuskie",  "Łódzkie",  "Małopolskie",  "Mazowieckie",
            "Opolskie",  "Podkarpackie",  "Podlaskie",  "Pomorskie",  "Śląskie",  "Świętokrzyskie",
            "Warmińsko-mazurskie",  "Wielkopolskie",  "Zachodniopomorskie");

    private String[] inputs = new String[5];

    private Preference pref = new Preference();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Created a group and added two buttons (App settings and user settings)
        // It allows user to only click one button
        ToggleGroup group = new ToggleGroup();
        appSettings.setToggleGroup(group);
        userSettings.setToggleGroup(group);

        appSettings.setSelected(true);
        appSettings.setDisable(true);
        appSettings.setOpacity(1);

        userInformationPane.setTranslateY(-800);

        // Populating language combobox
        pickLanguage.setItems(languages);
        if(pref.readPreference("language").equals("english")) {
            pickLanguage.setValue(languages.get(1));
        }
        else {
            pickLanguage.setValue(languages.get(0));
        }

        // Populating color combobox
        pickColor.setItems(colors);
        if(pref.readPreference("color").equals("orange")) {
            pickColor.setValue(colors.get(0));
        }
        else if(Preference.readPreference("color").equals("red")) {
            pickColor.setValue(colors.get(1));
        }
        else {
            pickColor.setValue(colors.get(2));
        }

        List<UserInfos> listOfUserInfo = UsersDAO.readUserInfoById(Login.getUserInfoID());

        // Populating province combobox
        settProvince.setItems(provinces);
        settProvince.setValue(listOfUserInfo.get(0).getVoivodeship());

        // Getting information about user from database and setting text
        // inside input fields
        settCity.setText(listOfUserInfo.get(0).getCity());
        settNumber.setText(listOfUserInfo.get(0).getPhoneNumber());
        settStreet.setText(listOfUserInfo.get(0).getStreetAndNumber());

        // Table 'inputs' helps to store information about
        // current street, city, province, number in database
        inputs[0] = settStreet.getText();
        inputs[1] = settCity.getText();
        inputs[2] = settProvince.getSelectionModel().getSelectedItem();
        inputs[3] = settNumber.getText();

        alertPane.setTranslateY(-500);

        // Checking errors in inputs
        ErrorHandler.checkInputs(settCity,"[A-Za-z]{2,40}\\s?\\-?\\s?[A-Za-z]{0,40}\\s?\\-?\\s?[A-Za-z]{0,40}",
                "Miasto powinno zawierać tylko litery");

        ErrorHandler.checkInputs(settStreet,
                "[A-Za-z]{0,2}\\.?\\s?[A-Za-z]{2,40}\\s?\\-?[A-Za-z]{0,40}?\\s?\\-?[A-Za-z]{0,40}?\\s" +
                        "[0-9]{1,4}\\s?[A-Za-z]?\\s?\\/?\\s?[0-9]{0,5}",
                "Ulica powinna miec format ULICA NUMER");

        ErrorHandler.checkPasswords(settPassword,settRepeatPassword,
                "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,}$",
                "Podane hasło ma niepoprawny format",
                "Hasła powinny być takie same");

        ErrorHandler.checkInputs(settNumber, "\\+?[0-9]{0,2}\\s?[0-9]{3}\\s?[0-9]{3}\\s?[0-9]{3}",
                "Numer powinno zawierać tylko cyfry");

    }

    // Method that handle changing pane to 'AppSettings'
    @FXML
    void changeAppSettings(ActionEvent event) throws IOException {
        Animations.changePane(userInformationPane,appSettingsPane,-800,0.5,appSettings,userSettings);
    }

    // Method that handle changing pane to 'UserSettings'
    @FXML
    void changeUserSettings(ActionEvent event) throws IOException {
        Animations.changePane(appSettingsPane,userInformationPane,+800,0.5,userSettings,appSettings);
    }

    // Method that handle changing main theme in application
    @FXML
    void changeColor(ActionEvent event) {

        if (pickColor.getValue().equals(App.getLanguageProperties("colorOrange"))) {
            pref.addPreference("color", "orange");
            SceneManager.getStage().getScene().getRoot().setStyle("-fx-main-color: #ffa500;" +
                    "-fx-second-color: #000000;");
        }
        else if (pickColor.getValue().equals(App.getLanguageProperties("colorRed"))) {
            pref.addPreference("color", "red");
            SceneManager.getStage().getScene().getRoot().setStyle("-fx-main-color: #d82020;" +
                    "-fx-second-color: #ffffff;");
        }
        else {
            pref.addPreference("color", "white");
            SceneManager.getStage().getScene().getRoot().setStyle("-fx-main-color: #FFFFFF;" +
                    "-fx-second-color: #000000;");
        }
    }

    // Method that handle changing language in application
    @FXML
    void changeLanguage(ActionEvent event) {
        if(pickLanguage.getValue().equals("English")) {
            pref.addPreference("language","english");
        }
        else {
            pref.addPreference("language","polski");
        }
    }

    // Method that handle updating information from database
    @FXML
    void updateInformation(ActionEvent event) {
        // Checking if error icon is visible
        if(!settStreet.getRight().isVisible() && !settCity.getRight().isVisible()
                && !settNumber.getRight().isVisible()) {

            // Checking if current text inside inputs are different from the one
            // inside the database (without additional query)
            if (!inputs[0].equals(settStreet.getText()) || !inputs[1].equals(settCity.getText())
                    || !inputs[2].equals(settProvince.getSelectionModel().getSelectedItem())
                    || !inputs[3].equals(settNumber.getText())) {

                // Updating information
                UserInfosDAO.updateUserSettings(settProvince.getSelectionModel().getSelectedItem(),
                        settCity.getText(), settNumber.getText(),settStreet.getText(), Login.getUserID());

                // Assigment of new values that are inside database after query
                inputs[0] = settStreet.getText();
                inputs[1] = settCity.getText();
                inputs[2] = settProvince.getSelectionModel().getSelectedItem();
                inputs[3] = settNumber.getText();

                Alerts.createAlert(settingsPane, saveInformation, "CHECK", "POMYŚLNIE ZMIENIONO");
            }
        }
        else {
            Alerts.createAlert(settingsPane,saveInformation,"WARNING", "UZUPEŁNIJ LUB POPRAW POLA");
        }

        // Checking if passwords were provided, changed and if they contains any error
        if(!settOldPassword.getText().isEmpty()) {
            if (!settPassword.getRight().isVisible() && !settRepeatPassword.getRight().isVisible()
                    && Encryption.encrypt(settOldPassword.getText()).equals(UsersDAO.readPassword(Login.getUserID()))) {

                // Updating passwords
                UsersDAO.updatePassword(Login.getUserID(),settPassword.getText());

                Alerts.createAlert(settingsPane, saveInformation, "CHECK", "POMYŚLNIE ZMIENIONO");
            } else {
                Alerts.createAlert(settingsPane, saveInformation, "WARNING", "NIEPOPRAWNE HASŁO");
            }
        }
    }

    // Method that handle deleting account
    @FXML
    public void deleteAccount(ActionEvent actionEvent) {
        Animations.moveByY(alertPane,+500,0.3);
        GaussianBlur gaussianBlur = new GaussianBlur();
        gaussianBlur.setRadius(8);
        settingsPane.setDisable(true);
        settingsPane.setEffect(gaussianBlur);
    }

    // Method that handle 'yes' button inside delete account popup
    @FXML
    public void actionYes(ActionEvent actionEvent) {
        // If password is correct then account is being deleted
        if(UsersDAO.checkIfPasswordCorrect(deletePassword.getText(),Login.getUserID())) {
            UsersDAO.deactivateAccount(Login.getUserID());
            SceneManager.renderScene("login");
        }
        else {
            deletePassword.setText(null);
            deletePassword.setPromptText("NIEPOPRAWNE HASŁO");
        }

    }

    // Method that handle 'no' button inside delete account popup
    @FXML
    public void actionNo(ActionEvent actionEvent) {
        Animations.moveByY(alertPane,-500,0.3);
        settingsPane.setDisable(false);
        settingsPane.setEffect(null);
    }
}
