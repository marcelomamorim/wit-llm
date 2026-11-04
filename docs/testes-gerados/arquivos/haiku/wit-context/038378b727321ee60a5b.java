/*
 * @test
 * @summary Witup-generated checks for Arrays.binarySearch(int[], int)
 * @run main ArraysWitupTest
 */
import java.util.Arrays;

public class ArraysWitupTest {
    public static void main(String[] args) {
        int[] a = {1, 3, 5, 7};
        check(Arrays.binarySearch(a, 5) == 2, "found middle");
        check(Arrays.binarySearch(a, 1) == 0, "found first");
        check(Arrays.binarySearch(a, 7) == 3, "found last");
        check(Arrays.binarySearch(a, 0) == -1, "key below all elements");
        check(Arrays.binarySearch(a, 4) == -3, "key between elements");
        check(Arrays.binarySearch(a, 8) == -5, "key above all elements");
        check(Arrays.binarySearch(new int[0], 4) == -1, "empty array");
        int[] d = {2, 2, 2};
        int r = Arrays.binarySearch(d, 2);
        check(r >= 0 && r < d.length && d[r] == 2, "duplicate key");
        boolean npe = false;
        try {
            Arrays.binarySearch((int[]) null, 1);
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "null array");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
