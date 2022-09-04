package main.java.controllers.auth;

import java.math.BigInteger;
import java.security.MessageDigest;

public class Encryption {
    /**
     * <p>
     * Method used to encrypt given string with MD5 algorithm
     * When succeed returns encrypted string, null in case of exception.
     * </p>
     *
     * @param source string that needs to be encrypted
     * @return returns encrypted string or null
     */
    public static String encrypt(String source) {
        String md5;
        try {
            MessageDigest mdEnc = MessageDigest.getInstance("MD5");
            mdEnc.update(source.getBytes(), 0, source.length());

            md5 = new BigInteger(1, mdEnc.digest()).toString(16);
        } catch (Exception ex) {
            return null;
        }
        return md5;
    }
}
