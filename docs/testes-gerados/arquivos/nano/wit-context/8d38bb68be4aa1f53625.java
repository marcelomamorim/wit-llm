/*
 * @test
 * @summary Tests for java.util.Arrays.equals(byte[], byte[])
 * @run main ArraysWitupTest
 */

import java.util.Arrays;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testEqualsWithIdenticalArrays();
        testEqualsWithNullArrays();
        testEqualsWithDifferentLengths();
        testEqualsWithDifferentContents();
    }

    static void testEqualsWithIdenticalArrays() {
        byte[] arr1 = {1, 2, 3};
        byte[] arr2 = {1, 2, 3};
        boolean result = Arrays.equals(arr1, arr2);
        if (!result) {
            throw new AssertionError("Expected true for identical arrays");
        }
    }

    static void testEqualsWithNullArrays() {
        byte[] arr1 = null;
        byte[] arr2 = {1, 2, 3};
        boolean result1 = Arrays.equals(arr1, arr2);
        byte[] arr3 = {1, 2, 3};
        boolean result2 = Arrays.equals(arr3, null);
        boolean result3 = Arrays.equals(null, null);
        if (result1 || result2 || !result3) {
            throw new AssertionError("Expected false when one array is null, true when both null");
        }
    }

    static void testEqualsWithDifferentLengths() {
        byte[] arr1 = {1, 2, 3};
        byte[] arr2 = {1, 2, 3, 4};
        boolean result = Arrays.equals(arr1, arr2);
        if (result) {
            throw new AssertionError("Expected false for arrays of different lengths");
        }
    }

    static void testEqualsWithDifferentContents() {
        byte[] arr1 = {1, 2, 3};
        byte[] arr2 = {1, 2, 4};
        boolean result = Arrays.equals(arr1, arr2);
        if (result) {
            throw new AssertionError("Expected false for arrays with different contents");
        }
    }
}
