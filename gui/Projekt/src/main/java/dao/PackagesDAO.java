package main.java.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.java.entity.Packages;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class PackagesDAO {

    static public List<Packages> getPackages(){
        Session session = HibernateUtil.getSessionFactory().openSession();

        Query query = session.createQuery("from Packages");

        List<Packages> listOfPackages = query.list();

        return listOfPackages;
    }

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


}
