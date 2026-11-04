/*
 * @test
 * @summary Test DESKeySpec constructor with offset for correct behavior and exception
 * @run main DESKeySpecWitupTest
 */

import javax.crypto.spec.DESKeySpec;
import java.security.InvalidKeyException;

public class DESKeySpecWitupTest {
    public static void main(String[] args) throws Exception {
        testValidKeyAndOffset();
        testInvalidKeyLengthThrows();
    }

    private static void testValidKeyAndOffset() throws Exception {
        byte[] key = new byte[16];
        for (int i = 0; i < 16; i++) {
            key[i] = (byte) i;
        }
        // offset 4, so key.length - offset = 12 >= 8 (DES_KEY_LEN)
        DESKeySpec spec = new DESKeySpec(key, 4);
        byte[] extractedKey = spec.getKey();
        if (extractedKey.length != DESKeySpec.DES_KEY_LEN) {
            throw new RuntimeException("Extracted key length incorrect");
        }
        for (int i = 0; i < DESKeySpec.DES_KEY_LEN; i++) {
            if (extractedKey[i] != key[i + 4]) {
                throw new RuntimeException("Extracted key bytes do not match input key bytes at offset");
            }
        }
    }

    private static void testInvalidKeyLengthThrows() {
        byte[] key = new byte[10];
        int offset = 3;
        // key.length - offset = 7 < 8, should throw InvalidKeyException
        try {
            new DESKeySpec(key, offset);
            throw new RuntimeException("Expected InvalidKeyException not thrown");
        } catch (InvalidKeyException e) {
            // expected
        }
    }
}
