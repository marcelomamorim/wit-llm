/*
 * @test
 * @summary Verify Arrays.setAll(double[], IntToDoubleFunction) fills values, call order, and null handling
 * @run main ArraysWitupTest
 */

import java.util.Arrays;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testFillsValues();
        testEmptyArrayDoesNotInvokeGenerator();
        testGeneratorCalledInIndexOrder();
        testNullGeneratorThrowsNPE();
        testNullArrayThrowsNPE();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testFillsValues() {
        double[] array = new double[5];
        Arrays.setAll(array, i -> i * 1.5);
        for (int i = 0; i < array.length; i++) {
            check(array[i] == i * 1.5, "unexpected value at index " + i + ": " + array[i]);
        }
    }

    static void testEmptyArrayDoesNotInvokeGenerator() {
        double[] array = new double[0];
        int[] calls = {0};
        Arrays.setAll(array, i -> {
            calls[0]++;
            return 1.0;
        });
        check(calls[0] == 0, "generator invoked for empty array");
    }

    static void testGeneratorCalledInIndexOrder() {
        double[] array = new double[4];
        int[] next = {0};
        Arrays.setAll(array, i -> {
            if (i != next[0]) {
                return -1.0;
            }
            next[0]++;
            return i * 10.0;
        });
        check(next[0] == 4, "generator not called exactly once per index");
        for (int i = 0; i < array.length; i++) {
            check(array[i] == i * 10.0, "index order or value wrong at " + i);
        }
    }

    static void testNullGeneratorThrowsNPE() {
        boolean thrown = false;
        try {
            Arrays.setAll(new double[3], null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null generator");
    }

    static void testNullArrayThrowsNPE() {
        boolean thrown = false;
        try {
            Arrays.setAll((double[]) null, i -> i * 1.0);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "expected NullPointerException for null array");
    }
}
