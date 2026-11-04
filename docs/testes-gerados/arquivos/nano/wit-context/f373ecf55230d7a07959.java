/*
 * @test
 * @summary Tests for java.util.Arrays.equals(short[], short[])
 * @run main ArraysWitupTest
 */

import java.util.Arrays;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testEqualsWithNulls();
        testEqualsWithIdenticalArrays();
        testEqualsWithDifferentLengths();
        testEqualsWithDifferentContents();
    }

    static void testEqualsWithNulls() {
        short[] a = null;
        short[] b = null;
        boolean result = Arrays.equals(a, b);
        if (result != true) {
            throw new AssertionError("Expected true when both arrays are null");
        }
        short[] c = {1, 2, 3};
        result = Arrays.equals(c, null);
        if (result != false) {
            throw new AssertionError("Expected false when second array is null");
        }
        result = Arrays.equals(null, c);
        if (result != false) {
            throw new AssertionError("Expected false when first array is null");
        }
    }

    static void testEqualsWithIdenticalArrays() {
        short[] a = {1, 2, 3};
        short[] b = {1, 2, 3};
        boolean result = Arrays.equals(a, b);
        if (result != true) {
            throw new AssertionError("Expected true for identical arrays");
        }
    }

    static void testEqualsWithDifferentLengths() {
        short[] a = {1, 2, 3};
        short[] b = {1, 2};
        boolean result = Arrays.equals(a, b);
        if (result != false) {
            throw new AssertionError("Expected false for arrays of different lengths");
        }
    }

    static void testEqualsWithDifferentContents() {
        short[] a = {1, 2, 3};
        short[] b = {1, 2, 4};
        boolean result = Arrays.equals(a, b);
        if (result != false) {
            throw new AssertionError("Expected false for arrays with different contents");
        }
    }
}
