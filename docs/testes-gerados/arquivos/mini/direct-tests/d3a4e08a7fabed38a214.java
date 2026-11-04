/*
 * @test
 * @summary Test java.util.Arrays.equals(boolean[], boolean[])
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
        boolean[] arr = {true, false, true};
        if (!java.util.Arrays.equals(arr, arr)) {
            throw new AssertionError("equals should return true for same reference");
        }
    }

    private static void testBothNull() {
        boolean[] a = null;
        boolean[] b = null;
        if (!java.util.Arrays.equals(a, b)) {
            throw new AssertionError("equals should return true for both null arrays");
        }
    }

    private static void testOneNull() {
        boolean[] a = null;
        boolean[] b = {true};
        if (java.util.Arrays.equals(a, b)) {
            throw new AssertionError("equals should return false if one array is null");
        }
        if (java.util.Arrays.equals(b, a)) {
            throw new AssertionError("equals should return false if one array is null");
        }
    }

    private static void testDifferentLengths() {
        boolean[] a = {true, false};
        boolean[] b = {true, false, true};
        if (java.util.Arrays.equals(a, b)) {
            throw new AssertionError("equals should return false for arrays of different lengths");
        }
    }

    private static void testEqualArrays() {
        boolean[] a = {true, false, true};
        boolean[] b = {true, false, true};
        if (!java.util.Arrays.equals(a, b)) {
            throw new AssertionError("equals should return true for equal arrays");
        }
    }

    private static void testDifferentArrays() {
        boolean[] a = {true, false, true};
        boolean[] b = {true, true, true};
        if (java.util.Arrays.equals(a, b)) {
            throw new AssertionError("equals should return false for arrays with different elements");
        }
    }
}
