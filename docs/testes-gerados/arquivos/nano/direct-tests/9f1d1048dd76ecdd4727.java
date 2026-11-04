/*
 * @test
 * @summary Test for String.repeat(int) method to ensure correct behavior and exception handling.
 * @run main StringWitupTest
 */

import java.lang.String;

public class StringWitupTest {

    public static void main(String[] args) {
        testRepeatNormal();
        testRepeatZero();
        testRepeatNegative();
        testRepeatSingleChar();
        testRepeatLarge();
    }

    static void testRepeatNormal() {
        String original = "abc";
        String result = original.repeat(3);
        if (!"abcabcabc".equals(result)) {
            throw new AssertionError("Expected 'abcabcabc', but got: " + result);
        }
    }

    static void testRepeatZero() {
        String original = "xyz";
        String result = original.repeat(0);
        if (!"".equals(result)) {
            throw new AssertionError("Expected empty string, but got: " + result);
        }
    }

    static void testRepeatNegative() {
        String original = "test";
        try {
            original.repeat(-1);
            throw new AssertionError("Expected IllegalArgumentException for negative count");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testRepeatSingleChar() {
        String original = "a";
        String result = original.repeat(5);
        if (!"aaaaa".equals(result)) {
            throw new AssertionError("Expected 'aaaaa', but got: " + result);
        }
    }

    static void testRepeatLarge() {
        String original = "x";
        int largeCount = 1000000;
        String result = original.repeat(largeCount);
        if (result.length() != largeCount || !result.equals("x".repeat(largeCount))) {
            throw new AssertionError("Large repeat failed");
        }
    }
}
