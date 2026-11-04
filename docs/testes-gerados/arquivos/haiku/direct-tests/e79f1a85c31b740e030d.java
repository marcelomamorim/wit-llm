/*
 * @test
 * @summary Verifies DESedeKeySpec(byte[] key, int offset) copies 24 bytes from the offset, rejects short key material, and rejects null keys
 * @run main DESedeKeySpecWitupTest
 */

import java.security.InvalidKeyException;
import java.util.Arrays;
import javax.crypto.spec.DESedeKeySpec;

public class DESedeKeySpecWitupTest {

    public static void main(String[] args) throws Exception {
        testFirst24BytesWithOffsetZero();
        testOffsetCopiesCorrectRange();
        testBoundaryOffsetWithExactly24Bytes();
        testShortKeyRejected();
        testOffsetLeavesTooFewBytesRejected();
        testNullKeyThrowsNullPointerException();
        testKeyIsCopiedFromInput();
        testGetKeyReturnsNewArrayEachTime();
    }

    static byte[] seq(int n) {
        byte[] b = new byte[n];
        for (int i = 0; i < n; i++) {
            b[i] = (byte) (i + 1);
        }
        return b;
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void expectInvalidKey(byte[] key, int offset) {
        try {
            new DESedeKeySpec(key, offset);
        } catch (InvalidKeyException e) {
            return;
        }
        throw new RuntimeException("Expected InvalidKeyException for offset " + offset);
    }

    static void testFirst24BytesWithOffsetZero() throws Exception {
        byte[] k = seq(24);
        DESedeKeySpec s = new DESedeKeySpec(k, 0);
        byte[] out = s.getKey();
        check(out.length == DESedeKeySpec.DES_EDE_KEY_LEN, "key length must be 24");
        check(Arrays.equals(k, out), "key bytes must match input");
    }

    static void testOffsetCopiesCorrectRange() throws Exception {
        byte[] k = seq(30);
        DESedeKeySpec s = new DESedeKeySpec(k, 6);
        byte[] expected = Arrays.copyOfRange(k, 6, 30);
        check(Arrays.equals(expected, s.getKey()), "key must be bytes [6..29]");
    }

    static void testBoundaryOffsetWithExactly24Bytes() throws Exception {
        byte[] k = seq(25);
        DESedeKeySpec s = new DESedeKeySpec(k, 1);
        byte[] expected = Arrays.copyOfRange(k, 1, 25);
        check(Arrays.equals(expected, s.getKey()), "key must be bytes [1..24]");
    }

    static void testShortKeyRejected() {
        expectInvalidKey(seq(23), 0);
    }

    static void testOffsetLeavesTooFewBytesRejected() {
        expectInvalidKey(seq(25), 2);
    }

    static void testNullKeyThrowsNullPointerException() {
        try {
            new DESedeKeySpec(null);
        } catch (NullPointerException e) {
            return;
        } catch (InvalidKeyException e) {
            throw new RuntimeException("Expected NullPointerException, got InvalidKeyException");
        }
        throw new RuntimeException("Expected NullPointerException for null key");
    }

    static void testKeyIsCopiedFromInput() throws Exception {
        byte[] k = seq(24);
        DESedeKeySpec s = new DESedeKeySpec(k, 0);
        k[0] = 99;
        check(s.getKey()[0] == 1, "modifying input after construction must not change key");
    }

    static void testGetKeyReturnsNewArrayEachTime() throws Exception {
        DESedeKeySpec s = new DESedeKeySpec(seq(24), 0);
        byte[] a = s.getKey();
        byte[] b = s.getKey();
        check(a != b, "getKey must return a new array each call");
        check(Arrays.equals(a, b), "returned arrays must have equal contents");
    }
}
