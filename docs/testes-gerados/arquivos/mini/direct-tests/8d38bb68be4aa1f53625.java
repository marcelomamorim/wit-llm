/*
 * @test
 * @summary Test java.util.Arrays.equals(byte[], byte[])
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
        byte[] arr = {1, 2, 3};
        if (!java.util.Arrays.equals(arr, arr)) {
            throw new AssertionError("equals should return true for same array reference");
        }
    }

    private static void testBothNull() {
        byte[] a = null;
        byte[] b = null;
        if (!java.util.Arrays.equals(a, b)) {
            throw new AssertionError("equals should return true for both null arrays");
        }
    }

    private static void testOneNull() {
        byte[] a = null;
        byte[] b = {1, 2, 3};
        if (java.util.Arrays.equals(a, b)) {
            throw new AssertionError("equals should return false if one array is null");
        }
        if (java.util.Arrays.equals(b, a)) {
            throw new AssertionError("equals should return false if one array is null");
        }
    }

    private static void testDifferentLengths() {
        byte[] a = {1, 2, 3};
        byte[] b = {1, 2};
        if (java.util.Arrays.equals(a, b)) {
            throw new AssertionError("equals should return false for arrays of different lengths");
        }
    }

    private static void testEqualArrays() {
        byte[] a = {1, 2, 3, 4, 5};
        byte[] b = {1, 2, 3, 4, 5};
        if (!java.util.Arrays.equals(a, b)) {
            throw new AssertionError("equals should return true for equal arrays");
        }
    }

    private static void testDifferentArrays() {
        byte[] a = {1, 2, 3, 4, 5};
        byte[] b = {1, 2, 3, 4, 6};
        if (java.util.Arrays.equals(a, b)) {
            throw new AssertionError("equals should return false for arrays with different elements");
        }
    }
}
