/*
 * @test
 * @summary Verifies Arrays.binarySearch(T[], T, Comparator) returns observable indices for natural, custom, and empty-array cases.
 * @run main ArraysWitupTest
 */
import java.util.Arrays;
import java.util.Comparator;

public class ArraysWitupTest {

    public static void main(String[] args) {
        String[] a = {"a", "b", "c"};
        Comparator<String> nullCmp = null;

        check(Arrays.binarySearch(a, "a", nullCmp) == 0, "first element found at 0");
        check(Arrays.binarySearch(a, "b", nullCmp) == 1, "middle element found at 1");
        check(Arrays.binarySearch(a, "c", nullCmp) == 2, "last element found at 2");
        check(Arrays.binarySearch(a, "bb", nullCmp) == -3, "missing key between b and c gives -(2)-1");
        check(Arrays.binarySearch(a, "0", nullCmp) == -1, "key before first gives -(0)-1");
        check(Arrays.binarySearch(a, "z", nullCmp) == -4, "key after last gives -(3)-1");

        String[] r = {"c", "b", "a"};
        Comparator<String> rev = Comparator.reverseOrder();
        check(Arrays.binarySearch(r, "b", rev) == 1, "reverse comparator finds b at 1");
        check(Arrays.binarySearch(r, "d", rev) == -1, "reverse comparator: d inserts at 0");

        Comparator<String> ci = String.CASE_INSENSITIVE_ORDER;
        String[] mixed = {"a", "B", "c"};
        check(Arrays.binarySearch(mixed, "b", ci) == 1, "case-insensitive comparator finds b at 1");

        String[] empty = new String[0];
        check(Arrays.binarySearch(empty, "x", nullCmp) == -1, "empty array returns -1");

        boolean npe = false;
        try {
            Arrays.binarySearch((String[]) null, "a", nullCmp);
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
