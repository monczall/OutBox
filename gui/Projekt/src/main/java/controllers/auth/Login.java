package main.java.controllers.auth;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import main.java.App;
import main.java.SceneManager;
import main.java.entity.Users;
import main.java.features.Alerts;
import main.java.features.Preference;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static main.java.dao.UsersDAO.getUsers;

public class Login implements Initializable {

    @FXML
    private MenuButton loginSettingsMenuButton;

    @FXML
    private MenuItem loginPolishLanguageMenuItem;

    @FXML
    private MenuItem loginEnglishLanguageMenuItem;

    @FXML
    private MenuItem loginOrangeColorMenuItem;

    @FXML
    private MenuItem loginRedColorMenuItem;

    @FXML
    private MenuItem loginWhiteColorMenuItem;

    @FXML
    private ImageView loginLogoImageView;

    @FXML
    private AnchorPane loginRightPaneAnchorPane;

    @FXML
    private Button loginExitButtonButton;

    @FXML
    private Button loginCreateAccountButton;

    @FXML
    private Button loginLoginButtonButton;

    @FXML
    private TextField loginEmailTextField;

    @FXML
    private Circle loginUserCircleCircle;

    @FXML
    private PasswordField loginPasswordPasswordField;

    @FXML
    private Circle loginPasswordCircleCircle;

    public static int userID;
    public static int userInfoID;
    public static String userEmail;

    private static Preference pref = new Preference();

    public void initialize(URL url, ResourceBundle rb) {
        ImageView outboxBlack = new ImageView("main/resources/images/outbox_black.png");
        outboxBlack.setFitHeight(200);
        outboxBlack.setFitWidth(150);
        ImageView outboxWhite = new ImageView("main/resources/images/outbox_white.png");
        outboxWhite.setFitHeight(200);
        outboxWhite.setFitWidth(150);

        ImageView cogsBlack = new ImageView("main/resources/images/settings_cogs_black.png");
        cogsBlack.setFitHeight(30);
        cogsBlack.setFitWidth(30);
        ImageView cogsWhite = new ImageView("main/resources/images/settings_cogs_white.png");
        cogsWhite.setFitHeight(30);
        cogsWhite.setFitWidth(30);


        if(pref.readPreference("language").equals("english")) {
            loginPolishLanguageMenuItem.setDisable(false);
            loginEnglishLanguageMenuItem.setDisable(true);

        }else {
            loginPolishLanguageMenuItem.setDisable(true);
            loginEnglishLanguageMenuItem.setDisable(false);
        }

        if(pref.readPreference("color").equals("red")) {
            loginRedColorMenuItem.setDisable(true);
            loginOrangeColorMenuItem.setDisable(false);
            loginWhiteColorMenuItem.setDisable(false);
            loginLogoImageView.setImage(outboxWhite.getImage());
            loginSettingsMenuButton.setGraphic(cogsBlack);
        }
        else if(pref.readPreference("color").equals("orange")) {
            loginRedColorMenuItem.setDisable(false);
            loginOrangeColorMenuItem.setDisable(true);
            loginWhiteColorMenuItem.setDisable(false);
            loginLogoImageView.setImage(outboxBlack.getImage());
            loginSettingsMenuButton.setGraphic(cogsWhite);
        }
        else{
            loginRedColorMenuItem.setDisable(false);
            loginOrangeColorMenuItem.setDisable(false);
            loginWhiteColorMenuItem.setDisable(true);
            loginLogoImageView.setImage(outboxBlack.getImage());
            loginSettingsMenuButton.setGraphic(cogsWhite);
        }

        if (App.isConnectionError()) {
            Alerts.createCustomAlert(loginRightPaneAnchorPane,
                    loginCreateAccountButton, "WARNING",
                    App.getLanguageProperties("authDatabaseConnectionAlert"),
                    425, 86, "alertFailure");
        }
    }

