/* @test    @summary Test the substring method of StringBuffer for valid and invalid inputs    @run main StringBufferWitupTest */

import java.lang.StringBuffer;

public class StringBufferWitupTest {

    public static void main(String[] args) {
        testValidSubstring();
        testNegativeStart();
        testStartGreaterThanCount();
    }

    private static void testValidSubstring() {
        StringBuffer sb = new StringBuffer("Hello, World!");
        String result = sb.substring(7);
        if (!"World!".equals(result)) {
            throw new AssertionError("Expected 'World!', but got: " + result);
        }
    }

    private static void testNegativeStart() {
        StringBuffer sb = new StringBuffer("Hello");
        try {
            sb.substring(-1);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for negative start");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testStartGreaterThanCount() {
        StringBuffer sb = new StringBuffer("Hello");
        int count = sb.length();
        try {
            sb.substring(count + 1);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for start > count");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }
}
