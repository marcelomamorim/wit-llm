/*
 * @test
 * @summary Witup-derived behavioral checks for Arrays.equals(int[], int[])
 * @run main ArraysWitupTest
 */

import java.util.Arrays;

public class ArraysWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    public static void main(String[] args) {
        int[] a = {1, 2, 3};
        int[] b = {1, 2, 3};

        check(Arrays.equals(a, a), "identity must be equal");
        check(Arrays.equals((int[]) null, (int[]) null), "null, null must be equal");
        check(!Arrays.equals(a, null), "non-null vs null must be unequal");
        check(!Arrays.equals(null, a), "null vs non-null must be unequal");
        check(Arrays.equals(new int[0], new int[0]), "empty arrays must be equal");
        check(Arrays.equals(a, b), "same content must be equal");
        check(Arrays.equals(b, a), "equality must be symmetric");
        check(!Arrays.equals(new int[]{1, 2}, new int[]{1, 2, 3}), "shorter array must be unequal");
        check(!Arrays.equals(new int[]{1, 2, 3}, new int[]{1, 2}), "longer array must be unequal");
        check(!Arrays.equals(new int[]{0, 2, 3}, new int[]{1, 2, 3}), "first element difference must be unequal");
        check(!Arrays.equals(new int[]{1, 2, 4}, new int[]{1, 2, 3}), "last element difference must be unequal");
        check(!Arrays.equals(new int[]{-1}, new int[]{1}), "sign difference must be unequal");
        check(Arrays.equals(new int[]{Integer.MIN_VALUE, -1, Integer.MAX_VALUE},
                            new int[]{Integer.MIN_VALUE, -1, Integer.MAX_VALUE}),
              "extreme values must be equal");
    }
}
