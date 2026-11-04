/*
 * @test
 * @summary Test StringBuffer.codePointCount for various valid and invalid inputs
 * @run main StringBufferWitupTest
 */

import java.lang.StringBuffer;

public class StringBufferWitupTest {

    public static void main(String[] args) {
        testCodePointCountValidRange();
        testCodePointCountEmptyBuffer();
        testCodePointCountInvalidIndices();
    }

    private static void testCodePointCountValidRange() {
        StringBuffer sb = new StringBuffer("abc\uD83D\uDE00"); // "abc😀"
        int count = sb.codePointCount(0, sb.length());
        // "abc😀" has 5 code points: 'a','b','c', and '😀'
        if (count != 5) {
            throw new AssertionError("Expected 5 code points, but got " + count);
        }
    }

    private static void testCodePointCountEmptyBuffer() {
        StringBuffer sb = new StringBuffer();
        int count = sb.codePointCount(0, 0);
        if (count != 0) {
            throw new AssertionError("Expected 0 code points in empty buffer, but got " + count);
        }
    }

    private static void testCodePointCountInvalidIndices() {
        StringBuffer sb = new StringBuffer("test");
        try {
            sb.codePointCount(-1, 2);
            throw new AssertionError("Expected IndexOutOfBoundsException for beginIndex -1");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
        try {
            sb.codePointCount(0, 10);
            throw new AssertionError("Expected IndexOutOfBoundsException for endIndex 10");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
        try {
            sb.codePointCount(3, 2);
            throw new AssertionError("Expected IndexOutOfBoundsException for beginIndex > endIndex");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
