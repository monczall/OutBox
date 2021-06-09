package main.java.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.java.entity.PackageType;
import main.java.entity.PdfAreaDTO;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.Date;
import java.util.List;

public class PackageTypeDAO {
    /**
     * Method used to get the list of all packages types that are in database.
     * Returned List is type of PackageType.
     *
     * @return List of package types
     */
    static public List<PackageType> getPackageTypes() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Query query = session.createQuery("from PackageType");

        List<PackageType> listOfPackageTypes = query.list();

        return listOfPackageTypes;
    }

    /**
     * <p>
     * Method used to update information about given packageTypeId
     * </p>
     *
     * @param packTypeId package to edit
     * @param size       max package size of given type (ex. 25cm x 25cm x 25cm)
     * @param weight     max package weight of given type
     * @param price      price to pay for package delivery
     */
    static public void updatePackageType(int packTypeId, String size, String weight, String price) {
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

    /**
     * <p>
     * Method used to get packages in areas in between selected date.
     * </p>
     *
     * @param dateStart starting date
     * @param dateEnd   ending date
     * @return List type of PdfAreaDTO
     */
    static public List<PdfAreaDTO> readAreasForPdf(Date dateStart, Date dateEnd) {
        ObservableList<PdfAreaDTO> packages = FXCollections.observableArrayList();

        String hql = "SELECT NEW main.java.entity.PdfAreaDTO(" +
                "A.name, (SELECT COUNT(P.id) FROM PackageType PT, Packages P, PackageHistory PH, Users U WHERE PT.sizeName = " +
                "'mala' AND P.typeId = PT.id AND P.courierId = U.id AND U.areaId = A.id AND P.id = PH.packageId " +
                "AND (PH.status = 'Dostarczona' OR PH.status = 'Zwrocona Do Nadawcy') AND PH.date BETWEEN :dateStart AND :dateEnd), " +
                "(SELECT COUNT(P.id) FROM PackageType PT, PackageHistory PH, Packages P, Users U WHERE PT.sizeName = " +
                "'srednia' AND P.typeId = PT.id AND P.courierId = U.id AND U.areaId = A.id AND P.id = PH.packageId " +
                "AND (PH.status = 'Dostarczona' OR PH.status = 'Zwrocona Do Nadawcy')  AND PH.date BETWEEN :dateStart AND :dateEnd), " +
                "(SELECT COUNT(P.id) FROM PackageType PT, PackageHistory PH, Packages P, Users U WHERE PT.sizeName = " +
                "'duza' AND P.typeId = PT.id AND P.courierId = U.id AND U.areaId = A.id AND P.id = PH.packageId " +
                "AND (PH.status = 'Dostarczona' OR PH.status = 'Zwrocona Do Nadawcy') AND PH.date BETWEEN :dateStart AND :dateEnd))" +
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
