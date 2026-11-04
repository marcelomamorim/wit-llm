/*
 * @test
 * @summary Test for Arrays.fill(double[] a, double val) method, covering normal and exceptional cases.
 * @run main ArraysWitupTest
 */

import java.util.Arrays;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testFillNormal();
        testFillNullArray();
        testFillEmptyArray();
        testFillLargeArray();
        testFillWithNaN();
    }

    static void testFillNormal() {
        double[] arr = {1.0, 2.0, 3.0};
        Arrays.fill(arr, 9.9);
        assert arr.length == 3 : "Array length mismatch";
        for (double v : arr) {
            assert v == 9.9 : "Value not filled correctly";
        }
    }

    static void testFillNullArray() {
        double[] arr = null;
        try {
            Arrays.fill(arr, 1.0);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testFillEmptyArray() {
        double[] arr = {};
        Arrays.fill(arr, 5.5);
        assert arr.length == 0 : "Empty array should remain empty";
    }

    static void testFillLargeArray() {
        double[] arr = new double[1000];
        Arrays.fill(arr, -1.0);
        for (double v : arr) {
            assert v == -1.0 : "Large array fill failed";
        }
    }

    static void testFillWithNaN() {
        double[] arr = {0.0, 1.0};
        Arrays.fill(arr, Double.NaN);
        for (double v : arr) {
            assert Double.isNaN(v) : "NaN fill failed";
        }
    }
}
