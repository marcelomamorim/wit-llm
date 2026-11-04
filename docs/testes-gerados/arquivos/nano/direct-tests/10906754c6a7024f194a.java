/*
 * @test
 * @summary Tests for String.substring(int, int)
 * @run main StringWitupTest
 */

import java.lang.String;

public class StringWitupTest {

    public static void main(String[] args) {
        testFullString();
        testEmptySubstring();
        testPartialSubstring();
        testBeginEqualsEnd();
        testBeginEqualsZero();
        testEndEqualsLength();
    }

    static void testFullString() {
        String original = "HelloWorld";
        String result = original.substring(0, original.length());
        if (!result.equals("HelloWorld")) {
            throw new AssertionError("Expected 'HelloWorld', got: " + result);
        }
    }

    static void testEmptySubstring() {
        String original = "Hello";
        String result = original.substring(2, 2);
        if (!result.equals("")) {
            throw new AssertionError("Expected empty string, got: " + result);
        }
    }

    static void testPartialSubstring() {
        String original = "HelloWorld";
        String result = original.substring(0, 5);
        if (!result.equals("Hello")) {
            throw new AssertionError("Expected 'Hello', got: " + result);
        }
    }

    static void testBeginEqualsEnd() {
        String original = "Test";
        String result = original.substring(3, 3);
        if (!result.equals("")) {
            throw new AssertionError("Expected empty string, got: " + result);
        }
    }

    static void testBeginEqualsZero() {
        String original = "Sample";
        String result = original.substring(0, 3);
        if (!result.equals("Sam")) {
            throw new AssertionError("Expected 'Sam', got: " + result);
        }
    }

    static void testEndEqualsLength() {
        String original = "Example";
        String result = original.substring(2, original.length());
        if (!result.equals("ample")) {
            throw new AssertionError("Expected 'ample', got: " + result);
        }
    }
}
