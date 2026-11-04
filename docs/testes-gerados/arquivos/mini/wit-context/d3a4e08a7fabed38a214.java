/*
 * @test
 * @summary Deterministic tests for java.util.Arrays.equals(boolean[], boolean[])
 * @run main ArraysWitupTest
 */

public class ArraysWitupTest {
    public static void main(String[] args) {
        testEqualsSameReference();
        testEqualsNullArguments();
        testEqualsDifferentLengths();
        testEqualsEqualArrays();
        testEqualsDifferentArrays();
    }

    private static void testEqualsSameReference() {
        boolean[] arr = {true, false, true};
        if (!java.util.Arrays.equals(arr, arr)) {
            throw new AssertionError("equals should return true for same array reference");
        }
    }

    private static void testEqualsNullArguments() {
        boolean[] arr = {true, false};
        if (java.util.Arrays.equals(null, arr)) {
            throw new AssertionError("equals should return false if first array is null");
        }
        if (java.util.Arrays.equals(arr, null)) {
            throw new AssertionError("equals should return false if second array is null");
        }
        if (java.util.Arrays.equals(null, null) != true) {
            throw new AssertionError("equals should return true if both arrays are null");
        }
    }

    private static void testEqualsDifferentLengths() {
        boolean[] a = {true, false};
        boolean[] b = {true, false, true};
        if (java.util.Arrays.equals(a, b)) {
            throw new AssertionError("equals should return false for arrays of different lengths");
        }
    }

    private static void testEqualsEqualArrays() {
        boolean[] a = {true, false, true, false};
        boolean[] b = {true, false, true, false};
        if (!java.util.Arrays.equals(a, b)) {
            throw new AssertionError("equals should return true for equal arrays");
        }
    }

    private static void testEqualsDifferentArrays() {
        boolean[] a = {true, false, true};
        boolean[] b = {true, true, true};
        if (java.util.Arrays.equals(a, b)) {
            throw new AssertionError("equals should return false for arrays with different elements");
        }
    }
}
