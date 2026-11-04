/*
 * @test
 * @summary Test java.util.Arrays.fill(double[] a, double val) method
 * @run main java.util.ArraysWitupTest
 */

public class ArraysWitupTest {
    public static void main(String[] args) {
        testFillWithPositiveValue();
        testFillWithNegativeValue();
        testFillWithZero();
        testFillEmptyArray();
        testFillNullArray();
    }

    private static void testFillWithPositiveValue() {
        double[] arr = new double[5];
        java.util.Arrays.fill(arr, 3.14);
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != 3.14) {
                throw new RuntimeException("testFillWithPositiveValue failed at index " + i);
            }
        }
    }

    private static void testFillWithNegativeValue() {
        double[] arr = new double[3];
        java.util.Arrays.fill(arr, -2.71);
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != -2.71) {
                throw new RuntimeException("testFillWithNegativeValue failed at index " + i);
            }
        }
    }

    private static void testFillWithZero() {
        double[] arr = new double[4];
        java.util.Arrays.fill(arr, 0.0);
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != 0.0) {
                throw new RuntimeException("testFillWithZero failed at index " + i);
            }
        }
    }

    private static void testFillEmptyArray() {
        double[] arr = new double[0];
        java.util.Arrays.fill(arr, 1.23);
        if (arr.length != 0) {
            throw new RuntimeException("testFillEmptyArray failed: array length changed");
        }
    }

    private static void testFillNullArray() {
        try {
            java.util.Arrays.fill(null, 1.0);
            throw new RuntimeException("testFillNullArray failed: expected NullPointerException");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
