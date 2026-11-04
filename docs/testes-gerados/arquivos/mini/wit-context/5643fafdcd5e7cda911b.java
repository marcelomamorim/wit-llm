/*
 * @test
 * @summary Test java.util.Arrays.equals(Object[], Object[]) for correctness and null handling
 * @run main ArraysWitupTest
 */

import java.util.Arrays;
import java.util.Objects;

public class ArraysWitupTest {
    public static void main(String[] args) {
        testEqualsSameReference();
        testEqualsBothNull();
        testEqualsOneNull();
        testEqualsDifferentLengths();
        testEqualsEqualArrays();
        testEqualsDifferentArrays();
    }

    private static void testEqualsSameReference() {
        Object[] arr = new Object[] {"a", "b", null};
        if (!Arrays.equals(arr, arr)) {
            throw new AssertionError("equals should return true for same array reference");
        }
    }

    private static void testEqualsBothNull() {
        Object[] a = null;
        Object[] b = null;
        if (!Arrays.equals(a, b)) {
            throw new AssertionError("equals should return true when both arrays are null");
        }
    }

    private static void testEqualsOneNull() {
        Object[] a = new Object[] {"a"};
        Object[] b = null;
        if (Arrays.equals(a, b)) {
            throw new AssertionError("equals should return false when one array is null");
        }
        if (Arrays.equals(b, a)) {
            throw new AssertionError("equals should return false when one array is null");
        }
    }

    private static void testEqualsDifferentLengths() {
        Object[] a = new Object[] {"a", "b"};
        Object[] b = new Object[] {"a"};
        if (Arrays.equals(a, b)) {
            throw new AssertionError("equals should return false for arrays of different lengths");
        }
    }

    private static void testEqualsEqualArrays() {
        Object[] a = new Object[] {"a", null, "c"};
        Object[] b = new Object[] {"a", null, "c"};
        if (!Arrays.equals(a, b)) {
            throw new AssertionError("equals should return true for arrays with equal elements including nulls");
        }
    }

    private static void testEqualsDifferentArrays() {
        Object[] a = new Object[] {"a", "b", "c"};
        Object[] b = new Object[] {"a", "x", "c"};
        if (Arrays.equals(a, b)) {
            throw new AssertionError("equals should return false for arrays with different elements");
        }
    }
}
