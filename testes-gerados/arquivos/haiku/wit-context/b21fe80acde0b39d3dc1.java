/*
 * @test
 * @summary Witup-generated tests for Arrays.sort(Object[], int, int) range checks and sorting
 * @run main ArraysWitupTest
 */

import java.util.Arrays;

public class ArraysWitupTest {

    public static void main(String[] args) throws Exception {
        testFromGreaterThanToThrowsIllegalArgument();
        testNegativeFromIndexThrowsArrayIndexOutOfBounds();
        testToIndexBeyondLengthThrowsArrayIndexOutOfBounds();
        testValidRangeSortsOnlyThatRange();
        testEmptyRangeLeavesArrayUnchanged();
    }

    static void testFromGreaterThanToThrowsIllegalArgument() {
        Object[] a = {3, 2, 1};
        try {
            Arrays.sort(a, 2, 1);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new AssertionError("no IllegalArgumentException when fromIndex > toIndex");
    }

    static void testNegativeFromIndexThrowsArrayIndexOutOfBounds() {
        Object[] a = {3, 2, 1};
        try {
            Arrays.sort(a, -1, 2);
        } catch (ArrayIndexOutOfBoundsException e) {
            return;
        }
        throw new AssertionError("no ArrayIndexOutOfBoundsException when fromIndex < 0");
    }

    static void testToIndexBeyondLengthThrowsArrayIndexOutOfBounds() {
        Object[] a = {3, 2, 1};
        try {
            Arrays.sort(a, 0, 4);
        } catch (ArrayIndexOutOfBoundsException e) {
            return;
        }
        throw new AssertionError("no ArrayIndexOutOfBoundsException when toIndex > length");
    }

    static void testValidRangeSortsOnlyThatRange() {
        Object[] a = {5, 4, 3, 2, 1};
        Arrays.sort(a, 1, 4);
        Object[] expected = {5, 2, 3, 4, 1};
        if (!Arrays.equals(expected, a)) {
            throw new AssertionError("unexpected result: " + Arrays.toString(a));
        }
    }

    static void testEmptyRangeLeavesArrayUnchanged() {
        Object[] a = {3, 2, 1};
        Arrays.sort(a, 2, 2);
        Object[] expected = {3, 2, 1};
        if (!Arrays.equals(expected, a)) {
            throw new AssertionError("empty range modified array: " + Arrays.toString(a));
        }
        Object[] empty = new Object[0];
        Arrays.sort(empty, 0, 0);
        if (empty.length != 0) {
            throw new AssertionError("empty array length changed");
        }
    }
}
