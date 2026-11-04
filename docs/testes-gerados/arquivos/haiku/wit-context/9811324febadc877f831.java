/*
 * @test
 * @summary Verify Arrays.equals(T[], T[], Comparator) null comparator handling and observable comparison behavior
 * @run main ArraysWitupTest
 */

import java.util.Arrays;
import java.util.Comparator;

public class ArraysWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        Comparator<String> natural = Comparator.naturalOrder();

        boolean npeThrown = false;
        try {
            Arrays.equals(new String[] {"a"}, new String[] {"a"}, null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        check(npeThrown, "null comparator must throw NullPointerException");

        npeThrown = false;
        try {
            Arrays.equals(null, null, null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        check(npeThrown, "null comparator must throw NullPointerException even when arrays are null");

        check(Arrays.equals(null, null, natural), "same reference (both null) must be equal");

        String[] arr = new String[] {"a", "b"};
        check(Arrays.equals(arr, arr, natural), "identical reference must be equal");

        check(!Arrays.equals(null, new String[] {"a"}, natural), "null vs non-null must be not equal");
        check(!Arrays.equals(new String[] {"a"}, null, natural), "non-null vs null must be not equal");

        check(!Arrays.equals(new String[] {"a"}, new String[] {"a", "b"}, natural),
              "different lengths must be not equal");

        check(Arrays.equals(new String[] {"a", "B"}, new String[] {"A", "b"},
                            String.CASE_INSENSITIVE_ORDER),
              "elements equal under comparator must be equal");

        check(!Arrays.equals(new String[] {"a", "b"}, new String[] {"a", "c"}, natural),
              "differing elements must be not equal");

        check(Arrays.equals(new String[0], new String[0], natural),
              "two empty arrays must be equal");
    }
}
