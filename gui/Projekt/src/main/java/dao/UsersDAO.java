package main.java.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main.java.controllers.auth.Encryption;
import main.java.entity.UserInfos;
import main.java.entity.Users;
import main.java.entity.UsersDTO;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

public class UsersDAO {

    static public List<Users> getUsers(){
        Session session = HibernateUtil.getSessionFactory().openSession();

        Query query = session.createQuery("from Users");

        List<Users> listOfUsers = query.list();

        return listOfUsers;
    }

    static public List<Users> getUsersId(int id){
        Session session = HibernateUtil.getSessionFactory().openSession();

        Query query = session.createQuery("FROM Users WHERE userInfoId = :id");
        query.setParameter("id",id);

        List<Users> listOfUsers = query.list();

        return listOfUsers;
    }

    static public void updatePassword(int userId, String newPassword){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        Users users = session.get(Users.class, userId);

        users.setPassword(newPassword);

        session.update(users);

        session.getTransaction().commit();

        session.close();
    }

    static public List<UserInfos> readUserInfoById(int userId){
        Session session = HibernateUtil.getSessionFactory().openSession();

        Query query=session.createQuery("from Users u where u.userInfoId = :id");
        query.setParameter("id",userId);
        List<Users> id = query.list();


        query=session.createQuery("from UserInfos u where u.id = :id");
        query.setParameter("id", id.get(0).getUserInfoId());
        List<UserInfos> user = query.list();

        return user;
    }

    static public boolean checkIfPasswordCorrect(String password, int id){
        Session session = HibernateUtil.getSessionFactory().openSession();

        Query query=session.createQuery("SELECT password from Users WHERE password = :password AND id = :id");

        query.setParameter("password", Encryption.encrypt(password));
        query.setParameter("id", id);

        /*Query query1 = session.createQuery("SELECT PH.status FROM PackageHistory PH, Users U, Packages P " +
                "WHERE U.id = :id AND PH.packageId = P.id AND U.id = P.userId AND PH.status = " +
                "(SELECT PH.status FROM PackageHistory PH WHERE PH.id = (SELECT MAX(PH.id) " +
                "FROM PackageHistory PH WHERE PH.packageId = P.id )) AND " +
                "NOT PH.status = 'Dostarczona' AND NOT PH.status = 'Zwrócona Do Nadawcy' GROUP BY PH.packageId");

        query1.setParameter("id", id);*/

        if(query.list().size() == 0)
            return false;

        return true;
    }

    static public ObservableList<UsersDTO> getUserEdit(){
        Session session = HibernateUtil.getSessionFactory().openSession();

        Query query = session.createQuery("FROM Users U WHERE NOT U.role = 'Klient' AND NOT U.role = 'Administrator' ");

        List<Users> userList = query.list();

        ObservableList<Users> user = FXCollections.observableArrayList();
        for (Users ent : userList) {
            user.add(ent);
        }
        ObservableList<UsersDTO> usersDTOS = FXCollections.observableArrayList();
        for (int i = 0; i < user.size(); i++) {
            usersDTOS.add(new UsersDTO(user.get(i).getId(), user.get(i).getUserInfosByUserInfoId().getName(),
                    user.get(i).getUserInfosByUserInfoId().getSurname(),
                    user.get(i).getUserInfosByUserInfoId().getPhoneNumber(),
                    user.get(i).getUserInfosByUserInfoId().getCity(),
                    user.get(i).getEmail(), user.get(i).getUserInfoId()));
        }

        session.close();

        return usersDTOS;
    }

    static public List<Users> getUsersById(int id){
        Session session = HibernateUtil.getSessionFactory().openSession();

        Query query = session.createQuery("FROM Users WHERE id = :id");
        query.setParameter("id",id);

        List<Users> listOfUsers = query.list();

        return listOfUsers;
    }

    static public String readPassword(int userId){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query=session.createQuery("SELECT password from Users WHERE id = :id");
        query.setParameter("id",userId);

        return String.valueOf(query.list().get(0));
    }


    static public void deactivateAccount(int userId)
    {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        Users users = session.get(Users.class, userId);

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime now = LocalDateTime.now();

        users.setEmail("UŻYTKOWNIK USUNIĘTY");
        users.setPassword(dateTimeFormatter.format(now) );

        session.update(users);
        session.getTransaction().commit();

        session.close();
    }

    static public List<Users> readDeactivatedAccounts(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        Query query = session.createQuery("FROM Users WHERE email = 'UŻYTKOWNIK USUNIĘTY'");

        return query.list();
    }

    static public List<Users> getCouriers(String role){
        Session session = HibernateUtil.getSessionFactory().openSession();

        Query query = session.createQuery("from Users WHERE role = :role");
        query.setParameter("role", role);

        List<Users> listOfUsers = query.list();

        return listOfUsers;
    }

    static public List<Users> getCouriersByAreaId(int areaId){
        Session session = HibernateUtil.getSessionFactory().openSession();

        Query query = session.createQuery("from Users WHERE areaId = :areaId AND role = 'Kurier'");
        query.setParameter("areaId", areaId);

        List<Users> listOfUsers = query.list();

        return listOfUsers;
    }

    static public Long getPackagesByCourier(int courierId){
        Session session = HibernateUtil.getSessionFactory().openSession();

        Query query = session.createQuery("select count(P.courierId) from Packages P WHERE P.courierId = :courierId ");
        query.setParameter("courierId", courierId);

        Long listOfUsers = (Long) query.uniqueResult();

        return listOfUsers;
    }
    
}
