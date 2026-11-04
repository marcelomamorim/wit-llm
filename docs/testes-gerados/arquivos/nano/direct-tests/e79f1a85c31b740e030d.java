/* @test    @summary Test DESedeKeySpec constructor with valid and invalid inputs    @run main DESedeKeySpecWitupTest */

import javax.crypto.spec.DESedeKeySpec;
import java.security.InvalidKeyException;

public class DESedeKeySpecWitupTest {

    public static void main(String[] args) {
        testValidKey();
        testShortKey();
        testValidKeyWithOffset();
        testShortKeyWithOffset();
    }

    static void testValidKey() {
        byte[] keyBytes = new byte[24];
        for (int i = 0; i < 24; i++) {
            keyBytes[i] = (byte) i;
        }
        try {
            DESedeKeySpec spec = new DESedeKeySpec(keyBytes);
            byte[] result = spec.getKey();
            assert result.length == 24 : "Expected key length 24";
            for (int i = 0; i < 24; i++) {
                assert result[i] == (byte) i : "Mismatch at byte " + i;
            }
        } catch (InvalidKeyException e) {
            throw new AssertionError("Unexpected InvalidKeyException in testValidKey", e);
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

    static void testValidKeyWithOffset() {
        byte[] keyBytes = new byte[30];
        for (int i = 0; i < 30; i++) {
            keyBytes[i] = (byte) (i + 1);
        }
        int offset = 3;
        try {
            DESedeKeySpec spec = new DESedeKeySpec(keyBytes, offset);
            byte[] result = spec.getKey();
            assert result.length == 24 : "Expected key length 24";
            for (int i = 0; i < 24; i++) {
                assert result[i] == (byte) (i + offset) : "Mismatch at byte " + i;
            }
        } catch (InvalidKeyException e) {
            throw new AssertionError("Unexpected InvalidKeyException in testValidKeyWithOffset", e);
        }
    }

    static void testShortKeyWithOffset() {
        byte[] keyBytes = new byte[25];
        for (int i = 0; i < 25; i++) {
            keyBytes[i] = (byte) (i + 1);
        }
        int offset = 2;
        try {
            new DESedeKeySpec(keyBytes, offset);
            throw new AssertionError("Expected InvalidKeyException for short key with offset");
        } catch (InvalidKeyException e) {
            // expected
        }
    }
}
