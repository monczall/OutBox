package main.java;

import com.mysql.cj.jdbc.exceptions.CommunicationsException;
import main.java.dao.*;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.*;
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
    public  void createDbIfNotExists() throws FileNotFoundException {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/outbox", "root", "");
            System.out.println("Connection established......");
            if (UserInfosDAO.getUserInfos().size() == 0) {
                ScriptRunner srUserInfos = new ScriptRunner(con);
                InputStream in = getClass().getClassLoader().getResourceAsStream("main/resources/database" +
                        "/populate_user_infos.sql");
                Reader userInfosReader = new BufferedReader(new InputStreamReader(in));
                srUserInfos.runScript(userInfosReader);
            }
            if (AreasDAO.getAreas().size() == 0) {
                ScriptRunner srAreas = new ScriptRunner(con);
                InputStream in = getClass().getClassLoader().getResourceAsStream("main/resources/database" +
                        "/populate_areas.sql");
                Reader areasReader = new BufferedReader(new InputStreamReader(in));
                srAreas.runScript(areasReader);
            }
            if (PackageTypeDAO.getPackageTypes().size() == 0) {
                ScriptRunner srPackageTypes = new ScriptRunner(con);
                InputStream in = getClass().getClassLoader().getResourceAsStream("main/resources/database" +
                        "/populate_package_type.sql");
                Reader packageTypesReader = new BufferedReader(new InputStreamReader(in));
                srPackageTypes.runScript(packageTypesReader);
            }
            if (UsersDAO.getUsers().size() == 0) {
                ScriptRunner srUsers = new ScriptRunner(con);
                InputStream in = getClass().getClassLoader().getResourceAsStream("main/resources/database" +
                        "/populate_users.sql");
                Reader usersReader = new BufferedReader(new InputStreamReader(in));
                srUsers.runScript(usersReader);
            }
            if (PackagesDAO.getPackages().size() == 0) {
                ScriptRunner srPackages = new ScriptRunner(con);
                InputStream in = getClass().getClassLoader().getResourceAsStream("main/resources/database" +
                        "/populate_packages.sql");
                Reader packagesReader = new BufferedReader(new InputStreamReader(in));
                srPackages.runScript(packagesReader);
            }
            if (PackageHistoryDAO.getStatuses().size() == 0) {
                ScriptRunner srPackageHistories = new ScriptRunner(con);
                InputStream in = getClass().getClassLoader().getResourceAsStream("main/resources/database" +
                        "/populate_package_history.sql");
                Reader packageHistoriesReader = new BufferedReader(new InputStreamReader(in));
                srPackageHistories.runScript(packageHistoriesReader);
            }
        } catch (SQLException e) {
            try {
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");

                ScriptRunner sr = new ScriptRunner(con);
                InputStream in = getClass().getClassLoader().getResourceAsStream("main/resources/database/database.sql");
                Reader reader = new BufferedReader(new InputStreamReader(in));
                sr.runScript(reader);

                in = getClass().getClassLoader().getResourceAsStream("main/resources/database/populate_user_infos.sql");
                Reader userInfosReader = new BufferedReader(new InputStreamReader(in));
                sr.runScript(userInfosReader);

                in = getClass().getClassLoader().getResourceAsStream("main/resources/database/populate_areas.sql");
                Reader areasReader = new BufferedReader(new InputStreamReader(in));
                sr.runScript(areasReader);

                in = getClass().getClassLoader().getResourceAsStream("main/resources/database/populate_package_type.sql");
                Reader packageTypesReader = new BufferedReader(new InputStreamReader(in));
                sr.runScript(packageTypesReader);

                in = getClass().getClassLoader().getResourceAsStream("main/resources/database/populate_users.sql");
                Reader usersReader = new BufferedReader(new InputStreamReader(in));
                sr.runScript(usersReader);

                in = getClass().getClassLoader().getResourceAsStream("main/resources/database/populate_packages.sql");
                Reader packagesReader = new BufferedReader(new InputStreamReader(in));
                sr.runScript(packagesReader);

                in = getClass().getClassLoader().getResourceAsStream("main/resources/database/populate_package_history.sql");
                Reader packageHistoriesReader = new BufferedReader(new InputStreamReader(in));
                sr.runScript(packageHistoriesReader);

                //Checking if tables are empty and eventually filling them

            } catch (Exception ex) {
                //Pushing alert if error when connecting to database
                setConnectionError(true);
            }
        }
    }
}
