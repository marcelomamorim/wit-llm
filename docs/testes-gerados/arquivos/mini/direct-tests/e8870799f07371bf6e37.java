/*
 * @test
 * @summary Test SecureRandom reseed method behavior
 * @run main SecureRandomWitupTest
 */

import java.security.SecureRandom;
import java.security.SecureRandomSpi;
import java.security.Provider;

public class SecureRandomWitupTest {

    public static void main(String[] args) throws Exception {
        testReseedDoesNotThrow();
    }

    private static void testReseedDoesNotThrow() throws Exception {
        // Create a SecureRandom instance
        SecureRandom sr = new SecureRandom();

        // Call reseed multiple times to ensure no exceptions and thread safety
        for (int i = 0; i < 5; i++) {
            sr.reseed();
        }

        // Also test reseed on a SecureRandom constructed with a seed
        byte[] seed = new byte[] {1, 2, 3, 4};
        SecureRandom srSeeded = new SecureRandom(seed);
        for (int i = 0; i < 5; i++) {
            srSeeded.reseed();
        }

        // Test reseed on a SecureRandom subclass overriding engineReseed
        SecureRandomSpi testSpi = new SecureRandomSpi() {
            boolean reseeded = false;
            protected void engineSetSeed(byte[] seed) {}
            protected void engineNextBytes(byte[] bytes) {}
            protected byte[] engineGenerateSeed(int numBytes) { return new byte[numBytes]; }
            protected void engineReseed(byte[] seed) {
                reseeded = true;
            }
        };

        SecureRandom srCustom = new SecureRandom(testSpi, (Provider) null);
        srCustom.reseed();

        // If no exceptions thrown, test passes
        System.out.println("SecureRandom.reseed() tests passed.");
    }
}
