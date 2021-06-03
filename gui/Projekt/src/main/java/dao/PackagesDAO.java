package main.java.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.java.controllers.auth.Login;
import main.java.entity.*;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.Date;
import java.util.List;


public class PackagesDAO {

    /**
     * <p>
     * Method used to get the list of all packages that are in database.
     * Returned List is type of Packages.
     * </p>
     *
     * @return returns list of packages
     */
    static public List<Packages> getPackages() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Query query = session.createQuery("from Packages");

        List<Packages> listOfPackages = query.list();

        return listOfPackages;
    }

    /**
     * <p>
     * Method used to get list of columns that are connected to give packageID.
     * Returned List is type of Packages.
     * </p>
     *
     * @param packageId ID of package that needs to be read
     * @return list of package columns
     */
    static public List<Packages> getPackagesById(int packageId) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Query query = session.createQuery("from Packages P WHERE P.id = :packageId");

        query.setParameter("packageId", packageId);

        List<Packages> listOfPackages = query.list();

        return listOfPackages;
    }

    /**
     * <p>
     * Method used to return list of all packages that have statuses
     * different than: "Dostarczona" or "Zwrócona do nadawcy". Returned
     * ObservableList is type of PackagesDTO and is grouped by packageNumber.
     * </p>
     *
     * @return list of packages
     */
    static public ObservableList<PackagesDTO> getPackagesWithStatus() {
        ObservableList<PackagesDTO> packages = FXCollections.observableArrayList();
        String hql = "SELECT NEW main.java.entity.PackagesDTO(" +
                "P.userId, P.id, P.packageNumber, P.timeOfPlannedDelivery, UI.name, UI.surname," +
                " UI.phoneNumber, UI.streetAndNumber, UI.city, PH.status, P.additionalComment, P.email, " +
                "P.userInfosByUserInfoId.voivodeship, P.userInfosByUserInfoId.name) " +
                "FROM Packages P, UserInfos UI, PackageHistory PH " +
                "WHERE P.id = PH.packageId " +
                "AND P.userInfoId = UI.id " +
                "AND PH.status = (SELECT PH.status " +
                "FROM PH " +
                "WHERE PH.id = (SELECT MAX(PH.id) " +
                "FROM PH " +
                "WHERE PH.packageId = P.id )) " +
                "AND NOT PH.status = 'Dostarczona' " +
                "AND NOT PH.status = 'Zwrocona Do Nadawcy' " +
                "GROUP BY P.packageNumber";
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery(hql);
        List<PackagesDTO> results = query.list();
        for (PackagesDTO ent : results) {
            packages.add(ent);
        }
        session.close();

        return packages;
    }

    /**
     * <p>
     * Method used to return list of all packages with given courierId
     * that have statuses different than: "Dostarczona" or
     * "Zwrócona do nadawcy". Returned ObservableList is type of PackagesDTO
     * and is grouped by packageNumber.
     * </p>
     *
     * @param courierId id of courier
     * @return list of packages
     */
    static public ObservableList<PackagesDTO> getPackagesWithStatusById(int courierId) {
        ObservableList<PackagesDTO> packages = FXCollections.observableArrayList();
        String hql = "SELECT NEW main.java.entity.PackagesDTO(" +
                "P.userId, P.id, P.packageNumber, P.timeOfPlannedDelivery, UI.name, UI.surname, " +
                "UI.phoneNumber, UI.streetAndNumber, UI.city, PH.status, P.additionalComment, P.email, " +
                "P.userInfosByUserInfoId.voivodeship, P.userInfosByUserInfoId.name) " +
                "FROM Packages P, UserInfos UI, PackageHistory PH " +
                "WHERE P.id = PH.packageId " +
                "AND P.courierId = :courierId " +
                "AND P.userInfoId = UI.id " +
                "AND PH.status = (SELECT PH.status " +
                "FROM PH " +
                "WHERE PH.id = (SELECT MAX(PH.id) " +
                "FROM PH " +
                "WHERE PH.packageId = P.id )) " +
                "AND NOT PH.status = 'Dostarczona' " +
                "AND NOT PH.status = 'Zwrocona Do Nadawcy' " +
                "AND NOT PH.status = 'Do Odebrania W Oddziale' " +
                "GROUP BY P.packageNumber";

        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery(hql);
        query.setParameter("courierId", courierId);

        List<PackagesDTO> results = query.list();
        for (PackagesDTO ent : results) {
            packages.add(ent);
        }
        session.close();

        return packages;
    }

    /**
     * <p>
     * Method used to return list of packages without assigned courierID. It
     * means that it returns packages that are not yet connected to any courier.
     * Returned List is type of Packages.
     * </p>
     *
     * @return list of packages
     */
    static public List<Packages> getPackagesByNullCourier() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from Packages P WHERE P.courierId IS NULL");

        List<Packages> listOfPackages = query.list();

        return listOfPackages;
    }

    /**
     * <p>
     * Method used in generating PDF with packages that status is
     * "Zarejestrowana" and its date is between given dates. Returns
     * ObservableList type of PdfDTO.
     * </p>
     *
     * @param dateStart date "from"
     * @param dateEnd   date "to"
     * @return ObservableList of packages
     */
    static public ObservableList<PdfDTO> readPackagesForPdf(Date dateStart, Date dateEnd) {
        ObservableList<PdfDTO> packages = FXCollections.observableArrayList();

        String hql = "SELECT NEW main.java.entity.PdfDTO(" +
                "P.packageNumber, PT.sizeName, " +
                "P.userInfosByUserInfoId.city, P.userInfosByUserInfoId.voivodeship, " +
                "PH.date, P.id, P.usersByCourierId.areaId) " +
                "FROM Packages P, PackageHistory PH, PackageType PT " +
                "WHERE P.id = PH.packageId " +
                "AND PH.date BETWEEN :dateStart AND :dateEnd " +
                "AND P.typeId = PT.id " +
                "AND (PH.status = 'Dostarczona' " +
                "OR PH.status = 'Zwrocona Do Nadawcy' " +
                "OR PH.status = 'Do Odebrania W Oddziale') " +
                "GROUP BY P.packageNumber";

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

    /**
     * <p>
     * Method used to read all packages informations that are not yet
     * delivered ("Dostarczona" or "Zwrócona do nadawcy")
     * </p>
     *
     * @param userId    id of user connected to package
     * @param userEmail email of user connected to package
     * @return packageDTO
     */
    static public ObservableList<PackagesDTO> readPackagesByID(int userId, String userEmail) {
        ObservableList<PackagesDTO> packages = FXCollections.observableArrayList();

        String hql = "SELECT NEW main.java.entity.PackagesDTO(" +
                "P.userId, P.id, P.packageNumber, P.timeOfPlannedDelivery, " +
                "P.usersByUserId.userInfosByUserInfoId.name, UI.surname, UI.phoneNumber, UI.streetAndNumber, " +
                "UI.city, PH.status, P.additionalComment, P.email, UI.voivodeship, P.userInfosByUserInfoId.name) " +
                "FROM Packages P, UserInfos UI, PackageHistory PH " +
                "WHERE (P.userId = :id " +
                "OR P.email = :email) " +
                "AND P.userInfoId = UI.id " +
                "AND P.id = PH.packageId " +
                "AND PH.status = (SELECT PH.status " +
                "FROM PH " +
                "WHERE PH.id = (SELECT MAX(PH.id) " +
                "FROM PH " +
                "WHERE PH.packageId = P.id )) " +
                "AND NOT PH.status = 'Dostarczona' " +
                "AND NOT PH.status = 'Zwrocona Do Nadawcy' " +
                "GROUP BY P.packageNumber";

        Session session = HibernateUtil.getSessionFactory().openSession();

        Query query = session.createQuery(hql);

        query.setParameter("id", userId);
        query.setParameter("email", userEmail);

        List<PackagesDTO> results = query.list();
        for (PackagesDTO ent : results) {
            packages.add(ent);
        }
        session.close();

        return packages;
    }

    /**
     * <p>
     * Method used to read all packages informations that are not yet
     * delivered ("Dostarczona" or "Zwrócona do nadawcy")
     * </p>
     *
     * @param userId    id of user connected to package
     * @param userEmail email of user connected to package
     * @return packageDTO
     */
    static public ObservableList<PackagesDTO> readHistoryByID(int userId, String userEmail) {
        ObservableList<PackagesDTO> packages = FXCollections.observableArrayList();

        String hql = "SELECT NEW main.java.entity.PackagesDTO(" +
                "P.userId, P.id, P.packageNumber, P.timeOfPlannedDelivery, " +
                "P.usersByUserId.userInfosByUserInfoId.name, UI.surname, UI.phoneNumber, UI.streetAndNumber, " +
                "UI.city, PH.status, P.additionalComment, P.email, " +
                "UI.voivodeship, P.userInfosByUserInfoId.name) " +
                "FROM Packages P, UserInfos UI, PackageHistory PH " +
                "WHERE (P.userId = :id " +
                "OR P.email = :email) " +
                "AND P.userInfoId = UI.id " +
                "AND P.id = PH.packageId " +
                "AND PH.status = (SELECT PH.status " +
                "FROM PH " +
                "WHERE PH.id = (SELECT MAX(PH.id) " +
                "FROM PH " +
                "WHERE PH.packageId = P.id )) " +
                "AND (PH.status = 'Dostarczona' " +
                "OR PH.status = 'Zwrocona Do Nadawcy') " +
                "GROUP BY P.packageNumber";

        Session session = HibernateUtil.getSessionFactory().openSession();

        Query query = session.createQuery(hql);

        query.setParameter("id", userId);
        query.setParameter("email", userEmail);

        List<PackagesDTO> results = query.list();
        for (PackagesDTO ent : results) {
            packages.add(ent);
        }
        session.close();
        return packages;
    }

    /**
     * <p>
     * Method used to return number of package types that are in database.
     * </p>
     *
     * @param month current month
     * @return List of package types
     */
    static public ObservableList<PieChartDTO> quantityOfPackagesType(String month) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        ObservableList<PieChartDTO> list = FXCollections.observableArrayList();

        Query query = session.createQuery("SELECT NEW main.java.entity.PieChartDTO(PT.sizeName, COUNT(P.typeId)) " +
                "FROM PackageType PT, Packages P " +
                "WHERE SUBSTRING(P.packageNumber, 3, 2) = :month " +
                "AND P.typeId = PT.id " +
                "AND (P.usersByCourierId.areaId = :areaId " +
                "OR P.userInfosByUserInfoId.voivodeship = :voivodeship " +
                "OR P.userInfosByUserInfoId.city = :city ) " +
                "GROUP BY PT.sizeName ");

        Users uu = UsersDAO.getUsersId(Login.getUserID()).get(0);

        query.setParameter("month", month);
        query.setParameter("areaId", uu.getAreaId());
        query.setParameter("voivodeship", uu.getUserInfosByUserInfoId().getVoivodeship());
        query.setParameter("city", uu.getUserInfosByUserInfoId().getCity());


        List<PieChartDTO> results = query.list();

        for (PieChartDTO ent : results) {
            list.add(ent);
        }
        session.close();

        return list;
    }

    /**
     * <p>
     * Method used to get number of packages delivered in given month
     * </p>
     *
     * @param month month that we are investigating
     * @return ObservalbeList of BarChartDTO
     */
    static public ObservableList<BarChartDTO> quantityOfPackagesMonthly(String month) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        ObservableList<BarChartDTO> list = FXCollections.observableArrayList();

        Query query = session.createQuery("SELECT NEW main.java.entity.BarChartDTO(SUBSTRING(P.packageNumber, 1, 2), " +
                "COUNT(P.packageNumber)) " +
                "FROM Packages P WHERE SUBSTRING(P.packageNumber, 3, 2) = :month " +
                "AND (P.usersByCourierId.areaId = :areaId " +
                "OR P.userInfosByUserInfoId.voivodeship = :voivodeship " +
                "OR P.userInfosByUserInfoId.city = :city ) " +
                "GROUP BY SUBSTRING(P.packageNumber, 1, 2)");

        Users uu = UsersDAO.getUsersId(Login.getUserID()).get(0);

        query.setParameter("month", month);
        query.setParameter("areaId", uu.getAreaId());
        query.setParameter("voivodeship", uu.getUserInfosByUserInfoId().getVoivodeship());
        query.setParameter("city", uu.getUserInfosByUserInfoId().getCity());


        List<BarChartDTO> results = query.list();

        for (BarChartDTO ent : results) {
            list.add(ent);
        }
        session.close();

        return list;
    }

    /**
     * <p>
     * Method used to get packages for courier to deliver
     * </p>
     *
     * @param courierId id of courier that delivers the package
     * @return observableList of packagesDTO
     */
    static public ObservableList<PackagesDTO> getPackagesWithStatusAndNames(int courierId) {
        ObservableList<PackagesDTO> packages = FXCollections.observableArrayList();
        String hql = "SELECT NEW main.java.entity.PackagesDTO(" +
                "P.id, P.packageNumber, P.usersByUserId.userInfosByUserInfoId.name, P" +
                ".usersByUserId.userInfosByUserInfoId.surname, PH.status, P.packageTypeByTypeId.sizeName," +
                " P.userInfosByUserInfoId.name, P.userInfosByUserInfoId.surname, P.usersByUserId" +
                ".userInfosByUserInfoId" +
                ".voivodeship) " +
                "FROM Packages P, UserInfos UI, PackageHistory PH " +
                "WHERE P.id = PH.packageId " +
                "AND P.courierId = :courierId " +
                "AND PH.status = (SELECT PH.status " +
                "FROM PH " +
                "WHERE PH.id = (SELECT MAX(PH.id) " +
                "FROM PH " +
                "WHERE PH.packageId = P.id )) " +
                "GROUP BY P.packageNumber";

        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery(hql);
        query.setParameter("courierId", courierId);
        List<PackagesDTO> results = query.list();
        for (PackagesDTO ent : results) {
            packages.add(ent);
        }
        session.close();

        return packages;
    }

    /**
     * <p>
     * Method used to get packages that are in given voivodeship
     * </p>
     *
     * @param voivodeship name of province we are looking in
     * @return observableList of packagesDTO
     */
    static public ObservableList<PackagesDTO> getPackagesByVoivodeship(String voivodeship) {
        ObservableList<PackagesDTO> packages = FXCollections.observableArrayList();
        String hql = "SELECT NEW main.java.entity.PackagesDTO(" +
                "P.userId, P.id, P.packageNumber, P.timeOfPlannedDelivery, UI.name, UI.surname," +
                " UI.phoneNumber, UI.streetAndNumber, UI.city, PH.status, P.additionalComment, P.email, " +
                "P.userInfosByUserInfoId.voivodeship, P.userInfosByUserInfoId.name) " +
                "FROM Packages P, UserInfos UI, PackageHistory PH " +
                "WHERE P.id = PH.packageId " +
                "AND (P.usersByUserId.userInfosByUserInfoId.voivodeship = :voivodeship " +
                "OR P.userInfosByUserInfoId.voivodeship = :voivodeship) " +
                "AND P.userInfoId = UI.id " +
                "AND PH.status = (SELECT PH.status " +
                "FROM PH " +
                "WHERE PH.id = (SELECT MAX(PH.id) " +
                "FROM PH " +
                "WHERE PH.packageId = P.id )) " +
                "GROUP BY P.packageNumber";

        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery(hql);
        query.setParameter("voivodeship", voivodeship);
        List<PackagesDTO> results = query.list();
        for (PackagesDTO ent : results) {
            packages.add(ent);
        }
        session.close();

        return packages;
    }

    /**
     * <p>
     * Method used to update package data
     * </p>
     *
     * @param ID            id of package
     * @param typeID        if of package type
     * @param userID        id of user sending package
     * @param courierID     id of assigned courier
     * @param userInfoID    sender's userInfoID
     * @param email         email of recipient
     * @param packageNumber generated package number
     * @param time          time brackets of delivery
     * @param additional    some additional comments
     */
    static public void updatePackage(int ID, int typeID, int userID, int courierID, int userInfoID, String email, String packageNumber, String time, String additional) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        Packages packages = new Packages();

        packages.setId(ID);
        packages.setTypeId(typeID);
        packages.setUserId(userID);
        packages.setCourierId(courierID);
        packages.setUserInfoId(userInfoID);
        packages.setEmail(email);
        packages.setPackageNumber(packageNumber);
        packages.setTimeOfPlannedDelivery(time);
        packages.setAdditionalComment(additional);


        session.update(packages);
        session.getTransaction().commit();
    }

    /**
     * <p>
     * Method used to get all packages without courier assigned to it
     * </p>
     *
     * @return List in type of Packages
     */
    static public List<Packages> getPackagesWithoutCourierId() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Query query = session.createQuery("from Packages P WHERE P.courierId = null");

        List<Packages> listOfPackages = query.list();

        return listOfPackages;
    }

    /**
     * <p>
     * Method used to change given courierId of given packageId
     * </p>
     *
     * @param packageId id of modified package
     * @param courierId id of new courier
     */
    static public void updateCourierId(int packageId, int courierId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        Packages packages = session.get(Packages.class, packageId);

        packages.setCourierId(courierId);

        session.update(packages);

        session.getTransaction().commit();

        session.close();
    }

}