/*
 * @test
 * @summary Test java.util.Arrays.equals(int[], int[])
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
        int[] arr = {1, 2, 3};
        if (!java.util.Arrays.equals(arr, arr)) {
            throw new AssertionError("equals should return true for same reference");
        }
    }

    private static void testBothNull() {
        int[] a = null;
        int[] b = null;
        if (!java.util.Arrays.equals(a, b)) {
            throw new AssertionError("equals should return true for both null arrays");
        }
    }

    private static void testOneNull() {
        int[] a = null;
        int[] b = {1, 2, 3};
        if (java.util.Arrays.equals(a, b)) {
            throw new AssertionError("equals should return false if one array is null");
        }
        if (java.util.Arrays.equals(b, a)) {
            throw new AssertionError("equals should return false if one array is null");
        }
    }

    private static void testDifferentLengths() {
        int[] a = {1, 2, 3};
        int[] b = {1, 2};
        if (java.util.Arrays.equals(a, b)) {
            throw new AssertionError("equals should return false for arrays of different lengths");
        }
    }

    private static void testEqualArrays() {
        int[] a = {1, 2, 3, 4, 5};
        int[] b = {1, 2, 3, 4, 5};
        if (!java.util.Arrays.equals(a, b)) {
            throw new AssertionError("equals should return true for equal arrays");
        }
    }

    private static void testDifferentArrays() {
        int[] a = {1, 2, 3, 4, 5};
        int[] b = {1, 2, 0, 4, 5};
        if (java.util.Arrays.equals(a, b)) {
            throw new AssertionError("equals should return false for arrays with different elements");
        }
    }
}
