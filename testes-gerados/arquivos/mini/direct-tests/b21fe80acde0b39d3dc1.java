/*
 * @test
 * @summary Test java.util.Arrays.sort(Object[], int, int) for correctness and boundary checks
 * @run main java.util.ArraysWitupTest
 */

import java.util.Arrays;

public class ArraysWitupTest {
    public static void main(String[] args) {
        testSortFullArray();
        testSortPartialArray();
        testSortEmptyRange();
        testSortSingleElementRange();
        testSortInvalidRange();
        testSortNullArray();
    }

    private static void testSortFullArray() {
        String[] arr = {"delta", "alpha", "charlie", "bravo"};
        Arrays.sort(arr, 0, arr.length);
        if (!arr[0].equals("alpha") || !arr[1].equals("bravo") || !arr[2].equals("charlie") || !arr[3].equals("delta")) {
            throw new AssertionError("Full array sort failed");
        }
    }

    private static void testSortPartialArray() {
        String[] arr = {"delta", "alpha", "charlie", "bravo"};
        Arrays.sort(arr, 1, 3); // sort only "alpha", "charlie"
        if (!arr[0].equals("delta") || !arr[1].equals("alpha") || !arr[2].equals("charlie") || !arr[3].equals("bravo")) {
            throw new AssertionError("Partial array sort failed");
        }
    }

    private static void testSortEmptyRange() {
        String[] arr = {"one", "two", "three"};
        Arrays.sort(arr, 1, 1); // empty range, should not change array
        if (!arr[0].equals("one") || !arr[1].equals("two") || !arr[2].equals("three")) {
            throw new AssertionError("Empty range sort modified array");
        }
    }

    private static void testSortSingleElementRange() {
        String[] arr = {"one", "two", "three"};
        Arrays.sort(arr, 2, 3); // single element range, no change expected
        if (!arr[0].equals("one") || !arr[1].equals("two") || !arr[2].equals("three")) {
            throw new AssertionError("Single element range sort modified array");
        }
    }

    private static void testSortInvalidRange() {
        String[] arr = {"a", "b"};
        try {
            Arrays.sort(arr, -1, 2);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative fromIndex");
        } catch (IndexOutOfBoundsException expected) {
        }
        try {
            Arrays.sort(arr, 1, 3);
            throw new AssertionError("Expected IndexOutOfBoundsException for toIndex > length");
        } catch (IndexOutOfBoundsException expected) {
        }
        try {
            Arrays.sort(arr, 2, 1);
            throw new AssertionError("Expected IllegalArgumentException for fromIndex > toIndex");
        } catch (IllegalArgumentException expected) {
        }
    }

    private static void testSortNullArray() {
        try {
            Arrays.sort(null, 0, 1);
            throw new AssertionError("Expected NullPointerException for null array");
        } catch (NullPointerException expected) {
        }
    }
}
