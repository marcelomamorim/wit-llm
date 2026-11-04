/* @test    @summary Test for Arrays.equals(Object[] a, Object[] a2) method    @run main ArraysWitupTest */

import java.util.Arrays;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testEqualsWithIdenticalArrays();
        testEqualsWithDifferentArrays();
        testEqualsWithNullArrays();
        testEqualsWithOneNullArray();
        testEqualsWithBothNull();
    }

    static void testEqualsWithIdenticalArrays() {
        Object[] array1 = {"a", "b", "c"};
        Object[] array2 = {"a", "b", "c"};
        boolean result = Arrays.equals(array1, array2);
        if (!result) {
            throw new AssertionError("Expected arrays to be equal");
        }
    }

    static void testEqualsWithDifferentArrays() {
        Object[] array1 = {"a", "b", "c"};
        Object[] array2 = {"a", "b", "d"};
        boolean result = Arrays.equals(array1, array2);
        if (result) {
            throw new AssertionError("Expected arrays to be not equal");
        }
    }

    static void testEqualsWithNullArrays() {
        Object[] array1 = {"a", "b"};
        Object[] array2 = null;
        boolean result = Arrays.equals(array1, array2);
        if (result) {
            throw new AssertionError("Expected arrays to be not equal when second is null");
        }
        result = Arrays.equals(null, array2);
        if (result) {
            throw new AssertionError("Expected arrays to be not equal when first is null");
        }
    }

    static void testEqualsWithOneNullArray() {
        Object[] array1 = null;
        Object[] array2 = {"a", "b"};
        boolean result = Arrays.equals(array1, array2);
        if (result) {
            throw new AssertionError("Expected arrays to be not equal when first is null");
        }
    }

    static void testEqualsWithBothNull() {
        Object[] array1 = null;
        Object[] array2 = null;
        boolean result = Arrays.equals(array1, array2);
        if (!result) {
            throw new AssertionError("Expected null arrays to be equal");
        }
    }
}
