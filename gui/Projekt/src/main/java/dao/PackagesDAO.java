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

    static public ObservableList<PackagesExtended> addTable()
    {/*
        ObservableList<Packages> packages = FXCollections.observableArrayList();

        Session session = HibernateUtil.getSessionFactory().openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();

        CriteriaQuery<PackagesExtended> criteria = cb.createQuery(PackagesExtended.class);
        Root<Packages> packagesRoot = criteria.from(Packages.class);
        Root<UserInfos> userInfosRoot = criteria.from(UserInfos.class);
        Root<PackageHistory> packageHistoryRoot = criteria.from(PackageHistory.class);
        List<Predicate> conditions = new ArrayList<>();
        conditions.add(cb.equal(packagesRoot.get(Packages_.userInfosByUserInfoId).get("id"), userInfosRoot.get(UserInfos_.ID)));
        conditions.add(cb.equal(packageHistoryRoot.get(PackageHistory_.packagesByPackageId).get("id"),packagesRoot.get(Packages_.ID)));
        criteria.select(cb.construct(PackagesExtended.class, packagesRoot.get(Packages_.PACKAGE_NUMBER),
                packagesRoot.get(Packages_.TIME_OF_PLANNED_DELIVERY), userInfosRoot.get(UserInfos_.NAME), userInfosRoot.get(UserInfos_.SURNAME),
                userInfosRoot.get(UserInfos_.PHONE_NUMBER), userInfosRoot.get(UserInfos_.STREET_AND_NUMBER), userInfosRoot.get(UserInfos_.CITY),
                packageHistoryRoot.get(PackageHistory_.STATUS))).where(conditions.toArray(conditions.toArray(new Predicate[]{})));

        //criteria.groupBy(packagesRoot.get(Packages_.PACKAGE_NUMBER));
        //Join<Packages, UserInfos> packagesUserInfosJoin = packagesRoot.join(Packages_.userInfosByUserInfoId);
        //Fetch<Packages, UserInfos> packagesUserInfosFetch = packagesRoot.fetch(Packages_.userInfosByUserInfoId);
        //Join<PackageHistory, Packages> packageHistoryPackagesJoin = packageHistoryRoot.join(PackageHistory_.packagesByPackageId);
        //criteria.where(cb.equal(packagesRoot.get(Packages_.ID), packageHistoryRoot.get(PackageHistory_.PACKAGE_ID)));
//
        Subquery<PackageHistory> subquery = criteria.subquery(PackageHistory.class);
        Root<PackageHistory> packageHistoryRoot1 = subquery.from(PackageHistory.class);
        Root<Packages> packagesRoot1 = subquery.from(Packages.class);
        subquery.select(packageHistoryRoot1.get(PackageHistory_.STATUS));
        subquery.where(cb.equal(packagesRoot.get(Packages_.ID), packageHistoryRoot1.get(PackageHistory_.PACKAGE_ID)));
        subquery.from(PackageHistory.class);
        criteria.where(packageHistoryRoot.get(PackageHistory_.STATUS).in(subquery));
//
//
        Subquery<Number> subquery1 = criteria.subquery(Number.class);
        Root<PackageHistory> packageHistoryRoot2 = subquery1.from(PackageHistory.class);
        subquery1.select(cb.max(packageHistoryRoot.get(PackageHistory_.ID))).where(conditions.toArray(conditions.toArray(new Predicate[]{})));;
        subquery1.from(PackageHistory.class);
        subquery1.where(cb.equal(packagesRoot.get(Packages_.ID), packageHistoryRoot2.get(PackageHistory_.PACKAGE_ID)));
        subquery.where(packageHistoryRoot.get(PackageHistory_.ID).in(subquery1));

        TypedQuery<PackagesExtended> query = session.createQuery(criteria);

        List<PackagesExtended> results = query.getResultList();

        ObservableList<PackagesExtended> packages = FXCollections.observableArrayList();

        for (PackagesExtended ent : results) {
            packages.add(ent);
        }
        session.close();
        */
        ObservableList<PackagesExtended> packages = FXCollections.observableArrayList();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/outbox","root","");
            Statement statement = connection.createStatement();
            String sql = "select p.id, p.user_infoID, p.additional_comment, p.package_number, ui.name, ui.surname, ui.city, ui.street_and_number,ui.phone_number, ph.status," +
                    " p.time_of_planned_delivery from packages p, package_history ph, user_infos ui where p.id = ph.packageID and" +
                    " p.user_infoID = ui.ID and ph.status = (select ph.status from package_history ph where ph.id = (select max(ph.id)" +
                    " from package_history ph where ph.packageID = p.ID)) group by p.package_number";
            ResultSet rs = statement.executeQuery(sql);

            while(rs.next()){
                packages.add(new PackagesExtended(rs.getInt("id"),rs.getInt("id"),
                        rs.getString("package_number"),rs.getString("time_of_planned_delivery"),
                        rs.getString("name"),rs.getString("surname"),rs.getString("phone_number"),
                        rs.getString("street_and_number"),rs.getString("city"),rs.getString("status"),
                        rs.getString("additional_comment")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
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
