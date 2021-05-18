package main.java.dao;

import com.itextpdf.text.pdf.PdfPCell;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class PackagesDAO {

    static public List<Packages> getPackages(){
        Session session = HibernateUtil.getSessionFactory().openSession();

        Query query = session.createQuery("from Packages");

        List<Packages> listOfPackages = query.list();

        return listOfPackages;
    }

    static public List<Packages> getPackagesById(int packageId){
        Session session = HibernateUtil.getSessionFactory().openSession();

        Query query = session.createQuery("from Packages P WHERE P.id = :packageId");

        query.setParameter("packageId",packageId);

        List<Packages> listOfPackages = query.list();

        return listOfPackages;
    }


    static public ObservableList<PackagesDTO> getPackagesWithStatus()
    {
        ObservableList<PackagesDTO> packages = FXCollections.observableArrayList();
        String hql = "SELECT NEW main.java.entity.PackagesDTO(" +
                "P.userId, P.id, P.packageNumber, P.timeOfPlannedDelivery, UI.name, UI.surname," +
                " UI.phoneNumber, UI.streetAndNumber, UI.city, PH.status, P.additionalComment, P.email) " +
                "FROM Packages P, UserInfos UI, PackageHistory PH " +
                "WHERE P.id = PH.packageId " +
                "AND P.userInfoId = UI.id " +
                "AND PH.status = (SELECT PH.status " +
                                 "FROM PH " +
                                 "WHERE PH.id = (SELECT MAX(PH.id) " +
                                                "FROM PH " +
                                                "WHERE PH.packageId = P.id )) " +
                "AND NOT PH.status = 'Dostarczona' " +
                "AND NOT PH.status = 'Zwrócona Do Nadawcy' " +
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

    static public ObservableList<PackagesDTO> getPackagesWithStatusAndAreaId(int areaID)
    {
        ObservableList<PackagesDTO> packages = FXCollections.observableArrayList();
        String hql = "SELECT NEW main.java.entity.PackagesDTO(" +
                "P.userId, P.id, P.packageNumber, P.timeOfPlannedDelivery, UI.name, UI.surname," +
                " UI.phoneNumber, UI.streetAndNumber, UI.city, PH.status, P.additionalComment, P.email) " +
                "FROM Packages P, UserInfos UI, PackageHistory PH " +
                "WHERE P.id = PH.packageId " +
                "AND P.userInfoId = UI.id " +
                "AND PH.status = (SELECT PH.status " +
                "FROM PH " +
                "WHERE PH.id = (SELECT MAX(PH.id) " +
                "FROM PH " +
                "WHERE PH.packageId = P.id )) " +
                "AND NOT PH.status = 'Dostarczona' " +
                "AND NOT PH.status = 'Zwrócona Do Nadawcy' " +
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

    static public List<Packages> getPackagesByNullCourier(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from Packages P WHERE P.courierId IS NULL");


        //SELECT * FROM packages P, user_infos U WHERE P.courierID IS NULL AND P.user_infoID = U.id AND U.voivodeship = "Podkarpackie"
        List<Packages> listOfPackages = query.list();

        return listOfPackages;
    }

    static public ObservableList<PdfDTO> readPackagesForPdf(Date dateStart, Date dateEnd) {
        ObservableList<PdfDTO> packages = FXCollections.observableArrayList();

        String hql = "SELECT NEW main.java.entity.PdfDTO(" +
                "P.packageNumber, PT.sizeName, " +
                "UI.city, UI.voivodeship, PH.date) " +
                "FROM Packages P, UserInfos UI, PackageHistory PH, PackageType PT, Users U " +
                "WHERE P.id = PH.packageId " +
                "AND PH.date BETWEEN :dateStart AND :dateEnd " +
                "AND P.typeId = PT.id " +
                "AND P.userInfoId = UI.id " +
                "AND P.userId = U.id " +
                "GROUP BY P.packageNumber";

        Session session = HibernateUtil.getSessionFactory().openSession();

        Query query = session.createQuery(hql);
        query.setParameter("dateStart", dateStart);
        query.setParameter("dateEnd", dateEnd);

        List<PdfDTO> results = query.list();
        for (PdfDTO ent : results) {
            packages.add(ent);
        }
        session.close();
        return packages;
    }

    static public ObservableList<PackagesDTO> readPackagesByID(int userId, String userEmail)
    {
        ObservableList<PackagesDTO> packages = FXCollections.observableArrayList();

        String hql = "SELECT NEW main.java.entity.PackagesDTO(" +
                "P.userId, P.id, P.packageNumber, P.timeOfPlannedDelivery, " +
                "UI.name, UI.surname, UI.phoneNumber, UI.streetAndNumber, " +
                "UI.city, PH.status, P.additionalComment, P.email) " +
                "FROM Packages P, UserInfos UI, PackageHistory PH " +
                "WHERE (P.userId = :id " +
                "OR P.email = :email) " +
                "AND P.userInfoId = UI.id " +
                "AND P.id = PH.packageId " +
                "AND PH.status = (SELECT PH.status " +
                                 "FROM PH " +
                                 "WHERE PH.id = (SELECT MAX(PH.id) " +
                                                "FROM PH " +
                                                "WHERE PH.packageId = P.id )) " +
                "AND NOT PH.status = 'Dostarczona' " +
                "AND NOT PH.status = 'Zwrócona Do Nadawcy' " +
                "GROUP BY P.packageNumber";

        Session session = HibernateUtil.getSessionFactory().openSession();

        Query query = session.createQuery(hql);

        query.setParameter("id",userId);
        query.setParameter("email",userEmail);

        List<PackagesDTO> results = query.list();
        for (PackagesDTO ent : results) {
            packages.add(ent);
        }
        session.close();

        return packages;
    }


    static public ObservableList<PackagesDTO> readHistoryByID(int userId, String userEmail)
    {
        ObservableList<PackagesDTO> packages = FXCollections.observableArrayList();

        String hql = "SELECT NEW main.java.entity.PackagesDTO(" +
                "P.userId, P.id, P.packageNumber, P.timeOfPlannedDelivery, " +
                "UI.name, UI.surname, UI.phoneNumber, UI.streetAndNumber, " +
                "UI.city, PH.status, P.additionalComment, P.email) " +
                "FROM Packages P, UserInfos UI, PackageHistory PH " +
                "WHERE (P.userId = :id " +
                "OR P.email = :email) " +
                "AND P.userInfoId = UI.id " +
                "AND P.id = PH.packageId " +
                "AND PH.status = (SELECT PH.status " +
                                "FROM PH " +
                                "WHERE PH.id = (SELECT MAX(PH.id) " +
                                                "FROM PH " +
                                                "WHERE PH.packageId = P.id )) " +
                "AND PH.status = 'Dostarczona' " +
                "AND PH.status = 'Zwrócona Do Nadawcy' " +
                "GROUP BY P.packageNumber";

        Session session = HibernateUtil.getSessionFactory().openSession();

        Query query = session.createQuery(hql);

        query.setParameter("id",userId);
        query.setParameter("email",userEmail);

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

    static public ObservableList<PackagesDTO> getPackagesWithStatusAndNames()
    {
        ObservableList<PackagesDTO> packages = FXCollections.observableArrayList();
        String hql = "SELECT NEW main.java.entity.PackagesDTO(" +
                "P.id, P.packageNumber, P.usersByUserId.userInfosByUserInfoId.name, P" +
                ".usersByUserId.userInfosByUserInfoId.surname, PH.status, P.packageTypeByTypeId.sizeName," +
                " P.userInfosByUserInfoId.name, P.userInfosByUserInfoId.surname " +
                ") " +
                "FROM Packages P, UserInfos UI, PackageHistory PH " +
                "WHERE P.id = PH.packageId " +
                "AND PH.status = (SELECT PH.status " +
                "FROM PH " +
                "WHERE PH.id = (SELECT MAX(PH.id) " +
                "FROM PH " +
                "WHERE PH.packageId = P.id )) " +
                "AND NOT PH.status = 'Dostarczona' " +
                "AND NOT PH.status = 'Zwrócona Do Nadawcy' " +
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

    static public void updatePackage(int ID, int typeID, int userID, int courierID, int userInfoID, String email, String packageNumber, String time, String additional){

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        Packages packages = new Packages();

        packages.setId(ID);
        packages.setTypeId(typeID);
        packages.setUserId(userID);
        packages.setCourierId(courierID);
        packages.setUserInfoId(userInfoID);
        packages.setEmail(email);
        packages.setPackageNumber(packageNumber);
        packages.setTimeOfPlannedDelivery(time);
        packages.setAdditionalComment(additional);


        session.update(packages);
        session.getTransaction().commit();
        session.close();
    }

}