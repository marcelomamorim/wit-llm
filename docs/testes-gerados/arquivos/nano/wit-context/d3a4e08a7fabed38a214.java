/*
 * @test
 * @summary Test for java.util.Arrays.equals(boolean[], boolean[])
 * @run main ArraysWitupTest
 */

import java.util.Arrays;

public class ArraysWitupTest {

    public static void main(String[] args) {
        testEqualsWithNullArrays();
        testEqualsWithSameArray();
        testEqualsWithDifferentLengths();
        testEqualsWithDifferentContents();
        testEqualsWithIdenticalArrays();
    }

    static void testEqualsWithNullArrays() {
        boolean[] a = null;
        boolean[] b = {true, false};
        boolean[] c = {true, false};
        assert !Arrays.equals(a, b) : "Expected false when first array is null";
        assert !Arrays.equals(b, a) : "Expected false when second array is null";
        assert !Arrays.equals(a, a) : "Expected false when both arrays are null";
    }

    static void testEqualsWithSameArray() {
        boolean[] a = {true, false, true};
        assert Arrays.equals(a, a) : "Array should be equal to itself";
    }

    static void testEqualsWithDifferentLengths() {
        boolean[] a = {true, false};
        boolean[] b = {true, false, true};
        assert !Arrays.equals(a, b) : "Arrays of different lengths should not be equal";
    }

    static void testEqualsWithDifferentContents() {
        boolean[] a = {true, false, true};
        boolean[] b = {true, true, false};
        assert !Arrays.equals(a, b) : "Arrays with different contents should not be equal";
    }

    static void testEqualsWithIdenticalArrays() {
        boolean[] a = {true, false, true};
        boolean[] b = {true, false, true};
        assert Arrays.equals(a, b) : "Identical arrays should be equal";
    }
}
