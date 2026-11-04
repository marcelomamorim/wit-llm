/* @test    @summary Test DESKeySpec constructor with valid key array and offset    @run main DESKeySpecWitupTest */

import javax.crypto.spec.DESKeySpec;
import java.security.InvalidKeyException;

public class DESKeySpecWitupTest {

    public static void main(String[] args) throws Exception {
        testValidKeyWithOffset();
        testInvalidKeySize();
    }

    private static void testValidKeyWithOffset() throws Exception {
        byte[] key = {
            0x01, 0x23, 0x45, 0x67, 0x89, 0xAB, 0xCD, 0xEF,
            0x10, 0x32, 0x54, 0x76, 0x98, 0xBA, 0xDC, 0xFE
        };
        int offset = 8;
        DESKeySpec spec = new DESKeySpec(key, offset);
        byte[] expected = {0x10, 0x32, 0x54, 0x76, 0x98, 0xBA, 0xDC, 0xFE};
        for (int i = 0; i < DESKeySpec.DES_KEY_LEN; i++) {
            if (spec.key[i] != expected[i]) {
                throw new AssertionError("Key bytes do not match expected values");
            }
        }
        System.out.println("testValidKeyWithOffset passed");
    }

    private static void testInvalidKeySize() {
        byte[] shortKey = {0x01, 0x23, 0x45};
        try {
            new DESKeySpec(shortKey, 0);
            throw new AssertionError("Expected InvalidKeyException for short key");
        } catch (InvalidKeyException e) {
            System.out.println("testInvalidKeySize passed");
        }
    }
}
