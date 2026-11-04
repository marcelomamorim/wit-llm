/*
 * @test
 * @summary Test for java.util.Arrays.equals(double[], double[])
 * @run main ArraysWitupTest
 */

import java.util.Arrays;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testEqualsWithIdenticalArrays();
        testEqualsWithNullArrays();
        testEqualsWithDifferentLengths();
        testEqualsWithDifferentValues();
    }

    static void testEqualsWithIdenticalArrays() {
        double[] arr1 = {1.0, 2.0, 3.0};
        double[] arr2 = {1.0, 2.0, 3.0};
        boolean result = Arrays.equals(arr1, arr2);
        if (!result) {
            throw new AssertionError("Expected true for identical arrays");
        }
    }

    static void testEqualsWithNullArrays() {
        double[] arr1 = null;
        double[] arr2 = {1.0, 2.0, 3.0};
        boolean result1 = Arrays.equals(arr1, arr2);
        if (result1) {
            throw new AssertionError("Expected false when first array is null");
        }
        boolean result2 = Arrays.equals(arr2, arr1);
        if (result2) {
            throw new AssertionError("Expected false when second array is null");
        }
        boolean result3 = Arrays.equals(null, null);
        if (result3) {
            throw new AssertionError("Expected true when both arrays are null");
        }
    }

    static void testEqualsWithDifferentLengths() {
        double[] arr1 = {1.0, 2.0};
        double[] arr2 = {1.0, 2.0, 3.0};
        boolean result = Arrays.equals(arr1, arr2);
        if (result) {
            throw new AssertionError("Expected false for arrays of different lengths");
        }
    }

    static void testEqualsWithDifferentValues() {
        double[] arr1 = {1.0, 2.0, 3.0};
        double[] arr2 = {1.0, 2.0, 4.0};
        boolean result = Arrays.equals(arr1, arr2);
        if (result) {
            throw new AssertionError("Expected false for arrays with different values");
        }
    }
}
