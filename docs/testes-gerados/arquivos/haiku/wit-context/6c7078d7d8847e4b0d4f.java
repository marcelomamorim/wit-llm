/*
 * @test
 * @summary Witup-generated checks for SecretKeySpec(byte[], int, int, String) exception paths and slice behavior
 * @run main SecretKeySpecWitupTest
 */

import java.util.Arrays;
import javax.crypto.spec.SecretKeySpec;

public class SecretKeySpecWitupTest {

    public static void main(String[] args) throws Exception {
        testMissingArguments();
        testEmptyKey();
        testInvalidOffsetLength();
        testNegativeLength();
        testValidSlice();
        testZeroLengthAtEnd();
        testKeyIsCopied();
    }

    static void testMissingArguments() {
        expectIAE(null, 0, 1, "AES");
        expectIAE(new byte[4], 0, 2, null);
    }

    static void testEmptyKey() {
        expectIAE(new byte[0], 0, 0, "AES");
    }

    static void testInvalidOffsetLength() {
        expectIAE(new byte[4], 2, 3, "AES");
    }

    static void testNegativeLength() {
        expectAIOOBE(new byte[4], 0, -1, "AES");
    }

    static void testValidSlice() {
        SecretKeySpec k = new SecretKeySpec(new byte[] {1, 2, 3, 4}, 1, 2, "AES");
        check(Arrays.equals(new byte[] {2, 3}, k.getEncoded()), "slice content");
        check("AES".equals(k.getAlgorithm()), "algorithm");
    }

    static void testZeroLengthAtEnd() {
        SecretKeySpec k = new SecretKeySpec(new byte[] {1, 2, 3, 4}, 4, 0, "AES");
        check(k.getEncoded().length == 0, "zero-length slice");
    }

    static void testKeyIsCopied() {
        byte[] src = {1, 2, 3, 4};
        SecretKeySpec k = new SecretKeySpec(src, 0, 4, "AES");
        src[0] = 9;
        check(k.getEncoded()[0] == 1, "key must be copied");
    }

    static void expectIAE(byte[] key, int off, int len, String alg) {
        try {
            new SecretKeySpec(key, off, len, alg);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("expected IllegalArgumentException");
    }

    static void expectAIOOBE(byte[] key, int off, int len, String alg) {
        try {
            new SecretKeySpec(key, off, len, alg);
        } catch (ArrayIndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("expected ArrayIndexOutOfBoundsException");
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException("Check failed: " + msg);
        }
    }
}
