/* @test    @summary Test for Arrays.equals(int[] a, int[] a2) method    @run main ArraysWitupTest */

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
        int[] array1 = {1, 2, 3};
        int[] array2 = {1, 2, 3};
        boolean result = Arrays.equals(array1, array2);
        if (!result) {
            throw new AssertionError("Expected true for identical arrays");
        }
    }

    static void testEqualsWithNullArrays() {
        int[] array1 = null;
        int[] array2 = {1, 2, 3};
        boolean result1 = Arrays.equals(array1, array2);
        int[] array3 = {1, 2, 3};
        boolean result2 = Arrays.equals(array3, null);
        boolean result3 = Arrays.equals(null, null);
        if (result1 || result2 || !result3) {
            throw new AssertionError("Expected false when one array is null, true when both are null");
        }
    }

    static void testEqualsWithDifferentLengths() {
        int[] array1 = {1, 2, 3};
        int[] array2 = {1, 2, 3, 4};
        boolean result = Arrays.equals(array1, array2);
        if (result) {
            throw new AssertionError("Expected false for arrays with different lengths");
        }
    }

    static void testEqualsWithDifferentContents() {
        int[] array1 = {1, 2, 3};
        int[] array2 = {1, 2, 4};
        boolean result = Arrays.equals(array1, array2);
        if (result) {
            throw new AssertionError("Expected false for arrays with different contents");
        }
    }

    static void testEqualsWithSameReference() {
        int[] array1 = {1, 2, 3};
        boolean result = Arrays.equals(array1, array1);
        if (!result) {
            throw new AssertionError("Expected true when comparing the same array reference");
        }
    }
}
