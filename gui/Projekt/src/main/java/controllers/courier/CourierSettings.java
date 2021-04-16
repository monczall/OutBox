package main.java.controllers.courier;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import main.java.SceneManager;
import main.java.preferences.Preference;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class CourierSettings implements Initializable {

    @FXML
    private ComboBox<String> pickLanguage;

    @FXML
    private ComboBox<String> pickColor;
    @FXML
    private AnchorPane mainPane;

    private ObservableList<String> languages = FXCollections.observableArrayList("Polski", "English");

    private ObservableList<String> colors = FXCollections.observableArrayList("Pomarańczowy","Czerwony", "Biały");

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        pickLanguage.setItems(languages);
        if(Preference.readPreference("language").equals("english")){
            pickLanguage.setValue(languages.get(1));
        }
        else{
            pickLanguage.setValue(languages.get(0));
        }

        pickColor.setItems(colors);
        if(Preference.readPreference("color").equals("orange")){
            pickColor.setValue(colors.get(0));
        }
        else if(Preference.readPreference("color").equals("red")){
            pickColor.setValue(colors.get(1));
        }
        else{
            pickColor.setValue(colors.get(2));
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
    void changeTheme(ActionEvent event) {
        Preference pref = new Preference();

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
            SceneManager.getStage().getScene().getRoot().setStyle("-fx-main-color: #ffffff;" +
                    "-fx-second-color: #000000;");
        }

    }
}