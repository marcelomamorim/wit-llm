/*
 * @test
 * @summary Witup-generated tests for Arrays.fill(int[], int, int, int) range checks
 * @run main ArraysWitupTest
 */

import java.util.Arrays;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testFillRange();
        testEmptyRangeAtBoundaries();
        testFullRangeIncludingLength();
        testFromGreaterThanTo();
        testNegativeFromIndex();
        testToIndexBeyondLength();
        testNullArray();
    }

    static void testFillRange() {
        int[] a = {0, 0, 0, 0, 0};
        Arrays.fill(a, 1, 4, 7);
        check(Arrays.equals(a, new int[]{0, 7, 7, 7, 0}), "partial range fill");
    }

    static void testEmptyRangeAtBoundaries() {
        int[] a = {1, 2, 3, 4, 5};
        Arrays.fill(a, 0, 0, 9);
        Arrays.fill(a, 5, 5, 9);
        check(Arrays.equals(a, new int[]{1, 2, 3, 4, 5}), "empty range must not modify array");
    }

    static void testFullRangeIncludingLength() {
        int[] a = new int[4];
        Arrays.fill(a, 0, a.length, 3);
        check(Arrays.equals(a, new int[]{3, 3, 3, 3}), "full range fill up to length");
    }

    static void testFromGreaterThanTo() {
        final int[] a = new int[5];
        assertThrows(IllegalArgumentException.class, () -> Arrays.fill(a, 3, 2, 1));
    }

    static void testNegativeFromIndex() {
        final int[] a = new int[5];
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> Arrays.fill(a, -1, 2, 1));
    }

    static void testToIndexBeyondLength() {
        final int[] a = new int[5];
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> Arrays.fill(a, 0, 6, 1));
    }

    static void testNullArray() {
        final int[] a = null;
        assertThrows(NullPointerException.class, () -> Arrays.fill(a, 0, 1, 1));
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Check failed: " + message);
        }
    }

    static void assertThrows(Class<? extends Throwable> expected, Runnable action) {
        try {
            action.run();
        } catch (Throwable t) {
            if (expected.isInstance(t)) {
                return;
            }
            throw new RuntimeException("Unexpected exception: " + t, t);
        }
        throw new RuntimeException("Expected exception not thrown: " + expected.getName());
    }
}
