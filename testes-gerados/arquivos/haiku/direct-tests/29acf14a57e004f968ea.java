/*
 * @test
 * @summary Validates Arrays.parallelPrefix(T[], BinaryOperator) for prefix results, order, empty arrays, and null arguments
 * @run main ArraysWitupTest
 */

import java.util.Arrays;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testIntegerPrefixSums();
        testNonCommutativeOrder();
        testEmptyArrayUnchanged();
        testSingleElementUnchanged();
        testNullOperatorThrowsNPE();
        testNullArrayThrowsNPE();
        testLargeArrayMatchesSequential();
    }

    static void testIntegerPrefixSums() {
        Integer[] a = {1, 2, 3, 4};
        Arrays.parallelPrefix(a, (x, y) -> x + y);
        check(a[0] == 1 && a[1] == 3 && a[2] == 6 && a[3] == 10,
              "integer prefix sums incorrect");
    }

    static void testNonCommutativeOrder() {
        String[] s = {"a", "b", "c", "d"};
        Arrays.parallelPrefix(s, (x, y) -> x + y);
        check(s[0].equals("a") && s[1].equals("ab") && s[2].equals("abc") && s[3].equals("abcd"),
              "non-commutative prefix order incorrect");
    }

    static void testEmptyArrayUnchanged() {
        Integer[] a = new Integer[0];
        Arrays.parallelPrefix(a, (x, y) -> x + y);
        check(a.length == 0, "empty array length changed");
    }

    static void testSingleElementUnchanged() {
        Integer[] a = {42};
        Arrays.parallelPrefix(a, (x, y) -> x * y);
        check(a[0] == 42, "single element should be unchanged");
    }

    static void testNullOperatorThrowsNPE() {
        final Integer[] a = {1, 2, 3};
        expectNPE(() -> Arrays.parallelPrefix(a, null), "null operator must throw NPE");
        final Integer[] empty = new Integer[0];
        expectNPE(() -> Arrays.parallelPrefix(empty, null),
                  "null operator must throw NPE even for empty array");
    }

    static void testNullArrayThrowsNPE() {
        final String[] nul = null;
        expectNPE(() -> Arrays.parallelPrefix(nul, (x, y) -> x + y),
                  "null array must throw NPE");
    }

    static void testLargeArrayMatchesSequential() {
        int n = 100000;
        Integer[] a = new Integer[n];
        for (int i = 0; i < n; i++) {
            a[i] = 1;
        }
        Arrays.parallelPrefix(a, (x, y) -> x + y);
        for (int i = 0; i < n; i++) {
            check(a[i] == i + 1, "large prefix mismatch at index " + i);
        }
    }

    static void expectNPE(Runnable r, String msg) {
        try {
            r.run();
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException(msg);
    }

    static void check(boolean condition, String msg) {
        if (!condition) {
            throw new RuntimeException(msg);
        }
    }
}
