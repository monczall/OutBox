package main.java.dao;

import com.sun.xml.bind.v2.runtime.reflect.Lister;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.java.entity.Packages;
import main.java.entity.UserInfos;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class PackagesDAO {

    static public ObservableList<Packages> addTable()
    {
        ObservableList<Packages> packages = FXCollections.observableArrayList();

        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Packages> list = session.createCriteria(Packages.class).list();

        for(Packages ent : list)
        {
            packages.add(ent);
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


}
