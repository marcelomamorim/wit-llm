/*
 * @test
 * @summary Tests for javax.crypto.spec.SecretKeySpec constructor with offset and length
 * @run main SecretKeySpecWitupTest
 */

import javax.crypto.spec.SecretKeySpec;

public class SecretKeySpecWitupTest {
    public static void main(String[] args) {
        testNullKey();
        testNullAlgorithm();
        testEmptyKey();
        testInvalidOffsetLength();
        testNegativeLength();
        testValidConstruction();
    }

    private static void testNullKey() {
        try {
            new SecretKeySpec(null, 0, 1, "AES");
            throw new RuntimeException("Expected IllegalArgumentException for null key");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testNullAlgorithm() {
        try {
            new SecretKeySpec(new byte[] {1, 2, 3}, 0, 3, null);
            throw new RuntimeException("Expected IllegalArgumentException for null algorithm");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testEmptyKey() {
        try {
            new SecretKeySpec(new byte[0], 0, 0, "AES");
            throw new RuntimeException("Expected IllegalArgumentException for empty key");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testInvalidOffsetLength() {
        byte[] key = new byte[5];
        try {
            new SecretKeySpec(key, 3, 3, "AES"); // 5 - 3 < 3
            throw new RuntimeException("Expected IllegalArgumentException for invalid offset/length");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testNegativeLength() {
        byte[] key = new byte[5];
        try {
            new SecretKeySpec(key, 0, -1, "AES");
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException for negative length");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testValidConstruction() {
        byte[] key = new byte[] {10, 20, 30, 40, 50};
        SecretKeySpec spec = new SecretKeySpec(key, 1, 3, "AES");
        byte[] expected = new byte[] {20, 30, 40};
        byte[] actual = spec.getEncoded();
        if (actual.length != expected.length) {
            throw new RuntimeException("Key length mismatch");
        }
        for (int i = 0; i < expected.length; i++) {
            if (actual[i] != expected[i]) {
                throw new RuntimeException("Key byte mismatch at index " + i);
            }
        }
        if (!"AES".equals(spec.getAlgorithm())) {
            throw new RuntimeException("Algorithm mismatch");
        }
    }
}
