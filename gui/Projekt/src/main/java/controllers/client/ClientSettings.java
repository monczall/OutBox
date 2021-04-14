package main.java.controllers.client;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import main.java.SceneManager;
import main.java.features.Alerts;
import main.java.features.Animations;
import main.java.features.ErrorHandler;
import main.java.preferences.Preference;
import org.controlsfx.control.ToggleSwitch;
import org.controlsfx.control.textfield.CustomPasswordField;
import org.controlsfx.control.textfield.CustomTextField;

import java.io.IOException;
import java.net.URL;
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
    private ToggleSwitch darkMode;

    @FXML
    private CustomTextField settStreet;

    @FXML
    private CustomTextField settCity;

    @FXML
    private CustomTextField settNumber;

    @FXML
    private CustomTextField settEmail;

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
    private AnchorPane window;

    //List of colors for combobox
    private ObservableList<String> colors = FXCollections.observableArrayList("Pomarańczowy","Czerwony");
    //List of languages for combobox
    private ObservableList<String> languages = FXCollections.observableArrayList("Polski", "English");
    //List of provinces for combobox
    private ObservableList<String> provinces = FXCollections.observableArrayList( "Dolnośląskie",  "Kujawsko-pomorskie", "Lubelskie", "Lubuskie",  "Łódzkie",  "Małopolskie",  "Mazowieckie",  "Opolskie",  "Podkarpackie",  "Podlaskie",  "Pomorskie",  "Śląskie",  "Świętokrzyskie",  "Warmińsko-mazurskie",  "Wielkopolskie",  "Zachodniopomorskie");

    private String[] inputs = new String[5];

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Created a group and added two buttons (App settings and user settings)
        ToggleGroup group = new ToggleGroup();
        appSettings.setToggleGroup(group);
        userSettings.setToggleGroup(group);

        appSettings.setSelected(true);
        appSettings.setDisable(true);
        appSettings.setOpacity(1);

        userInformationPane.setTranslateY(-800);

        //Populating color combobox
        pickColor.setItems(colors);
        pickColor.setValue(colors.get(0));

        //Populating language combobox
        pickLanguage.setItems(languages);
        pickLanguage.setValue(languages.get(0));

        //Populating province combobox
        settProvince.setItems(provinces);
        settProvince.setValue(provinces.get(0));

        //Setting text for inputs to test how it will work with database information
        settCity.setText("City");
        settEmail.setText("Email@gmail.com");
        settNumber.setText("1234567890"); //373 128
        settStreet.setText("Street 33");

        inputs[0] = settStreet.getText();
        inputs[1] = settCity.getText();
        inputs[2] = settProvince.getSelectionModel().getSelectedItem();
        inputs[3] = settNumber.getText();
        inputs[4] = settEmail.getText();

        alertPane.setTranslateY(-500);

        ErrorHandler.checkInputs(settCity,"[A-Za-z]{2,40}\\s?\\-?\\s?[A-Za-z]{0,40}\\s?\\-?\\s?[A-Za-z]{0,40}","Miasto powinno zawierać tylko litery");
        ErrorHandler.checkInputs(settEmail,"[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}", "Email powinien mieć poprawny format");
        ErrorHandler.checkInputs(settStreet, "[A-Za-z]{0,2}\\.?\\s?[A-Za-z]{2,40}\\s?\\-?[A-Za-z]{0,40}?\\s?\\-?[A-Za-z]{0,40}?\\s[0-9]{1,4}\\s?[A-Za-z]?\\s?\\/?\\s?[0-9]{0,5}", "Ulica powinna miec format ULICA NUMER");
        ErrorHandler.checkPasswords(settPassword,settRepeatPassword, "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,}$", "Podane hasło ma niepoprawny format","Hasła powinny być takie same");
        ErrorHandler.checkInputs(settNumber, "\\+?[0-9]{0,2}\\s?[0-9]{3}\\s?[0-9]{3}\\s?[0-9]{3}", "Numer powinno zawierać tylko cyfry");
    }

    @FXML
    void changeAppSettings(ActionEvent event) throws IOException {
        Animations.changePane(userInformationPane,appSettingsPane,-800,0.5,appSettings,userSettings);
    }

    @FXML
    void changeUserSettings(ActionEvent event) throws IOException {
        Animations.changePane(appSettingsPane,userInformationPane,+800,0.5,userSettings,appSettings);
    }

    @FXML
    void changeColor(ActionEvent event) {
        Preference pref = new Preference();

        if (pickColor.getValue().equals("Pomarańczowy"))
            pref.addPreference("color","orange");
        else
            pref.addPreference("color","red");
    }

    @FXML
    void changeLanguage(ActionEvent event) {
        Preference pref = new Preference();

        if(pickLanguage.getValue().equals("English"))
            pref.addPreference("language","english");
        else
            pref.addPreference("language","polski");
    }

    @FXML
    void updateInformation(ActionEvent event) {
        if(!settStreet.getRight().isVisible() && !settCity.getRight().isVisible() && !settNumber.getRight().isVisible()
                && !settEmail.getRight().isVisible() && !settPassword.getRight().isVisible() && !settRepeatPassword.getRight().isVisible()) {

            if (!inputs[0].equals(settStreet.getText()) || !inputs[1].equals(settCity.getText()) || !inputs[2].equals(settProvince.getSelectionModel().getSelectedItem())
                    || !inputs[3].equals(settNumber.getText()) || !inputs[4].equals(settEmail.getText()) || !settPassword.getText().isEmpty()) {
                Alerts.createAlert(settingsPane, saveInformation, "CHECK", "POMYŚLNIE ZMIENIONO");
            }
        }
        else
            Alerts.createAlert(settingsPane,saveInformation,"WARNING", "UZUPEŁNIJ LUB POPRAW POLA");
    }

    public void deleteAccount(ActionEvent actionEvent) {
        Animations.moveByY(alertPane,+500,0.3);
        GaussianBlur gaussianBlur = new GaussianBlur();
        gaussianBlur.setRadius(8);
        settingsPane.setDisable(true);
        settingsPane.setEffect(gaussianBlur);
    }

    public void actionYes(ActionEvent actionEvent) {
        SceneManager.renderScene("login");
    }

    public void actionNo(ActionEvent actionEvent) {
        Animations.moveByY(alertPane,-500,0.3);
        settingsPane.setDisable(false);
        settingsPane.setEffect(null);
    }
}
