package main.java.controllers.manager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import main.java.SceneManager;
import main.java.preferences.Preference;

import java.net.URL;
import java.util.ResourceBundle;

public class ManagerSettings implements Initializable {

    @FXML
    private ComboBox<String> comboColor;

    @FXML
    private ComboBox<String> comboLanguage;

    Preference pref = new Preference();

    private ObservableList<String> languages = FXCollections.observableArrayList("Polski", "English");

    private ObservableList<String> colors = FXCollections.observableArrayList("Pomarańczowy","Czerwony", "Biały");

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        comboLanguage.setItems(languages);
        if(Preference.readPreference("language").equals("english")){
            comboLanguage.setValue(languages.get(1));
        }
        else{
            comboLanguage.setValue(languages.get(0));
        }

        comboColor.setItems(colors);
        if(Preference.readPreference("color").equals("orange")){
            comboColor.setValue(colors.get(0));
        }
        else if(Preference.readPreference("color").equals("red")){
            comboColor.setValue(colors.get(1));
        }
        else{
            comboColor.setValue(colors.get(2));
        }

    }


    @FXML
    void changeLanguage(ActionEvent event) {
        Preference pref = new Preference();
        if(comboLanguage.getValue().equals("English"))
            pref.addPreference("language","english");
        else
            pref.addPreference("language","polski");
    }

    @FXML
    void changeTheme(ActionEvent event) {
        Preference pref = new Preference();

        if (comboColor.getValue().equals("Pomarańczowy")) {
            pref.addPreference("color", "orange");
            SceneManager.getStage().getScene().getRoot().setStyle("-fx-main-color: #ffa500;" +
                    "-fx-second-color: #000000;");
        }
        else if (comboColor.getValue().equals("Czerwony")){
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
