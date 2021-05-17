package test.auth;

import main.java.controllers.auth.Register;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RegisterTests extends Register {

    @Test
    public void shouldBeSuccessfulRegisterValidation() {
        System.out.println("Test sprawdzający poprawne dane przy rejestracji");

        String firstName = "Jan";
        String lastName = "Kowalski";
        String phoneNumber = "321654987";
        String email = "jan.kowalski@gmail.com";
        String street = "Mickiewicza 1C";
        String city = "Rzeszow";
        String voivodeship = "Podkarpackie";
        String password = "zaq1@WSX!";
        String password2 = "zaq1@WSX!";

        Register register = new Register();

        Assertions.assertTrue(register.isValid(firstName, lastName, phoneNumber,
                email, street, city, voivodeship, password, password2));
    }

    @Test
    public void shouldBeErrorAtFirstNameInRegisterValidation() {
        System.out.println("Test sprawdzający wykrycie bledu w imieniu przy rejestracji");

        String firstName = "Jan123";
        String lastName = "Kowalski";
        String phoneNumber = "321654987";
        String email = "jan.kowalski@gmail.com";
        String street = "Mickiewicza 1C";
        String city = "Rzeszow";
        String voivodeship = "Podkarpackie";
        String password = "zaq1@WSX!";
        String password2 = "zaq1@WSX!";

        Register register = new Register();

        Assertions.assertThrows(NullPointerException.class,
                ()-> Assertions.assertTrue(register.isValid(firstName, lastName, phoneNumber,
                        email, street, city, voivodeship, password, password2))
        );
    }

    @Test
    public void shouldBeErrorAtLastNameInRegisterValidation() {
        System.out.println("Test sprawdzający wykrycie bledu w nazwisku przy rejestracji");

        String firstName = "Jan";
        String lastName = "Kowalski123";
        String phoneNumber = "321654987";
        String email = "jan.kowalski@gmail.com";
        String street = "Mickiewicza 1C";
        String city = "Rzeszow";
        String voivodeship = "Podkarpackie";
        String password = "zaq1@WSX!";
        String password2 = "zaq1@WSX!";

        Register register = new Register();

        Assertions.assertThrows(NullPointerException.class,
                ()-> Assertions.assertTrue(register.isValid(firstName, lastName, phoneNumber,
                        email, street, city, voivodeship, password, password2))
        );
    }

    @Test
    public void shouldBeErrorAtPhoneNumberInRegisterValidation() {
        System.out.println("Test sprawdzający wykrycie bledu w numerze telefonu przy rejestracji");

        String firstName = "Jan";
        String lastName = "Kowalski";
        String phoneNumber = "321654987abc";
        String email = "jan.kowalski@gmail.com";
        String street = "Mickiewicza 1C";
        String city = "Rzeszow";
        String voivodeship = "Podkarpackie";
        String password = "zaq1@WSX!";
        String password2 = "zaq1@WSX!";

        Register register = new Register();

        Assertions.assertThrows(NullPointerException.class,
                ()-> Assertions.assertTrue(register.isValid(firstName, lastName, phoneNumber,
                        email, street, city, voivodeship, password, password2))
        );
    }

    @Test
    public void shouldBeErrorAtEmailInRegisterValidation() {
        System.out.println("Test sprawdzający wykrycie bledu w emailu przy rejestracji");

        String firstName = "Jan";
        String lastName = "Kowalski";
        String phoneNumber = "321654987";
        String email = "jan.kowalski!gmail.com";
        String street = "Mickiewicza 1C";
        String city = "Rzeszow";
        String voivodeship = "Podkarpackie";
        String password = "zaq1@WSX!";
        String password2 = "zaq1@WSX!";

        Register register = new Register();

        Assertions.assertThrows(NullPointerException.class,
                ()-> Assertions.assertTrue(register.isValid(firstName, lastName, phoneNumber,
                        email, street, city, voivodeship, password, password2))
        );
    }

    @Test
    public void shouldBeErrorAtStreetInRegisterValidation() {
        System.out.println("Test sprawdzający wykrycie bledu w ulicy przy rejestracji");

        String firstName = "Jan";
        String lastName = "Kowalski";
        String phoneNumber = "321654987";
        String email = "jan.kowalski@gmail.com";
        String street = "Mickiewicza @1C";
        String city = "Rzeszow";
        String voivodeship = "Podkarpackie";
        String password = "zaq1@WSX!";
        String password2 = "zaq1@WSX!";

        Register register = new Register();

        Assertions.assertThrows(NullPointerException.class,
                ()-> Assertions.assertTrue(register.isValid(firstName, lastName, phoneNumber,
                        email, street, city, voivodeship, password, password2))
        );
    }

    @Test
    public void shouldBeErrorAtCityInRegisterValidation() {
        System.out.println("Test sprawdzający wykrycie bledu w miescie przy rejestracji");

        String firstName = "Jan";
        String lastName = "Kowalski";
        String phoneNumber = "321654987";
        String email = "jan.kowalski@gmail.com";
        String street = "Mickiewicza 1C";
        String city = "!Rzeszow";
        String voivodeship = "Podkarpackie";
        String password = "zaq1@WSX!";
        String password2 = "zaq1@WSX!";

        Register register = new Register();

        Assertions.assertThrows(NullPointerException.class,
                ()-> Assertions.assertTrue(register.isValid(firstName, lastName, phoneNumber,
                        email, street, city, voivodeship, password, password2))
        );
    }

    @Test
    public void shouldBeErrorAtVoivodeshipInRegisterValidation() {
        System.out.println("Test sprawdzający wykrycie bledu w wojewodztwie przy rejestracji");

        String firstName = "Jan";
        String lastName = "Kowalski";
        String phoneNumber = "321654987";
        String email = "jan.kowalski@gmail.com";
        String street = "Mickiewicza 1C";
        String city = "Rzeszow";
        String voivodeship = "abc";
        String password = "zaq1@WSX!";
        String password2 = "zaq1@WSX!";

        Register register = new Register();

        Assertions.assertThrows(NullPointerException.class,
                ()-> Assertions.assertTrue(register.isValid(firstName, lastName, phoneNumber,
                        email, street, city, voivodeship, password, password2))
        );
    }

    @Test
    public void shouldBeErrorAtPasswordInRegisterValidation() {
        System.out.println("Test sprawdzający wykrycie bledu w hasle przy rejestracji (haslo za slabe)");

        String firstName = "Jan";
        String lastName = "Kowalski";
        String phoneNumber = "321654987";
        String email = "jan.kowalski@gmail.com";
        String street = "Mickiewicza 1C";
        String city = "Rzeszow";
        String voivodeship = "Podkarpackie";
        String password = "abc123";
        String password2 = "abc123";

        Register register = new Register();

        Assertions.assertThrows(NullPointerException.class,
                ()-> Assertions.assertTrue(register.isValid(firstName, lastName, phoneNumber,
                        email, street, city, voivodeship, password, password2))
        );
    }

    @Test
    public void shouldBeErrorAtRepeatPasswordInRegisterValidation() {
        System.out.println("Test sprawdzający wykrycie bledu w hasle przy rejestracji (hasla sie roznia)");

        String firstName = "Jan";
        String lastName = "Kowalski";
        String phoneNumber = "321654987";
        String email = "jan.kowalski@gmail.com";
        String street = "Mickiewicza 1C";
        String city = "Rzeszow";
        String voivodeship = "Podkarpackie";
        String password = "zaq1@WSX";
        String password2 = "zaq1@abc";

        Register register = new Register();

        Assertions.assertThrows(NullPointerException.class,
                ()-> Assertions.assertTrue(register.isValid(firstName, lastName, phoneNumber,
                        email, street, city, voivodeship, password, password2))
        );
    }
}
