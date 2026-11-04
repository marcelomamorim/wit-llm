/*
 * @test
 * @summary Tests for java.util.Arrays.equals(float[], float[])
 * @run main ArraysWitupTest
 */

import java.util.Arrays;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testEqualsWithNulls();
        testEqualsWithIdenticalArrays();
        testEqualsWithDifferentLengths();
        testEqualsWithDifferentContents();
    }

    static void testEqualsWithNulls() {
        boolean result = Arrays.equals(null, null);
        if (result != true) {
            throw new AssertionError("Expected true when both arrays are null");
        }
        result = Arrays.equals(new float[] {1.0f, 2.0f}, null);
        if (result != false) {
            throw new AssertionError("Expected false when second array is null");
        }
        result = Arrays.equals(null, new float[] {1.0f, 2.0f});
        if (result != false) {
            throw new AssertionError("Expected false when first array is null");
        }
    }

    static void testEqualsWithIdenticalArrays() {
        float[] arr = {1.0f, 2.0f, 3.0f};
        boolean result = Arrays.equals(arr, arr);
        if (result != true) {
            throw new AssertionError("Expected true for identical arrays");
        }
    }

    static void testEqualsWithDifferentLengths() {
        float[] arr1 = {1.0f, 2.0f};
        float[] arr2 = {1.0f, 2.0f, 3.0f};
        boolean result = Arrays.equals(arr1, arr2);
        if (result != false) {
            throw new AssertionError("Expected false for arrays of different lengths");
        }
    }

    static void testEqualsWithDifferentContents() {
        float[] arr1 = {1.0f, 2.0f, 3.0f};
        float[] arr2 = {1.0f, 2.0f, 4.0f};
        boolean result = Arrays.equals(arr1, arr2);
        if (result != false) {
            throw new AssertionError("Expected false for arrays with different contents");
        }
    }
}
