/*
 * @test
 * @summary Test java.util.Arrays.sort(Object[] a, int fromIndex, int toIndex) for boundary and exception cases
 * @run main ArraysWitupTest
 */

import java.util.Arrays;

public class ArraysWitupTest {
    public static void main(String[] args) {
        testValidSort();
        testFromIndexLessThanZero();
        testFromIndexGreaterThanToIndex();
        testToIndexGreaterThanLength();
        System.out.println("All tests passed.");
    }

    private static void testValidSort() {
        Object[] arr = new Integer[] {5, 3, 8, 1, 9};
        Arrays.sort(arr, 1, 4);
        // Sorted subrange [1,4): elements at indices 1,2,3
        // Expected sorted subrange: 1,3,8 at indices 1,2,3
        if (!arr[1].equals(1) || !arr[2].equals(3) || !arr[3].equals(8)) {
            throw new AssertionError("Subrange not sorted correctly");
        }
        // Elements outside the range remain unchanged
        if (!arr[0].equals(5) || !arr[4].equals(9)) {
            throw new AssertionError("Elements outside range modified");
        }
    }

    private static void testFromIndexLessThanZero() {
        Object[] arr = new Integer[] {1, 2, 3};
        try {
            Arrays.sort(arr, -1, 2);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for fromIndex < 0");
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected
        }
    }

    private static void testFromIndexGreaterThanToIndex() {
        Object[] arr = new Integer[] {1, 2, 3};
        try {
            Arrays.sort(arr, 2, 1);
            throw new AssertionError("Expected IllegalArgumentException for fromIndex > toIndex");
        } catch (IllegalArgumentException e) {
            // Expected
        }
    }

    private static void testToIndexGreaterThanLength() {
        Object[] arr = new Integer[] {1, 2, 3};
        try {
            Arrays.sort(arr, 1, 5);
            throw new AssertionError("Expected ArrayIndexOutOfBoundsException for toIndex > array length");
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected
        }
    }
}
