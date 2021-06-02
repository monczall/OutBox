package test;

import main.java.dao.HibernateUtil;
import main.java.entity.PackageType;
import main.java.entity.Users;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;

import java.util.List;

import static main.java.dao.UsersDAO.*;
import static main.java.dao.PackageTypeDAO.*;

public class AdminTests {

    private static SessionFactory sessionFactory;
    private Session session;

    @BeforeAll
    public static void setUp() {
        sessionFactory = HibernateUtil.getSessionFactory();
        System.out.println("Utworzono sessionFactory");
    }

    @AfterAll
    public static void tearDown() {
        if(sessionFactory != null){
            sessionFactory.close();
            System.out.println("Zniszczono sessionFactory");
        }
    }


    @Test
    public void shouldReturnAtLeastOneUser() {
        System.out.println("Uruchomiono test sprawdzający wczytywanie użytkowników do edycji");


        Assertions.assertTrue(getUserEdit().size() >= 1);
    }

    @Test
    public void shouldNotReturnClientOrAdminRole() {
        System.out.println("Uruchomiono test sprawdzający wczytywanie użytkowników do edycji bez klienta lub administratora");

        Assertions.assertTrue(getUserEdit().size() < getUsers().size());
    }


    @Test
    public void shouldReturnOneUser() {
        System.out.println("Uruchomiono test wczytujacy użytkownika po id");

        Assertions.assertTrue(getUsersById(1).size() == 1);
    }

    @Test
    public void shouldBeEqualTypeOfPackages() {
        System.out.println("Uruchomiono test sprawdzający ilość typów paczek w bazie");

        Assertions.assertTrue(getPackageTypes().size() == 3);
    }


    @Test
    public void shouldBePackakeTypeSizeCorrect() {
        System.out.println("Uruchomiono test sprawdzający poprawność informacji o wielkości paczki w bazie");

        int counter = 0;

        List<PackageType> list = getPackageTypes();

        for (int i = 0; i < list.size(); i++) {
            if (!list.get(i).getSize().matches("[0-9]{1,3}[x][0-9]{1,3}[x][0-9]{1,3}")){
                counter++;
            }

        }

        Assertions.assertTrue(counter == 0);
    }

    @Test
    public void shouldReturnClientOrAdminArea() {
        System.out.println("Uruchomiono test sprawdzający czy Kurier i Menadżer mają przypisany obszar");
        int counter = 0;

        List<Users> list = getUsers();

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getRole().equals("Kurier") || list.get(i).getRole().equals("Menadzer")){
                if (list.get(i).getAreaId() == null) {
                    counter++;
                }

            }
        }
        Assertions.assertTrue(counter == 0);
    }





    @BeforeEach
    public void openSession() {
        session = sessionFactory.openSession();
        System.out.println("\nOtwarto sesje");
    }

    @AfterEach
    public void closeSession() {
        if(session != null)
        {
            session.close();
            System.out.println("Zamknięto sesje\n");
        }
    }
}
