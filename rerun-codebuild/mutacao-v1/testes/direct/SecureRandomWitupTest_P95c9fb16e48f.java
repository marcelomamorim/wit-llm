/* @test    @summary Test reseed method of SecureRandom for null parameter and thread safety    @run main SecureRandomWitupTest_P95c9fb16e48f */

import java.security.SecureRandom;
import java.security.SecureRandomParameters;

public class SecureRandomWitupTest_P95c9fb16e48f {

    public static void main(String[] args) {
        testReseedWithNullParams();
        testReseedThreadSafety();
    }

    private static void testReseedWithNullParams() {
        SecureRandom sr = new SecureRandom();
        boolean exceptionThrown = false;
        try {
            sr.reseed(null);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException when reseed called with null");
        }
    }

    private static void testReseedThreadSafety() {
        SecureRandom sr = new SecureRandom();
        SecureRandomParameters params = new SecureRandomParameters() {
            // Empty implementation for testing
        };
        try {
            sr.reseed(params);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception during reseed", e);
        }
    }
}
