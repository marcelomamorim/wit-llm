/* @test    @summary Test StringBuffer.charAt(int index) method for valid and invalid indices    @run main StringBufferWitupTest */

import java.lang.StringBuffer;

public class StringBufferWitupTest {

    public static void main(String[] args) {
        testCharAtValidIndex();
        testCharAtNegativeIndex();
        testCharAtIndexEqualToLength();
    }

    private static void testCharAtValidIndex() {
        StringBuffer sb = new StringBuffer("hello");
        char c = sb.charAt(1);
        if (c != 'e') {
            throw new AssertionError("Expected 'e' at index 1, but got: " + c);
        }
    }

    private static void testCharAtNegativeIndex() {
        StringBuffer sb = new StringBuffer("world");
        try {
            sb.charAt(-1);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative index");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testCharAtIndexEqualToLength() {
        StringBuffer sb = new StringBuffer("test");
        try {
            sb.charAt(sb.length());
            throw new AssertionError("Expected IndexOutOfBoundsException for index equal to length");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
