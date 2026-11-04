/*
 * @test
 * @summary Tests observable behavior of Arrays.binarySearch(Object[], Object)
 * @run main ArraysWitupTest
 */
import java.util.Arrays;

public class ArraysWitupTest {
    public static void main(String[] args) {
        Object[] a = {1, 3, 5, 7};
        check(Arrays.binarySearch(a, 5) == 2, "found middle");
        check(Arrays.binarySearch(a, 1) == 0, "found first");
        check(Arrays.binarySearch(a, 7) == 3, "found last");
        check(Arrays.binarySearch(a, 4) == -3, "insertion point 2");
        check(Arrays.binarySearch(a, 0) == -1, "before start");
        check(Arrays.binarySearch(a, 8) == -5, "after end");
        check(Arrays.binarySearch(new Object[0], "x") == -1, "empty array");

        String[] s = {"a", "c", "e"};
        check(Arrays.binarySearch(s, "c") == 1, "strings found");
        check(Arrays.binarySearch(s, "d") == -3, "strings insertion point");

        Object[] dup = {2, 2, 2};
        int r = Arrays.binarySearch(dup, 2);
        check(r >= 0 && r < 3, "duplicate index in range");

        boolean npe = false;
        try {
            Arrays.binarySearch((Object[]) null, 1);
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "null array throws NullPointerException");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
