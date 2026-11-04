/*
 * @test
 * @summary Behavioral tests for Arrays.binarySearch(Object[], Object)
 * @run main ArraysWitupTest
 */

import java.util.Arrays;

public class ArraysWitupTest {

    public static void main(String[] args) {
        Object[] ints = {1, 3, 5, 7};
        check(Arrays.binarySearch(ints, 1) == 0, "first element");
        check(Arrays.binarySearch(ints, 5) == 2, "middle element");
        check(Arrays.binarySearch(ints, 7) == 3, "last element");
        check(Arrays.binarySearch(ints, 4) == -3, "missing inside range");
        check(Arrays.binarySearch(ints, 0) == -1, "missing before first");
        check(Arrays.binarySearch(ints, 8) == -5, "missing after last");

        Object[] strs = {"a", "c", "e"};
        check(Arrays.binarySearch(strs, "c") == 1, "string found");
        check(Arrays.binarySearch(strs, "b") == -2, "string missing");
        check(Arrays.binarySearch(strs, "z") == -4, "string missing at end");

        Object[] empty = new Object[0];
        check(Arrays.binarySearch(empty, 1) == -1, "empty array");

        Object[] single = {5};
        check(Arrays.binarySearch(single, 5) == 0, "single found");
        check(Arrays.binarySearch(single, 9) == -2, "single missing");

        boolean npe = false;
        try {
            Arrays.binarySearch((Object[]) null, 1);
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "null array must throw NullPointerException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
