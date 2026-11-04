/*
 * @test
 * @summary Witup-generated tests for Adler32.update(byte[], int, int) exception paths and basic checksum behavior
 * @run main Adler32WitupTest
 */

import java.util.zip.Adler32;

public class Adler32WitupTest {

    static int failures = 0;

    static void check(boolean cond, String msg) {
        if (!cond) {
            failures++;
            System.err.println("FAILED: " + msg);
        }
    }

    static void testNullArrayThrowsNPE() {
        Adler32 a = new Adler32();
        try {
            a.update(null, 0, 0);
            check(false, "expected NullPointerException for null array");
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testNegativeOffsetThrowsAIOOBE() {
        Adler32 a = new Adler32();
        byte[] b = new byte[4];
        try {
            a.update(b, -1, 1);
            check(false, "expected ArrayIndexOutOfBoundsException for negative off");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testNegativeLengthThrowsAIOOBE() {
        Adler32 a = new Adler32();
        byte[] b = new byte[4];
        try {
            a.update(b, 0, -1);
            check(false, "expected ArrayIndexOutOfBoundsException for negative len");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testOffsetPlusLengthOverflowThrowsAIOOBE() {
        Adler32 a = new Adler32();
        byte[] b = new byte[4];
        try {
            a.update(b, 2, 3);
            check(false, "expected ArrayIndexOutOfBoundsException when off+len exceeds length");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testValidRangeDoesNotThrowAndMatchesReference() {
        byte[] data = {'a', 'b', 'c', 'd'};
        Adler32 whole = new Adler32();
        whole.update(data, 0, data.length);
        Adler32 byByte = new Adler32();
        for (byte x : data) {
            byByte.update(x);
        }
        check(whole.getValue() == byByte.getValue(),
              "range update should equal byte-by-byte update");
    }

    static void testEmptyRangeAtEndIsAccepted() {
        byte[] b = new byte[4];
        Adler32 a = new Adler32();
        a.update(b, 4, 0);
        check(a.getValue() == 1L, "empty update at end should keep initial checksum 1");
    }

    static void testFailedCallDoesNotChangeChecksum() {
        byte[] b = {1, 2, 3};
        Adler32 a = new Adler32();
        a.update(b, 0, b.length);
        long before = a.getValue();
        try {
            a.update(b, 2, 5);
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
        check(a.getValue() == before, "rejected update must not alter checksum");
    }

    public static void main(String[] args) {
        testNullArrayThrowsNPE();
        testNegativeOffsetThrowsAIOOBE();
        testNegativeLengthThrowsAIOOBE();
        testOffsetPlusLengthOverflowThrowsAIOOBE();
        testValidRangeDoesNotThrowAndMatchesReference();
        testEmptyRangeAtEndIsAccepted();
        testFailedCallDoesNotChangeChecksum();
        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
