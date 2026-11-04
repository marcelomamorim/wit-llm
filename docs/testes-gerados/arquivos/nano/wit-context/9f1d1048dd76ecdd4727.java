/*
 * @test
 * @summary Tests for String.repeat(int)
 * @run main StringWitupTest
 */

import java.lang.String;

public class StringWitupTest {

    public static void main(String[] args) {
        testRepeatNormal();
        testRepeatZero();
        testRepeatNegative();
        testRepeatSingleChar();
        testRepeatLargeCount();
    }

    static void testRepeatNormal() {
        String s = "abc";
        String result = s.repeat(3);
        if (!"abcabcabc".equals(result)) {
            throw new AssertionError("Expected 'abcabcabc', got: " + result);
        }
    }

    static void testRepeatZero() {
        String s = "xyz";
        String result = s.repeat(0);
        if (!"".equals(result)) {
            throw new AssertionError("Expected empty string, got: " + result);
        }
    }

    static void testRepeatNegative() {
        String s = "test";
        try {
            s.repeat(-1);
            throw new AssertionError("Expected IllegalArgumentException for negative count");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    static void testRepeatSingleChar() {
        String s = "a";
        String result = s.repeat(4);
        if (!"aaaa".equals(result)) {
            throw new AssertionError("Expected 'aaaa', got: " + result);
        }
    }

    static void testRepeatLargeCount() {
        String s = "b";
        int count = Integer.MAX_VALUE / 2;
        try {
            s.repeat(count);
            // Should not throw, but may take time
        } catch (OutOfMemoryError e) {
            // acceptable if system cannot allocate
        }
    }
}
