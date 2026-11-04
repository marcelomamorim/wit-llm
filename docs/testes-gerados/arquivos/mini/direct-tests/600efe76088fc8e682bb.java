/*
 * @test
 * @summary Test SecureRandom.reseed(SecureRandomParameters) method behavior
 * @run main SecureRandomWitupTest
 */

import java.security.SecureRandom;
import java.security.SecureRandomParameters;

public class SecureRandomWitupTest {
    public static void main(String[] args) throws Exception {
        testReseedNullParams();
        testReseedWithDummyParams();
    }

    private static void testReseedNullParams() {
        SecureRandom sr = new SecureRandom();
        try {
            sr.reseed(null);
            throw new RuntimeException("Expected IllegalArgumentException for null params");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testReseedWithDummyParams() {
        SecureRandom sr = new SecureRandom();
        SecureRandomParameters dummyParams = new SecureRandomParameters() {};
        // Call reseed with a non-null dummy SecureRandomParameters instance
        // Expect no exception and no observable error
        sr.reseed(dummyParams);
    }
}
