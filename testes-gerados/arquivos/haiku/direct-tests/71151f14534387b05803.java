/*
 * @test
 * @summary Behavioral tests for String(int[] codePoints, int offset, int count)
 * @run main StringWitupTest
 */

public class StringWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testBasicLatin() {
        int[] cps = {97, 98, 99};
        String s = new String(cps, 0, 3);
        check(s.equals("abc"), "basic latin full range");
        check(s.length() == 3, "basic latin length");
    }

    static void testOffsetAndCount() {
        int[] cps = {'x', 'a', 'b', 'y'};
        String s = new String(cps, 1, 2);
        check(s.equals("ab"), "offset and count selection");
    }

    static void testZeroCount() {
        int[] cps = {97, 98};
        String s = new String(cps, 1, 0);
        check(s.isEmpty(), "count zero must yield empty string");
        String t = new String(new int[0], 0, 0);
        check(t.isEmpty(), "empty array with zero count must yield empty string");
    }

    static void testSupplementaryCodePoint() {
        int cp = 0x1F600;
        int[] cps = {cp};
        String s = new String(cps, 0, 1);
        check(s.length() == 2, "supplementary code point must use two chars");
        check(s.codePointAt(0) == cp, "supplementary code point must round-trip");
        check(s.codePointCount(0, s.length()) == 1, "supplementary code point count must be one");
    }

    static void testMixedNonLatin() {
        int[] cps = {0x41, 0x3B1};
        String s = new String(cps, 0, 2);
        check(s.length() == 2, "mixed code points length");
        check(s.codePointAt(0) == 0x41, "first code point preserved");
        check(s.codePointAt(1) == 0x3B1, "non-latin code point preserved");
    }

    static void testNullArrayThrowsNPE() {
        boolean thrown = false;
        try {
            new String((int[]) null, 0, 0);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null codePoints must throw NullPointerException");
    }

    public static void main(String[] args) {
        testBasicLatin();
        testOffsetAndCount();
        testZeroCount();
        testSupplementaryCodePoint();
        testMixedNonLatin();
        testNullArrayThrowsNPE();
    }
}
