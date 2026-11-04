/*
 * @test
 * @summary Test javax.crypto.spec.DESedeKeySpec constructors for valid and invalid inputs
 * @run main DESedeKeySpecWitupTest
 */

import javax.crypto.spec.DESedeKeySpec;
import java.security.InvalidKeyException;

public class DESedeKeySpecWitupTest {
    public static void main(String[] args) throws Exception {
        testValidKeyLength();
        testInvalidKeyLength();
        testValidKeyWithOffset();
        testInvalidKeyWithOffset();
    }

    private static void testValidKeyLength() throws Exception {
        byte[] key = new byte[24];
        for (int i = 0; i < 24; i++) {
            key[i] = (byte) i;
        }
        DESedeKeySpec spec = new DESedeKeySpec(key);
        byte[] returnedKey = spec.getKey();
        if (returnedKey.length != 24) {
            throw new RuntimeException("Returned key length is not 24 bytes");
        }
        for (int i = 0; i < 24; i++) {
            if (returnedKey[i] != key[i]) {
                throw new RuntimeException("Returned key content differs from input");
            }
        }
    }

    private static void testInvalidKeyLength() {
        byte[] shortKey = new byte[23];
        try {
            new DESedeKeySpec(shortKey);
            throw new RuntimeException("Expected InvalidKeyException for short key length");
        } catch (InvalidKeyException e) {
            // Expected
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
            throw new RuntimeException("Returned key length is not 24 bytes");
        }
        for (int i = 0; i < 24; i++) {
            if (returnedKey[i] != key[offset + i]) {
                throw new RuntimeException("Returned key content differs from input with offset");
            }
        }
    }

    private static void testInvalidKeyWithOffset() {
        byte[] key = new byte[30];
        int offset = 10; // 30 - 10 = 20 < 24, should throw
        try {
            new DESedeKeySpec(key, offset);
            throw new RuntimeException("Expected InvalidKeyException for short key length with offset");
        } catch (InvalidKeyException e) {
            // Expected
        }
    }
}
