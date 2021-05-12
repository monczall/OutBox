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
import main.java.SceneManager;
import main.java.controllers.auth.Encryption;
import main.java.controllers.auth.Login;
import main.java.dao.UserInfosDAO;
import main.java.dao.UsersDAO;
import main.java.entity.UserInfos;
import main.java.entity.Users;
import main.java.features.Alerts;
import main.java.features.Animations;
import main.java.features.ErrorHandler;
import main.java.features.Preference;
import org.controlsfx.control.ToggleSwitch;
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
    private ToggleSwitch darkMode;

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
    private AnchorPane window;

    @FXML
    private CustomPasswordField deletePassword;



    //List of colors for combobox
    private ObservableList<String> colors = FXCollections.observableArrayList("Pomarańczowy", "Czerwony",
            "Biały");
    //List of languages for combobox
    private ObservableList<String> languages = FXCollections.observableArrayList("Polski", "English");
    //List of provinces for combobox
    private ObservableList<String> provinces = FXCollections.observableArrayList( "Dolnośląskie",
            "Kujawsko-pomorskie", "Lubelskie", "Lubuskie",  "Łódzkie",  "Małopolskie",  "Mazowieckie",
            "Opolskie",  "Podkarpackie",  "Podlaskie",  "Pomorskie",  "Śląskie",  "Świętokrzyskie",
            "Warmińsko-mazurskie",  "Wielkopolskie",  "Zachodniopomorskie");

    private String[] inputs = new String[5];

    Preference pref = new Preference();

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

        //Populating language combobox
        pickLanguage.setItems(languages);
        if(pref.readPreference("language").equals("english")){
            pickLanguage.setValue(languages.get(1));
        }
        else{
            pickLanguage.setValue(languages.get(0));
        }

        //Populating color combobox
        pickColor.setItems(colors);
        if(pref.readPreference("color").equals("orange")){
            pickColor.setValue(colors.get(0));
        }
        else if(Preference.readPreference("color").equals("red")){
            pickColor.setValue(colors.get(1));
        }
        else{
            pickColor.setValue(colors.get(2));
        }

        List<UserInfos> listOfUserInfo = UsersDAO.readUserInfoById(Login.getUserInfoID());

        //Populating province combobox
        settProvince.setItems(provinces);
        settProvince.setValue(listOfUserInfo.get(0).getVoivodeship());

        //Setting text for inputs to test how it will work with database information
        settCity.setText(listOfUserInfo.get(0).getCity());
        settNumber.setText(listOfUserInfo.get(0).getPhoneNumber()); //373 128
        settStreet.setText(listOfUserInfo.get(0).getStreetAndNumber());

        inputs[0] = settStreet.getText();
        inputs[1] = settCity.getText();
        inputs[2] = settProvince.getSelectionModel().getSelectedItem();
        inputs[3] = settNumber.getText();

        alertPane.setTranslateY(-500);

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

        if (pickColor.getValue().equals("Pomarańczowy")) {
            pref.addPreference("color", "orange");
            SceneManager.getStage().getScene().getRoot().setStyle("-fx-main-color: #ffa500;" +
                    "-fx-second-color: #000000;");
        }
        else if (pickColor.getValue().equals("Czerwony")){
            pref.addPreference("color", "red");
            SceneManager.getStage().getScene().getRoot().setStyle("-fx-main-color: #d82020;" +
                    "-fx-second-color: #ffffff;");
        }
        else{
            pref.addPreference("color", "white");
            SceneManager.getStage().getScene().getRoot().setStyle("-fx-main-color: #FFFFFF;" +
                    "-fx-second-color: #000000;");
        }
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
        if(!settStreet.getRight().isVisible() && !settCity.getRight().isVisible()
                && !settNumber.getRight().isVisible()) {

            if (!inputs[0].equals(settStreet.getText()) || !inputs[1].equals(settCity.getText())
                    || !inputs[2].equals(settProvince.getSelectionModel().getSelectedItem())
                    || !inputs[3].equals(settNumber.getText())) {

                inputs[0] = settStreet.getText();
                inputs[1] = settCity.getText();
                inputs[2] = settProvince.getSelectionModel().getSelectedItem();
                inputs[3] = settNumber.getText();

                UserInfosDAO.updateUserSettings(settProvince.getSelectionModel().getSelectedItem(),
                        settCity.getText(), settNumber.getText(),settStreet.getText(), Login.getUserID());

                Alerts.createAlert(settingsPane, saveInformation, "CHECK", "POMYŚLNIE ZMIENIONO");
            }
        }
        else
            Alerts.createAlert(settingsPane,saveInformation,"WARNING", "UZUPEŁNIJ LUB POPRAW POLA");

        if(!settOldPassword.getText().isEmpty()) {
            if (!settPassword.getRight().isVisible() && !settRepeatPassword.getRight().isVisible()
                    && Encryption.encrypt(settOldPassword.getText()).equals(UsersDAO.readPassword(Login.getUserID()))) {

                UsersDAO.updatePassword(Login.getUserID(),settPassword.getText());

                Alerts.createAlert(settingsPane, saveInformation, "CHECK", "POMYŚLNIE ZMIENIONO");
            } else
                Alerts.createAlert(settingsPane, saveInformation, "WARNING", "NIEPOPRAWNE HASŁO");
        }
    }

    public void deleteAccount(ActionEvent actionEvent) {
        Animations.moveByY(alertPane,+500,0.3);
        GaussianBlur gaussianBlur = new GaussianBlur();
        gaussianBlur.setRadius(8);
        settingsPane.setDisable(true);
        settingsPane.setEffect(gaussianBlur);
    }

    public void actionYes(ActionEvent actionEvent) {
        if(UsersDAO.deleteAccount(deletePassword.getText(),1)){
            UserInfosDAO.deleteUser(1);
            SceneManager.renderScene("login");
        }
        else{
            Alerts.createAlert(settingsPane, saveInformation, "WARNING", "NIEPOPRAWNE HASŁO");
            System.out.println("NIEPOPRAWNE HASLO ALBO MA AKTYWNE PACZKI");
        }

    }

    public void actionNo(ActionEvent actionEvent) {
        Animations.moveByY(alertPane,-500,0.3);
        settingsPane.setDisable(false);
        settingsPane.setEffect(null);
    }

    public void setAlert(){
        Alerts.createAlert(this.settingsPane, this.saveInformation, "WARNING", "NIEPOPRAWNE HASŁO");
    }
}
