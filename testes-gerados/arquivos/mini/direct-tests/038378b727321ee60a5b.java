/*
 * @test
 * @summary Test java.util.Arrays.binarySearch(int[], int) basic and edge cases
 * @run main ArraysWitupTest
 */

public class ArraysWitupTest {
    public static void main(String[] args) {
        testEmptyArray();
        testSingleElementFound();
        testSingleElementNotFound();
        testMultipleElementsFound();
        testMultipleElementsNotFound();
        testDuplicateElements();
    }

    private static void testEmptyArray() {
        int[] a = new int[0];
        int key = 5;
        int result = java.util.Arrays.binarySearch(a, key);
        if (result >= 0) {
            throw new RuntimeException("Expected not found in empty array, got index " + result);
        }
    }

    private static void testSingleElementFound() {
        int[] a = {10};
        int key = 10;
        int result = java.util.Arrays.binarySearch(a, key);
        if (result != 0) {
            throw new RuntimeException("Expected index 0 for key found, got " + result);
        }
    }

    private static void testSingleElementNotFound() {
        int[] a = {10};
        int key = 5;
        int result = java.util.Arrays.binarySearch(a, key);
        if (result >= 0) {
            throw new RuntimeException("Expected not found for key less than element, got " + result);
        }
    }

    private static void testMultipleElementsFound() {
        int[] a = {1, 3, 5, 7, 9};
        int[] keys = {1, 3, 5, 7, 9};
        for (int i = 0; i < keys.length; i++) {
            int result = java.util.Arrays.binarySearch(a, keys[i]);
            if (result != i) {
                throw new RuntimeException("Expected index " + i + " for key " + keys[i] + ", got " + result);
            }
        }
    }

    private static void testMultipleElementsNotFound() {
        int[] a = {1, 3, 5, 7, 9};
        int[] keys = {0, 2, 4, 6, 8, 10};
        for (int key : keys) {
            int result = java.util.Arrays.binarySearch(a, key);
            if (result >= 0) {
                throw new RuntimeException("Expected not found for key " + key + ", got index " + result);
            }
        }
    }

    private static void testDuplicateElements() {
        int[] a = {1, 3, 3, 3, 5, 7};
        int key = 3;
        int result = java.util.Arrays.binarySearch(a, key);
        if (result < 1 || result > 3) {
            throw new RuntimeException("Expected index between 1 and 3 for duplicate key 3, got " + result);
        }
    }
}
