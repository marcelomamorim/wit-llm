/*
 * @test
 * @summary Behavioral tests for Arrays.parallelPrefix(double[], DoubleBinaryOperator)
 * @run main ArraysWitupTest
 */

import java.util.Arrays;

public class ArraysWitupTest {

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testPrefixSum() {
        double[] a = {1.0, 2.0, 3.0, 4.0};
        Arrays.parallelPrefix(a, (x, y) -> x + y);
        check(a[0] == 1.0, "sum[0]");
        check(a[1] == 3.0, "sum[1]");
        check(a[2] == 6.0, "sum[2]");
        check(a[3] == 10.0, "sum[3]");
    }

    static void testPrefixMax() {
        double[] a = {3.0, 1.0, 4.0, 1.0, 5.0};
        Arrays.parallelPrefix(a, Math::max);
        double[] expected = {3.0, 3.0, 4.0, 4.0, 5.0};
        check(Arrays.equals(a, expected), "max prefix: " + Arrays.toString(a));
    }

    static void testSingleElementUnchanged() {
        double[] a = {5.5};
        Arrays.parallelPrefix(a, (x, y) -> x * y);
        check(a.length == 1 && a[0] == 5.5, "single element changed");
    }

    static void testEmptyArrayNoOp() {
        double[] a = new double[0];
        Arrays.parallelPrefix(a, (x, y) -> x + y);
        check(a.length == 0, "empty array length changed");
    }

    static void testNullOperatorThrowsNpe() {
        double[] a = {1.0, 2.0};
        boolean thrown = false;
        try {
            Arrays.parallelPrefix(a, null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null operator");
    }

    static void testNullArrayThrowsNpe() {
        boolean thrown = false;
        try {
            Arrays.parallelPrefix(null, (x, y) -> x + y);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null array");
    }

    static void testLargeArrayParallelPath() {
        int n = 100000;
        double[] a = new double[n];
        Arrays.fill(a, 1.0);
        Arrays.parallelPrefix(a, (x, y) -> x + y);
        for (int i = 0; i < n; i++) {
            if (a[i] != (double) (i + 1)) {
                throw new RuntimeException("large prefix mismatch at " + i + ": " + a[i]);
            }
        }
    }

    public static void main(String[] args) {
        testPrefixSum();
        testPrefixMax();
        testSingleElementUnchanged();
        testEmptyArrayNoOp();
        testNullOperatorThrowsNpe();
        testNullArrayThrowsNpe();
        testLargeArrayParallelPath();
    }
}
