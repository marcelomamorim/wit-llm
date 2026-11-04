/*
 * @test
 * @summary Test java.util.Arrays.equals(double[], double[])
 * @run main ArraysWitupTest
 */

public class ArraysWitupTest {
    public static void main(String[] args) {
        testSameReference();
        testBothNull();
        testOneNull();
        testDifferentLengths();
        testEqualArrays();
        testDifferentArrays();
    }

    private static void testSameReference() {
        double[] arr = {1.0, 2.0, 3.0};
        if (!java.util.Arrays.equals(arr, arr)) {
            throw new AssertionError("equals should return true for same array reference");
        }
    }

    private static void testBothNull() {
        double[] a = null;
        double[] b = null;
        if (!java.util.Arrays.equals(a, b)) {
            throw new AssertionError("equals should return true for both null arrays");
        }
    }

    private static void testOneNull() {
        double[] a = null;
        double[] b = {1.0};
        if (java.util.Arrays.equals(a, b)) {
            throw new AssertionError("equals should return false if one array is null");
        }
        if (java.util.Arrays.equals(b, a)) {
            throw new AssertionError("equals should return false if one array is null");
        }
    }

    private static void testDifferentLengths() {
        double[] a = {1.0, 2.0};
        double[] b = {1.0, 2.0, 3.0};
        if (java.util.Arrays.equals(a, b)) {
            throw new AssertionError("equals should return false for arrays of different lengths");
        }
    }

    private static void testEqualArrays() {
        double[] a = {1.0, Double.NaN, 3.0, Double.POSITIVE_INFINITY, -0.0};
        double[] b = {1.0, Double.NaN, 3.0, Double.POSITIVE_INFINITY, -0.0};
        if (!java.util.Arrays.equals(a, b)) {
            throw new AssertionError("equals should return true for equal arrays including special double values");
        }
    }

    private static void testDifferentArrays() {
        double[] a = {1.0, 2.0, 3.0};
        double[] b = {1.0, 2.0, 4.0};
        if (java.util.Arrays.equals(a, b)) {
            throw new AssertionError("equals should return false for arrays with different elements");
        }
    }
}
