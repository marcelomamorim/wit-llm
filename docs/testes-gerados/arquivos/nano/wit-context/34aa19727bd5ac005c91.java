/* @test    @summary Test for StringBuffer.charAt(int) method to verify behavior and exception handling    @run main StringBufferWitupTest */

import java.lang.StringBuffer;

public class StringBufferWitupTest {

    public static void main(String[] args) {
        testCharAtValidIndex();
        testCharAtNegativeIndex();
        testCharAtIndexEqualLength();
        testCharAtIndexGreaterThanLength();
    }

    private static void testCharAtValidIndex() {
        StringBuffer sb = new StringBuffer("hello");
        char result = sb.charAt(1);
        if (result != 'e') {
            throw new AssertionError("Expected 'e' at index 1, but got: " + result);
        }
    }

    private static void testCharAtNegativeIndex() {
        StringBuffer sb = new StringBuffer("test");
        boolean thrown = false;
        try {
            sb.charAt(-1);
        } catch (StringIndexOutOfBoundsException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected StringIndexOutOfBoundsException for negative index");
        }
    }

    private static void testCharAtIndexEqualLength() {
        StringBuffer sb = new StringBuffer("abc");
        boolean thrown = false;
        try {
            sb.charAt(3);
        } catch (StringIndexOutOfBoundsException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected StringIndexOutOfBoundsException for index equal to length");
        }
    }

    private static void testCharAtIndexGreaterThanLength() {
        StringBuffer sb = new StringBuffer("abc");
        boolean thrown = false;
        try {
            sb.charAt(10);
        } catch (StringIndexOutOfBoundsException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected StringIndexOutOfBoundsException for index greater than length");
        }
    }
}
