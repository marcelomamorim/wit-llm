/*
 * @test
 * @summary Verifies observable behavior of Arrays.equals(int[], int[])
 * @run main ArraysWitupTest
 */

import java.util.Arrays;

public class ArraysWitupTest {

    public static void main(String[] args) {
        int[] a = {1, 2, 3};
        check(Arrays.equals(a, a), true, "same reference");
        check(Arrays.equals(new int[]{1, 2, 3}, new int[]{1, 2, 3}), true, "equal contents");
        check(Arrays.equals(new int[0], new int[0]), true, "both empty");
        check(Arrays.equals(null, null), true, "both null");
        check(Arrays.equals(null, a), false, "first null");
        check(Arrays.equals(a, null), false, "second null");
        check(Arrays.equals(new int[]{1, 2}, new int[]{1, 2, 3}), false, "different lengths");
        check(Arrays.equals(new int[]{1, 2, 3}, new int[]{1, 2}), false, "different lengths reversed");
        check(Arrays.equals(new int[]{9, 2, 3}, new int[]{1, 2, 3}), false, "mismatch at first element");
        check(Arrays.equals(new int[]{1, 2, 9}, new int[]{1, 2, 3}), false, "mismatch at last element");
        check(Arrays.equals(new int[]{1, 2, 3}, new int[]{1, 2, 3}), true, "repeat equal check");
        check(Arrays.equals(new int[]{Integer.MIN_VALUE, 0}, new int[]{Integer.MIN_VALUE, 0}), true, "extreme values equal");
        check(Arrays.equals(new int[]{Integer.MIN_VALUE}, new int[]{Integer.MAX_VALUE}), false, "extreme values differ");
    }

    private static void check(boolean actual, boolean expected, String message) {
        if (actual != expected) {
            throw new RuntimeException("Unexpected result for " + message
                    + ": expected " + expected + " but was " + actual);
        }
    }
}
