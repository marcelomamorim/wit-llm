/*
 * @test
 * @summary Test java.util.Arrays.equals(short[], short[]) method
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
        short[] arr = {1, 2, 3};
        if (!java.util.Arrays.equals(arr, arr)) {
            throw new AssertionError("equals should return true for same reference");
        }
    }

    private static void testBothNull() {
        short[] a = null;
        short[] b = null;
        if (!java.util.Arrays.equals(a, b)) {
            throw new AssertionError("equals should return true for both null arrays");
        }
    }

    private static void testOneNull() {
        short[] a = null;
        short[] b = {1, 2, 3};
        if (java.util.Arrays.equals(a, b)) {
            throw new AssertionError("equals should return false if one array is null");
        }
        if (java.util.Arrays.equals(b, a)) {
            throw new AssertionError("equals should return false if one array is null");
        }
    }

    private static void testDifferentLengths() {
        short[] a = {1, 2, 3};
        short[] b = {1, 2};
        if (java.util.Arrays.equals(a, b)) {
            throw new AssertionError("equals should return false for arrays of different lengths");
        }
    }

    private static void testEqualArrays() {
        short[] a = {5, 10, 15};
        short[] b = {5, 10, 15};
        if (!java.util.Arrays.equals(a, b)) {
            throw new AssertionError("equals should return true for equal arrays");
        }
    }

    private static void testDifferentArrays() {
        short[] a = {5, 10, 15};
        short[] b = {5, 10, 16};
        if (java.util.Arrays.equals(a, b)) {
            throw new AssertionError("equals should return false for arrays with different elements");
        }
    }
}
