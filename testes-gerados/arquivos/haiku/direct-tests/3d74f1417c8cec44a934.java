/*
 * @test
 * @summary Behavioral tests for Arrays.parallelPrefix(int[], IntBinaryOperator)
 * @run main ArraysWitupTest
 */

import java.util.Arrays;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testSmallSum();
        testMax();
        testLargeArrayParallelPath();
        testEmptyArrayIsNoOp();
        testNullOperatorThrowsNPE();
        testNullOperatorWithEmptyArrayThrowsNPE();
        testNullArrayThrowsNPE();
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testSmallSum() {
        int[] a = {1, 2, 3, 4};
        Arrays.parallelPrefix(a, (x, y) -> x + y);
        int[] expected = {1, 3, 6, 10};
        check(Arrays.equals(a, expected), "small sum prefix mismatch: " + Arrays.toString(a));
    }

    static void testMax() {
        int[] a = {3, 1, 4, 1, 5};
        Arrays.parallelPrefix(a, (x, y) -> Math.max(x, y));
        int[] expected = {3, 3, 4, 4, 5};
        check(Arrays.equals(a, expected), "max prefix mismatch: " + Arrays.toString(a));
    }

    static void testLargeArrayParallelPath() {
        int n = 100000;
        int[] a = new int[n];
        Arrays.fill(a, 1);
        Arrays.parallelPrefix(a, (x, y) -> x + y);
        for (int i = 0; i < n; i++) {
            check(a[i] == i + 1, "large prefix mismatch at index " + i + ": " + a[i]);
        }
    }

    static void testEmptyArrayIsNoOp() {
        int[] a = new int[0];
        Arrays.parallelPrefix(a, (x, y) -> x + y);
        check(a.length == 0, "empty array length changed");
    }

    static void testNullOperatorThrowsNPE() {
        int[] a = {1, 2, 3};
        boolean thrown = false;
        try {
            Arrays.parallelPrefix(a, null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null operator");
    }

    static void testNullOperatorWithEmptyArrayThrowsNPE() {
        int[] a = new int[0];
        boolean thrown = false;
        try {
            Arrays.parallelPrefix(a, null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null operator with empty array");
    }

    static void testNullArrayThrowsNPE() {
        boolean thrown = false;
        try {
            Arrays.parallelPrefix((int[]) null, (x, y) -> x + y);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null array");
    }
}
