/* @test    @summary Test for StringBuffer.codePointCount with valid and invalid indices    @run main StringBufferWitupTest */

import java.lang.StringBuffer;

public class StringBufferWitupTest {

    public static void main(String[] args) {
        testCodePointCountValid();
        testCodePointCountBeginIndexGreaterThanEndIndex();
        testCodePointCountBeginIndexNegative();
        testCodePointCountEndIndexExceedsLength();
    }

    private static void testCodePointCountValid() {
        StringBuffer sb = new StringBuffer("Hello World");
        int count = sb.codePointCount(0, sb.length());
        if (count != sb.toString().codePointCount(0, sb.toString().length())) {
            throw new AssertionError("Valid indices test failed: expected " + sb.toString().codePointCount(0, sb.toString().length()) + " but got " + count);
        }
    }

    private static void testCodePointCountBeginIndexGreaterThanEndIndex() {
        StringBuffer sb = new StringBuffer("Test");
        try {
            sb.codePointCount(3, 2);
            throw new AssertionError("Expected IndexOutOfBoundsException for beginIndex > endIndex");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testCodePointCountBeginIndexNegative() {
        StringBuffer sb = new StringBuffer("Negative");
        try {
            sb.codePointCount(-1, 5);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative beginIndex");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testCodePointCountEndIndexExceedsLength() {
        StringBuffer sb = new StringBuffer("Exceed");
        try {
            sb.codePointCount(0, sb.length() + 1);
            throw new AssertionError("Expected IndexOutOfBoundsException for endIndex > length");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
