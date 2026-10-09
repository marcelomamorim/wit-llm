/*
 * @test
 * @summary Test SecureRandom.reseed(SecureRandomParameters) for exception and normal behavior
 * @run main SecureRandomWitupTest_Pb759231c0ff7
 */

import java.security.SecureRandom;
import java.security.SecureRandomParameters;

public class SecureRandomWitupTest_Pb759231c0ff7 {
    public static void main(String[] args) throws Exception {
        testReseedNullParams();
        testReseedNormal();
    }

    private static void testReseedNullParams() {
        SecureRandom sr = new SecureRandom();
        try {
            sr.reseed(null);
            throw new RuntimeException("Expected IllegalArgumentException for null params");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }

    private static void testReseedNormal() throws Exception {
        SecureRandom sr = new SecureRandom();
        // Create a minimal SecureRandomParameters implementation for testing
        SecureRandomParameters params = new SecureRandomParameters() {
            public String toString() { return "TestParams"; }
        };
        // Call reseed with non-null params, expect no exception
        sr.reseed(params);
    }
}
