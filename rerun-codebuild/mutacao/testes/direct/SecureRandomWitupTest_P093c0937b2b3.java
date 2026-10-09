/* @test    @summary Test SecureRandom.generateSeed(int) with valid input    @run main SecureRandomWitupTest_P093c0937b2b3 */

import java.security.SecureRandom;
import java.util.Arrays;

public class SecureRandomWitupTest_P093c0937b2b3 {

    public static void main(String[] args) {
        testGenerateSeedPositive();
        testGenerateSeedZero();
        testGenerateSeedNegative();
    }

    public static void testGenerateSeedPositive() {
        SecureRandom sr = new SecureRandom();
        int seedSize = 16;
        byte[] seed = sr.generateSeed(seedSize);
        if (seed == null) {
            throw new AssertionError("generateSeed returned null for positive size");
        }
        if (seed.length != seedSize) {
            throw new AssertionError("Expected seed length " + seedSize + " but got " + seed.length);
        }
    }

    public static void testGenerateSeedZero() {
        SecureRandom sr = new SecureRandom();
        int seedSize = 0;
        byte[] seed = sr.generateSeed(seedSize);
        if (seed == null) {
            throw new AssertionError("generateSeed returned null for zero size");
        }
        if (seed.length != seedSize) {
            throw new AssertionError("Expected seed length " + seedSize + " but got " + seed.length);
        }
    }

    public static void testGenerateSeedNegative() {
        SecureRandom sr = new SecureRandom();
        int seedSize = -1;
        boolean exceptionThrown = false;
        try {
            sr.generateSeed(seedSize);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException for negative seed size");
        }
    }
}
