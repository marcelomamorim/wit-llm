/*
 * @test
 * @summary Test javax.crypto.spec.DESKeySpec constructor with byte[] key
 * @run main javax.crypto.spec.DESKeySpecWitupTest
 */

import java.security.InvalidKeyException;
import javax.crypto.spec.DESKeySpec;

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
            throw new RuntimeException("Expected InvalidKeyException for short key length");
        } catch (InvalidKeyException e) {
            // expected
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception type", e);
        }

        byte[] longKey = new byte[9];
        try {
            new DESKeySpec(longKey);
            throw new RuntimeException("Expected InvalidKeyException for long key length");
        } catch (InvalidKeyException e) {
            // expected
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception type", e);
        }

        byte[] emptyKey = new byte[0];
        try {
            new DESKeySpec(emptyKey);
            throw new RuntimeException("Expected InvalidKeyException for empty key");
        } catch (InvalidKeyException e) {
            // expected
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception type", e);
        }

        try {
            new DESKeySpec(null);
            throw new RuntimeException("Expected NullPointerException for null key");
        } catch (NullPointerException e) {
            // expected
        } catch (InvalidKeyException e) {
            // Not expected, but allowed if implementation throws InvalidKeyException instead of NPE
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception type", e);
        }
    }
}
