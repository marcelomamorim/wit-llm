/*
 * @test
 * @summary Tests StringBuffer.codePointCount observable behavior
 * @run main StringBufferWitupTest
 */

public class StringBufferWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testBasicAscii() {
        StringBuffer sb = new StringBuffer("hello");
        check(sb.codePointCount(0, 5) == 5, "full range of ASCII should count 5");
        check(sb.codePointCount(1, 4) == 3, "sub-range 1..4 should count 3");
        check(sb.codePointCount(2, 2) == 0, "empty range should count 0");
    }

    static void testEmptyBuffer() {
        StringBuffer sb = new StringBuffer();
        check(sb.codePointCount(0, 0) == 0, "empty buffer with empty range should count 0");
    }

    static void testSurrogatePair() {
        StringBuffer sb = new StringBuffer("a\uD83D\uDE00b");
        check(sb.length() == 4, "buffer should have 4 chars");
        check(sb.codePointCount(0, 4) == 3, "surrogate pair should count as one code point");
        check(sb.codePointCount(1, 3) == 1, "range covering only the surrogate pair should count 1");
        check(sb.codePointCount(1, 2) == 1, "lone high surrogate should count as one code point");
    }

    static void testAfterAppend() {
        StringBuffer sb = new StringBuffer();
        sb.append("ab");
        sb.append("\uD83D\uDE00");
        check(sb.codePointCount(0, sb.length()) == 3, "count should reflect appended content");
    }

    static void testOutOfRangeThrows() {
        StringBuffer sb = new StringBuffer("abc");
        boolean thrown = false;
        try {
            sb.codePointCount(-1, 2);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "negative beginIndex should throw IndexOutOfBoundsException");

        thrown = false;
        try {
            sb.codePointCount(0, 4);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "endIndex beyond length should throw IndexOutOfBoundsException");

        thrown = false;
        try {
            sb.codePointCount(2, 1);
        } catch (IndexOutOfBoundsException e) {
            thrown = true;
        }
        check(thrown, "beginIndex greater than endIndex should throw IndexOutOfBoundsException");
    }

    public static void main(String[] args) {
        testBasicAscii();
        testEmptyBuffer();
        testSurrogatePair();
        testAfterAppend();
        testOutOfRangeThrows();
    }
}
