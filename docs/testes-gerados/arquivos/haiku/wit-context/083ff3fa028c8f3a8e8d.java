/*
 * @test
 * @summary Observable behavior of Arrays.binarySearch(T[], T, Comparator)
 * @run main ArraysWitupTest
 */
import java.util.Arrays;
import java.util.Comparator;

public class ArraysWitupTest {
    public static void main(String[] args) {
        String[] words = {"a", "b", "c"};
        check(Arrays.binarySearch(words, "B", String.CASE_INSENSITIVE_ORDER) == 1, "case-insensitive hit");
        check(Arrays.binarySearch(words, "bb", String.CASE_INSENSITIVE_ORDER) == -3, "case-insensitive insertion point");

        Integer[] nums = {1, 3, 5};
        check(Arrays.binarySearch(nums, 3, null) == 1, "natural ordering hit with null comparator");
        check(Arrays.binarySearch(nums, 4, null) == -3, "natural ordering insertion point with null comparator");
        check(Arrays.binarySearch(nums, 0, null) == -1, "key below first element");
        check(Arrays.binarySearch(nums, 6, null) == -4, "key above last element");

        Integer[] empty = new Integer[0];
        check(Arrays.binarySearch(empty, 1, null) == -1, "empty array");

        Integer[] desc = {5, 3, 1};
        Comparator<Integer> rev = Comparator.reverseOrder();
        check(Arrays.binarySearch(desc, 3, rev) == 1, "reverse comparator hit");

        boolean npe = false;
        try {
            Arrays.binarySearch((String[]) null, "a", null);
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
