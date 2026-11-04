/*
 * @test
 * @summary Tests for StringBuffer.substring(int, int)
 * @run main StringBufferWitupTest
 */

import java.lang.StringBuffer;

public class StringBufferWitupTest {

    public static void main(String[] args) {
        testValidSubstring();
        testStartNegative();
        testEndGreaterThanLength();
        testStartGreaterThanEnd();
    }

    static void testValidSubstring() {
        StringBuffer sb = new StringBuffer("Hello, World!");
        String result = sb.substring(0, 5);
        if (!"Hello".equals(result)) {
            throw new AssertionError("Expected 'Hello', got: " + result);
        }
    }

    static void testStartNegative() {
        StringBuffer sb = new StringBuffer("Hello");
        try {
            sb.substring(-1, 3);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for negative start");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testEndGreaterThanLength() {
        StringBuffer sb = new StringBuffer("Hello");
        try {
            sb.substring(1, 10);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for end > length");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }

    }

    static void testStartGreaterThanEnd() {
        StringBuffer sb = new StringBuffer("Hello");
        String result = sb.substring(4, 2);
        if (!"".equals(result)) {
            throw new AssertionError("Expected empty string for start > end, got: " + result);
        }
    }
}
