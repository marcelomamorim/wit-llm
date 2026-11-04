/*
 * @test
 * @summary Witup-derived checks for the IvParameterSpec(byte[], int, int) constructor validation and copy semantics
 * @run main IvParameterSpecWitupTest
 */

import java.util.Arrays;
import javax.crypto.spec.IvParameterSpec;

public class IvParameterSpecWitupTest {

    public static void main(String[] args) throws Exception {
        testNullIvRejected();
        testOffsetLengthTooLongRejected();
        testNegativeLengthRejected();
        testValidSlice();
        testZeroLengthAtEnd();
        testCopySemantics();
        testGetIVReturnsNewArray();
    }

    static void testNullIvRejected() {
        expectIAE(null, 0, 0);
    }

    static void testOffsetLengthTooLongRejected() {
        byte[] iv = {1, 2, 3, 4};
        expectIAE(iv, 2, 3);
    }

    static void testNegativeLengthRejected() {
        byte[] iv = {1, 2, 3, 4};
        expectAIOOBE(iv, 0, -1);
    }

    static void testValidSlice() {
        byte[] iv = {1, 2, 3, 4};
        IvParameterSpec spec = new IvParameterSpec(iv, 1, 2);
        check(Arrays.equals(new byte[] {2, 3}, spec.getIV()), "slice offset=1 len=2");
    }

    static void testZeroLengthAtEnd() {
        byte[] iv = {1, 2, 3, 4};
        IvParameterSpec spec = new IvParameterSpec(iv, iv.length, 0);
        check(spec.getIV().length == 0, "zero length at end");
    }

    static void testCopySemantics() {
        byte[] iv = {1, 2, 3, 4};
        IvParameterSpec spec = new IvParameterSpec(iv, 0, 4);
        iv[0] = 99;
        check(Arrays.equals(new byte[] {1, 2, 3, 4}, spec.getIV()), "constructor must copy input");
    }

    static void testGetIVReturnsNewArray() {
        byte[] iv = {5, 6, 7};
        IvParameterSpec spec = new IvParameterSpec(iv, 0, 3);
        byte[] first = spec.getIV();
        first[0] = 42;
        check(Arrays.equals(new byte[] {5, 6, 7}, spec.getIV()), "getIV must return a fresh copy");
    }

    static void expectIAE(byte[] iv, int offset, int len) {
        try {
            new IvParameterSpec(iv, offset, len);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("Expected IllegalArgumentException");
    }

    static void expectAIOOBE(byte[] iv, int offset, int len) {
        try {
            new IvParameterSpec(iv, offset, len);
        } catch (ArrayIndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("Expected ArrayIndexOutOfBoundsException");
    }

    static void check(boolean condition, String msg) {
        if (!condition) {
            throw new RuntimeException("Check failed: " + msg);
        }
    }
}
