/*
 * @test
 * @summary Tests for Arrays.parallelPrefix(long[], LongBinaryOperator)
 * @run main ArraysWitupTest
 */

import java.util.Arrays;
import java.util.function.LongBinaryOperator;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testCumulativeSum();
        testEmptyArrayUnchanged();
        testSingleElementUnchanged();
        testNullOperatorThrowsNPE();
        testNullArrayThrowsNPE();
        testLargeArrayMatchesSequential();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testCumulativeSum() {
        long[] a = {1L, 2L, 3L, 4L};
        Arrays.parallelPrefix(a, (x, y) -> x + y);
        long[] expected = {1L, 3L, 6L, 10L};
        check(Arrays.equals(expected, a), "cumulative sum mismatch: " + Arrays.toString(a));
    }

    static void testEmptyArrayUnchanged() {
        long[] a = new long[0];
        Arrays.parallelPrefix(a, (x, y) -> x + y);
        check(a.length == 0, "empty array length changed");
    }

    static void testSingleElementUnchanged() {
        long[] a = {42L};
        Arrays.parallelPrefix(a, (x, y) -> x * y);
        check(a[0] == 42L, "single element changed: " + a[0]);
    }

    static void testNullOperatorThrowsNPE() {
        long[] a = {1L, 2L};
        boolean thrown = false;
        try {
            Arrays.parallelPrefix(a, null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null operator");
    }

    static void testNullArrayThrowsNPE() {
        boolean thrown = false;
        try {
            Arrays.parallelPrefix((long[]) null, (x, y) -> x + y);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null array");
    }

    static void testLargeArrayMatchesSequential() {
        int n = 100000;
        long[] a = new long[n];
        for (int i = 0; i < n; i++) {
            a[i] = (i * 31L) % 1000L;
        }
        long[] expected = a.clone();
        LongBinaryOperator max = (x, y) -> Math.max(x, y);
        for (int i = 1; i < n; i++) {
            expected[i] = max.applyAsLong(expected[i - 1], expected[i]);
        }
        Arrays.parallelPrefix(a, max);
        check(Arrays.equals(expected, a), "parallel prefix max differs from sequential result");
    }
}
