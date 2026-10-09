/*
 * @test
 * @summary Test for SecureRandom.nextBytes(byte[], SecureRandomParameters) handling null params
 * @run main SecureRandomWitupTest_P5b4dfb85afe6
 */

import java.security.SecureRandom;
import java.security.SecureRandomParameters;

public class SecureRandomWitupTest_P5b4dfb85afe6 {

    public static void main(String[] args) {
        testNextBytesWithNullParams();
    }

    public static void testNextBytesWithNullParams() {
        SecureRandom sr = new SecureRandom();
        byte[] bytes = new byte[10];
        try {
            sr.nextBytes(bytes, null);
            System.out.println("FAIL: Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: Caught expected IllegalArgumentException");
        } catch (Throwable t) {
            System.out.println("FAIL: Caught unexpected exception: " + t);
        }
    }
}
