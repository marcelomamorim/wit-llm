/*
 * @test
 * @summary Deterministic tests for java.util.Arrays.equals(float[], float[])
 * @run main ArraysWitupTest
 */

public class ArraysWitupTest {
    public static void main(String[] args) {
        testEqualsSameReference();
        testEqualsNullArguments();
        testEqualsDifferentLengths();
        testEqualsEqualArrays();
        testEqualsDifferentArrays();
    }

    private static void testEqualsSameReference() {
        float[] arr = {1.0f, 2.0f, 3.0f};
        if (!java.util.Arrays.equals(arr, arr)) {
            throw new AssertionError("equals should return true for same array reference");
        }
    }

    private static void testEqualsNullArguments() {
        float[] arr = {1.0f, 2.0f};
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
        float[] a = {1.0f, 2.0f};
        float[] b = {1.0f, 2.0f, 3.0f};
        if (java.util.Arrays.equals(a, b)) {
            throw new AssertionError("equals should return false for arrays of different lengths");
        }
    }

    private static void testEqualsEqualArrays() {
        float[] a = {1.0f, Float.NaN, 3.0f, Float.POSITIVE_INFINITY, -0.0f};
        float[] b = {1.0f, Float.NaN, 3.0f, Float.POSITIVE_INFINITY, +0.0f};
        // Note: According to Float.equals semantics, NaN == NaN is true, but 0.0f and -0.0f are different
        // The Arrays.equals uses Float.floatToIntBits for comparison, so -0.0f and +0.0f are different
        boolean expected = false; // Because -0.0f and +0.0f differ in bits
        boolean result = java.util.Arrays.equals(a, b);
        if (result != expected) {
            throw new AssertionError("equals returned " + result + " but expected " + expected);
        }
    }

    private static void testEqualsDifferentArrays() {
        float[] a = {1.0f, 2.0f, 3.0f};
        float[] b = {1.0f, 2.0f, 4.0f};
        if (java.util.Arrays.equals(a, b)) {
            throw new AssertionError("equals should return false for arrays with different elements");
        }
    }
}
