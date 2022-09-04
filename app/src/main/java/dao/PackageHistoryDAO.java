package main.java.dao;

import main.java.entity.PackageHistory;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.sql.Timestamp;
import java.util.List;

public class PackageHistoryDAO {

    /**
     * <p>
     * Method used to get the list of all statuses form package histories
     * that are in database. Returned List is type of PackageHistory.
     * </p>
     *
     * @return list of all statuses
     */
    static public List<PackageHistory> getStatuses() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        Query query = session.createQuery("SELECT status from PackageHistory");

        List<PackageHistory> listOfPackageHistories = query.list();

        return listOfPackageHistories;
    }

    /**
     * <p>
     * Method used to update status of package by creating new package
     * history record. It requires packageID, status and timestamp.
     * </p>
     *
     * @param packageID ID of package we want to update
     * @param status    new status to set
     * @param timestamp new timestamp to set
     */
    static public void updateStatus(int packageID, String status, Timestamp timestamp) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        PackageHistory packageHistory = new PackageHistory();

        packageHistory.setPackageId(packageID);
        packageHistory.setStatus(status);
        packageHistory.setDate(timestamp);
        session.save(packageHistory);

        session.getTransaction().commit();
        session.close();
    }

    /**
     * <p>
     * Method used to get list of all statuses from package history connected
     * to given packageID. Returned list is type of String.
     * </p>
     *
     * @param packageId Id of package we want to read
     * @return returns list of statuses
     */
    static public List<String> getStatusById(int packageId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery("SELECT status from PackageHistory WHERE packageId = :packageId");

        query.setParameter("packageId", packageId);

        List<String> statuses = query.list();

        return statuses;
    }

    /**
     * <p>
     * Method used to get list of all package histories from package history
     * connected to given packageID. Returned list is type of PackageHistory
     * and is ordered by date ascending.
     * </p>
     *
     * @param packageId Id of package we want to read
     * @return returns list of package histories ordered by date ascending
     */
    static public List<PackageHistory> getDateAndStatusById(int packageId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from PackageHistory WHERE packageId = :packageId ORDER BY date ASC");

        query.setParameter("packageId", packageId);

        List<PackageHistory> statuses = query.list();

        return statuses;
    }

}
