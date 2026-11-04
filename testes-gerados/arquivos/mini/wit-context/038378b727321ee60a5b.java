/*
 * @test
 * @summary Deterministic tests for java.util.Arrays.binarySearch(int[], int)
 * @run main ArraysWitupTest
 */

public class ArraysWitupTest {
    public static void main(String[] args) {
        testBinarySearchBasic();
        testBinarySearchEmptyArray();
        testBinarySearchNotFound();
        testBinarySearchSingleElement();
        testBinarySearchMultipleElements();
        testBinarySearchNullArray();
    }

    private static void testBinarySearchBasic() {
        int[] arr = {1, 3, 5, 7, 9};
        // Search for existing elements
        for (int i = 0; i < arr.length; i++) {
            int index = java.util.Arrays.binarySearch(arr, arr[i]);
            if (index != i) {
                throw new AssertionError("binarySearch failed to find element " + arr[i] + ", got index " + index);
            }
        }
    }

    private static void testBinarySearchEmptyArray() {
        int[] empty = new int[0];
        int result = java.util.Arrays.binarySearch(empty, 10);
        if (result >= 0) {
            throw new AssertionError("binarySearch on empty array should not find any element, got " + result);
        }
    }

    private static void testBinarySearchNotFound() {
        int[] arr = {2, 4, 6, 8, 10};
        int key = 5; // not in array
        int result = java.util.Arrays.binarySearch(arr, key);
        if (result >= 0) {
            throw new AssertionError("binarySearch should not find key " + key + ", but returned index " + result);
        }
    }

    private static void testBinarySearchSingleElement() {
        int[] arr = {42};
        int index = java.util.Arrays.binarySearch(arr, 42);
        if (index != 0) {
            throw new AssertionError("binarySearch failed to find single element, got " + index);
        }
        int notFound = java.util.Arrays.binarySearch(arr, 10);
        if (notFound >= 0) {
            throw new AssertionError("binarySearch incorrectly found non-existing element in single-element array");
        }
    }

    private static void testBinarySearchMultipleElements() {
        int[] arr = {1, 2, 2, 2, 3};
        // Should find one of the indices of 2
        int index = java.util.Arrays.binarySearch(arr, 2);
        if (index < 1 || index > 3) {
            throw new AssertionError("binarySearch failed to find an occurrence of 2, got " + index);
        }
    }

    private static void testBinarySearchNullArray() {
        try {
            java.util.Arrays.binarySearch(null, 1);
            throw new AssertionError("binarySearch should throw NullPointerException when array is null");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
