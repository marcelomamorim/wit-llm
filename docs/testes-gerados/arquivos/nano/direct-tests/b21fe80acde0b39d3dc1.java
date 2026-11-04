/*
 * @test
 * @summary Test java.util.Arrays.sort(Object[], int, int) for correctness and regressions
 * @run main ArraysWitupTest
 */

import java.util.Arrays;

public class ArraysWitupTest {
    public static void main(String[] args) {
        testSortWholeArray();
        testSortSubArray();
        testSortEmptyArray();
        testSortSingleElementArray();
        testSortArrayWithNulls();
    }

    static void testSortWholeArray() {
        Object[] arr = {3, 1, 2};
        Arrays.sort(arr, 0, arr.length);
        assert isSorted(arr) : "Whole array not sorted: " + java.util.Arrays.toString(arr);
    }

    static void testSortSubArray() {
        Object[] arr = {5, 3, 4, 1, 2};
        Arrays.sort(arr, 1, 4); // sort subarray {3,4,1}
        Object[] expected = {5, 1, 3, 4, 2};
        assert java.util.Arrays.equals(arr, expected) : "Subarray sort failed: " + java.util.Arrays.toString(arr);
    }

    static void testSortEmptyArray() {
        Object[] arr = {};
        Arrays.sort(arr, 0, 0);
        assert arr.length == 0 : "Empty array changed: " + java.util.Arrays.toString(arr);
    }

    static void testSortSingleElementArray() {
        Object[] arr = {42};
        Arrays.sort(arr, 0, 1);
        assert arr[0] == 42 : "Single element array changed: " + java.util.Arrays.toString(arr);
    }

    static void testSortArrayWithNulls() {
        Object[] arr = {null, 2, null, 1};
        try {
            Arrays.sort(arr, 0, arr.length);
            // Expect a NullPointerException because nulls are not comparable
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static boolean isSorted(Object[] arr) {
        for (int i = 1; i < arr.length; i++) {
            if (arr[i - 1] == null || arr[i] == null) {
                continue; // nulls are considered less than any non-null
            }
            if (((Comparable) arr[i - 1]).compareTo(arr[i]) > 0) {
                return false;
            }
        }
        return true;
    }
}
