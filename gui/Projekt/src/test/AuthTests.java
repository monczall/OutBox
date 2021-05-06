package test;

import main.java.dao.HibernateUtil;
import main.java.entity.UserInfos;
import main.java.entity.Users;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.jupiter.api.*;

import java.util.List;

public class AuthTests {

    private static SessionFactory sessionFactory;
    private Session session;

    @BeforeAll
    public static void setUp() {
        sessionFactory = HibernateUtil.getSessionFactory();
        System.out.println("Utworzono sessionFactory");
    }

    @BeforeEach
    public void openSession() {
        session = sessionFactory.openSession();
        System.out.println("");
        System.out.println("=============");
        System.out.println("Sesja otwarta");
    }

    @AfterEach
    public void closeSession() {
        if(session != null)
        {
            session.close();
            System.out.println("Sesja zamknieta");
            System.out.println("===============");
        }
    }

    @AfterAll
    public static void tearDown() {
        if(sessionFactory != null){
            sessionFactory.close();
            System.out.println("Zamknięto sessionFactory");
        }
    }

    @Test
    public void ReadUsersTest() {
        System.out.println("Test pobierający użytkowników...");

        session.beginTransaction();

        Query query = session.createQuery("from Users");

        List<Users> listOfAllUsers = query.list();

        Assertions.assertNotNull(listOfAllUsers);
    }

    @Test
    public void ReadUserInfosTest() {
        System.out.println("Test pobierający informacje na temat użytkowników...");

        session.beginTransaction();

        Query query = session.createQuery("from UserInfos ");

        List<UserInfos> listOfUserInfos = query.list();

        Assertions.assertNotNull(listOfUserInfos);
    }

    @Test
    public void ReadNonExistingUserTest() {
        System.out.println("Test pobierający nieistniejącego użytkownika...");

        session.beginTransaction();

        String email = "takiuzytkowniknieistnieje@gmail.com";

        Query query = session.createQuery("from Users u where u.email = :email");
        query.setParameter("email",email);

        List<Users> listOfNonExistingUser = query.list();

        Assertions.assertEquals(0,listOfNonExistingUser.size());
    }

    @Test
    public void ReadClientUserTest() {
        System.out.println("Test pobierający istniejącego użytkownika - klient...");

        session.beginTransaction();

        String email = "klient@gmail.com";

        Query query = session.createQuery("from Users u where u.email = :email");
        query.setParameter("email",email);

        List<Users> listOfClientUser = query.list();

        Assertions.assertEquals(1,listOfClientUser.size());
    }

    @Test
    public void ReadCourierUserTest() {
        System.out.println("Test pobierający istniejącego użytkownika - kurier...");

        session.beginTransaction();

        String email = "kurier@gmail.com";

        Query query = session.createQuery("from Users u where u.email = :email");
        query.setParameter("email",email);

        List<Users> listOfCourierUser = query.list();

        Assertions.assertEquals(1,listOfCourierUser.size());
    }

    @Test
    public void ReadManagerUserTest() {
        System.out.println("Test pobierający istniejącego użytkownika - menadzer...");

        session.beginTransaction();

        String email = "menadzer@gmail.com";

        Query query = session.createQuery("from Users u where u.email = :email");
        query.setParameter("email",email);

        List<Users> listOfManagerUser = query.list();

        Assertions.assertEquals(1,listOfManagerUser.size());
    }

    @Test
    public void ReadAdminUserTest() {
        System.out.println("Test pobierający istniejącego użytkownika - administrator...");

        session.beginTransaction();

        String email = "administrator@gmail.com";

        Query query = session.createQuery("from Users u where u.email = :email");
        query.setParameter("email",email);

        List<Users> listOfAdministratorUser = query.list();

        Assertions.assertEquals(1,listOfAdministratorUser.size());
    }

    @Test
    public void ReadNonExistingUserInfoTest() {
        System.out.println("Test pobierający informacje na temat nieistniejacego użytkownika");

        session.beginTransaction();

        int id = 9999;

        Query query = session.createQuery("from UserInfos ui where ui.id = :id");
        query.setParameter("id",id);

        List<UserInfos> listOfNonExistingUserInfo = query.list();

        Assertions.assertEquals(0,listOfNonExistingUserInfo.size());
    }

    @Test
    public void ReadClientUserInfoTest() {
        System.out.println("Test pobierający informacje na temat użytkownika - klient");

        session.beginTransaction();

        int id = 1;

        Query query = session.createQuery("from UserInfos ui where ui.id = :id");
        query.setParameter("id",id);

        List<UserInfos> listOfClientUserInfo = query.list();

        Assertions.assertEquals(1,listOfClientUserInfo.size());
    }

    @Test
    public void ReadCourierUserInfoTest() {
        System.out.println("Test pobierający informacje na temat użytkownika - kurier");

        session.beginTransaction();

        int id = 2;

        Query query = session.createQuery("from UserInfos ui where ui.id = :id");
        query.setParameter("id",id);

        List<UserInfos> listOfCourierUserInfo = query.list();

        Assertions.assertEquals(1,listOfCourierUserInfo.size());
    }

    @Test
    public void ReadManagerUserInfoTest() {
        System.out.println("Test pobierający informacje na temat użytkownika - menadzer");

        session.beginTransaction();

        int id = 3;

        Query query = session.createQuery("from UserInfos ui where ui.id = :id");
        query.setParameter("id",id);

        List<UserInfos> listOfManagerUserInfo = query.list();

        Assertions.assertEquals(1,listOfManagerUserInfo.size());
    }

    @Test
    public void ReadAdministratorUserInfoTest() {
        System.out.println("Test pobierający informacje na temat użytkownika - administrator");

        session.beginTransaction();

        int id = 4;

        Query query = session.createQuery("from UserInfos ui where ui.id = :id");
        query.setParameter("id",id);

        List<UserInfos> listOfAdministratorUserInfo = query.list();

        Assertions.assertEquals(1,listOfAdministratorUserInfo.size());
    }

}
