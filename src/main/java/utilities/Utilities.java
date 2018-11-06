package utilities;

import java.security.SecureRandom;
import java.util.Random;

/*
Class containing utilities methods, available for the whole project
 */
public class Utilities {

    //generate a random email
    public static String generateRandomEmail() {
        String defaultDomain = "surgimap.com";
        String randomEmail = "qa.test.automation" + System.currentTimeMillis() + "_" + generateRandomPassword(6) + "_@" + defaultDomain;
        System.out.println("Built random email: " + randomEmail);
        return randomEmail;
    }

    //generated a random password, given an input length
    public static String generateRandomPassword(int length) {
        String randomPassword = generateRandomString(length);

        //add uppercase and lowercase
        randomPassword = randomPassword.substring(0, 1).toUpperCase() + randomPassword.substring(1, randomPassword.length()).toLowerCase();

        //add symbol - not needed
        //randomPassword = randomPassword + ".";

        //add number
        randomPassword = randomPassword + (new Random().nextInt(9));

        return randomPassword;
    }

    //generate a random String, given an input length
    public static String generateRandomString(int len) {
        // final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        final String AB = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        SecureRandom rnd = new SecureRandom();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb.toString();
    }

    //pause the Thread for the given number of msecs
    public static void pauseExecution(long msec) {
        try {
            Thread.sleep(msec);
        } catch (InterruptedException e) {
            //do nothing;
        }
    }
}
