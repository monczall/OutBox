package main.java.dao;

import main.java.controllers.auth.Encryption;
import main.java.controllers.client.ClientSettings;
import main.java.entity.PackageHistory;
import main.java.entity.UserInfos;
import main.java.entity.Users;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class UserInfosDAO {

    /**
     * <p>
     *     Method used to get list of all user infos.
     *     List is type of UserInfos
     * </p>
     * @return list of userInfos
     */
    static public List<UserInfos> getUserInfos(){
        Session session = HibernateUtil.getSessionFactory().openSession();

        Query query = session.createQuery("from UserInfos");

        List<UserInfos> listOfUsers = query.list();

        session.close();

        return listOfUsers;
    }

    /**
     * <p>
     *     Method used to get list of user infos by given user info id.
     *     List is type of UserInfos
     * </p>
     * @param id UserInfosId to look for
     * @return list of userInfos
     */
    static public List<UserInfos> getUserInfoByID(int id){
        Session session = HibernateUtil.getSessionFactory().openSession();

        Query query = session.createQuery("FROM UserInfos WHERE id = :id");
        query.setParameter("id",id);

        List<UserInfos> userList = query.list();

        session.close();

        return userList;
    }

    /**
     * <p>
     *     Method used to get list of user info by given name and surname.
     *     List is type of UserInfos
     * </p>
     * @param name name to look for
     * @param surname surname to look for
     * @return list of userInfos
     */
    static public List<UserInfos> getUserInfoByNameAndSurname(String name ,String surname){
        Session session = HibernateUtil.getSessionFactory().openSession();

        Query query = session.createQuery("FROM UserInfos WHERE name = :name AND surname = :surname");
        query.setParameter("name",name).setParameter("surname", surname);
        List<UserInfos> userList = query.list();

        session.close();

        return userList;
    }

    /**
     * <p>
     *      Method used to add user info.
     * </p>
     * @param name name of user
     * @param surname surname of user
     * @param email email of user
     * @param phone_number phone number of user
     * @param street_and_number street and number of user
     * @param city city of user
     * @param voivodeship voivodeship of user
     * @param password password of user
     * @param role role of user
     * @param areaId areaId of user
     */
    static public void addUserInfo(String name, String surname, String email, String phone_number, String street_and_number, String city, String voivodeship, String password, String role, Integer areaId){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        UserInfos userInfo = new UserInfos();

        userInfo.setName(name);
        userInfo.setSurname(surname);
        userInfo.setPhoneNumber(phone_number);
        userInfo.setStreetAndNumber(street_and_number);
        userInfo.setCity(city);
        userInfo.setVoivodeship(voivodeship);
        session.save(userInfo);

        Users user = new Users();

        user.setUserInfoId(userInfo.getId());
        user.setAreaId(areaId);
        user.setPassword(password);
        user.setEmail(email);
        user.setRole(role);
        session.save(user);

        session.getTransaction().commit();
        session.close();
    }

    /**
     * <p>
     *     Method used to update user data.
     * </p>
     * @param userInfoId userInfoID that is connected to that user
     * @param userId id of user that is being updated
     * @param name name that is going to be set
     * @param surname surname that is going to be set
     * @param number phone number that is going to be set
     * @param city city name that is going to be set
     * @param street street and number that is going to be set
     * @param voivodeship voivodeship that is going to be set
     * @param email email that is going to be set
     * @param password password that is going to be set
     * @param role role that is going to be set
     * @param areaId areaId that user belongs to that is going to be set
     * @param userInfosID userInfosID that is connected to that user
     */
    static public void updateUser(int userInfoId, int userId, String name, String surname, String number,
                                  String city, String street, String voivodeship, String email, String password,
                                  String role, int areaId, int userInfosID){

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        UserInfos userInfos = new UserInfos();

        userInfos.setId(userInfoId);
        userInfos.setName(name);
        userInfos.setSurname(surname);
        userInfos.setPhoneNumber(number);
        userInfos.setCity(city);
        userInfos.setStreetAndNumber(street);
        userInfos.setVoivodeship(voivodeship);

        session.update(userInfos);
        session.getTransaction().commit();
        session.close();

        Session session2 = HibernateUtil.getSessionFactory().openSession();
        session2.beginTransaction();

        Users user = new Users();

        user.setId(userId);
        user.setEmail(email);
        user.setUserInfoId(userInfosID);
        user.setPassword(password);
        user.setRole(role);
        user.setAreaId(areaId);

        session2.update(user);
        session2.getTransaction().commit();

        session2.close();
    }

    /**
     * <p>
     *     Method used to update currently logged in user.
     * </p>
     * @param voivodeship voivodeship that is going to be set
     * @param city city name that is going to be set
     * @param number phone number that is going to be set
     * @param street street and number that is going to be set
     * @param userId id of user that is being updated
     */
    static public void updateUserSettings(String voivodeship, String city,
                                          String number, String street, int userId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        Users users = session.get(Users.class, userId);


        users.getUserInfosByUserInfoId().setVoivodeship(voivodeship);
        users.getUserInfosByUserInfoId().setCity(city);
        users.getUserInfosByUserInfoId().setPhoneNumber(number);
        users.getUserInfosByUserInfoId().setStreetAndNumber(street);

        session.update(users);
        session.getTransaction().commit();

        session.close();
    }

    /**
     * <p>
     *     Method used to update user data with given data.
     * </p>
     * @param userId id of user that is being updated
     * @param name name that is going to be set
     * @param surname surname that is going to be set
     * @param number phone number that is going to be set
     * @param street street and number that is going to be set
     * @param city city name that is going to be set
     * @param voivodeship voivodeship that is going to be set
     * @param role role that is going to be set
     * @param areaId areaId that user belongs to that is going to be set
     */
    static public void editUser(int userId, String name, String surname, String number,String street,
                                String city, String voivodeship, String role, int areaId
                                            ) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        Users users = session.get(Users.class, userId);

        users.getUserInfosByUserInfoId().setName(name);
        users.getUserInfosByUserInfoId().setSurname(surname);
        users.getUserInfosByUserInfoId().setVoivodeship(voivodeship);
        users.getUserInfosByUserInfoId().setCity(city);
        users.getUserInfosByUserInfoId().setPhoneNumber(number);
        users.getUserInfosByUserInfoId().setStreetAndNumber(street);


        users.setRole(role);
        users.setAreaId(areaId);

        session.update(users);
        session.getTransaction().commit();

        session.close();
    }

    /**
     * <p>
     *     Method used to delete selected user
     * </p>
     * @param id id of user to delete
     */
    static public void deleteUser(int id){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        UserInfos userInfo = new UserInfos();
        userInfo.setId(id);

        session.delete(userInfo);

        session.getTransaction().commit();
        session.close();
    }
}
