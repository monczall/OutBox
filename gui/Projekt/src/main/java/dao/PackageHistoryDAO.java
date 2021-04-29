package main.java.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.java.entity.PackageHistory;
import main.java.entity.Packages;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class PackageHistoryDAO {

    static public List<PackageHistory> getPackageHistories(){
        Session session = HibernateUtil.getSessionFactory().openSession();

        Query query = session.createQuery("from PackageHistory ");

        List<PackageHistory> listOfPackageHistories = query.list();

        return listOfPackageHistories;
    }
    
    static public List<PackageHistory> getStatuses()
    {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<PackageHistory> list = session.createCriteria(PackageHistory.class).list();

        return list;
    }

    static public List<String> getStatusById(int packageId){
        Session session = HibernateUtil.getSessionFactory().openSession();

        Query query=session.createQuery("SELECT status from PackageHistory WHERE packageId = :packageId");

        query.setParameter("packageId",packageId);

        List<String> statuses = query.list();

        return  statuses;
    }
}
