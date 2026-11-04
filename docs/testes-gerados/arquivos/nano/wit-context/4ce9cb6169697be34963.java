/* @test    @summary Test for SecureRandom.generateSeed(int) method handling negative input    @run main SecureRandomWitupTest */

import java.security.SecureRandom;

public class SecureRandomWitupTest {

    public static void main(String[] args) {
        testGenerateSeedNegative();
        testGenerateSeedPositive();
    }

    public static void testGenerateSeedNegative() {
        SecureRandom sr = new SecureRandom();
        try {
            sr.generateSeed(-1);
            System.err.println("FAIL: Expected IllegalArgumentException for negative input");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: Caught expected IllegalArgumentException for negative input");
        } catch (Throwable t) {
            System.err.println("FAIL: Caught unexpected exception: " + t);
        }
    }

    public static void testGenerateSeedPositive() {
        SecureRandom sr = new SecureRandom();
        try {
            byte[] seed = sr.generateSeed(10);
            if (seed != null && seed.length == 10) {
                System.out.println("PASS: generateSeed(10) returned array of length 10");
            } else {
                System.err.println("FAIL: generateSeed(10) did not return array of length 10");
            }
        } catch (Throwable t) {
            System.err.println("FAIL: Unexpected exception: " + t);
        }
    }
}
