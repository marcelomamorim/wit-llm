/*
 * @test
 * @summary Test java.util.Arrays.equals(char[], char[])
 * @run main ArraysWitupTest
 */
public class ArraysWitupTest {
    public static void main(String[] args) {
        testSameReference();
        testBothNull();
        testOneNull();
        testDifferentLengths();
        testEqualArrays();
        testDifferentArrays();
    }

    private static void testSameReference() {
        char[] arr = { 'a', 'b', 'c' };
        if (!java.util.Arrays.equals(arr, arr)) {
            throw new AssertionError("equals should return true for same reference");
        }
    }

    private static void testBothNull() {
        char[] a = null;
        char[] b = null;
        if (!java.util.Arrays.equals(a, b)) {
            throw new AssertionError("equals should return true for both null arrays");
        }
    }

    private static void testOneNull() {
        char[] a = null;
        char[] b = { 'a' };
        if (java.util.Arrays.equals(a, b)) {
            throw new AssertionError("equals should return false if one array is null");
        }
        if (java.util.Arrays.equals(b, a)) {
            throw new AssertionError("equals should return false if one array is null");
        }
    }

    private static void testDifferentLengths() {
        char[] a = { 'a', 'b' };
        char[] b = { 'a', 'b', 'c' };
        if (java.util.Arrays.equals(a, b)) {
            throw new AssertionError("equals should return false for arrays of different lengths");
        }
    }

    private static void testEqualArrays() {
        char[] a = { 'x', 'y', 'z' };
        char[] b = { 'x', 'y', 'z' };
        if (!java.util.Arrays.equals(a, b)) {
            throw new AssertionError("equals should return true for equal arrays");
        }
    }

    private static void testDifferentArrays() {
        char[] a = { 'x', 'y', 'z' };
        char[] b = { 'x', 'y', 'a' };
        if (java.util.Arrays.equals(a, b)) {
            throw new AssertionError("equals should return false for arrays with different content");
        }
    }
}
