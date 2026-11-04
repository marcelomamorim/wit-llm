/*
 * @test
 * @summary Witup-derived tests for Arrays.parallelPrefix null handling and prefix semantics
 * @run main ArraysWitupTest
 */

import java.util.Arrays;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testNullOperatorThrowsNPE();
        testNullArrayThrowsNPE();
        testEmptyArrayUnchanged();
        testSmallPrefix();
        testLargePrefixMatchesSequential();
    }

    static void testNullOperatorThrowsNPE() {
        Integer[] a = {1, 2, 3};
        try {
            Arrays.parallelPrefix(a, null);
            throw new AssertionError("expected NullPointerException for null op");
        } catch (NullPointerException expected) {
        }
    }

    static void testNullArrayThrowsNPE() {
        try {
            Arrays.parallelPrefix((Integer[]) null, (x, y) -> x + y);
            throw new AssertionError("expected NullPointerException for null array");
        } catch (NullPointerException expected) {
        }
    }

    static void testEmptyArrayUnchanged() {
        Integer[] a = {};
        Arrays.parallelPrefix(a, (x, y) -> x + y);
        if (a.length != 0) {
            throw new AssertionError("empty array length changed");
        }
    }

    static void testSmallPrefix() {
        Integer[] a = {1, 2, 3, 4, 5};
        Arrays.parallelPrefix(a, (x, y) -> x + y);
        Integer[] expected = {1, 3, 6, 10, 15};
        check(expected, a);
    }

    static void testLargePrefixMatchesSequential() {
        int n = 100000;
        Integer[] a = new Integer[n];
        Integer[] expected = new Integer[n];
        for (int i = 0; i < n; i++) {
            a[i] = i + 1;
        }
        int sum = 0;
        for (int i = 0; i < n; i++) {
            sum += i + 1;
            expected[i] = sum;
        }
        Arrays.parallelPrefix(a, (x, y) -> x + y);
        check(expected, a);
    }

    static void check(Integer[] expected, Integer[] actual) {
        if (expected.length != actual.length) {
            throw new AssertionError("length mismatch");
        }
        for (int i = 0; i < expected.length; i++) {
            if (!expected[i].equals(actual[i])) {
                throw new AssertionError("mismatch at " + i + ": expected "
                        + expected[i] + " but was " + actual[i]);
            }
        }
    }
}
