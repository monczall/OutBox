package main.java.dao;

import main.java.entity.Areas;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class AreasDAO {

    static public List<Areas> getAreas(){
        Session session = HibernateUtil.getSessionFactory().openSession();

        Query query = session.createQuery("from Areas");

        List<Areas> listOfAreas = query.list();

        return listOfAreas;
    }
}
