/*
 * @test
 * @summary Verifies observable behavior of Arrays.equals(Object[], Object[])
 * @run main ArraysWitupTest
 */

import java.util.Arrays;

public class ArraysWitupTest {

    public static void main(String[] args) {
        check(Arrays.equals((Object[]) null, (Object[]) null), 1);
        check(!Arrays.equals((Object[]) null, new Object[0]), 2);
        check(!Arrays.equals(new Object[0], (Object[]) null), 3);
        check(Arrays.equals(new Object[0], new Object[0]), 4);

        Object[] same = {1, null, 2};
        check(Arrays.equals(same, same), 5);
        check(Arrays.equals(same, new Object[]{1, null, 2}), 6);
        check(!Arrays.equals(same, new Object[]{1, null, 2, 3}), 7);
        check(!Arrays.equals(new Object[]{1, 2, 3}, new Object[]{1, 2}), 8);
        check(!Arrays.equals(new Object[]{1, 2}, new Object[]{2, 1}), 9);
        check(!Arrays.equals(new Object[]{1, null}, new Object[]{1, 2}), 10);
        check(!Arrays.equals(new Object[]{null, 2}, new Object[]{1, 2}), 11);

        check(Arrays.equals(new Object[]{Integer.valueOf(1000)},
                            new Object[]{Integer.valueOf(1000)}), 12);

        check(!Arrays.equals(new Object[]{new int[]{1}},
                             new Object[]{new int[]{1}}), 13);

        Object[] x = {1, 2};
        Object[] y = {1, 3};
        check(Arrays.equals(x, y) == Arrays.equals(y, x), 14);
        check(!Arrays.equals(x, y), 15);
    }

    static void check(boolean condition, int id) {
        if (!condition) {
            throw new RuntimeException("Check failed: " + id);
        }
    }
}
