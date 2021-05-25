package main.java.controllers.admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import main.java.SceneManager;
import main.java.features.Animations;
import main.java.features.Preference;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminSettings implements Initializable {

    @FXML
    private ComboBox<String> pickColor;

    @FXML
    private ComboBox<String> pickLanguage;


    @FXML
    private AnchorPane userInformationPane;

    Preference pref = new Preference();

    //List of colors for combobox
    private ObservableList<String> colors = FXCollections.observableArrayList("Pomarańczowy", "Czerwony", "Biały");
    //List of languages for combobox
    private ObservableList<String> languages = FXCollections.observableArrayList("Polski", "English");

    @Override
    public void initialize(URL location, ResourceBundle resources) {

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

        pickLanguage.setItems(languages);
        if(pref.readPreference("language").equals("english")){
            pickLanguage.setValue(languages.get(1));
        }
        else{
            pickLanguage.setValue(languages.get(0));
        }



        userInformationPane.setTranslateY(-800);
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
}
