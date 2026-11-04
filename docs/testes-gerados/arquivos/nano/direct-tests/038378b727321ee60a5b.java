/* @test    @summary Test java.util.Arrays.binarySearch for various cases    @run main ArraysWitupTest */

import java.util.Arrays;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testBinarySearchFound();
        testBinarySearchNotFound();
        testBinarySearchEmptyArray();
        testBinarySearchSingleElement();
        testBinarySearchMultipleOccurrences();
    }

    static void testBinarySearchFound() {
        int[] arr = {1, 3, 5, 7, 9};
        int key = 5;
        int index = Arrays.binarySearch(arr, key);
        if (index != 2) {
            throw new AssertionError("Expected index 2 for key 5, but got " + index);
        }
    }

    static void testBinarySearchNotFound() {
        int[] arr = {1, 3, 5, 7, 9};
        int key = 4;
        int index = Arrays.binarySearch(arr, key);
        if (index >= 0) {
            throw new AssertionError("Expected negative result for missing key 4, but got " + index);
        }
        int insertionPoint = -index - 1;
        if (insertionPoint != 2) {
            throw new AssertionError("Expected insertion point 2 for key 4, but got " + insertionPoint);
        }
    }

    static void testBinarySearchEmptyArray() {
        int[] arr = {};
        int key = 1;
        int index = Arrays.binarySearch(arr, key);
        if (index != -1) {
            throw new AssertionError("Expected -1 for empty array, but got " + index);
        }
    }

    static void testBinarySearchSingleElement() {
        int[] arr = {10};
        int keyPresent = 10;
        int indexPresent = Arrays.binarySearch(arr, keyPresent);
        if (indexPresent != 0) {
            throw new AssertionError("Expected index 0 for key 10, but got " + indexPresent);
        }
        int keyAbsent = 5;
        int indexAbsent = Arrays.binarySearch(arr, keyAbsent);
        if (indexAbsent != -1) {
            throw new AssertionError("Expected -1 for missing key 5, but got " + indexAbsent);
        }
    }

    static void testBinarySearchMultipleOccurrences() {
        int[] arr = {1, 2, 2, 2, 3};
        int key = 2;
        int index = Arrays.binarySearch(arr, key);
        if (index < 1 || index > 3 || arr[index] != key) {
            throw new AssertionError("Expected index of one occurrence of 2, but got " + index);
        }
    }
}
