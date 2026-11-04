/*
 * @test
 * @summary Test java.util.Arrays.binarySearch(Object[] a, Object key) for exception and normal cases
 * @run main ArraysWitupTest
 */

public class ArraysWitupTest {
    public static void main(String[] args) {
        testNullArray();
        testNormalSearch();
        testNotFound();
        testEmptyArray();
        testArrayWithNullKey();
    }

    private static void testNullArray() {
        try {
            java.util.Arrays.binarySearch(null, "key");
            throw new Error("Expected NullPointerException for null array");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testNormalSearch() {
        String[] arr = {"a", "b", "c", "d", "e"};
        int idx = java.util.Arrays.binarySearch(arr, "c");
        if (idx != 2) {
            throw new Error("Expected index 2 for key 'c', got " + idx);
        }
    }

    private static void testNotFound() {
        String[] arr = {"a", "b", "c", "d", "e"};
        int idx = java.util.Arrays.binarySearch(arr, "f");
        if (idx >= 0) {
            throw new Error("Expected negative index for key 'f' not found, got " + idx);
        }
    }

    private static void testEmptyArray() {
        String[] arr = new String[0];
        int idx = java.util.Arrays.binarySearch(arr, "any");
        if (idx >= 0) {
            throw new Error("Expected negative index for empty array, got " + idx);
        }
    }

    private static void testArrayWithNullKey() {
        String[] arr = {"a", "b", "c"};
        try {
            java.util.Arrays.binarySearch(arr, null);
            throw new Error("Expected NullPointerException or ClassCastException for null key");
        } catch (NullPointerException | ClassCastException expected) {
            // expected
        }
    }
}
