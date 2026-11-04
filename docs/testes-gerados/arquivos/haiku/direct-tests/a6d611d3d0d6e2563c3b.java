/*
 * @test
 * @summary Verify Arrays.fill(int[], int, int, int) range semantics
 * @run main ArraysWitupTest
 */
import java.util.Arrays;

public class ArraysWitupTest {

    public static void main(String[] args) {
        int[] a = {1, 2, 3, 4, 5};
        Arrays.fill(a, 1, 4, 9);
        check(Arrays.equals(a, new int[]{1, 9, 9, 9, 5}), "partial fill");

        Arrays.fill(a, 2, 2, 7);
        check(Arrays.equals(a, new int[]{1, 9, 9, 9, 5}), "empty range leaves array unchanged");

        Arrays.fill(a, 5, 5, 3);
        check(Arrays.equals(a, new int[]{1, 9, 9, 9, 5}), "empty range at end leaves array unchanged");

        Arrays.fill(a, 0, 5, -1);
        check(Arrays.equals(a, new int[]{-1, -1, -1, -1, -1}), "full fill");

        int[] empty = new int[0];
        Arrays.fill(empty, 0, 0, 1);
        check(empty.length == 0, "empty array fill");

        int[] b = {1, 2, 3};
        int[] before = b.clone();
        expectRejected(b, 2, 1);
        check(Arrays.equals(b, before), "no partial write when fromIndex > toIndex");

        expectRejected(b, -1, 2);
        check(Arrays.equals(b, before), "no partial write when fromIndex < 0");

        expectRejected(b, 0, 4);
        check(Arrays.equals(b, before), "no partial write when toIndex > length");
    }

    static void expectRejected(int[] arr, int from, int to) {
        try {
            Arrays.fill(arr, from, to, 0);
        } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
            return;
        }
        throw new RuntimeException("expected rejection for range [" + from + ", " + to + ")");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
