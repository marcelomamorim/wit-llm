/*
 * @test
 * @summary Deterministic tests for java.util.Arrays.equals(char[], char[])
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
        System.out.println("All tests passed.");
    }

    private static void testEqualsSameArray() {
        char[] arr = { 'a', 'b', 'c' };
        if (!java.util.Arrays.equals(arr, arr)) {
            throw new AssertionError("equals should return true when both arrays are the same instance");
        }
    }

    private static void testEqualsBothNull() {
        char[] a = null;
        char[] b = null;
        if (!java.util.Arrays.equals(a, b)) {
            throw new AssertionError("equals should return true when both arrays are null");
        }
    }

    private static void testEqualsOneNull() {
        char[] a = null;
        char[] b = { 'a' };
        if (java.util.Arrays.equals(a, b)) {
            throw new AssertionError("equals should return false when one array is null");
        }
        if (java.util.Arrays.equals(b, a)) {
            throw new AssertionError("equals should return false when one array is null");
        }
    }

    private static void testEqualsDifferentLengths() {
        char[] a = { 'a', 'b' };
        char[] b = { 'a', 'b', 'c' };
        if (java.util.Arrays.equals(a, b)) {
            throw new AssertionError("equals should return false when arrays have different lengths");
        }
    }

    private static void testEqualsEqualArrays() {
        char[] a = { 'x', 'y', 'z' };
        char[] b = { 'x', 'y', 'z' };
        if (!java.util.Arrays.equals(a, b)) {
            throw new AssertionError("equals should return true for arrays with same content");
        }
    }

    private static void testEqualsDifferentArrays() {
        char[] a = { 'x', 'y', 'z' };
        char[] b = { 'x', 'y', 'a' };
        if (java.util.Arrays.equals(a, b)) {
            throw new AssertionError("equals should return false for arrays with different content");
        }
    }
}
