/*
 * @test
 * @summary Test javax.crypto.spec.DESKeySpec constructor with byte[] key
 * @run main DESKeySpecWitupTest
 */

import javax.crypto.spec.DESKeySpec;
import java.security.InvalidKeyException;

public class DESKeySpecWitupTest {
    public static void main(String[] args) throws Exception {
        testValidKeyLength();
        testInvalidKeyLength();
    }

    private static void testValidKeyLength() throws Exception {
        byte[] validKey = new byte[8];
        for (int i = 0; i < validKey.length; i++) {
            validKey[i] = (byte) i;
        }
        DESKeySpec spec = new DESKeySpec(validKey);
        if (spec == null) {
            throw new Exception("DESKeySpec constructor returned null for valid key length");
        }
    }

    private static void testInvalidKeyLength() {
        byte[] shortKey = new byte[7];
        try {
            new DESKeySpec(shortKey);
            throw new RuntimeException("Expected InvalidKeyException for key length < 8");
        } catch (InvalidKeyException e) {
            // Expected exception
        }
    }
}
