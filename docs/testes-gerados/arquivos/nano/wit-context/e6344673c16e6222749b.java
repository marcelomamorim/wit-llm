/*
 * @test
 * @summary Tests for String constructor with char array, offset, and count
 * @run main StringWitupTest
 */

import java.lang.String;

public class StringWitupTest {

    public static void main(String[] args) {
        testValidInputs();
        testNegativeOffset();
        testCountExceedsLength();
        testCountNegative();
        testOffsetNegative();
    }

    static void testValidInputs() {
        char[] data = {'a', 'b', 'c', 'd'};
        String s = new String(data, 1, 2); // should succeed, "bc"
        if (!"bc".equals(s)) {
            throw new AssertionError("Expected 'bc', got: " + s);
        }
    }

    static void testNegativeOffset() {
        char[] data = {'a', 'b', 'c'};
        try {
            new String(data, -1, 2);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for negative offset");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testCountExceedsLength() {
        char[] data = {'a', 'b', 'c'};
        try {
            new String(data, 1, 5); // count exceeds length from offset
            throw new AssertionError("Expected StringIndexOutOfBoundsException for count exceeding length");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testCountNegative() {
        char[] data = {'a', 'b', 'c'};
        try {
            new String(data, 1, -1); // negative count
            throw new AssertionError("Expected StringIndexOutOfBoundsException for negative count");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testOffsetNegative() {
        char[] data = {'a', 'b', 'c'};
        try {
            new String(data, -2, 2); // negative offset
            throw new AssertionError("Expected StringIndexOutOfBoundsException for negative offset");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }
}
