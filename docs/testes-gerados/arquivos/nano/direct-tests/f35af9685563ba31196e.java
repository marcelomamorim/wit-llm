/* @test    @summary Test for Arrays.equals(char[] a, char[] a2) method    @run main ArraysWitupTest */

import java.util.Arrays;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testEqualsWithIdenticalArrays();
        testEqualsWithNullArrays();
        testEqualsWithDifferentLengths();
        testEqualsWithDifferentContents();
        testEqualsWithEmptyArrays();
    }

    static void testEqualsWithIdenticalArrays() {
        char[] array1 = {'a', 'b', 'c'};
        char[] array2 = {'a', 'b', 'c'};
        boolean result = Arrays.equals(array1, array2);
        if (!result) {
            throw new AssertionError("Expected true for identical arrays");
        }
    }

    static void testEqualsWithNullArrays() {
        char[] array1 = null;
        char[] array2 = {'a', 'b', 'c'};
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
        char[] array1 = {'a', 'b'};
        char[] array2 = {'a', 'b', 'c'};
        boolean result = Arrays.equals(array1, array2);
        if (result) {
            throw new AssertionError("Expected false for arrays with different lengths");
        }
    }

    static void testEqualsWithDifferentContents() {
        char[] array1 = {'a', 'b', 'c'};
        char[] array2 = {'a', 'b', 'd'};
        boolean result = Arrays.equals(array1, array2);
        if (result) {
            throw new AssertionError("Expected false for arrays with different contents");
        }
    }

    static void testEqualsWithEmptyArrays() {
        char[] array1 = {};
        char[] array2 = {};
        boolean result = Arrays.equals(array1, array2);
        if (!result) {
            throw new AssertionError("Expected true for two empty arrays");
        }
    }
}
