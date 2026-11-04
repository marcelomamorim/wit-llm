/*
 * @test
 * @summary Witup-style tests for Adler32.update(byte[], int, int)
 * @run main Adler32WitupTest
 */

import java.nio.charset.StandardCharsets;
import java.util.zip.Adler32;

public class Adler32WitupTest {

    public static void main(String[] args) {
        testNullArrayThrowsNPE();
        testNegativeOffsetThrowsAIOOBE();
        testNegativeLengthThrowsAIOOBE();
        testOffPlusLenBeyondArrayThrowsAIOOBE();
        testOffsetEqualLengthWithZeroLenThrowsAIOOBE();
        testValidRangeMatchesKnownChecksum();
        testOffsetWindowMatchesWholeArray();
        testZeroLengthAtEndDoesNotThrow();
        testEmptyUpdateKeepsInitialValue();
    }

    static byte[] wiki() {
        return "Wikipedia".getBytes(StandardCharsets.US_ASCII);
    }

    static void testNullArrayThrowsNPE() {
        Adler32 a = new Adler32();
        try {
            a.update(null, 0, 0);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null array");
    }

    static void testNegativeOffsetThrowsAIOOBE() {
        Adler32 a = new Adler32();
        byte[] b = wiki();
        try {
            a.update(b, -1, 1);
        } catch (ArrayIndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("expected ArrayIndexOutOfBoundsException for negative off");
    }

    static void testNegativeLengthThrowsAIOOBE() {
        Adler32 a = new Adler32();
        byte[] b = wiki();
        try {
            a.update(b, 0, -1);
        } catch (ArrayIndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("expected ArrayIndexOutOfBoundsException for negative len");
    }

    static void testOffPlusLenBeyondArrayThrowsAIOOBE() {
        Adler32 a = new Adler32();
        byte[] b = new byte[3];
        try {
            a.update(b, 1, 3);
        } catch (ArrayIndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("expected ArrayIndexOutOfBoundsException for off+len > length");
    }

    static void testOffsetEqualLengthWithZeroLenThrowsAIOOBE() {
        Adler32 a = new Adler32();
        byte[] b = new byte[3];
        try {
            a.update(b, 4, 0);
        } catch (ArrayIndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("expected ArrayIndexOutOfBoundsException for off > length");
    }

    static void testValidRangeMatchesKnownChecksum() {
        Adler32 a = new Adler32();
        byte[] b = wiki();
        a.update(b, 0, b.length);
        long expected = 0x11E60398L;
        if (a.getValue() != expected) {
            throw new RuntimeException("unexpected checksum: " + a.getValue());
        }
    }

    static void testOffsetWindowMatchesWholeArray() {
        byte[] w = wiki();
        byte[] padded = new byte[w.length + 2];
        System.arraycopy(w, 0, padded, 2, w.length);
        Adler32 a = new Adler32();
        a.update(padded, 2, w.length);
        Adler32 b = new Adler32();
        b.update(w, 0, w.length);
        if (a.getValue() != b.getValue()) {
            throw new RuntimeException("offset window checksum differs");
        }
    }

    static void testZeroLengthAtEndDoesNotThrow() {
        Adler32 a = new Adler32();
        byte[] b = new byte[3];
        a.update(b, 3, 0);
        if (a.getValue() != 1L) {
            throw new RuntimeException("zero-length update changed checksum");
        }
    }

    static void testEmptyUpdateKeepsInitialValue() {
        Adler32 a = new Adler32();
        a.update(new byte[0], 0, 0);
        if (a.getValue() != 1L) {
            throw new RuntimeException("expected initial value 1, got " + a.getValue());
        }
    }
}
