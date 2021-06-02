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

    /**
     * <p>
     *     Method used to get all users that exists in database. Returned list is
     *     type of Users.
     * </p>
     * @return list of users
     */
    static public List<Users> getUsers(){
        Session session = HibernateUtil.getSessionFactory().openSession();

        Query query = session.createQuery("from Users");

        List<Users> listOfUsers = query.list();

        return listOfUsers;
    }

    /**
     * <p>
     *     Method used to get all users that exists in database by userInfoId.
     *     Returned list is type of Users.
     * </p>
     * @return list of users
     */
    static public List<Users> getUsersId(int id){
        Session session = HibernateUtil.getSessionFactory().openSession();

        Query query = session.createQuery("FROM Users WHERE userInfoId = :id");
        query.setParameter("id",id);

        List<Users> listOfUsers = query.list();

        return listOfUsers;
    }

    /**
     * <p>
     *     Method is used to update password of user given by userID.
     * </p>
     * @param userId id of user that password is being changed
     * @param newPassword password that is going to be set
     */
    static public void updatePassword(int userId, String newPassword){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        newPassword = Encryption.encrypt(newPassword);

        Users users = session.get(Users.class, userId);

        users.setPassword(newPassword);

        session.update(users);

        session.getTransaction().commit();

        session.close();
    }

    /**
     * <p>
     *     Method used to get all users infos that exists in database by userID.
     *     Returned list is type of UserInfos.
     * </p>
     * @return list of users infos
     */
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

    /**
     * <p>
     *     Method used to check if password of user given by id is equal to given
     *     password.
     * </p>
     * @param password password to be checked
     * @param id id of checked user
     * @return boolean value
     */
    static public boolean checkIfPasswordCorrect(String password, int id){
        Session session = HibernateUtil.getSessionFactory().openSession();

        Query query=session.createQuery("SELECT password from Users WHERE password = :password AND id = :id");

        query.setParameter("password", Encryption.encrypt(password));
        query.setParameter("id", id);

        if(query.list().size() == 0)
            return false;

        return true;
    }

    /**
     * <p>
     *     Method used to get all users that exists in database that are not
     *     Klient or Administrator.
     *     Returned list is type of Users. List contains id, name, surname,
     *     phone number, city and userInfoID.
     * </p>
     * @return
     */
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

    /**
     * <p>
     *     Method used to get all users that exists in database by userId.
     *     Returned list is type of Users.
     * </p>
     * @param id id of user to read
     * @return list of user columns
     */
    static public List<Users> getUsersById(int id){
        Session session = HibernateUtil.getSessionFactory().openSession();

        Query query = session.createQuery("FROM Users WHERE id = :id");
        query.setParameter("id",id);

        List<Users> listOfUsers = query.list();

        return listOfUsers;
    }

    /**
     * <p>
     *     Method used to get password of user found by userInfoId.
     * </p>
     * @param userId id of user to read password from
     * @return string containing user's password
     */
    static public String readPassword(int userId){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query=session.createQuery("SELECT password from Users WHERE id = :id");
        query.setParameter("id",userId);

        return String.valueOf(query.list().get(0));
    }


    /**
     * <p>
     *     Method used to deactivate user's account. It sets date of deletion for
     *     future use in account automatic deletion. User's email is set to
     *     "UŻYTKOWNIK USUNIĘTY". After month user is deleted.
     * </p>
     * @param userId id of user that needs to be disabled
     */
    static public void deactivateAccount(int userId)
    {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        Users users = session.get(Users.class, userId);

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime now = LocalDateTime.now();

        users.setEmail("UZYTKOWNIK USUNIETY");
        users.setPassword(dateTimeFormatter.format(now) );

        session.update(users);
        session.getTransaction().commit();

        session.close();
    }

    /**
     * <p>
     *     Method used to get list of all deactivated users.
     *     Returned list is type of Users
     * </p>
     * @return list of deactivated users
     */
    static public List<Users> readDeactivatedAccounts(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        Query query = session.createQuery("FROM Users WHERE email = 'UZYTKOWNIK USUNIETY'");

        return query.list();
    }

    /**
     * <p>
     *     Method used to get users with given role.
     *     Returned list is type of Users
     * </p>
     * @param role defines role of users to look for
     * @return list of users with given role
     */
    static public List<Users> getCouriers(String role){
        Session session = HibernateUtil.getSessionFactory().openSession();

        Query query = session.createQuery("from Users WHERE role = :role");
        query.setParameter("role", role);

        List<Users> listOfUsers = query.list();

        return listOfUsers;
    }

    /**
     * <p>
     *     Method used to get couriers by given areaID
     * </p>
     * @param areaId id of area assigned to courier
     * @return list of users (couriers) with given areaID
     */
    static public List<Users> getCouriersByAreaId(int areaId){
        Session session = HibernateUtil.getSessionFactory().openSession();

        Query query = session.createQuery("from Users WHERE areaId = :areaId AND role = 'Kurier'");
        query.setParameter("areaId", areaId);

        List<Users> listOfUsers = query.list();

        return listOfUsers;
    }

    /**
     * <p>
     *     Method used to get number of packages assigned to given courier by
     *     courierID.
     * </p>
     * @param courierId id of courier to get number of packages assigned
     * @return number of counted packages
     */
    static public Long getPackagesByCourier(int courierId){
        Session session = HibernateUtil.getSessionFactory().openSession();

        Query query = session.createQuery("select count(P.courierId) from Packages P WHERE P.courierId = :courierId ");
        query.setParameter("courierId", courierId);

        Long listOfUsers = (Long) query.uniqueResult();

        return listOfUsers;
    }
    
}
