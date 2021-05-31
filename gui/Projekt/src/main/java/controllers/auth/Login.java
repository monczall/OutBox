package main.java.controllers.auth;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import main.java.App;
import main.java.SceneManager;
import main.java.dao.PackagesDAO;
import main.java.dao.UsersDAO;
import main.java.entity.Packages;
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
        List<Packages> packagesList = PackagesDAO.getPackagesWithoutCourierId();
        List<Users> usersList = UsersDAO.getCouriers("Kurier");
        for (int i = 0; i < packagesList.size(); i++) {
            for (int j = 0; j < usersList.size(); j++) {
                if (packagesList.get(i).getUsersByUserId().getUserInfosByUserInfoId().getVoivodeship().equals(usersList.get(j).getAreasByAreaId().getVoivodeship())) {
                    if (packagesList.get(i).getUsersByUserId().getUserInfosByUserInfoId().getCity().equals(usersList.get(j).getAreasByAreaId().getName())) {
                        List<Users> couriersInArea = UsersDAO.getCouriersByAreaId(usersList.get(j).getAreaId());
                        if(couriersInArea.size() > 1){
                            int courierId = couriersInArea.get(0).getId();
                            Long courierPackages = 999999L;
                            for(int k = 0; k < couriersInArea.size(); k++){
                                if(UsersDAO.getPackagesByCourier(couriersInArea.get(k).getId()) < courierPackages){
                                    courierPackages = UsersDAO.getPackagesByCourier(couriersInArea.get(k).getId());
                                    courierId = couriersInArea.get(k).getId();
                                }
                            }
                            PackagesDAO.updateCourierId(packagesList.get(i).getId(), courierId);
                        }else {
                            PackagesDAO.updateCourierId(packagesList.get(i).getId(), usersList.get(j).getId());
                        }
                        break;
                    }
                }
            }
        }

        // Outbox logos, they change depending on used theme
        ImageView outboxBlack = new ImageView("main/resources/images/outbox_black.png");
        outboxBlack.setFitHeight(200);
        outboxBlack.setFitWidth(150);
        ImageView outboxWhite = new ImageView("main/resources/images/outbox_white.png");
        outboxWhite.setFitHeight(200);
        outboxWhite.setFitWidth(150);

        // Settings icons, they change depending on used theme
        ImageView cogsBlack = new ImageView("main/resources/images/settings_cogs_black.png");
        cogsBlack.setFitHeight(30);
        cogsBlack.setFitWidth(30);
        ImageView cogsWhite = new ImageView("main/resources/images/settings_cogs_white.png");
        cogsWhite.setFitHeight(30);
        cogsWhite.setFitWidth(30);

        // Language changing
        if(pref.readPreference("language").equals("english")) {
            loginPolishLanguageMenuItem.setDisable(false);
            loginEnglishLanguageMenuItem.setDisable(true);

        }else {
            loginPolishLanguageMenuItem.setDisable(true);
            loginEnglishLanguageMenuItem.setDisable(false);
        }

        // Changes to UI depending on used theme
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
            // Database connection error alert
            Alerts.createCustomAlert(loginRightPaneAnchorPane,
                    loginLoginButtonButton, "WARNING",
                    App.getLanguageProperties("authDatabaseConnectionAlert"),
                    425, 86, "alertFailure");
        }
    }

    public void login() {
        // Checking if fields are not empty
        if (!isEmpty(loginEmailTextField.getText(),loginPasswordPasswordField.getText())) {
            // Checking if email has correct format
            if (isEmail(loginEmailTextField.getText())) {
                // Getting users to list
                List<Users> listOfUsers = getUsers();
                for (int i = 0; i < getUsers().size(); i++) {
                    // Checking if email and password is equal to registered user
                    if (loginEmailTextField.getText().equals(listOfUsers.get(i).getEmail())
                            && Encryption.encrypt(loginPasswordPasswordField.getText()).equals
                            (listOfUsers.get(i).getPassword())) {
                        //Setting user data
                        setUserID(listOfUsers.get(i).getId());
                        setUserInfoID(listOfUsers.get(i).getUserInfoId());
                        setUserEmail(listOfUsers.get(i).getEmail());

                        //Checking logged in user's role
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

                // UserTextField
                loginEmailTextField.getStyleClass().clear();
                loginEmailTextField.getStyleClass().add("textFieldsError");

                // UserCircle
                loginUserCircleCircle.getStyleClass().clear();
                loginUserCircleCircle.getStyleClass().add("fillError");

                // PasswordTextField
                loginPasswordPasswordField.getStyleClass().clear();
                loginPasswordPasswordField.getStyleClass().add("textFieldsError");

                // PasswordCircle
                loginPasswordCircleCircle.getStyleClass().clear();
                loginPasswordCircleCircle.getStyleClass().add("fillError");

                // No user error alert
                Alerts.createCustomAlert(loginRightPaneAnchorPane,
                        loginLoginButtonButton, "WARNING",
                        App.getLanguageProperties("authNoUserFoundAlert"),
                        485, 86, "alertFailure");

            } else {
                // UserTextField
                loginEmailTextField.getStyleClass().clear();
                loginEmailTextField.getStyleClass().add("textFieldsError");

                // UserCircle
                loginUserCircleCircle.getStyleClass().clear();
                loginUserCircleCircle.getStyleClass().add("fillError");

                // Wrong email format error alert
                Alerts.createCustomAlert(loginRightPaneAnchorPane,
                        loginLoginButtonButton, "WARNING",
                        App.getLanguageProperties("authWrongEmailFormatAlert"),
                        350, 86, "alertFailure");
            }
        } else {
            // Wrong empty fields error alert
            Alerts.createCustomAlert(loginRightPaneAnchorPane,
                    loginLoginButtonButton, "WARNING",
                    App.getLanguageProperties("authFillFormAlert"),
                    293, 86,
                    "alertFailure");
        }
    }

    /**
     * <p>
     *     Method is used to check if given strings are empty.
     *     In case of empty string it marks inputs from where string came
     *     on red color, indicating that there is error of fields,
     *     and returns true. If strings are not empty i does nothing and returns
     *     true.
     * </p>
     * @param email string that needs to be checked if empty
     * @param password string that needs to be checked if empty
     * @return returns boolean value
     */
    public boolean isEmpty(String email, String password) {
        int error = 0;
        // Checking if email is empty
        if (email.isEmpty()) {
            // UserTextField
            loginEmailTextField.getStyleClass().clear();
            loginEmailTextField.getStyleClass().add("textFieldsError");

            // UserCircle
            loginUserCircleCircle.getStyleClass().clear();
            loginUserCircleCircle.getStyleClass().add("fillError");
            error++;
        }
        // Checking if password is empty
        if (password.isEmpty()) {
            // PasswordTextField
            loginPasswordPasswordField.getStyleClass().clear();
            loginPasswordPasswordField.getStyleClass().add("textFieldsError");

            // PasswordCircle
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

    /**
     * <p>
     *      Method is used to determinate if given string is email.
     *      It matches string with given pattern. In case of correct email it
     *      returns true, otherwise it returns false.
     * </p>
     * @param email string that needs to be checked if its email
     * @return returns boolean value
     */
    public boolean isEmail(String email) {
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


    /**
     * <p>
     *     Method is used to clear errors on certain fields.
     *     It's doing it by changing their appearance.
     * </p>
     * @param keyEvent key that is being pressed
     */
    public void clearErrorsOnEmail(KeyEvent keyEvent) {
        // UserTextField
        loginEmailTextField.getStyleClass().clear();
        loginEmailTextField.getStyleClass().add("textFields");

        // UserCircle
        loginUserCircleCircle.getStyleClass().clear();
        loginUserCircleCircle.getStyleClass().add("fill");
    }

    /**
     * <p>
     *     Method is used to clear errors on certain fields.
     *     It's doing it by changing their appearance.
     * </p>
     * @param keyEvent key that is being pressed
     */
    public void clearErrorsOnPassword(KeyEvent keyEvent) {
        // PasswordTextField
        loginPasswordPasswordField.getStyleClass().clear();
        loginPasswordPasswordField.getStyleClass().add("textFields");

        // PasswordCircle
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

    /**
     * <p>
     *     Method used to change visual appearance of settings button on mouse
     *     enter event
     * </p>
     * @param mouseEvent mouse enter event
     */
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

    /**
     * <p>
     *     Method used to change visual appearance of settings button on mouse
     *     exiting event
     * </p>
     * @param mouseEvent mouse exit event
     */
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
    @FXML
    void exitApp(ActionEvent event) {
        Stage stage = (Stage) loginRightPaneAnchorPane.getScene().getWindow();
        stage.close();
    }

    @FXML
    void minApp(ActionEvent event) {
        Stage stage = (Stage) loginRightPaneAnchorPane.getScene().getWindow();
        stage.setIconified(true);
    }
}
