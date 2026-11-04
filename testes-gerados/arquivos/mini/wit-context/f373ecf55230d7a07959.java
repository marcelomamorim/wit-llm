/*
 * @test
 * @summary Deterministic tests for java.util.Arrays.equals(short[], short[])
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
        short[] arr = {1, 2, 3};
        if (!java.util.Arrays.equals(arr, arr)) {
            throw new AssertionError("equals should return true when both arrays are the same instance");
        }
    }

    private static void testEqualsBothNull() {
        short[] a = null;
        short[] b = null;
        if (!java.util.Arrays.equals(a, b)) {
            throw new AssertionError("equals should return true when both arrays are null");
        }
    }

    private static void testEqualsOneNull() {
        short[] a = null;
        short[] b = {1, 2, 3};
        if (java.util.Arrays.equals(a, b)) {
            throw new AssertionError("equals should return false when one array is null");
        }
        if (java.util.Arrays.equals(b, a)) {
            throw new AssertionError("equals should return false when one array is null");
        }
    }

    private static void testEqualsDifferentLengths() {
        short[] a = {1, 2, 3};
        short[] b = {1, 2};
        if (java.util.Arrays.equals(a, b)) {
            throw new AssertionError("equals should return false when arrays have different lengths");
        }
    }

    private static void testEqualsEqualArrays() {
        short[] a = {5, 10, 15};
        short[] b = {5, 10, 15};
        if (!java.util.Arrays.equals(a, b)) {
            throw new AssertionError("equals should return true for arrays with same elements");
        }
    }

    private static void testEqualsDifferentArrays() {
        short[] a = {5, 10, 15};
        short[] b = {5, 10, 16};
        if (java.util.Arrays.equals(a, b)) {
            throw new AssertionError("equals should return false for arrays with different elements");
        }
    }
}
