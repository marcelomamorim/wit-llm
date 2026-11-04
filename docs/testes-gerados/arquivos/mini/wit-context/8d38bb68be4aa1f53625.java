/*
 * @test
 * @summary Deterministic tests for java.util.Arrays.equals(byte[], byte[])
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
        byte[] arr = {1, 2, 3};
        if (!java.util.Arrays.equals(arr, arr)) {
            throw new AssertionError("equals should return true when comparing the same array instance");
        }
    }

    private static void testEqualsBothNull() {
        byte[] a = null;
        byte[] b = null;
        if (!java.util.Arrays.equals(a, b)) {
            throw new AssertionError("equals should return true when both arrays are null");
        }
    }

    private static void testEqualsOneNull() {
        byte[] a = null;
        byte[] b = {1, 2, 3};
        if (java.util.Arrays.equals(a, b)) {
            throw new AssertionError("equals should return false when one array is null");
        }
        if (java.util.Arrays.equals(b, a)) {
            throw new AssertionError("equals should return false when one array is null");
        }
    }

    private static void testEqualsDifferentLengths() {
        byte[] a = {1, 2, 3};
        byte[] b = {1, 2};
        if (java.util.Arrays.equals(a, b)) {
            throw new AssertionError("equals should return false when arrays have different lengths");
        }
    }

    private static void testEqualsEqualArrays() {
        byte[] a = {10, 20, 30, 40};
        byte[] b = {10, 20, 30, 40};
        if (!java.util.Arrays.equals(a, b)) {
            throw new AssertionError("equals should return true for arrays with same content");
        }
    }

    private static void testEqualsDifferentArrays() {
        byte[] a = {10, 20, 30, 40};
        byte[] b = {10, 20, 31, 40};
        if (java.util.Arrays.equals(a, b)) {
            throw new AssertionError("equals should return false for arrays with different content");
        }
    }
}
