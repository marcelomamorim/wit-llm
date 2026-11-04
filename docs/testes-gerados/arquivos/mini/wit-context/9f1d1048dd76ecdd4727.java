/*
 * @test
 * @summary Test String.repeat(int) method for expected exceptions and normal behavior
 * @run main StringWitupTest
 */

public class StringWitupTest {
    public static void main(String[] args) {
        testRepeatNegativeCount();
        testRepeatZeroCount();
        testRepeatOneCount();
        testRepeatEmptyString();
        testRepeatSingleCharString();
        testRepeatMultipleCharsString();
    }

    private static void testRepeatNegativeCount() {
        String s = "abc";
        try {
            s.repeat(-1);
            throw new Error("Expected IllegalArgumentException for negative count");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }

    private static void testRepeatZeroCount() {
        String s = "abc";
        String result = s.repeat(0);
        if (!"".equals(result)) {
            throw new Error("Expected empty string when count is zero");
        }
    }

    private static void testRepeatOneCount() {
        String s = "abc";
        String result = s.repeat(1);
        if (result != s) {
            throw new Error("Expected same string instance when count is one");
        }
    }

    private static void testRepeatEmptyString() {
        String s = "";
        String result = s.repeat(5);
        if (!"".equals(result)) {
            throw new Error("Expected empty string when repeating empty string");
        }
    }

    private static void testRepeatSingleCharString() {
        String s = "x";
        String result = s.repeat(3);
        if (!"xxx".equals(result)) {
            throw new Error("Expected repeated single char string");
        }
    }

    private static void testRepeatMultipleCharsString() {
        String s = "ab";
        String result = s.repeat(3);
        if (!"ababab".equals(result)) {
            throw new Error("Expected repeated multiple chars string");
        }
    }
}