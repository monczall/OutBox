package test;

import main.java.dao.HibernateUtil;
import main.java.dao.PackageHistoryDAO;
import main.java.entity.PackageHistory;
import main.java.entity.Users;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.jupiter.api.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class UnitTests {

//    @Autowired
//    private PackageHistoryDAO packageHistoryDAO;
//
//    @Test
//    @Transactional
//    @Rollback(true)
//    public void testAddPackageHistory(){
//        PackageHistory packageHistory = new PackageHistory();
//        packageHistory.setPackageId(1);
//
//        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        LocalDateTime now = LocalDateTime.now();
//
//        packageHistory.setStatus("Zarejestrowana");
//        packageHistory.setDate(Timestamp.valueOf(dateTimeFormatter.format(now)));
//        packageHistory.setPackageId(1);
//
//        session.clear();
//    }


    private static SessionFactory sessionFactory;
    private Session session;

    @BeforeAll
    public static void setUp() {
        sessionFactory = HibernateUtil.getSessionFactory();
        System.out.println(sessionFactory.isClosed());
        System.out.println("Utworzono sessionFactory");
    }

//    @AfterAll
//    public static void tearDown() {
//        if(sessionFactory != null){
//            sessionFactory.close();
//            System.out.println("Zniszcono sessionFactory");
//        }
//    }

    @Test
    public void testCreate() {
        System.out.println("Uruchomiono test dodajacy status...");

        session = sessionFactory.openSession();

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        PackageHistory packageHistory = new PackageHistory();

        packageHistory.setPackageId(1);
        packageHistory.setStatus("Zarejestrowana");
        packageHistory.setDate(Timestamp.valueOf(dateTimeFormatter.format(now)));

        Integer id = (Integer) session.save(packageHistory);

        session.clear();

        Assertions.assertTrue(id > 0);
    }

    @Test
    public void testRead() {
        System.out.println("Uruchomiono test wczytujacy uzytkownikow...");

        session.beginTransaction();

        Query query = session.createQuery("from Users");

        List<Users> listOfUsers = query.list();

        Assertions.assertNotNull(listOfUsers);

        //Assertions.assertTrue(listOfUsers.size() > 0);
        //Assertions.assertEquals(4,listOfUsers.size());
    }


    @BeforeEach
    public void openSession() {
        session = sessionFactory.openSession();
        System.out.println("Otwarto sesje");
    }

    @AfterEach
    public void closeSession() {
        if(session != null)
        {
            session.close();
            System.out.println("Zamknięto sesje");
        }
    }


    }
