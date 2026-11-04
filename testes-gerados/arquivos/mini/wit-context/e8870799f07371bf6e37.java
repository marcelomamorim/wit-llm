/*
 * @test
 * @summary Test SecureRandom.reseed() method behavior
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
        // Create a SecureRandom instance with default constructor
        SecureRandom sr = new SecureRandom();

        // Call reseed() multiple times to check no exception is thrown
        for (int i = 0; i < 5; i++) {
            sr.reseed();
        }

        // If no exception thrown, test passes
        System.out.println("SecureRandom.reseed() executed without exceptions.");
    }
}
