/*
 * @test
 * @summary Tests for String constructor with byte array, hibyte, offset, and count
 * @run main StringWitupTest
 */

import java.lang.String;

public class StringWitupTest {

    public static void main(String[] args) {
        testValidInputs();
        testNegativeOffset();
        testNegativeCount();
        testCountExceedsArrayLength();
        testZeroCount();
    }

    static void testValidInputs() {
        byte[] ascii = {65, 66, 67, 68}; // 'A', 'B', 'C', 'D'
        int hibyte = 0;
        int offset = 1;
        int count = 2;
        String s = new String(ascii, hibyte, offset, count);
        if (!s.equals("BC")) {
            throw new AssertionError("Expected 'BC', got: " + s);
        }
    }

    static void testNegativeOffset() {
        byte[] ascii = {65, 66, 67};
        try {
            new String(ascii, 0, -1, 2);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for negative offset");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testNegativeCount() {
        byte[] ascii = {65, 66, 67};
        try {
            new String(ascii, 0, 0, -1);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for negative count");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }

    }

    static void testCountExceedsArrayLength() {
        byte[] ascii = {65, 66, 67};
        int offset = 1;
        int count = 5;
        try {
            new String(ascii, 0, offset, count);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for count exceeding array length");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testZeroCount() {
        byte[] ascii = {65, 66, 67};
        String s = new String(ascii, 0, 0, 0);
        if (!s.equals("")) {
            throw new AssertionError("Expected empty string for zero count, got: " + s);
        }
    }
}
