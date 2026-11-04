/*
 * @test
 * @summary Witup-derived checks for Arrays.binarySearch(short[], short)
 * @run main ArraysWitupTest
 */

import java.util.Arrays;

public class ArraysWitupTest {
    public static void main(String[] args) {
        short[] empty = new short[0];
        check(Arrays.binarySearch(empty, (short) 5) == -1, "empty array");

        short[] a = {1, 3, 5, 7};
        check(Arrays.binarySearch(a, (short) 5) == 2, "key found in middle");
        check(Arrays.binarySearch(a, (short) 1) == 0, "key found at first index");
        check(Arrays.binarySearch(a, (short) 7) == 3, "key found at last index");
        check(Arrays.binarySearch(a, (short) 0) == -1, "key below all elements");
        check(Arrays.binarySearch(a, (short) 10) == -5, "key above all elements");
        check(Arrays.binarySearch(a, (short) 4) == -3, "key between elements");

        short[] b = {-5, -1, 0, 2};
        check(Arrays.binarySearch(b, (short) -1) == 1, "negative key found");

        short[] c = {Short.MIN_VALUE, 0, Short.MAX_VALUE};
        check(Arrays.binarySearch(c, Short.MAX_VALUE) == 2, "Short.MAX_VALUE found");
        check(Arrays.binarySearch(c, Short.MIN_VALUE) == 0, "Short.MIN_VALUE found");

        short[] d = {2, 2, 2, 2};
        int r = Arrays.binarySearch(d, (short) 2);
        check(r >= 0 && r < d.length && d[r] == 2, "duplicates return a valid matching index");

        try {
            Arrays.binarySearch((short[]) null, (short) 1);
            check(false, "null array must throw NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
