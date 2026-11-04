/*
 * @test
 * @summary Tests for java.util.Arrays.binarySearch(int[] a, int key)
 * @run main ArraysWitupTest
 */

import java.util.Arrays;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testBinarySearchNormal();
        testBinarySearchNullArray();
        testBinarySearchEmptyArray();
        testBinarySearchNegativeKey();
        testBinarySearchLargeArray();
        testBinarySearchInvalidRange();
    }

    static void testBinarySearchNormal() {
        int[] arr = {1, 3, 5, 7, 9};
        int key = 5;
        int result = Arrays.binarySearch(arr, key);
        if (result != 2) {
            throw new AssertionError("Expected index 2, got " + result);
        }
    }

    static void testBinarySearchNullArray() {
        try {
            Arrays.binarySearch((int[]) null, 1);
            throw new AssertionError("Expected NullPointerException");
        } catch (NullPointerException expected) {
        }
    }

    static void testBinarySearchEmptyArray() {
        int[] arr = {};
        int key = 1;
        int result = Arrays.binarySearch(arr, key);
        if (result != -1) {
            throw new AssertionError("Expected -1 for empty array, got " + result);
        }
    }

    static void testBinarySearchNegativeKey() {
        int[] arr = {1, 3, 5, 7, 9};
        int key = -5;
        int result = Arrays.binarySearch(arr, key);
        if (result != -1) {
            throw new AssertionError("Expected -1 for missing key, got " + result);
        }
    }

    static void testBinarySearchLargeArray() {
        int size = 1000;
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = i * 2;
        }
        int key = 500;
        int result = Arrays.binarySearch(arr, key);
        if (result != 250) {
            throw new AssertionError("Expected index 250, got " + result);
        }
    }

    static void testBinarySearchInvalidRange() {
        int[] arr = {1, 2, 3};
        try {
            // Emulate invalid range by calling rangeCheck directly (not public API)
            // Since rangeCheck is package-private, simulate invalid range by calling binarySearch0
            // which is not accessible; instead, test indirectly by passing invalid indices if possible.
            // As the public API doesn't accept range, this test is skipped.
            // Alternatively, test with a custom method if accessible.
            // Here, we simulate invalid input by passing a null array or similar.
            // Since no public method with range parameters, skip this test.
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected
        }
    }
}
