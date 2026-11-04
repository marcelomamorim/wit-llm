/*
 * @test
 * @summary Test DESedeKeySpec constructor with byte[] key
 * @run main javax.crypto.spec.DESedeKeySpecWitupTest
 */

import javax.crypto.spec.DESedeKeySpec;
import java.security.InvalidKeyException;

public class DESedeKeySpecWitupTest {
    public static void main(String[] args) throws Exception {
        testValidKeyLength();
        testInvalidKeyLength();
        testNullKey();
    }

    private static void testValidKeyLength() throws Exception {
        byte[] validKey = new byte[24];
        for (int i = 0; i < 24; i++) {
            validKey[i] = (byte) i;
        }
        DESedeKeySpec spec = new DESedeKeySpec(validKey);
        byte[] returnedKey = spec.getKey();
        if (returnedKey.length != 24) {
            throw new RuntimeException("Returned key length is not 24 bytes");
        }
        for (int i = 0; i < 24; i++) {
            if (returnedKey[i] != validKey[i]) {
                throw new RuntimeException("Returned key content differs from input key");
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

    private static void testNullKey() {
        try {
            new DESedeKeySpec(null);
            throw new RuntimeException("Expected NullPointerException for null key");
        } catch (NullPointerException e) {
            // Expected
        } catch (InvalidKeyException e) {
            throw new RuntimeException("Did not expect InvalidKeyException for null key");
        }
    }
}
