package main.java;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.java.dao.UserInfosDAO;
import main.java.dao.UsersDAO;
import main.java.entity.Users;
import main.java.features.Preference;
import org.hibernate.HibernateException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Properties;

public class App extends Application {

    private static final Preference pref = new Preference();
    static boolean connectionError = false;
    private static String name;


    public static void main(String[] args) throws FileNotFoundException, ParseException {

        PopulateDatabase pd = new PopulateDatabase();
        pd.createDbIfNotExists();

        try {
            List<Users> dA = UsersDAO.readDeactivatedAccounts();

            for (int i = 0; i < dA.size(); i++) {

                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate localDate = LocalDate.parse(dA.get(i).getPassword(), dateTimeFormatter);

                if (ChronoUnit.DAYS.between(localDate, LocalDateTime.now()) >= 31) {
                    UserInfosDAO.deleteUser(dA.get(i).getUserInfoId());
                }

            }
        } catch (ExceptionInInitializerError iie) {
            iie.printStackTrace();
        } catch (HibernateException he) {
            he.printStackTrace();
        }

        launch(args);
    }

    public static String getLanguageProperties(String propertyName) {

        Properties prop = new Properties();

        if (Preference.readPreference("language").equals("english"))
            name = "main/resources/languages/lang_en.properties";
        else
            name = "main/resources/languages/lang_pl.properties";

        try (InputStream input = App.class.getClassLoader().getResourceAsStream(name)) {

            if (input == null) {
                System.out.println("Sorry, unable to find config.properties");
            }

            prop.load(input);

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return prop.getProperty(propertyName);
    }

    public static boolean isConnectionError() {
        return connectionError;
    }

    public static void setConnectionError(boolean connectionError) {
        App.connectionError = connectionError;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        SceneManager.setStage(primaryStage);

        SceneManager.addScene(
                "login",
                "main/resources/view/auth/login.fxml");
        SceneManager.addScene(
                "register",
                "main/resources/view/auth/register.fxml");
        SceneManager.addScene(
                "passwordReset",
                "main/resources/view/auth/passwordReset.fxml");
        SceneManager.addScene(
                "successfulAccountCreation",
                "main/resources/view/auth/successfulAccountCreation.fxml");
        SceneManager.addScene(
                "successfulPasswordReset",
                "main/resources/view/auth/successfulPasswordReset.fxml");

        SceneManager.addScene(
                "client",
                "main/resources/view/client/client.fxml");

        SceneManager.addScene(
                "courier",
                "main/resources/view/courier/courier.fxml");

        SceneManager.addScene(
                "interbranchCourier",
                "main/resources/view/interbranchCourier/" +
                        "interbranchCourier.fxml");

        SceneManager.addScene(
                "manager",
                "main/resources/view/manager/manager.fxml");

        SceneManager.addScene(
                "admin",
                "main/resources/view/admin/admin.fxml");

        SceneManager.getStage().setTitle("OutBox");
        SceneManager.getStage().initStyle(StageStyle.UNDECORATED);
        SceneManager.renderScene("login");

    }
}
