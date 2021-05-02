package main.java.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.java.entity.*;
import main.java.entity.PackageHistory_;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.sql.Timestamp;
import org.hibernate.query.Query;


import java.util.List;

public class PackageHistoryDAO {


    static public List<PackageHistory> getPackageHistories(){
        Session session = HibernateUtil.getSessionFactory().openSession();

        Query query = session.createQuery("SELECT status from PackageHistory");

        List<PackageHistory> listOfPackageHistories = query.list();

        return listOfPackageHistories;
    }

    static public List<PackageHistory> getStatuses()
    {
        Session session = HibernateUtil.getSessionFactory().openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();

        CriteriaQuery<PackageHistory> criteria = cb.createQuery(PackageHistory.class);
        Root<PackageHistory> root = criteria.from(PackageHistory.class);
        criteria.select(root);
        criteria.equals(PackageHistory_.ID);
        TypedQuery<PackageHistory> query = session.createQuery(criteria);

        return query.getResultList();
    }

    static public void updateStatus(int packageID, String status, Timestamp timestamp){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        PackageHistory packageHistory = new PackageHistory();

        packageHistory.setPackageId(packageID);
        packageHistory.setStatus(status);
        packageHistory.setDate(timestamp);
        session.save(packageHistory);

        session.getTransaction().commit();
        session.close();
    }

    static public List<String> getStatusById(int packageId){
        Session session = HibernateUtil.getSessionFactory().openSession();

        Query query=session.createQuery("SELECT status from PackageHistory WHERE packageId = :packageId");

        query.setParameter("packageId",packageId);

        List<String> statuses = query.list();

        return  statuses;
    }
}
