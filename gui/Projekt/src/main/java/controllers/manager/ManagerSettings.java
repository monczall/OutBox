package main.java.controllers.manager;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import main.java.App;
import main.java.SceneManager;
import main.java.controllers.auth.Encryption;
import main.java.controllers.auth.Login;
import main.java.dao.UsersDAO;
import main.java.features.Alerts;
import main.java.features.Preference;
import org.controlsfx.control.textfield.CustomPasswordField;

import java.net.URL;
import java.util.ResourceBundle;

public class ManagerSettings implements Initializable {

    private final FontAwesomeIconView alertIcon = new FontAwesomeIconView();
    private final ObservableList<String> languages = FXCollections.observableArrayList("Polski", "English");
    private final ObservableList<String> colors = FXCollections.observableArrayList(
            App.getLanguageProperties("colorOrange"),
            App.getLanguageProperties("colorRed"),
            App.getLanguageProperties("colorWhite"));
    Preference pref = new Preference();
    @FXML
    private AnchorPane settingsPane;
    @FXML
    private ComboBox<String> comboColor;
    @FXML
    private ComboBox<String> comboLanguage;
    @FXML
    private CustomPasswordField settPassword;
    @FXML
    private CustomPasswordField settRepeatPassword;
    @FXML
    private Button saveInformation;
    @FXML
    private CustomPasswordField settOldPassword;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //set language
        comboLanguage.setItems(languages);
        if (Preference.readPreference("language").equals("english")) {
            comboLanguage.setValue(languages.get(1));
        } else {
            comboLanguage.setValue(languages.get(0));
        }

        //set colors
        comboColor.setItems(colors);
        if (Preference.readPreference("color").equals("orange")) {
            comboColor.setValue(colors.get(0));
        } else if (Preference.readPreference("color").equals("red")) {
            comboColor.setValue(colors.get(1));
        } else {
            comboColor.setValue(colors.get(2));
        }

    }

    /**
     * change the language
     */
    @FXML
    void changeLanguage(ActionEvent event) {
        Preference pref = new Preference();
        if (comboLanguage.getValue().equals("English")) {
            pref.addPreference("language", "english");
        } else {
            pref.addPreference("language", "polski");
        }
        SceneManager.renderScene("manager");
    }

    /**
     * change of theme
     */
    @FXML
    void changeTheme(ActionEvent event) {
        Preference pref = new Preference();

        if (comboColor.getValue().equals("Pomarańczowy") || comboColor.getValue().equals("Orange")) {
            pref.addPreference("color", "orange");
            SceneManager.getStage().getScene().getRoot().setStyle("-fx-main-color: #ffa500;" +
                    "-fx-second-color: #000000;" + "-fx-error-color: #d82020;");
        } else if (comboColor.getValue().equals("Czerwony") || comboColor.getValue().equals("Red")) {
            pref.addPreference("color", "red");
            SceneManager.getStage().getScene().getRoot().setStyle("-fx-main-color: #d82020;" +
                    "-fx-second-color: #ffffff;" + "-fx-error-color: #ffffff;");
        } else {
            pref.addPreference("color", "white");
            SceneManager.getStage().getScene().getRoot().setStyle("-fx-main-color: #ffffff;" +
                    "-fx-second-color: #000000;" + "-fx-error-color: #d82020;");
        }
    }

    /**
     * change your account password
     */
    public void updateInformation(ActionEvent actionEvent) {
        // Checking if passwords were provided, changed and if they contains any error
        if (!settOldPassword.getText().isEmpty() && !settPassword.getText().isEmpty() &&
                !settRepeatPassword.getText().isEmpty()) {
            if (settPassword.getText().equals(settRepeatPassword.getText())) {
                if (Encryption.encrypt(settOldPassword.getText()).equals(UsersDAO.readPassword(Login.getUserID()))) {
                    // Updating passwords
                    UsersDAO.updatePassword(Login.getUserID(), settPassword.getText());

                    Alerts.createAlert(settingsPane, saveInformation,
                            "CHECK",
                            App.getLanguageProperties("successfullyChanged"));
                    settOldPassword.setText("");
                    settPassword.setText("");
                    settRepeatPassword.setText("");
                } else {
                    Alerts.createAlert(settingsPane, saveInformation,
                            "WARNING",
                            App.getLanguageProperties("incorrectPassword"));

                    settOldPassword.getRight().setVisible(true);
                }
            } else {
                Alerts.createAlert(settingsPane, saveInformation,
                        "WARNING",
                        App.getLanguageProperties("samePasswords"));
            }
        } else {
            Alerts.createAlert(settingsPane, saveInformation,
                    "WARNING",
                    App.getLanguageProperties("providePasswords"));
        }
    }
}
