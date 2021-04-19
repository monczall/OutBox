package main.java.dao;

import com.sun.xml.bind.v2.runtime.reflect.Lister;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.java.entity.Packages;
import org.hibernate.Session;

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


}
