package main.java;

import com.mysql.cj.jdbc.exceptions.CommunicationsException;
import main.java.dao.*;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static main.java.App.setConnectionError;

public class PopulateDatabase {

    /**
     * <p>
     * Method used to create database structure and fill it with data if it
     * doesn't exists. Additionally if database exists, but is empty - fills
     * certain tables.
     * </p>
     *
     * @throws FileNotFoundException is being thrown if there is no file at given location
     */
    public static void createDbIfNotExists() throws FileNotFoundException {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/outbox", "root", "");
            System.out.println("Connection established......");
            if (UserInfosDAO.getUserInfos().size() == 0) {
                ScriptRunner srUserInfos = new ScriptRunner(con);
                Reader userInfosReader = new BufferedReader(new FileReader("main/resources/database" +
                        "/populate_user_infos.sql"));
                srUserInfos.runScript(userInfosReader);
            }
            if (AreasDAO.getAreas().size() == 0) {
                ScriptRunner srAreas = new ScriptRunner(con);
                Reader areasReader = new BufferedReader(new FileReader("main/resources/database/populate_areas.sql"));
                srAreas.runScript(areasReader);
            }
            if (PackageTypeDAO.getPackageTypes().size() == 0) {
                ScriptRunner srPackageTypes = new ScriptRunner(con);
                Reader packageTypesReader = new BufferedReader(new FileReader("main/resources/database/populate_package_type.sql"));
                srPackageTypes.runScript(packageTypesReader);
            }
            if (UsersDAO.getUsers().size() == 0) {
                ScriptRunner srUsers = new ScriptRunner(con);
                Reader usersReader = new BufferedReader(new FileReader("main/resources/database/populate_users.sql"));
                srUsers.runScript(usersReader);
            }
            if (PackagesDAO.getPackages().size() == 0) {
                ScriptRunner srPackages = new ScriptRunner(con);
                Reader packagesReader = new BufferedReader(new FileReader("main/resources/database/populate_packages.sql"));
                srPackages.runScript(packagesReader);
            }
            if (PackageHistoryDAO.getStatuses().size() == 0) {
                ScriptRunner srPackageHistories = new ScriptRunner(con);
                Reader packageHistoriesReader = new BufferedReader(new FileReader("main/resources/database/populate_package_history.sql"));
                srPackageHistories.runScript(packageHistoriesReader);
            }
        } catch (SQLException e) {
            try {
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");

                ScriptRunner sr = new ScriptRunner(con);
                //Creating a reader object
                Reader reader = new BufferedReader(new FileReader("main/resources/database/database.sql"));
                //Running the script
                sr.runScript(reader);


                Reader userInfosReader = new BufferedReader(new FileReader("main/resources/database/populate_user_infos.sql"));
                sr.runScript(userInfosReader);
                Reader areasReader = new BufferedReader(new FileReader("main/resources/database/populate_areas.sql"));
                sr.runScript(areasReader);
                Reader packageTypesReader = new BufferedReader(new FileReader("main/resources/database/populate_package_type.sql"));
                sr.runScript(packageTypesReader);
                Reader usersReader = new BufferedReader(new FileReader("main/resources/database/populate_users.sql"));
                sr.runScript(usersReader);
                Reader packagesReader = new BufferedReader(new FileReader("main/resources/database/populate_packages.sql"));
                sr.runScript(packagesReader);
                Reader packageHistoriesReader = new BufferedReader(new FileReader("main/resources/database/populate_package_history.sql"));
                sr.runScript(packageHistoriesReader);

                //Checking if tables are empty and eventually filling them

            } catch (Exception ex) {
                //Pushing alert if error when connecting to database
                setConnectionError(true);
            }
        }
    }
}
