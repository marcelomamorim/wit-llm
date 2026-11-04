/*
 * @test
 * @summary Test SecureRandom.nextBytes(byte[], SecureRandomParameters) behavior
 * @run main SecureRandomWitupTest
 */

import java.security.SecureRandom;
import java.security.SecureRandomParameters;

public class SecureRandomWitupTest {
    public static void main(String[] args) throws Exception {
        testNextBytesWithNullParams();
        testNextBytesWithNullBytes();
        testNextBytesWithValidParams();
    }

    private static void testNextBytesWithNullParams() {
        SecureRandom sr = new SecureRandom();
        byte[] bytes = new byte[1];
        try {
            sr.nextBytes(bytes, null);
            throw new RuntimeException("Expected IllegalArgumentException for null params");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testNextBytesWithNullBytes() {
        SecureRandom sr = new SecureRandom();
        SecureRandomParameters params = new SecureRandomParameters() {
            public SecureRandomParameters clone() { return this; }
        };
        try {
            sr.nextBytes(null, params);
            throw new RuntimeException("Expected NullPointerException for null bytes");
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testNextBytesWithValidParams() {
        SecureRandom sr = new SecureRandom();
        byte[] bytes = new byte[16];
        SecureRandomParameters params = new SecureRandomParameters() {
            public SecureRandomParameters clone() { return this; }
        };
        sr.nextBytes(bytes, params);
        boolean allZero = true;
        for (byte b : bytes) {
            if (b != 0) {
                allZero = false;
                break;
            }
        }
        if (allZero) {
            throw new RuntimeException("nextBytes did not modify the byte array");
        }
    }
}
