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

    static public List<UserInfos> getUserInfos(){
        Session session = HibernateUtil.getSessionFactory().openSession();

        Query query = session.createQuery("from UserInfos");

        List<UserInfos> listOfUsers = query.list();

        session.close();

        return listOfUsers;
    }

    static public List<UserInfos> getUserInfoByID(int id){
        Session session = HibernateUtil.getSessionFactory().openSession();

        Query query = session.createQuery("FROM UserInfos WHERE id = :id");
        query.setParameter("id",id);

        List<UserInfos> userList = query.list();

        session.close();

        return userList;
    }

    static public List<UserInfos> getUserInfoByNameAndSurname(String name ,String surname){
        Session session = HibernateUtil.getSessionFactory().openSession();

        Query query = session.createQuery("FROM UserInfos WHERE name = :name AND surname = :surname");
        query.setParameter("name",name).setParameter("surname", surname);
        List<UserInfos> userList = query.list();

        session.close();

        return userList;
    }

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

    static public void deleteUser(int id)
    {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        UserInfos userInfo = new UserInfos();
        userInfo.setId(id);

        session.delete(userInfo);

        session.getTransaction().commit();
        session.close();
    }

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


    static public void editUser(int userId, String name, String surname, String email,String number,String street,
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

        users.setEmail(email);
        users.setRole(role);
        users.setAreaId(areaId);

        session.update(users);
        session.getTransaction().commit();

        session.close();
    }

}
