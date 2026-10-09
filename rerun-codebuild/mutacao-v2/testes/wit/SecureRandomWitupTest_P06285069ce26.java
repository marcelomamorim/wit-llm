/*
 * @test
 * @summary Test SecureRandom.nextBytes(byte[], SecureRandomParameters) for null params and normal usage
 * @run main SecureRandomWitupTest_P06285069ce26
 */

import java.security.SecureRandom;
import java.security.SecureRandomParameters;
import java.util.Arrays;

public class SecureRandomWitupTest_P06285069ce26 {
    public static void main(String[] args) throws Exception {
        testNextBytesNullParams();
        testNextBytesNormal();
    }

    private static void testNextBytesNullParams() {
        SecureRandom sr = new SecureRandom();
        byte[] bytes = new byte[10];
        try {
            sr.nextBytes(bytes, null);
            throw new RuntimeException("Expected IllegalArgumentException for null params");
        } catch (IllegalArgumentException iae) {
            // expected
        }
    }

    private static void testNextBytesNormal() {
        SecureRandom sr = new SecureRandom();
        byte[] bytes = new byte[16];
        SecureRandomParameters params = new SecureRandomParameters() {
            // Minimal implementation for testing
            public String toString() { return "TestParams"; }
        };
        sr.nextBytes(bytes, params);
        // Check that bytes array is not all zero (very low probability of failure)
        boolean allZero = true;
        for (byte b : bytes) {
            if (b != 0) {
                allZero = false;
                break;
            }
        }
        if (allZero) {
            throw new RuntimeException("nextBytes produced all zero bytes, unlikely");
        }
    }
}
