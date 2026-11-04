/*
 * @test
 * @summary Test SecretKeySpec constructor with key and algorithm arguments
 * @run main SecretKeySpecWitupTest
 */

import javax.crypto.spec.SecretKeySpec;

public class SecretKeySpecWitupTest {
    public static void main(String[] args) {
        testNullKeyThrows();
        testNullAlgorithmThrows();
        testEmptyKeyThrows();
        testValidKeyAndAlgorithm();
    }

    private static void testNullKeyThrows() {
        try {
            new SecretKeySpec(null, "AES");
            throw new RuntimeException("Expected IllegalArgumentException for null key");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testNullAlgorithmThrows() {
        try {
            new SecretKeySpec(new byte[] {1, 2, 3}, null);
            throw new RuntimeException("Expected IllegalArgumentException for null algorithm");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testEmptyKeyThrows() {
        try {
            new SecretKeySpec(new byte[0], "AES");
            throw new RuntimeException("Expected IllegalArgumentException for empty key");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testValidKeyAndAlgorithm() {
        byte[] key = {1, 2, 3, 4, 5};
        String algorithm = "AES";
        SecretKeySpec spec = new SecretKeySpec(key, algorithm);
        if (spec == null) {
            throw new RuntimeException("SecretKeySpec constructor returned null");
        }
        // Defensive copy check: modifying original key should not affect spec
        key[0] = 9;
        byte[] internalKey = spec.getEncoded();
        if (internalKey[0] == 9) {
            throw new RuntimeException("SecretKeySpec did not clone key array defensively");
        }
        if (!algorithm.equals(spec.getAlgorithm())) {
            throw new RuntimeException("Algorithm name mismatch");
        }
    }
}
