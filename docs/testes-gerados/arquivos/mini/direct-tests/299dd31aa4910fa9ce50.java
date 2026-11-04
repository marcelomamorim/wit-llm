/*
 * @test
 * @summary Test StringBuffer.codePointCount(int, int) method
 * @run main StringBufferWitupTest
 */

public class StringBufferWitupTest {
    public static void main(String[] args) {
        testBasicCodePointCount();
        testEmptyStringBuffer();
        testFullRangeCodePointCount();
        testSingleCodePoint();
    }

    private static void testBasicCodePointCount() {
        StringBuffer sb = new StringBuffer("hello");
        int count = sb.codePointCount(0, 5);
        if (count != 5) {
            throw new AssertionError("Expected 5 code points, got " + count);
        }
    }

    private static void testEmptyStringBuffer() {
        StringBuffer sb = new StringBuffer();
        int count = sb.codePointCount(0, 0);
        if (count != 0) {
            throw new AssertionError("Expected 0 code points for empty buffer, got " + count);
        }
    }

    private static void testFullRangeCodePointCount() {
        StringBuffer sb = new StringBuffer("\uD83D\uDE00abc"); // 😀 + abc
        int count = sb.codePointCount(0, sb.length());
        if (count != 4) { // 😀 counts as one code point + 3 chars
            throw new AssertionError("Expected 4 code points, got " + count);
        }
    }

    private static void testSingleCodePoint() {
        StringBuffer sb = new StringBuffer("\uD83D\uDE00"); // single surrogate pair
        int count = sb.codePointCount(0, sb.length());
        if (count != 1) {
            throw new AssertionError("Expected 1 code point for surrogate pair, got " + count);
        }
    }
}
