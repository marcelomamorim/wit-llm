/* @test    @summary Test for StringBuffer.codePointAt(int index) method, including boundary conditions and exception handling.    @run main StringBufferWitupTest */

import java.lang.StringBuffer;

public class StringBufferWitupTest {

    public static void main(String[] args) {
        testCodePointAtValidIndex();
        testCodePointAtNegativeIndex();
        testCodePointAtIndexEqualToCount();
        testCodePointAtIndexGreaterThanCount();
        testCodePointAtIndexLessThanZero();
    }

    private static void testCodePointAtValidIndex() {
        StringBuffer sb = new StringBuffer("abc");
        int index = 1;
        int result = sb.codePointAt(index);
        int expected = (int) 'b';
        if (result != expected) {
            throw new AssertionError("Expected codePointAt(" + index + ") to be " + expected + " but got " + result);
        }
    }

    private static void testCodePointAtNegativeIndex() {
        StringBuffer sb = new StringBuffer("abc");
        try {
            sb.codePointAt(-1);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for negative index");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testCodePointAtIndexEqualToCount() {
        StringBuffer sb = new StringBuffer("abc");
        int index = sb.length(); // 3
        try {
            sb.codePointAt(index);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for index equal to length");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testCodePointAtIndexGreaterThanCount() {
        StringBuffer sb = new StringBuffer("abc");
        int index = sb.length() + 1; // 4
        try {
            sb.codePointAt(index);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for index greater than length");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testCodePointAtIndexLessThanZero() {
        StringBuffer sb = new StringBuffer("abc");
        int index = -5;
        try {
            sb.codePointAt(index);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for index less than zero");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }
}
