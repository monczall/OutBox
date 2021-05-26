package main.java;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.java.dao.UserInfosDAO;
import main.java.dao.UsersDAO;
import main.java.entity.Users;
import main.java.features.Preference;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

import static java.util.concurrent.TimeUnit.DAYS;

public class App extends Application {

    static boolean connectionError = false;
    private static Preference pref = new Preference();
    private static String name;


    public static void main(String[] args) throws FileNotFoundException, ParseException {

        PopulateDatabase.createDbIfNotExists();

        List<Users> dA = UsersDAO.readDeactivatedAccounts();

        for(int i = 0; i < dA.size(); i++){

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate localDate = LocalDate.parse(dA.get(i).getPassword(),dateTimeFormatter);

            if(ChronoUnit.DAYS.between(localDate, LocalDateTime.now()) >= 31){
                UserInfosDAO.deleteUser(dA.get(i).getUserInfoId());
            }

        }

        launch(args);
    }

    public static String getLanguageProperties(String propertyName){

        Properties prop = new Properties();

        if(pref.readPreference("language").equals("english"))
            name = "main/resources/languages/lang_en.properties";
        else
            name = "main/resources/languages/lang_pl.properties";

        try (InputStream input = App.class.getClassLoader().getResourceAsStream(name)){

            if(input == null){
                System.out.println("Sorry, unable to find config.properties");
            }

            prop.load(input);

        }catch (IOException ex){
            ex.printStackTrace();
        }

        return prop.getProperty(propertyName);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        SceneManager.setStage(primaryStage);

        SceneManager.addScene(
                "login",
                "../resources/view/auth/login.fxml");
        SceneManager.addScene(
                "register",
                "../resources/view/auth/register.fxml");
        SceneManager.addScene(
                "passwordReset",
                "../resources/view/auth/passwordReset.fxml");
        SceneManager.addScene(
                "successfulAccountCreation",
                "../resources/view/auth/successfulAccountCreation.fxml");
        SceneManager.addScene(
                "successfulPasswordReset",
                "../resources/view/auth/successfulPasswordReset.fxml");


        SceneManager.addScene(
                "client",
                "../resources/view/client/client.fxml");

        SceneManager.addScene(
                "courier",
                "../resources/view/courier/courier.fxml");

        SceneManager.addScene(
                "interbranchCourier",
                "../resources/view/interbranchCourier/" +
                "interbranchCourier.fxml");

        SceneManager.addScene(
                "manager",
                "../resources/view/manager/manager.fxml");

        SceneManager.addScene(
                "admin",
                "../resources/view/admin/admin.fxml");
        SceneManager.getStage().initStyle(StageStyle.UNDECORATED);
        SceneManager.renderScene("login");

    }

    public static boolean isConnectionError() {
        return connectionError;
    }

    public static void setConnectionError(boolean connectionError) {
        App.connectionError = connectionError;
    }
}
