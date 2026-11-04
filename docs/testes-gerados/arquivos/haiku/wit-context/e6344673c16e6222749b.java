/*
 * @test
 * @summary Validates String(char[], int, int) range handling for valid inputs, out-of-range offset/count, null arrays and int overflow of offset + count
 * @run main StringWitupTest
 */
public class StringWitupTest {
    private static int failures = 0;

    public static void main(String[] args) {
        testValidRanges();
        testInvalidRangesThrow();
        testNullArrayThrowsNPE();
        testOverflowOffsetCountThrows();
        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            failures++;
            System.err.println("FAIL: " + message);
        }
    }

    private static void testValidRanges() {
        char[] v = {'a', 'b', 'c'};
        check("abc".equals(new String(v, 0, 3)), "full range");
        check("bc".equals(new String(v, 1, 2)), "offset 1 count 2");
        check("".equals(new String(v, 0, 0)), "zero count at start");
        check("".equals(new String(v, 1, 0)), "zero count in middle");
        check("".equals(new String(v, 3, 0)), "zero count at end boundary");
    }

    private static void testInvalidRangesThrow() {
        char[] v = {'a', 'b', 'c'};
        expectSIOOBE(v, -1, 1, "negative offset");
        expectSIOOBE(v, 0, -1, "negative count");
        expectSIOOBE(v, 2, 2, "offset+count beyond length");
        expectSIOOBE(v, 4, 0, "offset beyond length");
        expectSIOOBE(v, 0, 4, "count beyond length");
    }

    private static void expectSIOOBE(char[] v, int offset, int count, String label) {
        try {
            String s = new String(v, offset, count);
            failures++;
            System.err.println("FAIL: expected StringIndexOutOfBoundsException for " + label + ", got " + s);
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testNullArrayThrowsNPE() {
        try {
            String s = new String((char[]) null, 0, 0);
            failures++;
            System.err.println("FAIL: expected NullPointerException for null array, got " + s);
        } catch (NullPointerException e) {
            // expected
        }
    }

    private static void testOverflowOffsetCountThrows() {
        char[] v = {'a', 'b', 'c'};
        expectSIOOBE(v, Integer.MAX_VALUE, 1, "offset+count overflow");
        expectSIOOBE(v, 1, Integer.MAX_VALUE, "count near MAX_VALUE with offset");
    }
}
