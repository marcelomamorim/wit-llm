/*
 * @test
 * @summary Behavioral tests for Arrays.fill(char[], int, int, char)
 * @run main ArraysWitupTest
 */

import java.util.Arrays;

public class ArraysWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testFullRange() {
        char[] a = {'a', 'b', 'c', 'd'};
        Arrays.fill(a, 0, a.length, 'x');
        check(Arrays.equals(a, new char[]{'x', 'x', 'x', 'x'}), "full range fill failed");
    }

    static void testPartialRange() {
        char[] a = {'a', 'b', 'c', 'd', 'e'};
        Arrays.fill(a, 1, 4, 'z');
        check(Arrays.equals(a, new char[]{'a', 'z', 'z', 'z', 'e'}), "partial range fill failed");
    }

    static void testFromIndexInclusiveToIndexExclusive() {
        char[] a = {'1', '2', '3'};
        Arrays.fill(a, 2, 3, '9');
        check(a[0] == '1' && a[1] == '2' && a[2] == '9', "boundary semantics failed");
    }

    static void testEmptyRangeLeavesArrayUnchanged() {
        char[] a = {'p', 'q', 'r'};
        Arrays.fill(a, 1, 1, 'Z');
        check(Arrays.equals(a, new char[]{'p', 'q', 'r'}), "empty range modified array");
    }

    static void testEmptyArray() {
        char[] a = new char[0];
        Arrays.fill(a, 0, 0, 'k');
        check(a.length == 0, "empty array length changed");
    }

    static void testInvalidRangeDoesNotModifyArray() {
        char[][] invalid = {
            {'a', 'b', 'c'}
        };
        int[][] ranges = {
            {-1, 2},
            {2, 1},
            {0, 4}
        };
        for (int[] r : ranges) {
            char[] a = {'a', 'b', 'c'};
            try {
                Arrays.fill(a, r[0], r[1], 'X');
            } catch (RuntimeException expected) {
                // exception is acceptable for invalid ranges
            }
            check(Arrays.equals(a, new char[]{'a', 'b', 'c'}) || containsOnlyX(a, r),
                  "invalid range produced inconsistent state");
        }
    }

    static boolean containsOnlyX(char[] a, int[] r) {
        int from = r[0];
        int to = r[1];
        if (from < 0 || to > a.length || from > to) {
            return false;
        }
        for (int i = 0; i < a.length; i++) {
            boolean inRange = i >= from && i < to;
            if (inRange ? a[i] != 'X' : a[i] == 'X') {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        testFullRange();
        testPartialRange();
        testFromIndexInclusiveToIndexExclusive();
        testEmptyRangeLeavesArrayUnchanged();
        testEmptyArray();
        testInvalidRangeDoesNotModifyArray();
    }
}
