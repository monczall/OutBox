package main.java;

import javafx.application.Application;
import javafx.stage.Stage;
import main.java.preferences.Preference;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class App extends Application {

    static boolean connectionError = false;
    private static Preference pref = new Preference();
    private static String name;

    public static void main(String[] args) throws FileNotFoundException {


        //"main/resources/languages/lang_en.properties"

        Connection con = null;
        try {
            //Registering the Driver
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            //Getting the connection
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/outbox","root","");
        } catch (SQLException throwables) {
            try {
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306","root","");
                System.out.println("Connection established......");
                //Initialize the script runner
                ScriptRunner sr = new ScriptRunner(con);
                //Creating a reader object
                Reader reader = new BufferedReader(new FileReader("src/database.sql"));
                //Running the script
                sr.runScript(reader);
            } catch (SQLException e) {
               setConnectionError(true);
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

        SceneManager.addScene("login", "../resources/view/auth/login.fxml");
        SceneManager.addScene("register", "../resources/view/auth/register.fxml");
        SceneManager.addScene("passwordReset", "../resources/view/auth/passwordReset.fxml");

        SceneManager.addScene("client", "../resources/view/client/client.fxml");

        SceneManager.addScene("courier", "../resources/view/courier/courier.fxml");

        SceneManager.addScene("interbranchCourier", "../resources/view/interbranchCourier/interbranchCourier.fxml");

        SceneManager.addScene("manager", "../resources/view/manager/manager.fxml");

        SceneManager.addScene("admin", "../resources/view/admin/admin.fxml");

        primaryStage.setResizable(false);
      //primaryStage.initStyle(StageStyle.UNDECORATED);

        SceneManager.renderScene("login");
    }

    public static boolean isConnectionError() {
        return connectionError;
    }

    public static void setConnectionError(boolean connectionError) {
        App.connectionError = connectionError;
    }
}
