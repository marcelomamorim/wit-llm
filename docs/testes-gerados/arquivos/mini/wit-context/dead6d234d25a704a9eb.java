/*
 * @test
 * @summary Test java.util.Arrays.equals(double[], double[]) method
 * @run main ArraysWitupTest
 */

public class ArraysWitupTest {
    public static void main(String[] args) {
        testEqualsSameReference();
        testEqualsNullArrays();
        testEqualsDifferentLengths();
        testEqualsEqualArrays();
        testEqualsDifferentArrays();
    }

    private static void testEqualsSameReference() {
        double[] arr = {1.0, 2.0, 3.0};
        if (!java.util.Arrays.equals(arr, arr)) {
            throw new AssertionError("equals should return true for same array reference");
        }
    }

    private static void testEqualsNullArrays() {
        double[] arr = {1.0, 2.0};
        if (java.util.Arrays.equals(null, arr)) {
            throw new AssertionError("equals should return false if first array is null and second is not");
        }
        if (java.util.Arrays.equals(arr, null)) {
            throw new AssertionError("equals should return false if second array is null and first is not");
        }
        if (java.util.Arrays.equals(null, null) != true) {
            throw new AssertionError("equals should return true if both arrays are null");
        }
    }

    private static void testEqualsDifferentLengths() {
        double[] a = {1.0, 2.0};
        double[] b = {1.0, 2.0, 3.0};
        if (java.util.Arrays.equals(a, b)) {
            throw new AssertionError("equals should return false for arrays of different lengths");
        }
    }

    private static void testEqualsEqualArrays() {
        double[] a = {1.0, 2.0, Double.NaN, 4.0};
        double[] b = {1.0, 2.0, Double.NaN, 4.0};
        if (!java.util.Arrays.equals(a, b)) {
            throw new AssertionError("equals should return true for arrays with same elements including NaN");
        }
    }

    private static void testEqualsDifferentArrays() {
        double[] a = {1.0, 2.0, 3.0};
        double[] b = {1.0, 2.0, 4.0};
        if (java.util.Arrays.equals(a, b)) {
            throw new AssertionError("equals should return false for arrays with different elements");
        }
    }
}
