package test.auth;

import main.java.controllers.auth.PasswordReset;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PasswordResetTests extends PasswordReset {

    @Test
    public void shouldBeEmail() {
        System.out.println("Test sprawdzający czy string jest emailem");

        String email = "email@gmail.com";

        Assertions.assertTrue(isEmail(email));
    }

    @Test
    public void shouldNotBeEmail() {
        System.out.println("Test sprawdzający czy string jest emailem");

        String email = "email!gmail.com";

        Assertions.assertFalse(isEmail(email));
    }

    @Test
    public void shouldBeGoodPassword() {
        System.out.println("Test sprawdzający czy hasla sa poprawne");

        String password = "zaq1@WSX!";
        String password2 = "zaq1@WSX!";

        PasswordReset passwordReset = new PasswordReset();

        Assertions.assertTrue(passwordReset.isValid(password,password2));
    }

    @Test
    public void shouldBeWeakPassword() {
        System.out.println("Test sprawdzający czy wykryte zostanie slabe haslo");

        String password = "123";
        String password2 = "123";

        PasswordReset passwordReset = new PasswordReset();

        Assertions.assertThrows(NullPointerException.class,
                ()-> Assertions.assertTrue(passwordReset.isValid(password,
                        password2))
        );
    }

    @Test
    public void shouldBeNotMatchingPassword() {
        System.out.println("Test sprawdzający czy wykryte zostanie blednie " +
                "powtorzone haslo");

        String password = "zaq1@WSX!";
        String password2 = "zaq1@WSX";

        PasswordReset passwordReset = new PasswordReset();

        Assertions.assertThrows(NullPointerException.class,
                ()-> Assertions.assertTrue(passwordReset.isValid(password,
                        password2))
        );
    }
}