    public void login() {
        if (!isEmpty()) {
            if (isEmail(loginEmailTextField.getText())) {

                List<Users> listOfUsers = getUsers();
                for (int i = 0; i < getUsers().size(); i++) {
                    if (loginEmailTextField.getText().equals(listOfUsers.get(i).getEmail())
                            && Encryption.encrypt(loginPasswordPasswordField.getText()).equals
                            (listOfUsers.get(i).getPassword())) {

                        setUserID(listOfUsers.get(i).getId());
                        setUserInfoID(listOfUsers.get(i).getUserInfoId());
                        setUserEmail(listOfUsers.get(i).getEmail());

                        String role = listOfUsers.get(i).getRole();
                        if (role.equals("Klient")) {
                            SceneManager.renderScene("client");

                        } else if (role.equals("Kurier")) {
                            SceneManager.renderScene("courier");

                        } else if (role.equals("Kurier Międzyoddziałowy")) {
                            SceneManager.renderScene("interbranchCourier");

                        } else if (role.equals("Menadżer")) {
                            SceneManager.renderScene("manager");

                        } else if (role.equals("Administrator")) {
                            SceneManager.renderScene("admin");

                        }

                    }
                }

                //UserTextField
                loginEmailTextField.getStyleClass().clear();
                loginEmailTextField.getStyleClass().add("textFieldsError");
                //UserCircle
                loginUserCircleCircle.getStyleClass().clear();
                loginUserCircleCircle.getStyleClass().add("fillError");

                //PasswordTextField
                loginPasswordPasswordField.getStyleClass().clear();
                loginPasswordPasswordField.getStyleClass().add("textFieldsError");
                //PasswordCircle
                loginPasswordCircleCircle.getStyleClass().clear();
                loginPasswordCircleCircle.getStyleClass().add("fillError");

                Alerts.createCustomAlert(loginRightPaneAnchorPane,
                        loginCreateAccountButton, "WARNING",
                        App.getLanguageProperties("authNoUserFoundAlert"),
                        435, 86, "alertFailure");

            } else {
                //UserTextField
                loginEmailTextField.getStyleClass().clear();
                loginEmailTextField.getStyleClass().add("textFieldsError");
                //UserCircle
                loginUserCircleCircle.getStyleClass().clear();
                loginUserCircleCircle.getStyleClass().add("fillError");

                Alerts.createCustomAlert(loginRightPaneAnchorPane,
                        loginCreateAccountButton, "WARNING",
                        App.getLanguageProperties("authWrongEmailFormatAlert"),
                        350, 86, "alertFailure");
            }
        } else {
            Alerts.createCustomAlert(loginRightPaneAnchorPane,
                    loginCreateAccountButton, "WARNING",
                    App.getLanguageProperties("authFillFormAlert"),
                    293, 86,
                    "alertFailure");
        }
    }

