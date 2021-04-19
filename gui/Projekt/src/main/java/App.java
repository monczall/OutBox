package main.java;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class App extends Application {

    public static void main(String[] args) throws FileNotFoundException {
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
                e.printStackTrace();
            }
        }

        launch(args);
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
}
