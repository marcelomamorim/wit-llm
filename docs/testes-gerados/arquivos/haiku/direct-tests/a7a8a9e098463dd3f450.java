/*
 * @test
 * @summary Verifies Arrays.parallelSetAll(double[], IntToDoubleFunction) fills every index deterministically and rejects null arguments
 * @run main ArraysWitupTest
 */

import java.util.Arrays;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testFillsAllIndices();
        testEmptyArray();
        testLargeArrayDeterministic();
        testNullGenerator();
        testNullArray();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testFillsAllIndices() {
        double[] array = new double[8];
        Arrays.parallelSetAll(array, i -> i * 0.5);
        for (int i = 0; i < array.length; i++) {
            check(array[i] == i * 0.5, "unexpected value at index " + i + ": " + array[i]);
        }
    }

    static void testEmptyArray() {
        double[] array = new double[0];
        Arrays.parallelSetAll(array, i -> {
            throw new IllegalStateException("generator must not be called for empty array");
        });
        check(array.length == 0, "empty array length changed");
    }

    static void testLargeArrayDeterministic() {
        int n = 20000;
        double[] array = new double[n];
        Arrays.parallelSetAll(array, i -> i * 2.0 + 1.0);
        for (int i = 0; i < n; i++) {
            check(array[i] == i * 2.0 + 1.0, "mismatch at index " + i);
        }
    }

    static void testNullGenerator() {
        double[] array = new double[4];
        try {
            Arrays.parallelSetAll(array, null);
            throw new RuntimeException("expected NullPointerException for null generator");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testNullArray() {
        try {
            Arrays.parallelSetAll(null, i -> (double) i);
            throw new RuntimeException("expected NullPointerException for null array");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
