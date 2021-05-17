package test;

import javafx.collections.ObservableList;
import main.java.controllers.auth.Encryption;
import main.java.controllers.auth.Login;
import main.java.controllers.client.ClientTrackPackage;
import main.java.controllers.client.PopulatePackageItem;
import main.java.dao.HibernateUtil;
import main.java.dao.PackagesDAO;
import main.java.entity.PackageHistory;
import main.java.entity.PackagesDTO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import org.opentest4j.AssertionFailedError;

import javax.xml.bind.SchemaOutputResolver;

import static org.hamcrest.CoreMatchers.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClientTests {

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
        System.out.println("Utworzono sessionFactory");
    }

    @AfterAll
    public static void tearDown() {
        if(sessionFactory != null){
            sessionFactory.close();
            System.out.println("Zniszcono sessionFactory");
        }
    }

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
    public void testList() {
        System.out.println("Uruchomiono test listy...");

        List<PopulatePackageItem> listActual = ClientTrackPackage.packageTest(5,"nenej26509@87708b.com");

        List<PopulatePackageItem> listExpected = ClientTrackPackage.packageTest(5,"nenej26509@87708b.com");

        int x = 0;

        for(int i = 0; i < listActual.size(); i++){
            for(int j = 0 ; j < listExpected.size(); j++){
                if(listActual.get(i).getPackageNumber().equals(listExpected.get(j).getPackageNumber())){
                    x++;
                }
            }
        }

        Assertions.assertTrue(x == listActual.size() * listExpected.size());
    }

    @Test
    public void testFailedEncryption() {
        System.out.println("Uruchomiono test szyfrowania (zle) hasła...");

        Assertions.assertThrows(AssertionFailedError.class,
                ()->{
                    Assertions.assertEquals("9e38e8d688743e0d07d669a1fcbcd35b","zaq1@WSd");
                });
    }

    @Test
    public void testSuccessEncryption() {
        System.out.println("Uruchomiono test szyfrowania (dobre) hasła...");

        Assertions.assertThrows(AssertionFailedError.class,
                ()->{
                    Assertions.assertEquals("9e38e8d688743e0d07d669a1fcbcd35b","zaq1@WSX");
                });
    }

    @Test
    @Tag("Powinno wyjsc 0 rekordow")
    public void testReadPackagesById(){
        ObservableList<PackagesDTO> packages = PackagesDAO.readPackagesByID(22,"niematego@mail.com");

        Assertions.assertTrue(packages.size() == 0);
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
