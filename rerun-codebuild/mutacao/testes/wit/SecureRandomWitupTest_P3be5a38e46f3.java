/* @test    @summary Test for SecureRandom.reseed method to verify exception handling and behavior.    @run main SecureRandomWitupTest_P3be5a38e46f3 */

import java.security.SecureRandom;
import java.security.SecureRandomParameters;

public class SecureRandomWitupTest_P3be5a38e46f3 {

    public static void main(String[] args) {
        testReseedWithNullParams();
        testReseedWithValidParams();
    }

    private static void testReseedWithNullParams() {
        SecureRandom sr = new SecureRandom();
        try {
            sr.reseed(null);
            System.out.println("FAIL: Expected IllegalArgumentException for null params");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: Caught expected IllegalArgumentException for null params");
        } catch (Throwable t) {
            System.out.println("FAIL: Caught unexpected exception: " + t);
        }
    }

    private static void testReseedWithValidParams() {
        SecureRandom sr = new SecureRandom();
        SecureRandomParameters params = new SecureRandomParameters();
        try {
            sr.reseed(params);
            System.out.println("PASS: reseed with valid params executed without exception");
        } catch (UnsupportedOperationException e) {
            System.out.println("PASS: Caught expected UnsupportedOperationException");
        } catch (Throwable t) {
            System.out.println("FAIL: Caught unexpected exception: " + t);
        }
    }
}
