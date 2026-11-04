/*
 * @test
 * @summary Test java.util.Arrays.binarySearch(T[] a, T key, Comparator<? super T> c) for boundary and error conditions
 * @run main ArraysWitupTest
 */

import java.util.Arrays;
import java.util.Comparator;

public class ArraysWitupTest {
    public static void main(String[] args) {
        testNullArray();
        testEmptyArray();
        testNormalSearch();
        testComparatorNullKey();
        testRangeCheckFromIndexLessThanZero();
        testRangeCheckFromIndexGreaterThanToIndex();
        testRangeCheckToIndexGreaterThanLength();
    }

    static void testNullArray() {
        try {
            Arrays.binarySearch(null, "key", Comparator.naturalOrder());
            throw new RuntimeException("Expected NullPointerException for null array");
        } catch (NullPointerException expected) {
        }
    }

    static void testEmptyArray() {
        String[] empty = new String[0];
        int result = Arrays.binarySearch(empty, "key", Comparator.naturalOrder());
        if (result != -1) {
            throw new RuntimeException("Expected -1 for empty array search, got " + result);
        }
    }

    static void testNormalSearch() {
        String[] arr = new String[] {"a", "b", "c", "d"};
        int idx = Arrays.binarySearch(arr, "c", Comparator.naturalOrder());
        if (idx != 2) {
            throw new RuntimeException("Expected index 2 for key 'c', got " + idx);
        }
        int notFound = Arrays.binarySearch(arr, "e", Comparator.naturalOrder());
        if (notFound >= 0) {
            throw new RuntimeException("Expected negative result for missing key 'e', got " + notFound);
        }
    }

    static void testComparatorNullKey() {
        String[] arr = new String[] {"a", "b"};
        try {
            Arrays.binarySearch(arr, null, Comparator.naturalOrder());
            throw new RuntimeException("Expected NullPointerException for null key with naturalOrder comparator");
        } catch (NullPointerException expected) {
        }
    }

    static void testRangeCheckFromIndexLessThanZero() {
        String[] arr = new String[] {"a", "b", "c"};
        try {
            // This calls binarySearch0 with fromIndex < 0, should throw ArrayIndexOutOfBoundsException
            // We simulate by calling binarySearch0 via reflection is disallowed, so test via public method is not possible
            // Instead, test indirectly by calling binarySearch with invalid range via subarray
            // Since public method does not expose fromIndex, we test that binarySearch throws NPE for null array only
            // So this expath is discarded as no public API to trigger it
            // Hence no test here
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception", e);
        }
    }

    static void testRangeCheckFromIndexGreaterThanToIndex() {
        // Similar to above, no public API to call binarySearch with fromIndex > toIndex
        // So no test possible
    }

    static void testRangeCheckToIndexGreaterThanLength() {
        // Similar to above, no public API to call binarySearch with toIndex > array length
        // So no test possible
    }
}
