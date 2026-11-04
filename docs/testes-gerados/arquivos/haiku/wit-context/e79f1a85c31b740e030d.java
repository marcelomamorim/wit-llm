/*
 * @test
 * @summary Witup-generated tests for DESedeKeySpec(byte[], int) constructor and key copying
 * @run main DESedeKeySpecWitupTest
 */

import java.security.InvalidKeyException;
import java.util.Arrays;
import javax.crypto.spec.DESedeKeySpec;

public class DESedeKeySpecWitupTest {

    public static void main(String[] args) throws Exception {
        testExactLengthKeyAtOffsetZero();
        testKeyAtNonZeroOffsetCopiesRightBytes();
        testShortKeyThrowsInvalidKeyException();
        testOffsetLeavingFewerThan24BytesThrows();
        testOffsetBeyondArrayLengthThrows();
        testNullKeyThrowsNullPointerException();
        testGetKeyReturnsIndependentCopy();
    }

    static void testExactLengthKeyAtOffsetZero() throws Exception {
        byte[] key = sequence(24, 1);
        DESedeKeySpec spec = new DESedeKeySpec(key, 0);
        check(Arrays.equals(key, spec.getKey()), "exact 24-byte key at offset 0 must be preserved");
    }

    static void testKeyAtNonZeroOffsetCopiesRightBytes() throws Exception {
        byte[] key = sequence(30, 10);
        DESedeKeySpec spec = new DESedeKeySpec(key, 6);
        byte[] expected = Arrays.copyOfRange(key, 6, 30);
        check(Arrays.equals(expected, spec.getKey()), "key must start at the given offset and have 24 bytes");
    }

    static void testShortKeyThrowsInvalidKeyException() {
        byte[] key = sequence(23, 1);
        expectInvalidKey(key, 0);
    }

    static void testOffsetLeavingFewerThan24BytesThrows() {
        byte[] key = sequence(30, 1);
        expectInvalidKey(key, 7);
    }

    static void testOffsetBeyondArrayLengthThrows() {
        byte[] key = sequence(10, 1);
        expectInvalidKey(key, 20);
    }

    static void testNullKeyThrowsNullPointerException() {
        try {
            new DESedeKeySpec(null, 0);
        } catch (NullPointerException e) {
            return;
        } catch (InvalidKeyException e) {
            throw new RuntimeException("null key must not produce InvalidKeyException", e);
        }
        throw new RuntimeException("expected NullPointerException for null key");
    }

    static void testGetKeyReturnsIndependentCopy() throws Exception {
        byte[] key = sequence(24, 5);
        DESedeKeySpec spec = new DESedeKeySpec(key, 0);
        byte[] first = spec.getKey();
        first[0] = (byte) 0x7F;
        byte[] second = spec.getKey();
        check(second[0] == key[0], "modifying returned key must not change the spec");
        check(first != second, "getKey must return a new array on each call");
    }

    static void expectInvalidKey(byte[] key, int offset) {
        try {
            new DESedeKeySpec(key, offset);
        } catch (InvalidKeyException e) {
            return;
        }
        throw new RuntimeException("expected InvalidKeyException for length=" + key.length + " offset=" + offset);
    }

    static byte[] sequence(int length, int start) {
        byte[] b = new byte[length];
        for (int i = 0; i < length; i++) {
            b[i] = (byte) (start + i);
        }
        return b;
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
