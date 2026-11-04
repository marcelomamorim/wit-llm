/*
 * @test
 * @summary Test DESedeKeySpec constructor with offset for valid and invalid inputs
 * @run main DESedeKeySpecWitupTest
 */

import javax.crypto.spec.DESedeKeySpec;
import java.security.InvalidKeyException;

public class DESedeKeySpecWitupTest {
    public static void main(String[] args) throws Exception {
        testValidKeyWithOffset();
        testInvalidKeyWithOffset();
    }

    private static void testValidKeyWithOffset() throws Exception {
        byte[] keyMaterial = new byte[30];
        for (int i = 0; i < 30; i++) {
            keyMaterial[i] = (byte) i;
        }
        int offset = 3;
        DESedeKeySpec spec = new DESedeKeySpec(keyMaterial, offset);
        byte[] key = spec.getKey();
        if (key.length != 24) {
            throw new RuntimeException("Key length is not 24 bytes");
        }
        for (int i = 0; i < 24; i++) {
            if (key[i] != keyMaterial[offset + i]) {
                throw new RuntimeException("Key byte mismatch at index " + i);
            }
        }
    }

    private static void testInvalidKeyWithOffset() {
        byte[] shortKey = new byte[25];
        int offset = 2; // 25 - 2 = 23 < 24, should throw
        try {
            new DESedeKeySpec(shortKey, offset);
            throw new RuntimeException("Expected InvalidKeyException not thrown");
        } catch (InvalidKeyException e) {
            // Expected exception
        }
    }
}
