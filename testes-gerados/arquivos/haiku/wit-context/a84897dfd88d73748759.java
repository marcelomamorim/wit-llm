/*
 * @test
 * @summary Witup-derived tests for Arrays.parallelSetAll(int[], IntUnaryOperator)
 * @run main ArraysWitupTest
 */

import java.util.Arrays;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testFillsArrayWithGeneratorValues();
        testEmptyArrayIsAccepted();
        testNullGeneratorThrowsNPE();
        testNullArrayThrowsNPE();
    }

    static void testFillsArrayWithGeneratorValues() {
        int[] a = new int[1000];
        Arrays.parallelSetAll(a, i -> i * 3);
        for (int i = 0; i < a.length; i++) {
            if (a[i] != i * 3) {
                throw new AssertionError("Unexpected value at index " + i + ": " + a[i]);
            }
        }
    }

    static void testEmptyArrayIsAccepted() {
        int[] a = new int[0];
        Arrays.parallelSetAll(a, i -> {
            throw new AssertionError("generator must not be invoked for empty array");
        });
        if (a.length != 0) {
            throw new AssertionError("Empty array length changed");
        }
    }

    static void testNullGeneratorThrowsNPE() {
        int[] a = new int[4];
        boolean thrown = false;
        try {
            Arrays.parallelSetAll(a, null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected NullPointerException for null generator");
        }
    }

    static void testNullArrayThrowsNPE() {
        boolean thrown = false;
        try {
            Arrays.parallelSetAll(null, i -> i);
        } catch (NullPointerException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected NullPointerException for null array");
        }
    }
}
