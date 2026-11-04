/* @test    @summary Test for Arrays.equals(float[], float[]) method    @run main ArraysWitupTest */

import java.util.Arrays;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testEqualsWithIdenticalArrays();
        testEqualsWithNullArrays();
        testEqualsWithDifferentLengths();
        testEqualsWithDifferentContents();
        testEqualsWithSameReference();
    }

    static void testEqualsWithIdenticalArrays() {
        float[] array1 = {1.0f, 2.0f, 3.0f};
        float[] array2 = {1.0f, 2.0f, 3.0f};
        boolean result = Arrays.equals(array1, array2);
        if (!result) {
            throw new AssertionError("Expected true for identical arrays");
        }
    }

    static void testEqualsWithNullArrays() {
        float[] array1 = null;
        float[] array2 = {1.0f, 2.0f, 3.0f};
        boolean result1 = Arrays.equals(array1, array2);
        if (result1) {
            throw new AssertionError("Expected false when first array is null");
        }
        boolean result2 = Arrays.equals(array2, null);
        if (result2) {
            throw new AssertionError("Expected false when second array is null");
        }
        boolean result3 = Arrays.equals(null, null);
        if (!result3) {
            throw new AssertionError("Expected true when both arrays are null");
        }
    }

    static void testEqualsWithDifferentLengths() {
        float[] array1 = {1.0f, 2.0f};
        float[] array2 = {1.0f, 2.0f, 3.0f};
        boolean result = Arrays.equals(array1, array2);
        if (result) {
            throw new AssertionError("Expected false for arrays with different lengths");
        }
    }

    static void testEqualsWithDifferentContents() {
        float[] array1 = {1.0f, 2.0f, 3.0f};
        float[] array2 = {1.0f, 2.0f, 4.0f};
        boolean result = Arrays.equals(array1, array2);
        if (result) {
            throw new AssertionError("Expected false for arrays with different contents");
        }
    }

    static void testEqualsWithSameReference() {
        float[] array = {1.0f, 2.0f, 3.0f};
        boolean result = Arrays.equals(array, array);
        if (!result) {
            throw new AssertionError("Expected true when comparing array with itself");
        }
    }
}
