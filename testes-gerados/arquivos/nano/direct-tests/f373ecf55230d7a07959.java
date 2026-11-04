/* @test    @summary Test for Arrays.equals(short[], short[]) method    @run main ArraysWitupTest */

import java.util.Arrays;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testEqualsWithIdenticalArrays();
        testEqualsWithDifferentArrays();
        testEqualsWithNullArrays();
        testEqualsWithDifferentLengths();
    }

    static void testEqualsWithIdenticalArrays() {
        short[] array1 = {1, 2, 3};
        short[] array2 = {1, 2, 3};
        boolean result = Arrays.equals(array1, array2);
        if (!result) {
            throw new AssertionError("Expected true for identical arrays");
        }
    }

    static void testEqualsWithDifferentArrays() {
        short[] array1 = {1, 2, 3};
        short[] array2 = {4, 5, 6};
        boolean result = Arrays.equals(array1, array2);
        if (result) {
            throw new AssertionError("Expected false for different arrays");
        }
    }

    static void testEqualsWithNullArrays() {
        short[] array1 = null;
        short[] array2 = {1, 2, 3};
        boolean result1 = Arrays.equals(array1, array2);
        if (result1) {
            throw new AssertionError("Expected false when first array is null");
        }
        boolean result2 = Arrays.equals(array2, array1);
        if (result2) {
            throw new AssertionError("Expected false when second array is null");
        }
        boolean result3 = Arrays.equals(null, null);
        if (!result3) {
            throw new AssertionError("Expected true when both arrays are null");
        }
    }

    static void testEqualsWithDifferentLengths() {
        short[] array1 = {1, 2};
        short[] array2 = {1, 2, 3};
        boolean result = Arrays.equals(array1, array2);
        if (result) {
            throw new AssertionError("Expected false for arrays of different lengths");
        }
    }
}
