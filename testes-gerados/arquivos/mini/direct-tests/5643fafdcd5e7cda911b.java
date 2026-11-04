/*
 * @test
 * @summary Test java.util.Arrays.equals(Object[], Object[])
 * @run main ArraysWitupTest
 */

import java.util.Arrays;
import java.util.Objects;

public class ArraysWitupTest {
    public static void main(String[] args) {
        testSameReference();
        testBothNull();
        testOneNull();
        testDifferentLengths();
        testEqualArrays();
        testDifferentArrays();
        testArraysWithNullElements();
    }

    private static void testSameReference() {
        Object[] arr = new Object[] {"a", "b", "c"};
        if (!Arrays.equals(arr, arr)) {
            throw new AssertionError("equals should return true for same array reference");
        }
    }

    private static void testBothNull() {
        Object[] a = null;
        Object[] b = null;
        if (!Arrays.equals(a, b)) {
            throw new AssertionError("equals should return true for both null arrays");
        }
    }

    private static void testOneNull() {
        Object[] a = null;
        Object[] b = new Object[] {"a"};
        if (Arrays.equals(a, b)) {
            throw new AssertionError("equals should return false if one array is null");
        }
        if (Arrays.equals(b, a)) {
            throw new AssertionError("equals should return false if one array is null");
        }
    }

    private static void testDifferentLengths() {
        Object[] a = new Object[] {"a", "b"};
        Object[] b = new Object[] {"a"};
        if (Arrays.equals(a, b)) {
            throw new AssertionError("equals should return false for arrays of different lengths");
        }
    }

    private static void testEqualArrays() {
        Object[] a = new Object[] {"a", "b", "c"};
        Object[] b = new Object[] {"a", "b", "c"};
        if (!Arrays.equals(a, b)) {
            throw new AssertionError("equals should return true for equal arrays");
        }
    }

    private static void testDifferentArrays() {
        Object[] a = new Object[] {"a", "b", "c"};
        Object[] b = new Object[] {"a", "x", "c"};
        if (Arrays.equals(a, b)) {
            throw new AssertionError("equals should return false for arrays with different elements");
        }
    }

    private static void testArraysWithNullElements() {
        Object[] a = new Object[] {"a", null, "c"};
        Object[] b = new Object[] {"a", null, "c"};
        Object[] c = new Object[] {"a", "b", "c"};

        if (!Arrays.equals(a, b)) {
            throw new AssertionError("equals should return true for arrays with matching null elements");
        }
        if (Arrays.equals(a, c)) {
            throw new AssertionError("equals should return false when null elements differ");
        }
    }
}
