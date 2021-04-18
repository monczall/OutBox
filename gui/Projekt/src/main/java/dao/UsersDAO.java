package main.java.dao;

import main.java.entity.Users;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class UsersDAO {

    static public List<Users>  showTable(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query=session.createQuery("from  Users");
        List<Users> list = query.list();

        return list;
    }

}
