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
import main.java.SceneManager;
import main.java.dao.HibernateUtil;
import main.java.dao.UserInfosDAO;
import main.java.dao.UsersDAO;
import main.java.entity.UserInfos;
import main.java.entity.Users;
import main.java.features.Alerts;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static main.java.dao.UserInfosDAO.getUsers;

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

    public void initialize(URL url, ResourceBundle rb){

    }

    public void login(){
        List<Users> test = UsersDAO.showTable();
        for(Users users: test){
            System.out.println(users.getPassword());
        }
        if(!isEmpty()){
            if(isEmail(loginEmailTextField.getText())){
                if(false){
                    //LOGOWANIE "NA SZTYWNO"
                    if(loginEmailTextField.getText().equals("client") && loginPasswordPasswordField.getText().equals("123123")){
                        SceneManager.renderScene("client");
                        System.out.println("Zalogowano jako uzytkownik!");

                    }else if(loginEmailTextField.getText().equals("courier") && loginPasswordPasswordField.getText().equals("123123")){
                        SceneManager.renderScene("courier");
                        System.out.println("Zalogowano jako kurier!");

                    }else if(loginEmailTextField.getText().equals("courier2") && loginPasswordPasswordField.getText().equals("123123")){
                        //SceneManager.renderScene("TU PODAC NAZWE SCENY KURIERA MIEDZYODDZIALOWEGO");
                        System.out.println("Zalogowano jako kurier miedzyoddzialowy!");

                    }else if(loginEmailTextField.getText().equals("kierownik") && loginPasswordPasswordField.getText().equals("123123")){
                        //SceneManager.renderScene("TU PODAC NAZWE SCENY KIEROWNIKA");
                        System.out.println("Zalogowano jako kierownik!");

                    }else if(loginEmailTextField.getText().equals("admin") && loginPasswordPasswordField.getText().equals("123123")){
                        SceneManager.renderScene("admin");
                        System.out.println("Zalogowano jako admin!");

                    }else{
                        Alerts.createCustomAlert(loginRightPaneAnchorPane, loginCreateAccountButton,"WARNING","Podany użytkownik lub/i hasło jest błędne", 375, 86, "alertFailure");
                    }
                    System.out.println(loginEmailTextField.getText());
                    System.out.println(loginPasswordPasswordField.getText());
                }else{
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

                    Alerts.createCustomAlert(loginRightPaneAnchorPane, loginCreateAccountButton,"WARNING","Podany użytkownik lub/i hasło jest błędne", 375, 86, "alertFailure");
                }
            }else{
                //UserTextField
                loginEmailTextField.getStyleClass().clear();
                loginEmailTextField.getStyleClass().add("textFieldsError");
                //UserCircle
                loginUserCircleCircle.getStyleClass().clear();
                loginUserCircleCircle.getStyleClass().add("fillError");

                Alerts.createCustomAlert(loginRightPaneAnchorPane, loginCreateAccountButton,"WARNING","Format adresu email jest błędny", 310, 86, "alertFailure");
            }
        }else{
            Alerts.createCustomAlert(loginRightPaneAnchorPane, loginCreateAccountButton,"WARNING","Uzupełnij dane", 293, 86, "alertFailure");
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
    }

    public void clearErrorsOnPassword(KeyEvent keyEvent) {
        //PasswordTextField
        loginPasswordPasswordField.getStyleClass().clear();
        loginPasswordPasswordField.getStyleClass().add("textFields");
        //PasswordCircle
        loginPasswordCircleCircle.getStyleClass().clear();
        loginPasswordCircleCircle.getStyleClass().add("fill");
    }

}
