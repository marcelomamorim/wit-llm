/*
 * @test
 * @summary Test java.util.Arrays.equals(float[], float[]) method
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
        float[] arr = {1.0f, 2.0f, 3.0f};
        if (!java.util.Arrays.equals(arr, arr)) {
            throw new AssertionError("equals should return true for same array reference");
        }
    }

    private static void testBothNull() {
        float[] a = null;
        float[] b = null;
        if (!java.util.Arrays.equals(a, b)) {
            throw new AssertionError("equals should return true for both null arrays");
        }
    }

    private static void testOneNull() {
        float[] a = null;
        float[] b = {1.0f};
        if (java.util.Arrays.equals(a, b)) {
            throw new AssertionError("equals should return false if one array is null");
        }
        if (java.util.Arrays.equals(b, a)) {
            throw new AssertionError("equals should return false if one array is null");
        }
    }

    private static void testDifferentLengths() {
        float[] a = {1.0f, 2.0f};
        float[] b = {1.0f, 2.0f, 3.0f};
        if (java.util.Arrays.equals(a, b)) {
            throw new AssertionError("equals should return false for arrays of different lengths");
        }
    }

    private static void testEqualArrays() {
        float[] a = {1.0f, Float.NaN, 3.0f, Float.POSITIVE_INFINITY, -0.0f};
        float[] b = {1.0f, Float.NaN, 3.0f, Float.POSITIVE_INFINITY, +0.0f};
        // Note: Float.NaN != Float.NaN but Arrays.equals treats NaNs as equal
        // Also, -0.0f and +0.0f are considered equal by Float.compare
        if (!java.util.Arrays.equals(a, b)) {
            throw new AssertionError("equals should return true for equal float arrays including NaN and infinity");
        }
    }

    private static void testDifferentArrays() {
        float[] a = {1.0f, 2.0f, 3.0f};
        float[] b = {1.0f, 2.0f, 4.0f};
        if (java.util.Arrays.equals(a, b)) {
            throw new AssertionError("equals should return false for arrays with different elements");
        }
    }
}
