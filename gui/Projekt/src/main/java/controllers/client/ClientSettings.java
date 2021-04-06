package main.java.controllers.client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import main.java.features.Animations;
import main.java.preferences.Preference;
import org.controlsfx.control.ToggleSwitch;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientSettings implements Initializable {

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

    //List of colors for combobox
    private ObservableList<String> colors = FXCollections.observableArrayList("Pomarańczowy","Czerwony");
    //List of languages for combobox
    private ObservableList<String> languages = FXCollections.observableArrayList("Polski", "English");

    private Scene scene;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ToggleGroup group = new ToggleGroup();
        appSettings.setToggleGroup(group);
        userSettings.setToggleGroup(group);

        appSettings.setSelected(true);
        appSettings.setDisable(true);
        appSettings.setOpacity(1);

        userInformationPane.setTranslateY(-800);

        pickColor.setItems(colors);
        pickColor.setValue(colors.get(0));

        pickLanguage.setItems(languages);
        pickLanguage.setValue(languages.get(0));

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
        if (pickColor.getValue().equals("Pomarańczowy")) {
            pref.addPreference("color","orange");
        } else {
            pref.addPreference("color","red");
        }
    }
}
