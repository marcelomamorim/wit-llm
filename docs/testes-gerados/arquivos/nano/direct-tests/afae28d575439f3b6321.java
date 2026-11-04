/*
 * @test
 * @summary Test String.offsetByCodePoints for various inputs
 * @run main StringWitupTest
 */

import java.lang.String;

public class StringWitupTest {

    public static void main(String[] args) {
        testOffsetByCodePointsBasic();
        testOffsetByCodePointsNegativeIndex();
        testOffsetByCodePointsIndexOutOfBounds();
        testOffsetByCodePointsLargeOffset();
    }

    static void testOffsetByCodePointsBasic() {
        String s = "abc";
        int index = 1;
        int offset = 1;
        int result = s.offsetByCodePoints(index, offset);
        int expected = 2;
        if (result != expected) {
            throw new AssertionError("Expected " + expected + " but got " + result);
        }
    }

    static void testOffsetByCodePointsNegativeIndex() {
        String s = "abc";
        try {
            s.offsetByCodePoints(-1, 1);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative index");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testOffsetByCodePointsIndexOutOfBounds() {
        String s = "abc";
        try {
            s.offsetByCodePoints(4, 1);
            throw new AssertionError("Expected IndexOutOfBoundsException for index > length()");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testOffsetByCodePointsLargeOffset() {
        String s = "abc";
        int index = 1;
        int offset = 10;
        int result = s.offsetByCodePoints(index, offset);
        int expected = 3;
        if (result != expected) {
            throw new AssertionError("Expected " + expected + " but got " + result);
        }
    }
}
