package main.java;

import main.java.dao.*;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static main.java.App.setConnectionError;

public class PopulateDatabase {


    public static void createDbIfNotExists() throws FileNotFoundException {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "");
            System.out.println("Connection established......");
            //Initialize the script runner
            ScriptRunner sr = new ScriptRunner(con);
            //Creating a reader object
            Reader reader = new BufferedReader(new FileReader("database/database.sql"));
            //Running the script
            sr.runScript(reader);

            //Checking if tables are empty and eventually filling them
            if(UserInfosDAO.getUserInfos().size() == 0){
                ScriptRunner srUserInfos = new ScriptRunner(con);
                Reader userInfosReader = new BufferedReader(new FileReader("database/populate_user_infos.sql"));
                srUserInfos.runScript(userInfosReader);
            }
            if(AreasDAO.getAreas().size() == 0){
                ScriptRunner srAreas = new ScriptRunner(con);
                Reader areasReader = new BufferedReader(new FileReader("database/populate_areas.sql"));
                srAreas.runScript(areasReader);
            }
            if(PackageTypeDAO.getPackageTypes().size() == 0){
                ScriptRunner srPackageTypes = new ScriptRunner(con);
                Reader packageTypesReader = new BufferedReader(new FileReader("database/populate_package_type.sql"));
                srPackageTypes.runScript(packageTypesReader);
            }
            if(UsersDAO.getUsers().size() == 0){
                ScriptRunner srUsers = new ScriptRunner(con);
                Reader usersReader = new BufferedReader(new FileReader("database/populate_users.sql"));
                srUsers.runScript(usersReader);
            }
            if(PackagesDAO.getPackages().size() == 0){
                ScriptRunner srPackages = new ScriptRunner(con);
                Reader packagesReader = new BufferedReader(new FileReader("database/populate_packages.sql"));
                srPackages.runScript(packagesReader);
            }
            if(PackageHistoryDAO.getPackageHistories().size() == 0){
                ScriptRunner srPackageHistories = new ScriptRunner(con);
                Reader packageHistoriesReader = new BufferedReader(new FileReader("database/populate_package_history.sql"));
                srPackageHistories.runScript(packageHistoriesReader);
            }
            
        } catch (SQLException e) {
            //Pushing alert if error when connecting to database
            setConnectionError(true);
        }
    }
}
