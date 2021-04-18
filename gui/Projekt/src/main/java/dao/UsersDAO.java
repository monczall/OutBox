package main.java.dao;

import main.java.entity.UserInfos;
import main.java.entity.Users;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class UsersDAO {
    static public void addUser(int user_infoID, String password, int areaID, String role){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        Users user = new Users();
        user.setUserInfoId(user_infoID);
        user.setPassword(password);
        user.setAreaId(areaID);
        user.setRole(role);

        session.save(user);

        transaction.commit();

        session.close();
    }

    static public List<Users>  showTable(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query=session.createQuery("from  Users");
        List<Users> list = query.list();

        return list;
    }

}
