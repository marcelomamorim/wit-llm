/*
 * @test
 * @summary Test SecretKeySpec constructor with offset and length
 * @run main SecretKeySpecWitupTest
 */

public class SecretKeySpecWitupTest {
    public static void main(String[] args) throws Exception {
        testValidConstruction();
        testNullKey();
        testNullAlgorithm();
        testEmptyKey();
        testInvalidOffsetLengthCombination();
        testNegativeLength();
    }

    private static void testValidConstruction() {
        byte[] key = {1, 2, 3, 4, 5};
        int offset = 1;
        int len = 3;
        String algorithm = "AES";
        javax.crypto.spec.SecretKeySpec sks = new javax.crypto.spec.SecretKeySpec(key, offset, len, algorithm);
        byte[] extractedKey = sks.getEncoded();
        if (extractedKey.length != len) {
            throw new RuntimeException("Length of extracted key does not match len");
        }
        for (int i = 0; i < len; i++) {
            if (extractedKey[i] != key[offset + i]) {
                throw new RuntimeException("Extracted key byte mismatch at index " + i);
            }
        }
        if (!algorithm.equals(sks.getAlgorithm())) {
            throw new RuntimeException("Algorithm mismatch");
        }
    }

    private static void testNullKey() {
        try {
            new javax.crypto.spec.SecretKeySpec(null, 0, 1, "AES");
            throw new RuntimeException("Expected IllegalArgumentException for null key");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testNullAlgorithm() {
        try {
            new javax.crypto.spec.SecretKeySpec(new byte[]{1,2,3}, 0, 3, null);
            throw new RuntimeException("Expected IllegalArgumentException for null algorithm");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testEmptyKey() {
        try {
            new javax.crypto.spec.SecretKeySpec(new byte[0], 0, 0, "AES");
            throw new RuntimeException("Expected IllegalArgumentException for empty key");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testInvalidOffsetLengthCombination() {
        byte[] key = {1, 2, 3};
        try {
            new javax.crypto.spec.SecretKeySpec(key, 2, 2, "AES"); // 3 - 2 < 2
            throw new RuntimeException("Expected IllegalArgumentException for invalid offset/length");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testNegativeLength() {
        byte[] key = {1, 2, 3};
        try {
            new javax.crypto.spec.SecretKeySpec(key, 0, -1, "AES");
            throw new RuntimeException("Expected ArrayIndexOutOfBoundsException for negative length");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
    }
}