    private boolean isEmpty() {
        int error = 0;
        if (loginEmailTextField.getText().isEmpty()) {
            //UserTextField
            loginEmailTextField.getStyleClass().clear();
            loginEmailTextField.getStyleClass().add("textFieldsError");
            //UserCircle
            loginUserCircleCircle.getStyleClass().clear();
            loginUserCircleCircle.getStyleClass().add("fillError");
            error++;
        }
        if (loginPasswordPasswordField.getText().isEmpty()) {
            //PasswordTextField
            loginPasswordPasswordField.getStyleClass().clear();
            loginPasswordPasswordField.getStyleClass().add("textFieldsError");
            //PasswordCircle
            loginPasswordCircleCircle.getStyleClass().clear();
            loginPasswordCircleCircle.getStyleClass().add("fillError");
            error++;
        }
        if (error > 0) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isEmail(String email) {
        Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+" +
                "@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        Matcher mat = pattern.matcher(email);

        if (mat.matches()) {
            return true;
        } else {
            return false;
        }
    }

    public void handleLogin(MouseEvent mouseEvent) {
        login();
    }

    public void handleLoginOnEnterPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            login();
        }
    }

    public void handleCreateAccount(MouseEvent mouseEvent) {
        SceneManager.renderScene("register");
    }

    public void handlePasswordReset(MouseEvent mouseEvent) {
        SceneManager.renderScene("passwordReset");
    }

    public void handleExit(ActionEvent actionEvent) {
        Stage stage = (Stage) loginExitButtonButton.getScene().getWindow();
        stage.close();
    }

    public void handleUser(MouseEvent mouseEvent) {
        SceneManager.renderScene("client");
        System.out.println("Zalogowano jako uzytkownik!");
    }

    public void handleCourier(MouseEvent mouseEvent) {
        SceneManager.renderScene("courier");
        System.out.println("Zalogowano jako kurier!");
    }

    public void handleCourier2(MouseEvent mouseEvent) {
        SceneManager.renderScene("interbranchCourier");
        System.out.println("Zalogowano jako kurier miedzyoddzialowy!");
    }

    public void handleManager(MouseEvent mouseEvent) {
        SceneManager.renderScene("manager");
        System.out.println("Zalogowano jako kierownik!");
    }

    public void handleAdmin(MouseEvent mouseEvent) {
        SceneManager.renderScene("admin");
        System.out.println("Zalogowano jako admin!");
    }

    public void clearErrorsOnEmail(KeyEvent keyEvent) {
        //UserTextField
        loginEmailTextField.getStyleClass().clear();
        loginEmailTextField.getStyleClass().add("textFields");
        //UserCircle
        loginUserCircleCircle.getStyleClass().clear();
        loginUserCircleCircle.getStyleClass().add("fill");
        //PasswordTextField
        loginPasswordPasswordField.getStyleClass().clear();
        loginPasswordPasswordField.getStyleClass().add("textFields");
        //PasswordCircle
        loginPasswordCircleCircle.getStyleClass().clear();
        loginPasswordCircleCircle.getStyleClass().add("fill");
    }

    public void clearErrorsOnPassword(KeyEvent keyEvent) {
        //UserTextField
        loginEmailTextField.getStyleClass().clear();
        loginEmailTextField.getStyleClass().add("textFields");
        //UserCircle
        loginUserCircleCircle.getStyleClass().clear();
        loginUserCircleCircle.getStyleClass().add("fill");
        //PasswordTextField
        loginPasswordPasswordField.getStyleClass().clear();
        loginPasswordPasswordField.getStyleClass().add("textFields");
        //PasswordCircle
        loginPasswordCircleCircle.getStyleClass().clear();
        loginPasswordCircleCircle.getStyleClass().add("fill");
    }

    public static int getUserID() {
        return userID;
    }

    public static void setUserID(int userID) {
        Login.userID = userID;
    }

    public static int getUserInfoID() {
        return userInfoID;
    }

    public static void setUserInfoID(int userInfoID) {
        Login.userInfoID = userInfoID;
    }

    public static String getUserEmail() {
        return userEmail;
    }

    public static void setUserEmail(String userEmail) {
        Login.userEmail = userEmail;
    }


    public void setPolishLanguage(ActionEvent actionEvent) {
        pref.addPreference("language","polish");
        SceneManager.renderScene("login");
    }

    public void setEnglishLanguage(ActionEvent actionEvent) {
        pref.addPreference("language","english");
        SceneManager.renderScene("login");
    }

    public void setOrangeColor(ActionEvent actionEvent) {
        pref.addPreference("color","orange");
        SceneManager.renderScene("login");
    }

    public void setRedColor(ActionEvent actionEvent) {
        pref.addPreference("color","red");
        SceneManager.renderScene("login");
    }

    public void setWhiteColor(ActionEvent actionEvent) {
        pref.addPreference("color","white");
        SceneManager.renderScene("login");
    }

    public void handleMouseEnterMenuSettingsButton(MouseEvent mouseEvent) {
        ImageView cogsBlack = new ImageView("main/resources/images/settings_cogs_black.png");
        cogsBlack.setFitHeight(30);
        cogsBlack.setFitWidth(30);
        ImageView cogsWhite = new ImageView("main/resources/images/settings_cogs_white.png");
        cogsWhite.setFitHeight(30);
        cogsWhite.setFitWidth(30);

        if(pref.readPreference("color").equals("red")) {
            loginSettingsMenuButton.setGraphic(cogsWhite);
        }
        else if(pref.readPreference("color").equals("orange")) {
            loginSettingsMenuButton.setGraphic(cogsBlack);
        }
        else{
            loginSettingsMenuButton.setGraphic(cogsBlack);
        }
    }

    public void handleMouseExitMenuSettingsButton(MouseEvent mouseEvent) {
        ImageView cogsBlack = new ImageView("main/resources/images/settings_cogs_black.png");
        cogsBlack.setFitHeight(30);
        cogsBlack.setFitWidth(30);
        ImageView cogsWhite = new ImageView("main/resources/images/settings_cogs_white.png");
        cogsWhite.setFitHeight(30);
        cogsWhite.setFitWidth(30);

        if(pref.readPreference("color").equals("red")) {
            loginSettingsMenuButton.setGraphic(cogsBlack);
        }
        else if(pref.readPreference("color").equals("orange")) {
            loginSettingsMenuButton.setGraphic(cogsWhite);
        }
        else{
            loginSettingsMenuButton.setGraphic(cogsWhite);
        }
    }
}
