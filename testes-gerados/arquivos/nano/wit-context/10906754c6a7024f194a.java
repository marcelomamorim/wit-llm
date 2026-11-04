/*
 * @test
 * @summary Tests the String.substring(int, int) method for various inputs.
 * @run main StringWitupTest
 */

import java.lang.String;

public class StringWitupTest {

    public static void main(String[] args) {
        testValidSubstring();
        testBeginIndexGreaterThanEndIndex();
        testBeginIndexNegative();
        testEndIndexExceedsLength();
        testBeginIndexEqualsEndIndex();
        testFullString();
    }

    static void testValidSubstring() {
        String s = "Hello, World!";
        String result = s.substring(7, 12);
        if (!"World".equals(result)) {
            throw new AssertionError("Expected 'World', got: " + result);
        }
    }

    static void testBeginIndexGreaterThanEndIndex() {
        String s = "Hello";
        try {
            s.substring(4, 2);
            throw new AssertionError("Expected StringIndexOutOfBoundsException not thrown");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testBeginIndexNegative() {
        String s = "Test";
        try {
            s.substring(-1, 2);
            throw new AssertionError("Expected StringIndexOutOfBoundsException not thrown");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testEndIndexExceedsLength() {
        String s = "Sample";
        try {
            s.substring(2, 10);
            throw new AssertionError("Expected StringIndexOutOfBoundsException not thrown");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testBeginIndexEqualsEndIndex() {
        String s = "abc";
        String result = s.substring(1, 1);
        if (!"".equals(result)) {
            throw new AssertionError("Expected empty string, got: " + result);
        }
    }

    static void testFullString() {
        String s = "FullString";
        String result = s.substring(0, s.length());
        if (!s.equals(result)) {
            throw new AssertionError("Expected full string, got: " + result);
        }
    }
}
