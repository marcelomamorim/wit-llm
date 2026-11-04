/*
 * @test
 * @summary Test DESKeySpec constructor with byte array and offset
 * @run main javax.crypto.spec.DESKeySpecWitupTest
 */

import java.security.InvalidKeyException;
import javax.crypto.spec.DESKeySpec;

public class DESKeySpecWitupTest {
    public static void main(String[] args) throws Exception {
        testValidKeyAndOffset();
        testInvalidKeyTooShort();
        testInvalidKeyExactLengthAtOffset();
    }

    private static void testValidKeyAndOffset() throws Exception {
        byte[] key = new byte[16];
        for (int i = 0; i < 16; i++) {
            key[i] = (byte) i;
        }
        // offset 4, enough bytes from offset (16 - 4 = 12 >= 8)
        DESKeySpec spec = new DESKeySpec(key, 4);
        byte[] extracted = spec.getKey();
        for (int i = 0; i < 8; i++) {
            if (extracted[i] != key[4 + i]) {
                throw new RuntimeException("Key bytes do not match at index " + i);
            }
        }
    }

    private static void testInvalidKeyTooShort() {
        byte[] key = new byte[10];
        try {
            new DESKeySpec(key, 3); // 10 - 3 = 7 < 8, should throw
            throw new RuntimeException("Expected InvalidKeyException not thrown");
        } catch (InvalidKeyException e) {
            // expected
        }
    }

    private static void testInvalidKeyExactLengthAtOffset() throws Exception {
        byte[] key = new byte[8];
        for (int i = 0; i < 8; i++) {
            key[i] = (byte) (i + 1);
        }
        // offset 0, length exactly 8, should succeed
        DESKeySpec spec = new DESKeySpec(key, 0);
        byte[] extracted = spec.getKey();
        for (int i = 0; i < 8; i++) {
            if (extracted[i] != key[i]) {
                throw new RuntimeException("Key bytes do not match at index " + i);
            }
        }
    }
}
