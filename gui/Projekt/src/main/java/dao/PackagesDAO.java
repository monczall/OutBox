package main.java.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.java.entity.*;
import main.java.entity.PackageHistory_;
import main.java.entity.Packages_;
import main.java.entity.UserInfos_;
import main.java.entity.Packages;
import main.java.entity.UserInfos;

import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PackagesDAO {



    static public List<Packages> getPackages(){
        Session session = HibernateUtil.getSessionFactory().openSession();

        Query query = session.createQuery("from Packages");

        List<Packages> listOfPackages = query.list();

        return listOfPackages;
    }

    static public ObservableList<PackagesDTO> addTable()
    {
        ObservableList<PackagesDTO> packages = FXCollections.observableArrayList();
        String hql = "SELECT NEW main.java.entity.PackagesDTO(P.userId, P.id, P.packageNumber, P" +
                ".timeOfPlannedDelivery, UI.name, UI.surname, UI.phoneNumber, UI.streetAndNumber, UI.city, PH.status," +
                " P.additionalComment) FROM Packages P, UserInfos UI, PackageHistory PH WHERE P.id = PH.packageId " +
                "AND P.userInfoId = UI.id and PH.status = (SELECT PH.status FROM PH WHERE PH.id = (SELECT MAX(PH.id) FROM" +
                " PH WHERE PH.packageId = P.id )) AND NOT PH.status = 'Dostarczona' GROUP BY P.packageNumber";
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery(hql);
        List<PackagesDTO> results = query.list();
        for (PackagesDTO ent : results) {
            packages.add(ent);
        }
        session.close();
        return packages;
    }

    static public ObservableList<PackagesDTO> addTableHistory()
    {
        ObservableList<PackagesDTO> packages = FXCollections.observableArrayList();
        String hql = "SELECT NEW main.java.entity.PackagesDTO(P.userId, P.id, P.packageNumber, P" +
                ".timeOfPlannedDelivery," +
                " UI.name, UI.surname, UI.phoneNumber, UI.streetAndNumber, UI.city, PH.status, P.additionalComment) " +
                "FROM Packages P, UserInfos UI, PackageHistory PH WHERE P.id = PH.packageId AND P.userInfoId = UI.id AND " +
                "PH.status = (SELECT PH.status FROM PH WHERE PH.id = (SELECT MAX(PH.id) FROM PH WHERE PH.packageId = P.id )) AND PH.status = 'Dostarczona'" +
                "GROUP BY P.packageNumber";
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery(hql);
        List<PackagesDTO> results = query.list();
        for (PackagesDTO ent : results) {
            packages.add(ent);
        }
        session.close();
        return packages;
    }

    static public List<Packages> readPackages(){
        Session session = HibernateUtil.getSessionFactory().openSession();

        Query query = session.createQuery("FROM Packages GROUP BY packageNumber");

        List<Packages> packageList = query.list();

        session.close();

        return packageList;
    }
    static public List<Packages> readPackagesForManager(String sql){
        Session session = HibernateUtil.getSessionFactory().openSession();

        Query query = session.createQuery(sql);

        List<Packages> packageList = query.list();

        session.close();

        return packageList;
    }

}