package main.java.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.java.entity.PackageType;
import main.java.entity.PdfDTO;
import main.java.entity.Users;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.Date;
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
	
	static public void updatePackageType(int packTypeId, String size, String weight, String price){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        PackageType packageType = session.get(PackageType.class, packTypeId);

        packageType.setSize(size);
        packageType.setWeight(weight);
        packageType.setPrice(price);

        session.update(packageType);

        session.getTransaction().commit();
        session.close();
	}

    static public ObservableList<PdfDTO> readAreasForPdf(Date dateStart, Date dateEnd) {
        ObservableList<PdfDTO> packages = FXCollections.observableArrayList();


        String hql = "SELECT NEW main.java.entity.PdfAreasDTO(" +
                "A.name, (SELECT COUNT(P.id) FROM PackageType PT, Packages P, Users U WHERE PT.sizeName = " +
                "'mała' AND P.typeId = PT.id AND P.courierId = U.id AND U.areaId = A.id), " +
                "(SELECT COUNT(P.id) FROM PackageType PT, Packages P, Users U WHERE PT.sizeName = " +
                "'średnia' AND P.typeId = PT.id AND P.courierId = U.id AND U.areaId = A.id), " +
                "(SELECT COUNT(P.id) FROM PackageType PT, Packages P, Users U WHERE PT.sizeName = " +
                "'duża' AND P.typeId = PT.id AND P.courierId = U.id AND U.areaId = A.id))" +
                "FROM Areas A " +
                "WHERE PH.date BETWEEN :dateStart AND :dateEnd ";

        Session session = HibernateUtil.getSessionFactory().openSession();

        Query query = session.createQuery(hql);
        query.setParameter("dateStart", dateStart);
        query.setParameter("dateEnd", dateEnd);

        List<PdfDTO> results = query.list();
        for (PdfDTO ent : results) {
            packages.add(ent);
        }
        session.close();
        return packages;
    }
}
