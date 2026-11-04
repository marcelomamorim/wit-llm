/*
 * @test
 * @summary Verifies that SecureRandom.nextBytes(byte[], SecureRandomParameters) rejects null params before touching the output array
 * @run main SecureRandomWitupTest
 */

import java.security.SecureRandom;

public class SecureRandomWitupTest {

    public static void main(String[] args) throws Exception {
        testNullParamsThrowsIAE();
        testNullParamsWithNullBytesThrowsIAE();
        testNullParamsDoesNotModifyBuffer();
    }

    static void testNullParamsThrowsIAE() {
        SecureRandom sr = new SecureRandom();
        byte[] bytes = new byte[16];
        try {
            sr.nextBytes(bytes, null);
            throw new RuntimeException("Expected IllegalArgumentException for null params");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    static void testNullParamsWithNullBytesThrowsIAE() {
        SecureRandom sr = new SecureRandom();
        try {
            sr.nextBytes(null, null);
            throw new RuntimeException("Expected IllegalArgumentException for null params");
        } catch (IllegalArgumentException expected) {
            // expected: params check precedes bytes null check
        }
    }

    static void testNullParamsDoesNotModifyBuffer() {
        SecureRandom sr = new SecureRandom();
        byte[] bytes = new byte[32];
        try {
            sr.nextBytes(bytes, null);
        } catch (IllegalArgumentException expected) {
            // expected
        }
        for (int i = 0; i < bytes.length; i++) {
            if (bytes[i] != 0) {
                throw new RuntimeException("Buffer modified at index " + i);
            }
        }
    }
}
