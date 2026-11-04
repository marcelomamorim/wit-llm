/*
 * @test
 * @summary Tests the single-argument DESedeKeySpec constructor: key copying, length validation, null handling and getKey behavior
 * @run main DESedeKeySpecWitupTest
 */

import java.security.InvalidKeyException;
import java.util.Arrays;
import javax.crypto.spec.DESedeKeySpec;

public class DESedeKeySpecWitupTest {

    public static void main(String[] args) throws Exception {
        testExactLengthKeyIsCopied();
        testLongerKeyUsesFirst24Bytes();
        testShortKeyThrowsInvalidKeyException();
        testNullKeyThrowsNullPointerException();
        testInputModificationDoesNotAffectSpec();
        testGetKeyReturnsNewArray();
    }

    private static void testExactLengthKeyIsCopied() throws Exception {
        byte[] key = new byte[DESedeKeySpec.DES_EDE_KEY_LEN];
        for (int i = 0; i < key.length; i++) {
            key[i] = (byte) i;
        }
        DESedeKeySpec spec = new DESedeKeySpec(key);
        check(Arrays.equals(key, spec.getKey()), "24-byte key should be returned unchanged");
    }

    private static void testLongerKeyUsesFirst24Bytes() throws Exception {
        byte[] key = new byte[30];
        for (int i = 0; i < key.length; i++) {
            key[i] = (byte) (i + 1);
        }
        DESedeKeySpec spec = new DESedeKeySpec(key);
        byte[] expected = Arrays.copyOf(key, 24);
        check(Arrays.equals(expected, spec.getKey()),
              "Only the first 24 bytes should be used for a longer key");
    }

    private static void testShortKeyThrowsInvalidKeyException() {
        byte[] key = new byte[23];
        try {
            new DESedeKeySpec(key);
            throw new RuntimeException("Expected InvalidKeyException for 23-byte key");
        } catch (InvalidKeyException e) {
            // expected
        }
    }

    private static void testNullKeyThrowsNullPointerException() {
        try {
            new DESedeKeySpec(null);
            throw new RuntimeException("Expected NullPointerException for null key");
        } catch (NullPointerException e) {
            // expected
        } catch (InvalidKeyException e) {
            throw new RuntimeException("Unexpected InvalidKeyException for null key", e);
        }
    }

    private static void testInputModificationDoesNotAffectSpec() throws Exception {
        byte[] key = new byte[24];
        Arrays.fill(key, (byte) 0x11);
        DESedeKeySpec spec = new DESedeKeySpec(key);
        key[0] = (byte) 0x22;
        check(spec.getKey()[0] == (byte) 0x11,
              "Modifying the input array after construction must not change the spec");
    }

    private static void testGetKeyReturnsNewArray() throws Exception {
        byte[] key = new byte[24];
        Arrays.fill(key, (byte) 0x33);
        DESedeKeySpec spec = new DESedeKeySpec(key);
        byte[] first = spec.getKey();
        first[0] = (byte) 0x44;
        byte[] second = spec.getKey();
        check(first != second, "getKey should return a new array each call");
        check(second[0] == (byte) 0x33,
              "Mutating a returned key array must not change the spec");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
