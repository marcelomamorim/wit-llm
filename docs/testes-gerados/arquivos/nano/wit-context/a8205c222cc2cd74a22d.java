/* @test    @summary Test for String.valueOf(char[], int, int) method with boundary conditions and exception scenarios    @run main StringWitupTest */

import java.lang.String;

public class StringWitupTest {

    public static void main(String[] args) {
        testValidInputs();
        testCountGreaterThanLength();
        testCountNegative();
        testOffsetNegative();
        testCountExceedsArray();
    }

    static void testValidInputs() {
        char[] data = {'a', 'b', 'c', 'd'};
        String result = String.valueOf(data, 1, 2);
        if (!"bc".equals(result)) {
            throw new AssertionError("Expected 'bc', got: " + result);
        }
    }

    static void testCountGreaterThanLength() {
        char[] data = {'a', 'b', 'c'};
        try {
            String.valueOf(data, 1, 5);
            throw new AssertionError("Expected StringIndexOutOfBoundsException not thrown");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testCountNegative() {
        char[] data = {'a', 'b', 'c'};
        try {
            String.valueOf(data, 0, -1);
            throw new AssertionError("Expected StringIndexOutOfBoundsException not thrown");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testOffsetNegative() {
        char[] data = {'a', 'b', 'c'};
        try {
            String.valueOf(data, -1, 2);
            throw new AssertionError("Expected StringIndexOutOfBoundsException not thrown");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testCountExceedsArray() {
        char[] data = {'a', 'b', 'c'};
        try {
            String.valueOf(data, 2, 2);
            throw new AssertionError("Expected StringIndexOutOfBoundsException not thrown");
        } catch (StringIndexOutOfBoundsException e) {
            // expected
        }
    }
}
