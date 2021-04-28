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

import static main.java.App.setConnectionError;

public class PopulateDatabase {


    public static void createDbIfNotExists() throws FileNotFoundException {
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
    }
}
