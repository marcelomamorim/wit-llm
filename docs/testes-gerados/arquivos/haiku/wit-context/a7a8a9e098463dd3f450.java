/*
 * @test
 * @summary Witup generated checks for Arrays.parallelSetAll(double[], IntToDoubleFunction)
 * @run main ArraysWitupTest
 */

import java.util.Arrays;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testFillsArrayWithGeneratedValues();
        testNullGeneratorThrowsNPE();
        testNullArrayThrowsNPE();
    }

    static void testFillsArrayWithGeneratedValues() {
        double[] a = new double[5];
        Arrays.parallelSetAll(a, i -> i * 1.5);
        for (int i = 0; i < a.length; i++) {
            check(a[i] == i * 1.5, "unexpected value at index " + i + ": " + a[i]);
        }
    }

    static void testNullGeneratorThrowsNPE() {
        double[] a = new double[3];
        try {
            Arrays.parallelSetAll(a, null);
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("NullPointerException expected for null generator");
    }

    static void testNullArrayThrowsNPE() {
        try {
            Arrays.parallelSetAll(null, i -> 1.0);
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("NullPointerException expected for null array");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
