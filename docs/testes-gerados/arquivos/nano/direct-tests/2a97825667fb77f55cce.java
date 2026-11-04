/*
 * @test
 * @summary Tests for String.codePointCount method to ensure correct behavior and exception handling.
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
        int count = s.codePointCount(0, s.length());
        assert count == 3 : "Expected 3 code points, but got " + count;
    }

    private static void testCodePointCountBeginIndexNegative() {
        String s = "abc";
        try {
            s.codePointCount(-1, 2);
            assert false : "Expected IndexOutOfBoundsException for negative beginIndex";
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testCodePointCountBeginIndexGreaterThanEndIndex() {
        String s = "abc";
        try {
            s.codePointCount(2, 1);
            assert false : "Expected IndexOutOfBoundsException for beginIndex > endIndex";
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    private static void testCodePointCountEndIndexExceedsLength() {
        String s = "abc";
        try {
            s.codePointCount(0, s.length() + 1);
            assert false : "Expected IndexOutOfBoundsException for endIndex > length";
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
