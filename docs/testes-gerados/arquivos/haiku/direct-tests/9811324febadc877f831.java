/*
 * @test
 * @summary Behavioral checks for Arrays.equals(T[], T[], Comparator)
 * @run main ArraysWitupTest
 */

import java.util.Arrays;
import java.util.Comparator;

public class ArraysWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    public static void main(String[] args) {
        // null comparator -> NullPointerException (Objects.requireNonNull first)
        boolean threw = false;
        try {
            Arrays.equals(new String[]{"a"}, new String[]{"a"}, null);
        } catch (NullPointerException e) {
            threw = true;
        }
        check(threw, "null comparator must throw NPE");

        // null comparator is checked even when both arrays are null
        threw = false;
        try {
            Arrays.equals(null, null, null);
        } catch (NullPointerException e) {
            threw = true;
        }
        check(threw, "null comparator must throw NPE even for null arrays");

        // both arrays null -> true
        check(Arrays.equals((String[]) null, (String[]) null, Comparator.naturalOrder()),
              "two null arrays should be equal");

        // one array null -> false
        check(!Arrays.equals((String[]) null, new String[]{"a"}, Comparator.naturalOrder()),
              "null first argument should not be equal");
        check(!Arrays.equals(new String[]{"a"}, (String[]) null, Comparator.naturalOrder()),
              "null second argument should not be equal");

        // empty arrays -> true
        check(Arrays.equals(new String[0], new String[0], Comparator.naturalOrder()),
              "empty arrays should be equal");

        // different lengths -> false
        check(!Arrays.equals(new Integer[]{1}, new Integer[]{1, 2}, Comparator.naturalOrder()),
              "different lengths should not be equal");

        // comparator returning 0 counts as equal elements
        check(Arrays.equals(new String[]{"a", "B"}, new String[]{"A", "b"},
                            String.CASE_INSENSITIVE_ORDER),
              "case-insensitive comparator should treat elements as equal");
        check(!Arrays.equals(new String[]{"a", "B"}, new String[]{"A", "b"},
                             Comparator.naturalOrder()),
              "natural order should distinguish case");

        // same reference: returns true without invoking the comparator
        Object[] same = new Object[]{"x"};
        Comparator<Object> throwing = (x, y) -> {
            throw new AssertionError("comparator must not be called");
        };
        check(Arrays.equals(same, same, throwing), "identical array reference should be equal");

        // short-circuit: stops at first mismatching element
        final int[] calls = {0};
        Comparator<Integer> counting = (x, y) -> {
            calls[0]++;
            return x.compareTo(y);
        };
        check(!Arrays.equals(new Integer[]{1, 2, 3}, new Integer[]{2, 9, 9}, counting),
              "arrays with different first element should not be equal");
        check(calls[0] == 1, "comparator should be called once before mismatch, was " + calls[0]);

        // equal arrays compare every element
        calls[0] = 0;
        check(Arrays.equals(new Integer[]{1, 2, 3}, new Integer[]{1, 2, 3}, counting),
              "equal integer arrays should be equal");
        check(calls[0] == 3, "comparator should be called for each element, was " + calls[0]);
    }
}
