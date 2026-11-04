/*
 * @test
 * @summary Functional checks for Arrays.binarySearch(int[], int)
 * @run main ArraysWitupTest
 */

public class ArraysWitupTest {

    private static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    public static void main(String[] args) {
        int[] a = {1, 3, 5, 7, 9};
        check(Arrays.binarySearch(a, 1) == 0, "first element");
        check(Arrays.binarySearch(a, 5) == 2, "middle element");
        check(Arrays.binarySearch(a, 9) == 4, "last element");
        check(Arrays.binarySearch(a, 0) == -1, "key before first");
        check(Arrays.binarySearch(a, 4) == -3, "key in gap");
        check(Arrays.binarySearch(a, 10) == -6, "key after last");

        check(Arrays.binarySearch(new int[0], 1) == -1, "empty array");

        int[] d = {1, 2, 2, 2, 3};
        int r = Arrays.binarySearch(d, 2);
        check(r >= 1 && r <= 3 && d[r] == 2, "duplicate key");

        int[] ext = {Integer.MIN_VALUE, 0, Integer.MAX_VALUE};
        check(Arrays.binarySearch(ext, Integer.MAX_VALUE) == 2, "extreme values");
        check(Arrays.binarySearch(ext, Integer.MIN_VALUE) == 0, "minimum value");

        boolean npe = false;
        try {
            Arrays.binarySearch((int[]) null, 1);
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "null array must throw NullPointerException");
    }
}
