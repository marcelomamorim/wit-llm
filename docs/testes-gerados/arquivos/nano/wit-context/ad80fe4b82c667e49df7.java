/*
 * @test
 * @summary Tests for java.util.Arrays.equals(int[], int[])
 * @run main ArraysWitupTest
 */

import java.util.Arrays;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testEqualsSameReference();
        testEqualsBothNull();
        testEqualsOneNull();
        testEqualsDifferentLengths();
        testEqualsEqualArrays();
        testEqualsDifferentArrays();
    }

    static void testEqualsSameReference() {
        int[] arr = {1, 2, 3};
        boolean result = Arrays.equals(arr, arr);
        if (!result) {
            throw new AssertionError("Expected true for same reference");
        }
    }

    static void testEqualsBothNull() {
        boolean result = Arrays.equals(null, null);
        if (result) {
            throw new AssertionError("Expected false when both null");
        }
    }

    static void testEqualsOneNull() {
        int[] arr = {1, 2, 3};
        boolean result1 = Arrays.equals(arr, null);
        boolean result2 = Arrays.equals(null, arr);
        if (result1 || result2) {
            throw new AssertionError("Expected false when one null");
        }
    }

    static void testEqualsDifferentLengths() {
        int[] arr1 = {1, 2, 3};
        int[] arr2 = {1, 2};
        boolean result = Arrays.equals(arr1, arr2);
        if (result) {
            throw new AssertionError("Expected false for different lengths");
        }
    }

    static void testEqualsEqualArrays() {
        int[] arr1 = {1, 2, 3};
        int[] arr2 = {1, 2, 3};
        boolean result = Arrays.equals(arr1, arr2);
        if (!result) {
            throw new AssertionError("Expected true for equal arrays");
        }
    }

    static void testEqualsDifferentArrays() {
        int[] arr1 = {1, 2, 3};
        int[] arr2 = {3, 2, 1};
        boolean result = Arrays.equals(arr1, arr2);
        if (result) {
            throw new AssertionError("Expected false for different arrays");
        }
    }
}
