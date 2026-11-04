/*
 * @test
 * @summary Witup-derived checks for String.codePointCount(int, int) valid and IndexOutOfBoundsException paths
 * @run main StringWitupTest
 */

public class StringWitupTest {
    static int failures = 0;

    static void check(boolean condition, String message) {
        if (!condition) {
            failures++;
            System.err.println("FAIL: " + message);
        }
    }

    static void expectIndexOutOfBounds(String s, int beginIndex, int endIndex, String message) {
        try {
            int result = s.codePointCount(beginIndex, endIndex);
            check(false, message + " - no exception, returned " + result);
        } catch (IndexOutOfBoundsException expected) {
            // expected
        }
    }

    public static void main(String[] args) {
        String latin = "abc";
        check(latin.codePointCount(0, 3) == 3, "latin full range");
        check(latin.codePointCount(1, 1) == 0, "latin empty range");
        check(latin.codePointCount(1, 2) == 1, "latin single char");
        check("".codePointCount(0, 0) == 0, "empty string");

        String sup = "" + (char) 0xD83D + (char) 0xDE00;
        check(sup.codePointCount(0, 2) == 1, "surrogate pair counts as one code point");
        check(sup.codePointCount(0, 1) == 1, "lone high surrogate at start counts as one");

        String mixed = "a" + sup + "b";
        check(mixed.length() == 4, "mixed length precondition");
        check(mixed.codePointCount(0, 4) == 3, "mixed full range");
        check(mixed.codePointCount(1, 3) == 1, "mixed surrogate sub-range");

        String unpaired = "" + (char) 0xD83D + "a";
        check(unpaired.codePointCount(0, 2) == 2, "unpaired surrogate");

        expectIndexOutOfBounds(latin, -1, 1, "negative beginIndex");
        expectIndexOutOfBounds(latin, 2, 1, "beginIndex greater than endIndex");
        expectIndexOutOfBounds(latin, 0, 4, "endIndex greater than length");
        expectIndexOutOfBounds(latin, 4, 4, "beginIndex beyond length");
        expectIndexOutOfBounds(sup, 0, 3, "endIndex beyond UTF16 length");
        expectIndexOutOfBounds(mixed, -1, 4, "negative beginIndex on UTF16 string");

        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
