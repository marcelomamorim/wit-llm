/*
 * @test
 * @summary Tests for String.codePointCount(int, int)
 * @run main StringWitupTest
 */

import java.lang.String;

public class StringWitupTest {

    public static void main(String[] args) {
        testCodePointCountValidRange();
        testCodePointCountBeginIndexNegative();
        testCodePointCountBeginIndexGreaterThanEndIndex();
        testCodePointCountEndIndexExceedsLength();
    }

    private static void testCodePointCountValidRange() {
        String s = "abc";
        int result = s.codePointCount(0, 3);
        if (result != 3) {
            throw new AssertionError("Expected 3, got " + result);
        }
    }

    private static void testCodePointCountBeginIndexNegative() {
        String s = "abc";
        try {
            s.codePointCount(-1, 2);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative beginIndex");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testCodePointCountBeginIndexGreaterThanEndIndex() {
        String s = "abc";
        try {
            s.codePointCount(2, 1);
            throw new AssertionError("Expected IndexOutOfBoundsException for beginIndex > endIndex");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testCodePointCountEndIndexExceedsLength() {
        String s = "abc";
        try {
            s.codePointCount(0, 4);
            throw new AssertionError("Expected IndexOutOfBoundsException for endIndex > length()");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
