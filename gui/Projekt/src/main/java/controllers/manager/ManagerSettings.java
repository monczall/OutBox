package main.java.controllers.manager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import main.java.preferences.Preference;

import java.net.URL;
import java.util.ResourceBundle;

public class ManagerSettings implements Initializable {

    @FXML
    private ComboBox<String> comboColor;

    @FXML
    private ComboBox<String> comboLanguage;

    //List of colors for combobox
    private ObservableList<String> colors = FXCollections.observableArrayList("Pomarańczowy","Czerwony");
    //List of languages for combobox
    private ObservableList<String> languages = FXCollections.observableArrayList("Polski", "English");

    @FXML
    void changeLanguage(ActionEvent event) {
        Preference pref = new Preference();

        if(comboLanguage.getValue().equals("English"))
            pref.addPreference("language","english");
        else
            pref.addPreference("language","polski");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comboColor.setItems(colors);
        comboColor.setValue(colors.get(0));

        comboLanguage.setItems(languages);
        comboLanguage.setValue(languages.get(0));
    }
}
