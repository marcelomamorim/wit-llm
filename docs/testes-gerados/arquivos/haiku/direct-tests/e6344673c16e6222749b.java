/*
 * @test
 * @summary Verify String(char[], int, int) constructor: valid ranges, copy semantics, and invalid range/null handling
 * @run main StringWitupTest
 */

public class StringWitupTest {

    static int failures = 0;

    static void check(boolean condition, String message) {
        if (!condition) {
            failures++;
            System.err.println("FAILED: " + message);
        }
    }

    static void expectStringIndexOutOfBounds(char[] value, int offset, int count) {
        try {
            new String(value, offset, count);
            check(false, "expected StringIndexOutOfBoundsException for offset=" + offset + ", count=" + count);
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    public static void main(String[] args) {
        char[] data = {'a', 'b', 'c', 'd'};

        // Valid ranges
        check(new String(data, 0, 4).equals("abcd"), "full range");
        check(new String(data, 1, 2).equals("bc"), "middle range");
        check(new String(data, 3, 1).equals("d"), "last char");
        check(new String(data, 2, 0).isEmpty(), "zero count at middle");
        check(new String(data, 4, 0).isEmpty(), "zero count at end");

        // Copy semantics: later changes to the array must not affect the String
        String s = new String(data, 0, 4);
        data[0] = 'Z';
        check(s.equals("abcd"), "string must be independent of source array");
        check(new String(data, 0, 2).equals("Zb"), "new string reflects current array contents");
        data[0] = 'a';

        // Invalid ranges
        expectStringIndexOutOfBounds(data, -1, 2);
        expectStringIndexOutOfBounds(data, 0, -1);
        expectStringIndexOutOfBounds(data, 3, 2);
        expectStringIndexOutOfBounds(data, 5, 0);

        // Null array
        try {
            new String((char[]) null, 0, 0);
            check(false, "expected NullPointerException for null array");
        } catch (NullPointerException e) {
            // expected
        }

        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
