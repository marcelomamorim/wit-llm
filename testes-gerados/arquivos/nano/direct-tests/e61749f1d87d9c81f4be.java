/* @test    @summary Test for Arrays.copyOfRange method with valid inputs    @run main ArraysWitupTest */

import java.util.Arrays;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testCopyOfRangeWithValidInput();
        testCopyOfRangeWithEmptyArray();
        testCopyOfRangeWithFromEqualsTo();
        testCopyOfRangeWithFromGreaterThanTo();
    }

    private static void testCopyOfRangeWithValidInput() {
        Integer[] original = {1, 2, 3, 4, 5};
        Integer[] result = Arrays.copyOfRange(original, 1, 4);
        if (result.length != 3) {
            throw new AssertionError("Expected length 3, but got " + result.length);
        }
        if (!Arrays.equals(result, new Integer[]{2, 3, 4})) {
            throw new AssertionError("Unexpected array content: " + Arrays.toString(result));
        }
    }

    private static void testCopyOfRangeWithEmptyArray() {
        Integer[] original = {};
        Integer[] result = Arrays.copyOfRange(original, 0, 0);
        if (result.length != 0) {
            throw new AssertionError("Expected empty array, but got: " + Arrays.toString(result));
        }
    }

    private static void testCopyOfRangeWithFromEqualsTo() {
        Integer[] original = {10, 20, 30};
        Integer[] result = Arrays.copyOfRange(original, 2, 2);
        if (result.length != 0) {
            throw new AssertionError("Expected empty array, but got: " + Arrays.toString(result));
        }
    }

    private static void testCopyOfRangeWithFromGreaterThanTo() {
        Integer[] original = {1, 2, 3};
        try {
            Arrays.copyOfRange(original, 3, 2);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException not thrown");
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected exception
        }
    }
}
