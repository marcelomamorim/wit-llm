/* @test    @summary Test for StringBuffer.setLength(int) method behavior and exception paths    @run main StringBufferWitupTest */

import java.lang.StringBuffer;

public class StringBufferWitupTest {

    public static void main(String[] args) {
        testSetLengthNormal();
        testSetLengthNegative();
        testSetLengthExceeding();
    }

    private static void testSetLengthNormal() {
        StringBuffer sb = new StringBuffer("hello");
        sb.setLength(3);
        String result = sb.toString();
        if (!"hel".equals(result)) {
            throw new AssertionError("Expected 'hel', but got: " + result);
        }
    }

    private static void testSetLengthNegative() {
        StringBuffer sb = new StringBuffer("test");
        try {
            sb.setLength(-1);
            throw new AssertionError("Expected IllegalArgumentException for negative length");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testSetLengthExceeding() {
        StringBuffer sb = new StringBuffer("abc");
        sb.setLength(10);
        String result = sb.toString();
        if (result.length() != 10) {
            throw new AssertionError("Expected length 10, but got: " + result.length());
        }
        for (int i = 3; i < 10; i++) {
            if (result.charAt(i) != '\u0000') {
                throw new AssertionError("Expected null character at position " + i);
            }
        }
    }
}
