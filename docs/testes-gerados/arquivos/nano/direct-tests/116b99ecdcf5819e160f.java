/* @test    @summary Test for Arrays.copyOfRange method with valid and invalid inputs    @run main ArraysWitupTest */

import java.util.Arrays;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testCopyOfRangeValid();
        testCopyOfRangeNegativeLength();
        testCopyOfRangeFromGreaterThanTo();
        testCopyOfRangeFromNegative();
        testCopyOfRangeToExceedsLength();
    }

    static void testCopyOfRangeValid() {
        int[] original = {1, 2, 3, 4, 5};
        int from = 1;
        int to = 4;
        int[] result = Arrays.copyOfRange(original, from, to);
        int[] expected = {2, 3, 4};
        if (!Arrays.equals(result, expected)) {
            throw new AssertionError("Expected " + Arrays.toString(expected) + ", but got " + Arrays.toString(result));
        }
    }

    static void testCopyOfRangeNegativeLength() {
        int[] original = {1, 2, 3};
        int from = 0;
        int to = 0;
        int[] result = Arrays.copyOfRange(original, from, to);
        int[] expected = {};
        if (!Arrays.equals(result, expected)) {
            throw new AssertionError("Expected empty array, but got " + Arrays.toString(result));
        }
    }

    static void testCopyOfRangeFromGreaterThanTo() {
        int[] original = {1, 2, 3};
        int from = 2;
        int to = 1;
        boolean exceptionThrown = false;
        try {
            Arrays.copyOfRange(original, from, to);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException for from > to");
        }
    }

    static void testCopyOfRangeFromNegative() {
        int[] original = {1, 2, 3};
        int from = -1;
        int to = 2;
        int[] result = null;
        try {
            result = Arrays.copyOfRange(original, from, to);
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected exception
        }
        if (result != null) {
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for negative from");
        }
    }

    static void testCopyOfRangeToExceedsLength() {
        int[] original = {1, 2, 3};
        int from = 1;
        int to = 10;
        int[] result = Arrays.copyOfRange(original, from, to);
        int[] expected = {2, 3, 0, 0, 0, 0, 0, 0, 0};
        if (!Arrays.equals(result, expected)) {
            throw new AssertionError("Expected " + Arrays.toString(expected) + ", but got " + Arrays.toString(result));
        }
    }
}
