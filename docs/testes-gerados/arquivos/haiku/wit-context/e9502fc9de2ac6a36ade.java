/*
 * @test
 * @summary Witup-derived tests for String.copyValueOf(char[], int, int) bounds checks and results
 * @run main StringWitupTest
 */

public class StringWitupTest {

    public static void main(String[] args) {
        char[] data = {'a', 'b', 'c', 'd'};

        check("bcd".equals(String.copyValueOf(data, 1, 3)), "valid slice returns bcd");
        check("".equals(String.copyValueOf(data, 0, 0)), "zero count at start returns empty");
        check("".equals(String.copyValueOf(data, 4, 0)), "zero count at end returns empty");
        check("abcd".equals(String.copyValueOf(data, 0, 4)), "full range returns full array");

        expectStringIndexOutOfBounds(data, -1, 2, "negative offset");
        expectStringIndexOutOfBounds(data, 0, -1, "negative count");
        expectStringIndexOutOfBounds(data, 2, 3, "offset plus count exceeds length");
        expectStringIndexOutOfBounds(data, 5, 0, "offset beyond length");
    }

    private static void expectStringIndexOutOfBounds(char[] data, int offset, int count, String label) {
        boolean thrown = false;
        try {
            String.copyValueOf(data, offset, count);
        } catch (StringIndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "expected StringIndexOutOfBoundsException for " + label);
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
