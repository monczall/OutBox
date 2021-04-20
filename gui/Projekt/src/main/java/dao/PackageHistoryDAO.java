package main.java.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.java.entity.PackageHistory;
import main.java.entity.Packages;
import org.hibernate.Session;

import java.util.List;

public class PackageHistoryDAO {
    static public List<PackageHistory> getStatuses()
    {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<PackageHistory> list = session.createCriteria(PackageHistory.class).list();

        return list;
    }
}
