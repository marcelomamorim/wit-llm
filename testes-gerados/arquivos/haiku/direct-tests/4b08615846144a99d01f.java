/*
 * @test
 * @summary Behavioral tests for Arrays.setAll(int[], IntUnaryOperator)
 * @run main ArraysWitupTest
 */

import java.util.Arrays;
import java.util.function.IntUnaryOperator;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testFillsWithGeneratorValues();
        testEmptyArrayDoesNotInvokeGenerator();
        testInvokesGeneratorInAscendingIndexOrder();
        testNullGeneratorThrowsNPE();
        testNullArrayThrowsNPE();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testFillsWithGeneratorValues() {
        int[] a = new int[5];
        Arrays.setAll(a, i -> i * i);
        int[] expected = {0, 1, 4, 9, 16};
        check(Arrays.equals(expected, a), "unexpected contents: " + Arrays.toString(a));
    }

    static void testEmptyArrayDoesNotInvokeGenerator() {
        int[] calls = new int[1];
        int[] a = new int[0];
        Arrays.setAll(a, i -> { calls[0]++; return i; });
        check(calls[0] == 0, "generator invoked for empty array");
        check(a.length == 0, "empty array length changed");
    }

    static void testInvokesGeneratorInAscendingIndexOrder() {
        int[] next = new int[1];
        int[] a = new int[4];
        Arrays.setAll(a, i -> {
            check(i == next[0], "expected index " + next[0] + " but got " + i);
            next[0]++;
            return i + 10;
        });
        check(next[0] == 4, "generator not invoked once per element");
        check(a[0] == 10 && a[3] == 13, "unexpected values: " + Arrays.toString(a));
    }

    static void testNullGeneratorThrowsNPE() {
        boolean thrown = false;
        try {
            Arrays.setAll(new int[3], (IntUnaryOperator) null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null generator");
    }

    static void testNullArrayThrowsNPE() {
        boolean thrown = false;
        try {
            Arrays.setAll((int[]) null, i -> i);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null array");
    }
}
