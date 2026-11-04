/* @test    @summary Test setCharAt method of StringBuffer for valid and invalid indices    @run main StringBufferWitupTest */

import java.lang.StringBuffer;

public class StringBufferWitupTest {

    public static void main(String[] args) {
        testSetCharAtValidIndex();
        testSetCharAtNegativeIndex();
        testSetCharAtIndexEqualToLength();
    }

    private static void testSetCharAtValidIndex() {
        StringBuffer sb = new StringBuffer("hello");
        sb.setCharAt(1, 'a');
        String result = sb.toString();
        if (!"hallo".equals(result)) {
            throw new AssertionError("Expected 'hallo', but got: " + result);
        }
    }

    private static void testSetCharAtNegativeIndex() {
        StringBuffer sb = new StringBuffer("test");
        try {
            sb.setCharAt(-1, 'x');
            throw new AssertionError("Expected IndexOutOfBoundsException for negative index");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testSetCharAtIndexEqualToLength() {
        StringBuffer sb = new StringBuffer("abc");
        try {
            sb.setCharAt(3, 'z');
            throw new AssertionError("Expected IndexOutOfBoundsException for index equal to length");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
