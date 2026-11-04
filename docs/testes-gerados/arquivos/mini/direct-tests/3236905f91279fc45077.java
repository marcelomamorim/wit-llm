/*
 * @test
 * @summary Test java.util.Arrays.binarySearch(short[], short) basic and edge cases
 * @run main java.util.ArraysWitupTest
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
        short[] a = new short[0];
        int result = java.util.Arrays.binarySearch(a, (short)5);
        if (result != -1) {
            throw new AssertionError("Expected -1 for empty array, got " + result);
        }
    }

    private static void testSingleElementFound() {
        short[] a = new short[] {10};
        int result = java.util.Arrays.binarySearch(a, (short)10);
        if (result != 0) {
            throw new AssertionError("Expected index 0 for key 10, got " + result);
        }
    }

    private static void testSingleElementNotFound() {
        short[] a = new short[] {10};
        int result = java.util.Arrays.binarySearch(a, (short)5);
        if (result != -1) {
            throw new AssertionError("Expected -1 for key 5 not found, got " + result);
        }
    }

    private static void testMultipleElementsFound() {
        short[] a = new short[] {1, 3, 5, 7, 9};
        int result = java.util.Arrays.binarySearch(a, (short)7);
        if (result != 3) {
            throw new AssertionError("Expected index 3 for key 7, got " + result);
        }
    }

    private static void testMultipleElementsNotFound() {
        short[] a = new short[] {1, 3, 5, 7, 9};
        int result = java.util.Arrays.binarySearch(a, (short)6);
        if (result >= 0) {
            throw new AssertionError("Expected negative result for key 6 not found, got " + result);
        }
    }

    private static void testDuplicateElements() {
        short[] a = new short[] {2, 4, 4, 4, 6, 8};
        int result = java.util.Arrays.binarySearch(a, (short)4);
        if (result < 1 || result > 3) {
            throw new AssertionError("Expected index between 1 and 3 for key 4, got " + result);
        }
    }
}
