/*
 * @test
 * @summary Test java.util.Arrays.binarySearch(char[], char) basic and boundary cases
 * @run main java.util.ArraysWitupTest
 */

public class ArraysWitupTest {
    public static void main(String[] args) {
        testEmptyArray();
        testSingleElementFound();
        testSingleElementNotFound();
        testMultipleElementsFound();
        testMultipleElementsNotFound();
        testAllEqualElements();
        testKeyLessThanAll();
        testKeyGreaterThanAll();
    }

    private static void testEmptyArray() {
        char[] a = new char[0];
        int result = java.util.Arrays.binarySearch(a, 'a');
        if (result >= 0) throw new Error("Expected not found in empty array, got " + result);
    }

    private static void testSingleElementFound() {
        char[] a = { 'x' };
        int result = java.util.Arrays.binarySearch(a, 'x');
        if (result != 0) throw new Error("Expected index 0, got " + result);
    }

    private static void testSingleElementNotFound() {
        char[] a = { 'x' };
        int result = java.util.Arrays.binarySearch(a, 'y');
        if (result >= 0) throw new Error("Expected not found, got " + result);
    }

    private static void testMultipleElementsFound() {
        char[] a = { 'a', 'b', 'c', 'd', 'e' };
        for (int i = 0; i < a.length; i++) {
            int result = java.util.Arrays.binarySearch(a, a[i]);
            if (result != i) throw new Error("Expected index " + i + ", got " + result);
        }
    }

    private static void testMultipleElementsNotFound() {
        char[] a = { 'a', 'c', 'e', 'g', 'i' };
        char[] keys = { 'b', 'd', 'f', 'h', 'j' };
        for (char key : keys) {
            int result = java.util.Arrays.binarySearch(a, key);
            if (result >= 0) throw new Error("Expected not found for key " + key + ", got " + result);
        }
    }

    private static void testAllEqualElements() {
        char[] a = { 'z', 'z', 'z', 'z' };
        int result = java.util.Arrays.binarySearch(a, 'z');
        if (result < 0 || result >= a.length) throw new Error("Expected found index in range, got " + result);
        result = java.util.Arrays.binarySearch(a, 'a');
        if (result >= 0) throw new Error("Expected not found for 'a', got " + result);
    }

    private static void testKeyLessThanAll() {
        char[] a = { 'b', 'c', 'd' };
        int result = java.util.Arrays.binarySearch(a, 'a');
        if (result >= 0) throw new Error("Expected not found for key less than all, got " + result);
    }

    private static void testKeyGreaterThanAll() {
        char[] a = { 'b', 'c', 'd' };
        int result = java.util.Arrays.binarySearch(a, 'e');
        if (result >= 0) throw new Error("Expected not found for key greater than all, got " + result);
    }
}
