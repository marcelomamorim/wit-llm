/*
 * @test
 * @summary Test for java.util.Arrays.equals(Object[], Object[])
 * @run main ArraysWitupTest
 */

import java.util.Arrays;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testEqualsWithNullArrays();
        testEqualsWithSameArray();
        testEqualsWithDifferentLengths();
        testEqualsWithDifferentElements();
    }

    static void testEqualsWithNullArrays() {
        boolean result1 = Arrays.equals(null, new Object[] {1});
        boolean result2 = Arrays.equals(new Object[] {1}, null);
        boolean result3 = Arrays.equals(null, null);
        if (result1 != false || result2 != false || result3 != true) {
            throw new AssertionError("Failed null array tests");
        }
    }

    static void testEqualsWithSameArray() {
        Object[] arr = {1, 2, 3};
        boolean result = Arrays.equals(arr, arr);
        if (result != true) {
            throw new AssertionError("Failed same array test");
        }
    }

    static void testEqualsWithDifferentLengths() {
        Object[] arr1 = {1, 2};
        Object[] arr2 = {1, 2, 3};
        boolean result = Arrays.equals(arr1, arr2);
        if (result != false) {
            throw new AssertionError("Failed different length test");
        }
    }

    static void testEqualsWithDifferentElements() {
        Object[] arr1 = {1, 2, 3};
        Object[] arr2 = {1, 2, 4};
        boolean result = Arrays.equals(arr1, arr2);
        if (result != false) {
            throw new AssertionError("Failed different elements test");
        }
    }
}
