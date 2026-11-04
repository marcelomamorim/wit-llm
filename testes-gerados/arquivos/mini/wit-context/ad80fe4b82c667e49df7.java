/*
 * @test
 * @summary Deterministic tests for java.util.Arrays.equals(int[], int[])
 * @run main ArraysWitupTest
 */

public class ArraysWitupTest {
    public static void main(String[] args) {
        testEqualsSameArray();
        testEqualsBothNull();
        testEqualsOneNull();
        testEqualsDifferentLengths();
        testEqualsEqualArrays();
        testEqualsDifferentArrays();
    }

    private static void testEqualsSameArray() {
        int[] arr = {1, 2, 3};
        if (!java.util.Arrays.equals(arr, arr)) {
            throw new AssertionError("equals should return true when both arrays are the same instance");
        }
    }

    private static void testEqualsBothNull() {
        int[] a = null;
        int[] b = null;
        if (!java.util.Arrays.equals(a, b)) {
            throw new AssertionError("equals should return true when both arrays are null");
        }
    }

    private static void testEqualsOneNull() {
        int[] a = null;
        int[] b = {1, 2, 3};
        if (java.util.Arrays.equals(a, b)) {
            throw new AssertionError("equals should return false when one array is null");
        }
        if (java.util.Arrays.equals(b, a)) {
            throw new AssertionError("equals should return false when one array is null");
        }
    }

    private static void testEqualsDifferentLengths() {
        int[] a = {1, 2, 3};
        int[] b = {1, 2};
        if (java.util.Arrays.equals(a, b)) {
            throw new AssertionError("equals should return false when arrays have different lengths");
        }
    }

    private static void testEqualsEqualArrays() {
        int[] a = {5, 10, 15};
        int[] b = {5, 10, 15};
        if (!java.util.Arrays.equals(a, b)) {
            throw new AssertionError("equals should return true for arrays with same elements");
        }
    }

    private static void testEqualsDifferentArrays() {
        int[] a = {5, 10, 15};
        int[] b = {5, 10, 16};
        if (java.util.Arrays.equals(a, b)) {
            throw new AssertionError("equals should return false for arrays with different elements");
        }
    }
}
