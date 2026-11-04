/*
 * @test
 * @summary Tests IvParameterSpec(byte[], int, int) validation and copy semantics
 * @run main IvParameterSpecWitupTest
 */

import java.util.Arrays;
import javax.crypto.spec.IvParameterSpec;

public class IvParameterSpecWitupTest {

    public static void main(String[] args) throws Exception {
        testNullIvThrowsIAE();
        testOffsetPlusLenTooLongThrowsIAE();
        testNegativeLenThrowsAIOOBE();
        testExactBoundaryAccepted();
        testCopiesRequestedRange();
        testSourceModificationDoesNotAffectSpec();
        testZeroLengthAtEnd();
    }

    static Throwable capture(Runnable r) {
        try {
            r.run();
            return null;
        } catch (Throwable t) {
            return t;
        }
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testNullIvThrowsIAE() {
        final byte[] iv = null;
        Throwable t = capture(() -> new IvParameterSpec(iv, 0, 0));
        check(t instanceof IllegalArgumentException, "null iv must throw IllegalArgumentException");
    }

    static void testOffsetPlusLenTooLongThrowsIAE() {
        final byte[] iv = new byte[4];
        Throwable t = capture(() -> new IvParameterSpec(iv, 2, 3));
        check(t instanceof IllegalArgumentException,
              "iv.length - offset < len must throw IllegalArgumentException");
    }

    static void testNegativeLenThrowsAIOOBE() {
        final byte[] iv = new byte[4];
        Throwable t = capture(() -> new IvParameterSpec(iv, 0, -1));
        check(t instanceof ArrayIndexOutOfBoundsException,
              "negative len must throw ArrayIndexOutOfBoundsException");
    }

    static void testExactBoundaryAccepted() {
        final byte[] iv = {10, 11, 12, 13};
        IvParameterSpec spec = new IvParameterSpec(iv, 1, 3);
        check(Arrays.equals(spec.getIV(), new byte[] {11, 12, 13}),
              "exact boundary offset/len must be accepted");
    }

    static void testCopiesRequestedRange() {
        final byte[] iv = {1, 2, 3, 4, 5, 6};
        IvParameterSpec spec = new IvParameterSpec(iv, 2, 2);
        check(Arrays.equals(spec.getIV(), new byte[] {3, 4}),
              "only the requested range must be copied");
    }

    static void testSourceModificationDoesNotAffectSpec() {
        final byte[] iv = {7, 8, 9};
        IvParameterSpec spec = new IvParameterSpec(iv, 0, 3);
        iv[0] = 99;
        check(Arrays.equals(spec.getIV(), new byte[] {7, 8, 9}),
              "spec must hold a defensive copy of the IV bytes");
    }

    static void testZeroLengthAtEnd() {
        final byte[] iv = {1, 2, 3};
        IvParameterSpec spec = new IvParameterSpec(iv, 3, 0);
        check(spec.getIV().length == 0, "zero length at end must yield empty IV");
    }
}
