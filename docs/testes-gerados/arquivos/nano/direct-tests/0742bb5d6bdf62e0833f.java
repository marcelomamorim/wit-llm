/* @test    @summary Test StringBuffer.subSequence for valid and invalid inputs    @run main StringBufferWitupTest */

import java.lang.StringBuffer;

public class StringBufferWitupTest {

    public static void main(String[] args) {
        testValidSubSequence();
        testInvalidSubSequence();
    }

    static void testValidSubSequence() {
        StringBuffer sb = new StringBuffer("Hello, World!");
        CharSequence cs = sb.subSequence(0, 5);
        if (!"Hello".contentEquals(cs)) {
            throw new AssertionError("Expected 'Hello', but got: " + cs);
        }
        cs = sb.subSequence(7, 12);
        if (!"World".contentEquals(cs)) {
            throw new AssertionError("Expected 'World', but got: " + cs);
        }
    }

    static void testInvalidSubSequence() {
        StringBuffer sb = new StringBuffer("Test");
        try {
            sb.subSequence(-1, 2);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative start");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
        try {
            sb.subSequence(1, 10);
            throw new AssertionError("Expected IndexOutOfBoundsException for end > length");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
        try {
            sb.subSequence(3, 2);
            throw new AssertionError("Expected IndexOutOfBoundsException for start > end");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
