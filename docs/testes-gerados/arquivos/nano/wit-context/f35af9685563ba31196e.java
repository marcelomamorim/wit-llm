/*
 * @test
 * @summary Tests for java.util.Arrays.equals(char[], char[])
 * @run main ArraysWitupTest
 */

import java.util.Arrays;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testEqualsSameReference();
        testEqualsBothNull();
        testEqualsOneNull();
        testEqualsDifferentLengths();
        testEqualsMismatch();
        testEqualsMatch();
        testEqualsOutOfBounds();
    }

    static void testEqualsSameReference() {
        char[] arr = {'a', 'b', 'c'};
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
        char[] arr = {'a'};
        boolean result1 = Arrays.equals(arr, null);
        boolean result2 = Arrays.equals(null, arr);
        if (result1 || result2) {
            throw new AssertionError("Expected false when one null");
        }
    }

    static void testEqualsDifferentLengths() {
        char[] arr1 = {'a', 'b'};
        char[] arr2 = {'a', 'b', 'c'};
        boolean result = Arrays.equals(arr1, arr2);
        if (result) {
            throw new AssertionError("Expected false for different lengths");
        }
    }

    static void testEqualsMismatch() {
        char[] arr1 = {'a', 'b', 'c'};
        char[] arr2 = {'a', 'x', 'c'};
        boolean result = Arrays.equals(arr1, arr2);
        if (result) {
            throw new AssertionError("Expected false for mismatch");
        }
    }

    static void testEqualsMatch() {
        char[] arr1 = {'a', 'b', 'c'};
        char[] arr2 = {'a', 'b', 'c'};
        boolean result = Arrays.equals(arr1, arr2);
        if (!result) {
            throw new AssertionError("Expected true for matching arrays");
        }
    }

    static void testEqualsOutOfBounds() {
        try {
            // Using range check conditions to simulate out-of-bounds scenario
            char[] a = {'a', 'b', 'c'};
            char[] a2 = {'a', 'b', 'c'};
            // No direct way to set indices, but test with nulls or mismatched lengths handled above
            // So, simulate by passing nulls or invalid arrays if needed
            // Here, just ensure no exception is thrown for valid arrays
            boolean result = Arrays.equals(a, a2);
            if (!result) {
                throw new AssertionError("Expected true for valid arrays");
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new AssertionError("Unexpected ArrayIndexOutOfBoundsException", e);
        }
    }
}
