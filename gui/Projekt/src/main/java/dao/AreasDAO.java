package main.java.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.java.entity.Areas;
import main.java.entity.AreasDTO;
import main.java.entity.Users;
import main.java.entity.UsersDTO;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class AreasDAO {

    /**
     * <p>
     * Method used to get the list of all areas that are in database.
     * Returned List is type of Areas.
     * </p>
     *
     * @return list of areas
     */
    static public List<Areas> getAreas() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Query query = session.createQuery("from Areas");

        List<Areas> listOfAreas = query.list();

        return listOfAreas;
    }

    /**
     * <p>
     * Method used to insert new areas to database. It requires name,
     * voivodeship, city, street and street number.
     * </p>
     *
     * @param name                         friendly name of new area
     * @param voivodeship                  name of voivodeship where area is located
     * @param city                         name of city where area is located
     * @param department_street_and_number name and number of street
     *                                     where area is located
     */
    static public void insertAreas(String name, String voivodeship, String city, String department_street_and_number) {
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

    /**
     * <p>
     * Method used to return all area names that are available in database.
     * Returned ObservableList is type of String.
     * </p>
     *
     * @return returns observable list of strings
     */
    static public ObservableList<String> getAreasName() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Query query = session.createQuery("select name from Areas");

        List<String> listOfAreas = query.list();
        ObservableList<String> areas = FXCollections.observableArrayList();
        for (String ent : listOfAreas) {
            areas.add(ent);
        }
        return areas;
    }

    /**
     * <p>
     * Method used to get ID that is related to given name of area.
     * Returned value is type of int.
     * </p>
     *
     * @param name name of area that we want to get ID of
     * @return returns ID of given area
     */
    static public int getAreasIdByName(String name) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Query query = session.createQuery("select id from Areas WHERE name =:name");
        query.setParameter("name", name);

        List<Integer> listOfId = query.list();

        return listOfId.get(0);

    }

    /**
     * <p>
     * Method used to get list of columns from area by given areaID.
     * Returned list is type of Areas.
     * </p>
     *
     * @param areaId is ID of area that we are looking for
     * @return list of columns from found area
     */
    static public List<Areas> getAreasById(int areaId) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Query query = session.createQuery("from Areas WHERE id =:areaId");
        query.setParameter("areaId", areaId);

        List<Areas> list = query.list();

        return list;

    }

    static public ObservableList<AreasDTO> getAreaEdit() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Query query = session.createQuery("FROM Areas");

        List<Areas> areaList = query.list();

        ObservableList<Areas> area = FXCollections.observableArrayList();
        for (Areas ent : areaList) {
            area.add(ent);
        }
        ObservableList<AreasDTO> areasDTOS = FXCollections.observableArrayList();
        for (int i = 0; i < area.size(); i++) {
            areasDTOS.add(new AreasDTO(area.get(i).getId(),area.get(i).getName(), area.get(i).getDepartmentStreetAndNumber(),
                    area.get(i).getCity(), area.get(i).getVoivodeship()));
        }

        session.close();

        return areasDTOS;
    }

    static public void editArea(int areaId, String name, String voivodeship,
                                String city, String street
    ) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        Areas area = session.get(Areas.class, areaId);

        area.setName(name);
        area.setVoivodeship(voivodeship);
        area.setCity(city);
        area.setDepartmentStreetAndNumber(street);


        session.update(area);
        session.getTransaction().commit();

        session.close();
    }
}
