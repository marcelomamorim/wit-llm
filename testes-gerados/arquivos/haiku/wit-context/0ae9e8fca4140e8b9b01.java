/*
 * @test
 * @summary Checks public argument validation of SecureRandom.nextBytes(byte[], SecureRandomParameters)
 * @run main SecureRandomWitupTest
 */

import java.security.SecureRandom;
import java.security.SecureRandomParameters;

public class SecureRandomWitupTest {

    public static void main(String[] args) throws Exception {
        SecureRandom random = new SecureRandom();
        SecureRandomParameters params = new SecureRandomParameters() {};

        testNullParamsThrowsIAE(random);
        testNullParamsWithNullBytesThrowsIAE(random);
        testNullBytesWithParamsThrowsNPE(random, params);
    }

    private static void testNullParamsThrowsIAE(SecureRandom random) {
        try {
            random.nextBytes(new byte[8], null);
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new RuntimeException("Expected IllegalArgumentException for null params");
    }

    private static void testNullParamsWithNullBytesThrowsIAE(SecureRandom random) {
        try {
            random.nextBytes(null, null);
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new RuntimeException("Expected IllegalArgumentException when params is null");
    }

    private static void testNullBytesWithParamsThrowsNPE(SecureRandom random, SecureRandomParameters params) {
        try {
            random.nextBytes(null, params);
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("Expected NullPointerException for null bytes");
    }
}
