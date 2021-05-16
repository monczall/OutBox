package test;

import javafx.collections.ObservableList;
import main.java.dao.HibernateUtil;
import main.java.dao.PackageHistoryDAO;
import main.java.entity.PackageHistory;
import main.java.entity.PackagesDTO;
import main.java.entity.Users;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.hibernate.query.Query;
import org.junit.jupiter.api.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import main.java.dao.PackagesDAO;

import static main.java.dao.PackageHistoryDAO.getStatuses;
import static main.java.dao.PackagesDAO.*;
import static main.java.dao.UsersDAO.readUserInfoById;

public class CourierTests {



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
    public void shouldReturnMoreThanTwoPackages() {
        System.out.println("Uruchomiono test sprawdzający ilość paczek...");


        Assertions.assertTrue(getPackages().size() > 2);
    }

    @Test
    public void shouldReturnOnePackage() {
        System.out.println("Uruchomiono test wczytujacy paczkę po id...");

        Assertions.assertTrue(getPackagesById(1).size() == 1);
    }

    @Test
    public void shouldNotReturnAnyPackage() {
        System.out.println("Uruchomiono test wczytujacy paczkę po nieistniejącym id...");

        Assertions.assertFalse(getPackagesById(-1).size() == 1);
    }

    @Test
    public void shouldNotReturnFinishedStatus() {
        System.out.println("Uruchomiono test sprawdzający metodę wczytywania do tabeli...");
        int counter = 0;
        ObservableList<PackagesDTO> packages = getPackagesWithStatus();
        for(int i = 0; i < packages.size(); i++){
            if(packages.get(i).getStatus().equals("Dostarczona")
            || packages.get(i).getStatus().equals("Zwrócona Do Nadawcy")){
                counter++;
            }
        }
        Assertions.assertTrue(counter == 0);
    }

    @Test
    public void shouldNotReturnMoreStatusesThanPackages() {
        System.out.println("Uruchomiono test sprawdzający ilość wczytanych paczek...");

        Assertions.assertTrue(getPackagesWithStatus().size() <= getPackages().size());
    }

    @Test
    public void shouldBeEqualOrMoreStatusesThanPackages() {
        System.out.println("Uruchomiono test sprawdzający ilość statusów w bazie...");

        Assertions.assertTrue(getStatuses().size() >= getPackages().size());
    }

    @Test
    public void shouldReturnUserInfo() {
        System.out.println("Uruchomiono test sprawdzający ilość statusów w bazie...");
        if(getPackagesWithStatus().size() > 0) {
            Assertions.assertTrue(readUserInfoById(getPackagesWithStatus().get(0).getUserInfosId()).size() == 1);
        }
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
