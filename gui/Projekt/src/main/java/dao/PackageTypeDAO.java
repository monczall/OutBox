package main.java.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.java.entity.PackageType;
import main.java.entity.PdfAreaDTO;
import main.java.entity.Users;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.Date;
import java.util.List;

public class PackageTypeDAO {
    /**
     * Method used to get the list of all packages types that are in database.
     * Returned List is type of PackageType.
     * @return List of package types
     */
    static public List<PackageType> getPackageTypes(){
        Session session = HibernateUtil.getSessionFactory().openSession();

        Query query = session.createQuery("from PackageType");

        List<PackageType> listOfPackageTypes = query.list();

        return listOfPackageTypes;
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


    static public List<PdfAreaDTO> readAreasForPdf(Date dateStart, Date dateEnd) {
        ObservableList<PdfAreaDTO> packages = FXCollections.observableArrayList();

        String hql = "SELECT NEW main.java.entity.PdfAreaDTO(" +
                "A.name, (SELECT COUNT(P.id) FROM PackageType PT, Packages P, PackageHistory PH, Users U WHERE PT.sizeName = " +
                "'mała' AND P.typeId = PT.id AND P.courierId = U.id AND U.areaId = A.id AND P.id = PH.packageId " +
                "AND PH.status = 'Zarejestrowana' AND PH.date BETWEEN :dateStart AND :dateEnd), " +
                "(SELECT COUNT(P.id) FROM PackageType PT, PackageHistory PH, Packages P, Users U WHERE PT.sizeName = " +
                "'średnia' AND P.typeId = PT.id AND P.courierId = U.id AND U.areaId = A.id AND P.id = PH.packageId " +
                "AND PH.status = 'Zarejestrowana' AND PH.date BETWEEN :dateStart AND :dateEnd), " +
                "(SELECT COUNT(P.id) FROM PackageType PT, PackageHistory PH, Packages P, Users U WHERE PT.sizeName = " +
                "'duża' AND P.typeId = PT.id AND P.courierId = U.id AND U.areaId = A.id AND P.id = PH.packageId " +
                "AND PH.status = 'Zarejestrowana' AND PH.date BETWEEN :dateStart AND :dateEnd))" +
                "FROM Packages P, Users U, Areas A, PackageHistory PH WHERE P.id = PH.packageId AND PH.date BETWEEN " +
                ":dateStart AND :dateEnd GROUP BY A.name";

        Session session = HibernateUtil.getSessionFactory().openSession();

        Query query = session.createQuery(hql);
        query.setParameter("dateStart", dateStart);
        query.setParameter("dateEnd", dateEnd);

        List<PdfAreaDTO> results = query.list();

        session.close();
        return results;
    }
}
