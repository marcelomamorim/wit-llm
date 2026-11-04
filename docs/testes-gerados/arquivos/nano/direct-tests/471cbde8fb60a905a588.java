/* @test    @summary Test StringBuffer.codePointBefore(int index) method for correct behavior and exception handling.    @run main StringBufferWitupTest */

import java.lang.StringBuffer;

public class StringBufferWitupTest {

    public static void main(String[] args) {
        testCodePointBeforeValidIndex();
        testCodePointBeforeIndexZero();
        testCodePointBeforeNegativeIndex();
        testCodePointBeforeIndexGreaterThanLength();
    }

    private static void testCodePointBeforeValidIndex() {
        StringBuffer sb = new StringBuffer("abc");
        int index = 2; // position after 'b'
        int result = sb.codePointBefore(index);
        int expected = 'b';
        if (result != expected) {
            throw new AssertionError("Expected " + expected + " but got " + result);
        }
    }

    private static void testCodePointBeforeIndexZero() {
        StringBuffer sb = new StringBuffer("abc");
        try {
            sb.codePointBefore(0);
            throw new AssertionError("Expected IndexOutOfBoundsException for index 0");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testCodePointBeforeNegativeIndex() {
        StringBuffer sb = new StringBuffer("abc");
        try {
            sb.codePointBefore(-1);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative index");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testCodePointBeforeIndexGreaterThanLength() {
        StringBuffer sb = new StringBuffer("abc");
        int index = sb.length() + 1;
        try {
            sb.codePointBefore(index);
            throw new AssertionError("Expected IndexOutOfBoundsException for index > length");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
