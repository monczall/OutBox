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
import main.java.entity.Users;
import main.java.features.Alerts;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static main.java.dao.UsersDAO.getUsers;

public class Login implements Initializable{

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

    public void initialize(URL url, ResourceBundle rb){
        if(App.isConnectionError()){
            Alerts.createCustomAlert(loginRightPaneAnchorPane, loginCreateAccountButton,"WARNING", App.getLanguageProperties("authDatabaseConnectionAlert"), 425, 86, "alertFailure");
        }
    }

    public void login(){
        if(!isEmpty()){
            if(isEmail(loginEmailTextField.getText())){

                List<Users> listOfUsers = getUsers();
                for(int i = 0; i < getUsers().size(); i++){
                    if(loginEmailTextField.getText().equals(listOfUsers.get(i).getEmail()) && Encryption.encrypt(loginPasswordPasswordField.getText()).equals(listOfUsers.get(i).getPassword())){

                        setUserID(listOfUsers.get(i).getId());
                        setUserInfoID(listOfUsers.get(i).getUserInfoId());

                        String role = listOfUsers.get(i).getRole();
                        if(role.equals("Klient")){
                            System.out.println("Zalogowano klient");
                            SceneManager.renderScene("client");

                        }else if(role.equals("Kurier")){
                            System.out.println("Zalogowano kurier");
                            SceneManager.renderScene("courier");

                        }else if(role.equals("Kurier Międzyoddziałowy")){
                            System.out.println("Zalogowano kurier międzyoddziałowy");
                            SceneManager.renderScene("interbranchCourier");

                        }else if(role.equals("Menadżer")){
                            System.out.println("Zalogowano menadżer");
                            SceneManager.renderScene("manager");

                        }else if(role.equals("Administrator")){
                            System.out.println("Zalogowano administrator");
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

                Alerts.createCustomAlert(loginRightPaneAnchorPane, loginCreateAccountButton,"WARNING",App.getLanguageProperties("authNoUserFoundAlert"), 435, 86, "alertFailure");

            }else{
                //UserTextField
                loginEmailTextField.getStyleClass().clear();
                loginEmailTextField.getStyleClass().add("textFieldsError");
                //UserCircle
                loginUserCircleCircle.getStyleClass().clear();
                loginUserCircleCircle.getStyleClass().add("fillError");

                Alerts.createCustomAlert(loginRightPaneAnchorPane, loginCreateAccountButton,"WARNING",App.getLanguageProperties("authWrongEmailFormatAlert"), 350, 86, "alertFailure");
            }
        }else{
            Alerts.createCustomAlert(loginRightPaneAnchorPane, loginCreateAccountButton,"WARNING",App.getLanguageProperties("authFillFormAlert"), 293, 86, "alertFailure");
        }
    }

    private boolean isEmpty(){
        int error = 0;
        if(loginEmailTextField.getText().isEmpty()){
            //UserTextField
            loginEmailTextField.getStyleClass().clear();
            loginEmailTextField.getStyleClass().add("textFieldsError");
            //UserCircle
            loginUserCircleCircle.getStyleClass().clear();
            loginUserCircleCircle.getStyleClass().add("fillError");
            error++;
        }
        if(loginPasswordPasswordField.getText().isEmpty()){
            //PasswordTextField
            loginPasswordPasswordField.getStyleClass().clear();
            loginPasswordPasswordField.getStyleClass().add("textFieldsError");
            //PasswordCircle
            loginPasswordCircleCircle.getStyleClass().clear();
            loginPasswordCircleCircle.getStyleClass().add("fillError");
            error++;
        }
        if(error > 0){
            return true;
        }else{
            return false;
        }
    }
    private boolean isEmail(String email){
        Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        Matcher mat = pattern.matcher(email);

        if(mat.matches()){
            return true;
        }else{
            return false;
        }
    }

    public void handleLogin(MouseEvent mouseEvent) {
        login();
    }

    public void handleLoginOnEnterPressed(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.ENTER)
        {
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
}
