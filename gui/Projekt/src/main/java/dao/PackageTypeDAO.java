package main.java.dao;

import main.java.entity.PackageType;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class PackageTypeDAO {

    static public List<PackageType> getPackageTypes(){
        Session session = HibernateUtil.getSessionFactory().openSession();

        Query query = session.createQuery("from PackageType");

        List<PackageType> listOfPackageTypes = query.list();

        return listOfPackageTypes;
    }

    static public List<PackageType> getTypeInfo(){
        Session session = HibernateUtil.getSessionFactory().openSession();

        Query query = session.createQuery("from PackageType");

        List<PackageType> listOfTypeInfo = query.list();

        return listOfTypeInfo;
    }

    static public List<String> getTypeById(int typeId){
        Session session = HibernateUtil.getSessionFactory().openSession();

        Query query=session.createQuery("SELECT sizeName from PackageType WHERE id = :typeId");

        query.setParameter("typeId",typeId);

        List<String> type = query.list();

        return type;
    }
}
