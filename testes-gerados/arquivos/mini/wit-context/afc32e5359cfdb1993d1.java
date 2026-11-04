/*
 * @test
 * @summary Test deterministic behavior of java.util.Arrays.fill(double[], double)
 * @run main ArraysWitupTest
 */

public class ArraysWitupTest {
    public static void main(String[] args) {
        testFillWithNormalArray();
        testFillWithEmptyArray();
        testFillWithNaN();
        testFillWithPositiveInfinity();
        testFillWithNegativeInfinity();
        testFillWithZero();
        System.out.println("All tests passed.");
    }

    private static void testFillWithNormalArray() {
        double[] arr = new double[5];
        Arrays.fill(arr, 3.14);
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != 3.14) {
                throw new AssertionError("Element at index " + i + " expected to be 3.14 but was " + arr[i]);
            }
        }
    }

    private static void testFillWithEmptyArray() {
        double[] arr = new double[0];
        Arrays.fill(arr, 1.23);
        if (arr.length != 0) {
            throw new AssertionError("Empty array length changed");
        }
    }

    private static void testFillWithNaN() {
        double[] arr = new double[3];
        Arrays.fill(arr, Double.NaN);
        for (int i = 0; i < arr.length; i++) {
            if (!Double.isNaN(arr[i])) {
                throw new AssertionError("Element at index " + i + " expected to be NaN but was " + arr[i]);
            }
        }
    }

    private static void testFillWithPositiveInfinity() {
        double[] arr = new double[4];
        Arrays.fill(arr, Double.POSITIVE_INFINITY);
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != Double.POSITIVE_INFINITY) {
                throw new AssertionError("Element at index " + i + " expected to be POSITIVE_INFINITY but was " + arr[i]);
            }
        }
    }

    private static void testFillWithNegativeInfinity() {
        double[] arr = new double[2];
        Arrays.fill(arr, Double.NEGATIVE_INFINITY);
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != Double.NEGATIVE_INFINITY) {
                throw new AssertionError("Element at index " + i + " expected to be NEGATIVE_INFINITY but was " + arr[i]);
            }
        }
    }

    private static void testFillWithZero() {
        double[] arr = new double[3];
        Arrays.fill(arr, 0.0);
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != 0.0) {
                throw new AssertionError("Element at index " + i + " expected to be 0.0 but was " + arr[i]);
            }
        }
    }
}
