/* @test    @summary Test StringBuffer.codePointAt(int index) method for correctness and exception handling    @run main StringBufferWitupTest */

import java.lang.StringBuffer;

public class StringBufferWitupTest {

    public static void main(String[] args) {
        testCodePointAtValidIndex();
        testCodePointAtNegativeIndex();
        testCodePointAtIndexEqualToLength();
        testCodePointAtIndexGreaterThanLength();
    }

    private static void testCodePointAtValidIndex() {
        StringBuffer sb = new StringBuffer("abc");
        int index = 1;
        int expected = sb.charAt(index);
        int result = sb.codePointAt(index);
        if (result != expected) {
            throw new AssertionError("Expected: " + expected + ", but got: " + result);
        }
    }

    private static void testCodePointAtNegativeIndex() {
        StringBuffer sb = new StringBuffer("abc");
        try {
            sb.codePointAt(-1);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative index");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testCodePointAtIndexEqualToLength() {
        StringBuffer sb = new StringBuffer("abc");
        try {
            sb.codePointAt(sb.length());
            throw new AssertionError("Expected IndexOutOfBoundsException for index equal to length");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testCodePointAtIndexGreaterThanLength() {
        StringBuffer sb = new StringBuffer("abc");
        try {
            sb.codePointAt(sb.length() + 1);
            throw new AssertionError("Expected IndexOutOfBoundsException for index greater than length");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
