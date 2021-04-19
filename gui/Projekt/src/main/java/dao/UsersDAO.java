package main.java.dao;

import main.java.entity.Users;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class UsersDAO {


    static public String readPassword(int userId){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query=session.createQuery("SELECT password from Users WHERE id = :id");
        query.setParameter("id",userId);

        return String.valueOf(query.list().get(0));
    }

    static public void updatePassword(int userId, String newPassword){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        Users users = session.get(Users.class, userId);

        users.setPassword(newPassword);

        session.update(users);

        session.getTransaction().commit();
    }

}
