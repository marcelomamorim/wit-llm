/* @test    @summary Test for Arrays.equals(double[] a, double[] a2) method    @run main ArraysWitupTest */

import java.util.Arrays;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testEqualsWithIdenticalArrays();
        testEqualsWithDifferentArrays();
        testEqualsWithNullArrays();
        testEqualsWithDifferentLengths();
    }

    static void testEqualsWithIdenticalArrays() {
        double[] array1 = {1.0, 2.0, 3.0};
        double[] array2 = {1.0, 2.0, 3.0};
        boolean result = Arrays.equals(array1, array2);
        if (!result) {
            throw new AssertionError("Expected true for identical arrays");
        }
    }

    static void testEqualsWithDifferentArrays() {
        double[] array1 = {1.0, 2.0, 3.0};
        double[] array2 = {1.0, 2.0, 4.0};
        boolean result = Arrays.equals(array1, array2);
        if (result) {
            throw new AssertionError("Expected false for different arrays");
        }
    }

    static void testEqualsWithNullArrays() {
        double[] array1 = null;
        double[] array2 = {1.0, 2.0, 3.0};
        boolean result1 = Arrays.equals(array1, array2);
        if (result1) {
            throw new AssertionError("Expected false when first array is null");
        }
        double[] array3 = {1.0, 2.0, 3.0};
        boolean result2 = Arrays.equals(array3, null);
        if (result2) {
            throw new AssertionError("Expected false when second array is null");
        }
        boolean result3 = Arrays.equals(null, null);
        if (!result3) {
            throw new AssertionError("Expected true when both arrays are null");
        }
    }

    static void testEqualsWithDifferentLengths() {
        double[] array1 = {1.0, 2.0};
        double[] array2 = {1.0, 2.0, 3.0};
        boolean result = Arrays.equals(array1, array2);
        if (result) {
            throw new AssertionError("Expected false for arrays with different lengths");
        }
    }
}
