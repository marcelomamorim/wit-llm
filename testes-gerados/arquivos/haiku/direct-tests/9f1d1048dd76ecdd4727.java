/*
 * @test
 * @summary White-box-free behavioral tests for String.repeat(int) covering negative count, zero, one, empty receiver, Latin1 and UTF16 paths, multi-copy loop and overflow guard
 * @run main StringWitupTest
 */
public class StringWitupTest {

    public static void main(String[] args) {
        testNegativeCountThrows();
        testZeroCountReturnsEmpty();
        testOneCountReturnsSameInstance();
        testEmptyReceiver();
        testSingleCharLatin1();
        testMultiCharLatin1();
        testUtf16Receiver();
        testVariousCounts();
        testLargeResultLength();
        testOverflowThrowsOutOfMemoryError();
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static String expected(String s, int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append(s);
        }
        return sb.toString();
    }

    static void testNegativeCountThrows() {
        try {
            "abc".repeat(-1);
            check(false, "expected IllegalArgumentException for -1");
        } catch (IllegalArgumentException e) {
            check(e.getMessage().contains("-1"), "message should contain count");
        }
        try {
            "".repeat(Integer.MIN_VALUE);
            check(false, "expected IllegalArgumentException for MIN_VALUE");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testZeroCountReturnsEmpty() {
        check("abc".repeat(0).isEmpty(), "abc.repeat(0) should be empty");
        check("a".repeat(0).equals(""), "a.repeat(0) should equal empty string");
    }

    static void testOneCountReturnsSameInstance() {
        String s = "hello";
        check(s.repeat(1) == s, "repeat(1) should return this");
    }

    static void testEmptyReceiver() {
        check("".repeat(5).equals(""), "empty.repeat(5) should be empty");
        check("".repeat(1000).isEmpty(), "empty.repeat(1000) should be empty");
    }

    static void testSingleCharLatin1() {
        check("a".repeat(4).equals("aaaa"), "a.repeat(4) mismatch");
        check("z".repeat(2).equals("zz"), "z.repeat(2) mismatch");
    }

    static void testMultiCharLatin1() {
        check("ab".repeat(3).equals("ababab"), "ab.repeat(3) mismatch");
        check("xyz".repeat(2).equals("xyzxyz"), "xyz.repeat(2) mismatch");
    }

    static void testUtf16Receiver() {
        String s = "\u00e9\u4e2d";
        check(s.repeat(3).equals(expected(s, 3)), "UTF16 repeat(3) mismatch");
        check(s.repeat(2).length() == 4, "UTF16 repeat(2) length mismatch");
        String single = "\u4e2d";
        check(single.repeat(5).equals(expected(single, 5)), "single UTF16 char repeat mismatch");
    }

    static void testVariousCounts() {
        String[] inputs = {"abc", "xy", "q"};
        for (String in : inputs) {
            for (int n = 0; n <= 20; n++) {
                check(in.repeat(n).equals(expected(in, n)),
                      "mismatch for " + in + " x " + n);
            }
        }
    }

    static void testLargeResultLength() {
        check("abc".repeat(1000).length() == 3000, "abc.repeat(1000) length mismatch");
    }

    static void testOverflowThrowsOutOfMemoryError() {
        try {
            "ab".repeat(Integer.MAX_VALUE);
            check(false, "expected OutOfMemoryError for overflowing size");
        } catch (OutOfMemoryError e) {
            // expected: Integer.MAX_VALUE / count < len
        }
    }
}
