package main.java.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.java.entity.Areas;
import main.java.entity.PackageType;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public class AreasDAO {

    static public List<Areas> getAreas(){
        Session session = HibernateUtil.getSessionFactory().openSession();

        Query query = session.createQuery("from Areas");

        List<Areas> listOfAreas = query.list();

        return listOfAreas;
    }

    static public void insertAreas(String name, String voivodeship, String city, String department_street_and_number){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        Areas area = new Areas();

        area.setName(name);
        area.setVoivodeship(voivodeship);
        area.setCity(city);
        area.setDepartmentStreetAndNumber(department_street_and_number);

        session.save(area);

        session.getTransaction().commit();
    }

    static public ObservableList<String> getAreasName(){
        Session session = HibernateUtil.getSessionFactory().openSession();

        Query query = session.createQuery("select name from Areas");

        List<String> listOfAreas = query.list();
        ObservableList<String> areas = FXCollections.observableArrayList();
        for (String ent : listOfAreas) {
            areas.add(ent);
        }
        return areas;
    }

    static public int getAreasIdByName(String name){
        Session session = HibernateUtil.getSessionFactory().openSession();

        Query query = session.createQuery("select id from Areas WHERE name =:name");
        query.setParameter("name", name);

        List<Integer> listOfId = query.list();

        return listOfId.get(0);

    }

    static public List<Areas> getAreasByVoivodeship(String voivodeship){
        Session session = HibernateUtil.getSessionFactory().openSession();

        Query query = session.createQuery("from Areas WHERE voivodeship=:voivodeship");
        query.setParameter("voivodeship", voivodeship);

        List<Areas> listOfAreas = query.list();

        return listOfAreas;
    }

    static public List<Areas> getAreasById(int areaId){
        Session session = HibernateUtil.getSessionFactory().openSession();

        Query query = session.createQuery("from Areas WHERE id =:areaId");
        query.setParameter("areaId", areaId);

        List<Areas> list = query.list();

        return list;

    }
}
