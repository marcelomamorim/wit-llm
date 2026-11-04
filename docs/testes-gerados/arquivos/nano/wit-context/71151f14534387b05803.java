/*
 * @test
 * @summary Tests for String(int[] codePoints, int offset, int count)
 * @run main StringWitupTest
 */

import java.lang.String;

public class StringWitupTest {

    public static void main(String[] args) {
        testValidInputs();
        testNegativeOffset();
        testOffsetExceedsArrayLength();
        testCountExceedsArrayBounds();
        testZeroCount();
    }

    static void testValidInputs() {
        int[] codePoints = {65, 66, 67}; // 'A', 'B', 'C'
        String s = new String(codePoints, 0, 3);
        if (!s.equals("ABC")) {
            throw new AssertionError("Expected 'ABC', got: " + s);
        }
    }

    static void testNegativeOffset() {
        int[] codePoints = {65, 66, 67};
        try {
            new String(codePoints, -1, 2);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for negative offset");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testOffsetExceedsArrayLength() {
        int[] codePoints = {65, 66, 67};
        try {
            new String(codePoints, 4, 1);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for offset > array length");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }

        try {
            new String(codePoints, 3, 1);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for offset == array length");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testCountExceedsArrayBounds() {
        int[] codePoints = {65, 66, 67};
        try {
            new String(codePoints, 1, 3);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for count exceeding array bounds");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testZeroCount() {
        int[] codePoints = {65, 66, 67};
        String s = new String(codePoints, 0, 0);
        if (!s.equals("")) {
            throw new AssertionError("Expected empty string for zero count, got: " + s);
        }
    }
}
