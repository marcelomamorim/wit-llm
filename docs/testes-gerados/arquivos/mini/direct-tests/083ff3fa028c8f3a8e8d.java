/*
 * @test
 * @summary Test java.util.Arrays.binarySearch(T[] a, T key, Comparator<? super T> c) basic behavior
 * @run main java.util.ArraysWitupTest
 */

import java.util.Arrays;
import java.util.Comparator;

public class ArraysWitupTest {
    public static void main(String[] args) {
        testNullArray();
        testEmptyArray();
        testSingleElementArray();
        testMultipleElementsNaturalOrder();
        testMultipleElementsCustomComparator();
        testKeyNotFound();
    }

    private static void testNullArray() {
        try {
            Arrays.binarySearch(null, "key", Comparator.naturalOrder());
            throw new RuntimeException("Expected NullPointerException for null array");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testEmptyArray() {
        String[] empty = new String[0];
        int result = Arrays.binarySearch(empty, "key", Comparator.naturalOrder());
        if (result != -1) {
            throw new RuntimeException("Expected -1 for empty array, got " + result);
        }
    }

    private static void testSingleElementArray() {
        String[] arr = {"a"};
        int idx = Arrays.binarySearch(arr, "a", Comparator.naturalOrder());
        if (idx != 0) {
            throw new RuntimeException("Expected index 0 for key 'a', got " + idx);
        }
        int notFound = Arrays.binarySearch(arr, "b", Comparator.naturalOrder());
        if (notFound >= 0) {
            throw new RuntimeException("Expected negative for key 'b' not found, got " + notFound);
        }
    }

    private static void testMultipleElementsNaturalOrder() {
        Integer[] arr = {1, 3, 5, 7, 9};
        int idx = Arrays.binarySearch(arr, 5, Comparator.naturalOrder());
        if (idx != 2) {
            throw new RuntimeException("Expected index 2 for key 5, got " + idx);
        }
        int notFound = Arrays.binarySearch(arr, 4, Comparator.naturalOrder());
        if (notFound >= 0) {
            throw new RuntimeException("Expected negative for key 4 not found, got " + notFound);
        }
    }

    private static void testMultipleElementsCustomComparator() {
        String[] arr = {"apple", "banana", "cherry"};
        Comparator<String> reverse = new Comparator<String>() {
            public int compare(String s1, String s2) {
                return s2.compareTo(s1);
            }
        };
        int idx = Arrays.binarySearch(arr, "banana", reverse);
        // Because array is not sorted in reverse order, result is undefined but test that no exception
        // and result is negative (not found) or some index
        if (idx < -arr.length - 1 || idx >= arr.length) {
            throw new RuntimeException("binarySearch returned invalid index " + idx);
        }
    }

    private static void testKeyNotFound() {
        Integer[] arr = {10, 20, 30};
        int idx = Arrays.binarySearch(arr, 25, Comparator.naturalOrder());
        if (idx >= 0) {
            throw new RuntimeException("Expected negative index for key 25 not found, got " + idx);
        }
    }
}
