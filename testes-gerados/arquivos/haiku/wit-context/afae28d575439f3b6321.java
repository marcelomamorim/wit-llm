/*
 * @test
 * @summary Behavioral tests for String.offsetByCodePoints bounds checks and code point stepping
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

    static void expectIndexOutOfBounds(String s, int index, int offset, String message) {
        try {
            s.offsetByCodePoints(index, offset);
            check(false, message + " expected IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }
    }

    public static void main(String[] args) {
        String ascii = "abc";
        check(ascii.offsetByCodePoints(0, 0) == 0, "zero offset at start");
        check(ascii.offsetByCodePoints(0, 2) == 2, "forward by 2 in ascii");
        check(ascii.offsetByCodePoints(ascii.length(), 0) == ascii.length(), "index at length with zero offset");
        check(ascii.offsetByCodePoints(2, -1) == 1, "backward by 1 from index 2");

        String empty = "";
        check(empty.offsetByCodePoints(0, 0) == 0, "empty string zero offset");

        String surrogate = "a\uD83D\uDE00b";
        check(surrogate.offsetByCodePoints(0, 1) == 1, "ascii char before surrogate pair");
        check(surrogate.offsetByCodePoints(1, 1) == 3, "surrogate pair counts as one code point");
        check(surrogate.offsetByCodePoints(3, -1) == 1, "backward over surrogate pair");

        expectIndexOutOfBounds(ascii, -1, 0, "negative index");
        expectIndexOutOfBounds(ascii, ascii.length() + 1, 0, "index beyond length");
        expectIndexOutOfBounds(ascii, 0, 5, "code point offset beyond string end");
        expectIndexOutOfBounds(ascii, 1, -2, "code point offset before string start");

        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
