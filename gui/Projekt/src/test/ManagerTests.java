package test;

import main.java.dao.HibernateUtil;
import main.java.entity.UserInfos;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.jupiter.api.*;

import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ManagerTests {

    private static SessionFactory sessionFactory;
    private Session session;

    @BeforeAll
    public static void setUp() {
        sessionFactory = HibernateUtil.getSessionFactory();
        System.out.println("Utworzono sessionFactory");
    }

    @AfterAll
    public static void tearDown() {
        if (sessionFactory != null) {
            sessionFactory.close();
            System.out.println("Zniszczono sessionFactory");
        }
    }

    @Test
    public void validationEditAndAddCourierTest() {
        System.out.println("Test sprawdzajacy poprawność wprowadzonych danych");

        String name = "Patryk";
        String surname = "Ja";
        String phone = "123123121";
        String street = "ulica 12";
        String email = "email@ten.pl";
        String city = "miasto";
        String voivodeship = "podkarpacie";

        boolean status = true;

        if (!email.matches("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}")) {
            status = false;
        }
        if (!name.matches("[a-zA-Z]+")) {
            status = false;
        }
        if (!surname.matches("[a-zA-Z]+")) {
            status = false;
        }
        if (!city.matches("[A-Za-z]+")) {
            status = false;
        }
        if (!street.matches("[A-Za-z]{0,2}\\.?\\s?[A-Za-z]{2,40}\\s?\\-?[A-Za-z]{0,40}?\\" +
                "s?\\-?[A-Za-z]{0,40}?\\s[0-9]{1,4}\\s?[A-Za-z]?\\s?\\/?\\s?[0-9]{0,5}")) {
            status = false;
        }
        if (!phone.matches("[0-9]*") || phone.length() != 9) {
            status = false;
        }
        if (!voivodeship.matches("[a-zA-Z]+")) {
            status = false;
        }

        Assertions.assertTrue(status);
    }

    @Test
    public void getUserInfoByNameAndSurnameTest() {
        System.out.println("Test pobierający istniejącego użytkownika po podaniu imienia i nazwiska");

        session.beginTransaction();

        Query query = session.createQuery("FROM UserInfos WHERE name = :name AND surname = :surname");
        query.setParameter("name", "Jan").setParameter("surname", "Kowalski");

        List<UserInfos> userList = query.list();

        Assertions.assertEquals(1, userList.size());

    }

    @BeforeEach
    public void openSession() {
        session = sessionFactory.openSession();
        System.out.println("\nOtwarto sesje");
    }

    @AfterEach
    public void closeSession() {
        if (session != null) {
            session.close();
            System.out.println("Zamknięto sesje\n");
        }
    }


}
