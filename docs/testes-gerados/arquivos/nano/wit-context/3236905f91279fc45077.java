/*
 * @test    @summary Test for Arrays.binarySearch(short[] a, short key) with various inputs
 * @run main ArraysWitupTest
 */

import java.util.Arrays;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testBinarySearchNormal();
        testBinarySearchRangeCheck();
        testBinarySearchRangeCheckInvalid();
    }

    static void testBinarySearchNormal() {
        short[] array = {1, 3, 5, 7, 9};
        int index = Arrays.binarySearch(array, (short) 5);
        if (index != 2) {
            throw new AssertionError("Expected index 2, got " + index);
        }
        index = Arrays.binarySearch(array, (short) 10);
        if (index != -6) {
            throw new AssertionError("Expected insertion point -6, got " + index);
        }
    }

    static void testBinarySearchRangeCheck() {
        short[] array = {1, 2, 3, 4, 5};
        try {
            Arrays.binarySearch(array, (short) 3);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }

    static void testBinarySearchRangeCheckInvalid() {
        short[] array = {1, 2, 3, 4, 5};
        try {
            // This will cause fromIndex > toIndex
            Arrays.binarySearch(array, 0, 0, (short) 3);
            // The above call is valid, but to simulate fromIndex > toIndex:
            Arrays.binarySearch(array, 3, 2, (short) 3);
            throw new AssertionError("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }
}
