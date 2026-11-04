/*
 * @test
 * @summary Test java.util.Arrays.binarySearch(Object[], Object) basic and edge cases
 * @run main ArraysWitupTest
 */

public class ArraysWitupTest {
    public static void main(String[] args) {
        testEmptyArray();
        testSingleElementFound();
        testSingleElementNotFound();
        testMultipleElementsFound();
        testMultipleElementsNotFound();
        testNullKeyInArray();
        testNullArray();
    }

    private static void testEmptyArray() {
        Object[] empty = new Object[0];
        int result = java.util.Arrays.binarySearch(empty, "any");
        if (result >= 0) {
            throw new RuntimeException("Expected not found in empty array, got " + result);
        }
    }

    private static void testSingleElementFound() {
        Object[] arr = new Object[] {"a"};
        int idx = java.util.Arrays.binarySearch(arr, "a");
        if (idx != 0) {
            throw new RuntimeException("Expected index 0 for found element, got " + idx);
        }
    }

    private static void testSingleElementNotFound() {
        Object[] arr = new Object[] {"a"};
        int idx = java.util.Arrays.binarySearch(arr, "b");
        if (idx >= 0) {
            throw new RuntimeException("Expected not found for missing element, got " + idx);
        }
    }

    private static void testMultipleElementsFound() {
        Object[] arr = new Object[] {"a", "b", "c", "d", "e"};
        int idx = java.util.Arrays.binarySearch(arr, "c");
        if (idx != 2) {
            throw new RuntimeException("Expected index 2 for element 'c', got " + idx);
        }
    }

    private static void testMultipleElementsNotFound() {
        Object[] arr = new Object[] {"a", "b", "d", "e"};
        int idx = java.util.Arrays.binarySearch(arr, "c");
        if (idx >= 0) {
            throw new RuntimeException("Expected not found for missing element 'c', got " + idx);
        }
    }

    private static void testNullKeyInArray() {
        Object[] arr = new Object[] {"a", null, "c"};
        try {
            java.util.Arrays.binarySearch(arr, null);
            throw new RuntimeException("Expected NullPointerException for null key");
        } catch (NullPointerException e) {
            // expected
        } catch (ClassCastException e) {
            // allowed if null not comparable
        }
    }

    private static void testNullArray() {
        try {
            java.util.Arrays.binarySearch(null, "a");
            throw new RuntimeException("Expected NullPointerException for null array");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
