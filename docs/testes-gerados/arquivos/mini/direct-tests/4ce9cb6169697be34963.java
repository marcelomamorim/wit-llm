/*
 * @test
 * @summary Test SecureRandom.generateSeed(int) for valid and invalid inputs
 * @run main SecureRandomWitupTest
 */

import java.security.SecureRandom;

public class SecureRandomWitupTest {
    public static void main(String[] args) throws Exception {
        testGenerateSeedPositiveLength();
        testGenerateSeedZeroLength();
        testGenerateSeedNegativeLength();
    }

    private static void testGenerateSeedPositiveLength() throws Exception {
        SecureRandom sr = new SecureRandom();
        int length = 16;
        byte[] seed = sr.generateSeed(length);
        if (seed == null) {
            throw new RuntimeException("generateSeed returned null for positive length");
        }
        if (seed.length != length) {
            throw new RuntimeException("generateSeed returned array of wrong length: expected " + length + " but got " + seed.length);
        }
    }

    private static void testGenerateSeedZeroLength() throws Exception {
        SecureRandom sr = new SecureRandom();
        int length = 0;
        byte[] seed = sr.generateSeed(length);
        if (seed == null) {
            throw new RuntimeException("generateSeed returned null for zero length");
        }
        if (seed.length != 0) {
            throw new RuntimeException("generateSeed returned array of wrong length for zero length: expected 0 but got " + seed.length);
        }
    }

    private static void testGenerateSeedNegativeLength() throws Exception {
        SecureRandom sr = new SecureRandom();
        try {
            sr.generateSeed(-1);
            throw new RuntimeException("generateSeed did not throw IllegalArgumentException for negative length");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
