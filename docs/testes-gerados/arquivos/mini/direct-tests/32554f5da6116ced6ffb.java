/*
 * @test
 * @summary Test javax.crypto.spec.SecretKeySpec constructor with byte[] and String
 * @run main SecretKeySpecWitupTest
 */

import javax.crypto.spec.SecretKeySpec;

public class SecretKeySpecWitupTest {
    public static void main(String[] args) {
        testValidKeyAndAlgorithm();
        testNullKey();
        testNullAlgorithm();
        testEmptyKey();
    }

    private static void testValidKeyAndAlgorithm() {
        byte[] key = {1, 2, 3, 4};
        String algorithm = "AES";
        SecretKeySpec spec = new SecretKeySpec(key, algorithm);
        // Check that the algorithm is set correctly
        if (!algorithm.equals(spec.getAlgorithm())) {
            throw new RuntimeException("Algorithm mismatch");
        }
        // Check that the key is a clone, not the same reference
        byte[] internalKey = spec.getEncoded();
        if (internalKey == key) {
            throw new RuntimeException("Key array was not cloned");
        }
        // Check that the contents are equal
        if (internalKey.length != key.length) {
            throw new RuntimeException("Key length mismatch");
        }
        for (int i = 0; i < key.length; i++) {
            if (internalKey[i] != key[i]) {
                throw new RuntimeException("Key content mismatch at index " + i);
            }
        }
    }

    private static void testNullKey() {
        try {
            new SecretKeySpec(null, "AES");
            throw new RuntimeException("Expected IllegalArgumentException for null key");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }

    private static void testNullAlgorithm() {
        try {
            new SecretKeySpec(new byte[] {1, 2, 3}, null);
            throw new RuntimeException("Expected IllegalArgumentException for null algorithm");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }

    private static void testEmptyKey() {
        try {
            new SecretKeySpec(new byte[0], "AES");
            throw new RuntimeException("Expected IllegalArgumentException for empty key");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }
}
