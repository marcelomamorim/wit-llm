/*
 * @test
 * @summary Test DESedeKeySpec constructor with offset for correct behavior and exceptions
 * @run main javax.crypto.spec.DESedeKeySpecWitupTest
 */

import javax.crypto.spec.DESedeKeySpec;
import java.security.InvalidKeyException;

public class DESedeKeySpecWitupTest {
    public static void main(String[] args) throws Exception {
        testValidKeyExactLength();
        testValidKeyWithOffset();
        testInvalidKeyTooShort();
        testInvalidKeyTooShortWithOffset();
    }

    private static void testValidKeyExactLength() throws Exception {
        byte[] key = new byte[24];
        for (int i = 0; i < 24; i++) {
            key[i] = (byte) i;
        }
        DESedeKeySpec spec = new DESedeKeySpec(key, 0);
        byte[] returnedKey = spec.getKey();
        if (returnedKey.length != 24) {
            throw new RuntimeException("Returned key length is not 24");
        }
        for (int i = 0; i < 24; i++) {
            if (returnedKey[i] != key[i]) {
                throw new RuntimeException("Returned key content mismatch at index " + i);
            }
        }
    }

    private static void testValidKeyWithOffset() throws Exception {
        byte[] key = new byte[30];
        for (int i = 0; i < 30; i++) {
            key[i] = (byte) (i + 1);
        }
        int offset = 3;
        DESedeKeySpec spec = new DESedeKeySpec(key, offset);
        byte[] returnedKey = spec.getKey();
        if (returnedKey.length != 24) {
            throw new RuntimeException("Returned key length is not 24");
        }
        for (int i = 0; i < 24; i++) {
            if (returnedKey[i] != key[offset + i]) {
                throw new RuntimeException("Returned key content mismatch at index " + i);
            }
        }
    }

    private static void testInvalidKeyTooShort() {
        byte[] key = new byte[23];
        try {
            new DESedeKeySpec(key, 0);
            throw new RuntimeException("Expected InvalidKeyException for key length < 24");
        } catch (InvalidKeyException e) {
            // expected
        }
    }

    private static void testInvalidKeyTooShortWithOffset() {
        byte[] key = new byte[30];
        int offset = 10; // 30 - 10 = 20 < 24
        try {
            new DESedeKeySpec(key, offset);
            throw new RuntimeException("Expected InvalidKeyException for insufficient key length with offset");
        } catch (InvalidKeyException e) {
            // expected
        }
    }
}
