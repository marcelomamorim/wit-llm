/*
 * @test
 * @summary Witup-generated checks for the DESedeKeySpec(byte[]) constructor: key length validation, prefix copy, null handling and defensive copy.
 * @run main DESedeKeySpecWitupTest
 */

import java.security.InvalidKeyException;
import java.util.Arrays;
import javax.crypto.spec.DESedeKeySpec;

public class DESedeKeySpecWitupTest {

    public static void main(String[] args) throws Exception {
        testExactLengthKeyIsAccepted();
        testShortKeyThrowsInvalidKeyException();
        testEmptyKeyThrowsInvalidKeyException();
        testLongKeyUsesFirst24Bytes();
        testNullKeyThrowsNullPointerException();
        testDefensiveCopyOfInput();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static byte[] sequence(int length) {
        byte[] b = new byte[length];
        for (int i = 0; i < length; i++) {
            b[i] = (byte) (i + 1);
        }
        return b;
    }

    static void testExactLengthKeyIsAccepted() throws Exception {
        byte[] key = sequence(24);
        DESedeKeySpec spec = new DESedeKeySpec(key);
        check(Arrays.equals(key, spec.getKey()), "24-byte key must be returned unchanged");
    }

    static void testShortKeyThrowsInvalidKeyException() {
        try {
            new DESedeKeySpec(sequence(23));
            throw new RuntimeException("expected InvalidKeyException for 23-byte key");
        } catch (InvalidKeyException e) {
            // expected
        }
    }

    static void testEmptyKeyThrowsInvalidKeyException() {
        try {
            new DESedeKeySpec(new byte[0]);
            throw new RuntimeException("expected InvalidKeyException for empty key");
        } catch (InvalidKeyException e) {
            // expected
        }
    }

    static void testLongKeyUsesFirst24Bytes() throws Exception {
        byte[] key = sequence(48);
        DESedeKeySpec spec = new DESedeKeySpec(key);
        byte[] expected = Arrays.copyOf(key, 24);
        check(Arrays.equals(expected, spec.getKey()), "only the first 24 bytes must be used");
    }

    static void testNullKeyThrowsNullPointerException() {
        try {
            new DESedeKeySpec(null);
            throw new RuntimeException("expected NullPointerException for null key");
        } catch (NullPointerException e) {
            // expected
        } catch (InvalidKeyException e) {
            throw new RuntimeException("unexpected InvalidKeyException for null key", e);
        }
    }

    static void testDefensiveCopyOfInput() throws Exception {
        byte[] key = sequence(24);
        DESedeKeySpec spec = new DESedeKeySpec(key);
        byte original = key[0];
        key[0] = (byte) (original ^ 0x7F);
        check(spec.getKey()[0] == original, "constructor must copy key material");
    }
}
