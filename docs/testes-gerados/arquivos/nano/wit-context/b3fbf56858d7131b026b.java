/* @test    @summary Test for DESedeKeySpec constructor with valid and invalid inputs    @run main DESedeKeySpecWitupTest */

import javax.crypto.spec.DESedeKeySpec;
import java.security.InvalidKeyException;

public class DESedeKeySpecWitupTest {

    public static void main(String[] args) {
        testValidKey();
        testNullKey();
        testShortKey();
    }

    static void testValidKey() {
        byte[] validKey = new byte[24];
        try {
            DESedeKeySpec spec = new DESedeKeySpec(validKey);
            byte[] returnedKey = spec.getKey();
            if (returnedKey.length != 24) {
                throw new AssertionError("Expected key length 24, got " + returnedKey.length);
            }
        } catch (InvalidKeyException e) {
            throw new AssertionError("Unexpected InvalidKeyException for valid key", e);
        }
    }

    static void testNullKey() {
        try {
            new DESedeKeySpec((byte[]) null);
            throw new AssertionError("Expected NullPointerException for null key");
        } catch (NullPointerException e) {
            // expected
        } catch (InvalidKeyException e) {
            throw new AssertionError("Unexpected InvalidKeyException for null key", e);
        }
    }

    static void testShortKey() {
        byte[] shortKey = new byte[23];
        try {
            new DESedeKeySpec(shortKey);
            throw new AssertionError("Expected InvalidKeyException for short key");
        } catch (InvalidKeyException e) {
            // expected
        }
    }
}
