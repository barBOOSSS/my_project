package by.pleshkov.service.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashPassword {

       public static String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        byte[] bytes = md5.digest(password.getBytes());
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02X", b));
        }
        return String.valueOf(builder);
    }
}
