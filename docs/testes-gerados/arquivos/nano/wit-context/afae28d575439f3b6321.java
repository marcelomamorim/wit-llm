/*
 * @test    @summary Test for String.offsetByCodePoints(int, int) method.
 * @run main StringWitupTest
 */

import java.lang.String;

public class StringWitupTest {

    public static void main(String[] args) {
        testOffsetByCodePointsValid();
        testOffsetByCodePointsOutOfBounds();
    }

    private static void testOffsetByCodePointsValid() {
        String s = "abc";
        int index = 1;
        int offset = 1;
        int result = s.offsetByCodePoints(index, offset);
        if (result != 2) {
            throw new AssertionError("Expected 2, but got " + result);
        }
    }

    private static void testOffsetByCodePointsOutOfBounds() {
        String s = "abc";
        try {
            s.offsetByCodePoints(-1, 1);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative index");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
        try {
            s.offsetByCodePoints(s.length() + 1, 1);
            throw new AssertionError("Expected IndexOutOfBoundsException for index greater than length");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
