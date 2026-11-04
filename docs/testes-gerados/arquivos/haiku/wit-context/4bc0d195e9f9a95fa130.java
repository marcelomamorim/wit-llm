/*
 * @test
 * @summary Witup-generated tests for Arrays.setAll(double[], IntToDoubleFunction)
 * @run main ArraysWitupTest
 */

import java.util.Arrays;
import java.util.function.IntToDoubleFunction;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testFillsValuesInOrder();
        testEmptyArrayDoesNotInvokeGenerator();
        testNullGeneratorThrowsNullPointerException();
        testNullArrayThrowsNullPointerException();
    }

    static void testFillsValuesInOrder() {
        double[] a = new double[5];
        IntToDoubleFunction gen = i -> i * 1.5;
        Arrays.setAll(a, gen);
        for (int i = 0; i < a.length; i++) {
            if (a[i] != i * 1.5) {
                throw new AssertionError("index " + i + ": " + a[i]);
            }
        }
    }

    static void testEmptyArrayDoesNotInvokeGenerator() {
        final int[] calls = {0};
        Arrays.setAll(new double[0], i -> {
            calls[0]++;
            return 0.0;
        });
        if (calls[0] != 0) {
            throw new AssertionError("generator invoked " + calls[0] + " times");
        }
    }

    static void testNullGeneratorThrowsNullPointerException() {
        try {
            Arrays.setAll(new double[3], null);
        } catch (NullPointerException e) {
            return;
        }
        throw new AssertionError("expected NullPointerException for null generator");
    }

    static void testNullArrayThrowsNullPointerException() {
        try {
            Arrays.setAll((double[]) null, i -> 1.0);
        } catch (NullPointerException e) {
            return;
        }
        throw new AssertionError("expected NullPointerException for null array");
    }
}
