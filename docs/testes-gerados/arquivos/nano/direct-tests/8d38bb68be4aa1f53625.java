/* @test    @summary Test for Arrays.equals(byte[] a, byte[] a2) method    @run main ArraysWitupTest */

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
        byte[] array1 = {1, 2, 3};
        byte[] array2 = {1, 2, 3};
        boolean result = Arrays.equals(array1, array2);
        if (!result) {
            throw new AssertionError("Expected true for identical arrays");
        }
    }

    static void testEqualsWithNullArrays() {
        byte[] array1 = null;
        byte[] array2 = {1, 2, 3};
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
        byte[] array1 = {1, 2, 3};
        byte[] array2 = {1, 2};
        boolean result = Arrays.equals(array1, array2);
        if (result) {
            throw new AssertionError("Expected false for arrays with different lengths");
        }
    }

    static void testEqualsWithDifferentContents() {
        byte[] array1 = {1, 2, 3};
        byte[] array2 = {1, 2, 4};
        boolean result = Arrays.equals(array1, array2);
        if (result) {
            throw new AssertionError("Expected false for arrays with different contents");
        }
    }

    static void testEqualsWithSameReference() {
        byte[] array1 = {1, 2, 3};
        boolean result = Arrays.equals(array1, array1);
        if (!result) {
            throw new AssertionError("Expected true when comparing the same array reference");
        }
    }
}
