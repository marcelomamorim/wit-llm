/*
 * @test
 * @summary Test String constructor with char array, offset, and count
 * @run main StringWitupTest
 */

import java.lang.String;

public class StringWitupTest {

    public static void main(String[] args) {
        testValidSubstring();
        testEmptySubstring();
        testFullArray();
        testNegativeOffset();
        testCountExceedsArray();
        testZeroCount();
    }

    static void testValidSubstring() {
        char[] data = {'a', 'b', 'c', 'd', 'e'};
        String s = new String(data, 1, 3); // should be "bcd"
        if (!"bcd".equals(s)) {
            throw new AssertionError("Expected 'bcd', but got: " + s);
        }
    }

    static void testEmptySubstring() {
        char[] data = {'x', 'y', 'z'};
        String s = new String(data, 2, 0); // empty string
        if (!"".equals(s)) {
            throw new AssertionError("Expected empty string, but got: " + s);
        }
    }

    static void testFullArray() {
        char[] data = {'h', 'e', 'l', 'l', 'o'};
        String s = new String(data, 0, data.length); // "hello"
        if (!"hello".equals(s)) {
            throw new AssertionError("Expected 'hello', but got: " + s);
        }
    }

    static void testNegativeOffset() {
        try {
            char[] data = {'a', 'b'};
            new String(data, -1, 1);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative offset");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testCountExceedsArray() {
        try {
            char[] data = {'a', 'b'};
            new String(data, 0, 3);
            throw new AssertionError("Expected IndexOutOfBoundsException for count exceeding array length");
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testZeroCount() {
        char[] data = {'a', 'b', 'c'};
        String s = new String(data, 1, 0); // empty string
        if (!"".equals(s)) {
            throw new AssertionError("Expected empty string, but got: " + s);
        }
    }
}
