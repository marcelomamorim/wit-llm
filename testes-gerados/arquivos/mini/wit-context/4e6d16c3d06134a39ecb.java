/*
 * @test
 * @summary Test java.util.Arrays.binarySearch(char[] a, char key) for normal and boundary cases
 * @run main ArraysWitupTest
 */

public class ArraysWitupTest {
    public static void main(String[] args) {
        testBinarySearchNormal();
        testBinarySearchEmptyArray();
        testBinarySearchSingleElement();
        testBinarySearchMultipleElements();
        testBinarySearchNotFound();
        testBinarySearchNullArray();
    }

    private static void testBinarySearchNormal() {
        char[] arr = { 'a', 'b', 'c', 'd', 'e' };
        int idx = java.util.Arrays.binarySearch(arr, 'c');
        if (idx != 2) {
            throw new AssertionError("Expected index 2 for 'c', got " + idx);
        }
    }

    private static void testBinarySearchEmptyArray() {
        char[] arr = new char[0];
        int idx = java.util.Arrays.binarySearch(arr, 'a');
        if (idx >= 0) {
            throw new AssertionError("Expected negative index for empty array, got " + idx);
        }
    }

    private static void testBinarySearchSingleElement() {
        char[] arr = { 'z' };
        int idxFound = java.util.Arrays.binarySearch(arr, 'z');
        if (idxFound != 0) {
            throw new AssertionError("Expected index 0 for 'z', got " + idxFound);
        }
        int idxNotFound = java.util.Arrays.binarySearch(arr, 'a');
        if (idxNotFound >= 0) {
            throw new AssertionError("Expected negative index for 'a' not found, got " + idxNotFound);
        }
    }

    private static void testBinarySearchMultipleElements() {
        char[] arr = { 'a', 'c', 'e', 'g', 'i' };
        int idx = java.util.Arrays.binarySearch(arr, 'g');
        if (idx != 3) {
            throw new AssertionError("Expected index 3 for 'g', got " + idx);
        }
    }

    private static void testBinarySearchNotFound() {
        char[] arr = { 'a', 'b', 'c', 'd', 'e' };
        int idx = java.util.Arrays.binarySearch(arr, 'f');
        if (idx >= 0) {
            throw new AssertionError("Expected negative index for 'f' not found, got " + idx);
        }
    }

    private static void testBinarySearchNullArray() {
        try {
            java.util.Arrays.binarySearch(null, 'a');
            throw new AssertionError("Expected NullPointerException for null array");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
